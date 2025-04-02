package com.example.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/board")
public class BoardController {

	// 윈도우
	private static final String UPLOAD_DIR = "C:/upload/";

	// 리눅스
	@Autowired
	private EntityManager entityManager;

	@GetMapping("/boardfile")
	public String boardList(Model model) {
		// SQL 쿼리 문자열 정리
		String sql = "SELECT idx, title, content, writer, regdate, "
				+ "CASE WHEN fileName IS NOT NULL THEN 'Y' ELSE 'N' END AS file_existence " + "FROM tbl_board "
				+ "ORDER BY idx DESC";

		// 네이티브 쿼리 실행
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> resultList = query.getResultList();

		// 결과 담을 리스트 생성
		List<Map<String, Object>> boardList = new ArrayList<>();

		// 결과 가공
		for (Object[] row : resultList) {
			Map<String, Object> map = new HashMap<>();
			map.put("idx", row[0]);
			map.put("title", row[1]);
			map.put("content", row[2]);
			map.put("writer", row[3]);
			map.put("regdate", row[4]);
			map.put("file_existence", row[5]);
			boardList.add(map);
		}

		// 뷰로 데이터 전달
		model.addAttribute("boardList", boardList);

		// board 디렉토리에 있는 boardlist.jsp로 이동
		return "board/boardlist";
	}

	@GetMapping("boardWriter")
	public String boardWriter() {
		return "board/writer";
	}

	@PostMapping("/write")
	@ResponseBody
	@Transactional
	public String save(@RequestParam("writer") String writer, @RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam(value = "fileName", required = false) MultipartFile file) {
		String fileName = null;
		String filePath = null;

//		// 윈도우
//		File uploadDir = new File(UPLOAD_DIR);
//		if (!uploadDir.exists()) {
//			uploadDir.mkdirs();
//		}

		try {
			createDirectoryWithPermissions(UPLOAD_DIR);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}

		if (file != null && !file.isEmpty()) {
			try {
				String originalFileName = file.getOriginalFilename();
				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				fileName = UUID.randomUUID().toString() + fileExtension;
				filePath = UPLOAD_DIR + fileName;

				File dest = new File(filePath);
				file.transferTo(dest);
			} catch (IOException e) {
				e.printStackTrace();
				return "error";
			}
		}

		String sql = "insert into tbl_board (title, content, writer, regdate, fileName, filepath)"
				+ "values(:title, :content, :writer, DATE_FORMAT(NOW(), '%Y-%m-%d'), :fileName, :filepath)";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("title", title);
		query.setParameter("writer", writer);
		query.setParameter("content", content);
		query.setParameter("fileName", fileName);
		query.setParameter("filepath", filePath);
		query.executeUpdate();
		return "success";
	}

	// 상세보기
	@GetMapping("/boardDetail/{idx}")
	public String boardDetail(@PathVariable("idx") Long idx, Model model) {
		String sql = " SELECT idx, title, content, writer, regdate, fileName, filepath  "
				+ " FROM tbl_board WHERE idx = :idx ";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("idx", idx);

		Object[] row = (Object[]) query.getSingleResult();
		Map<String, Object> detail = new HashMap<>();

		detail.put("idx", row[0]);
		detail.put("title", row[1]);
		detail.put("content", row[2]);
		detail.put("writer", row[3]);
		detail.put("regdate", row[4]);
		detail.put("fileName", row[5]);
		detail.put("filepath", row[6]);

		model.addAttribute("detail", detail);
		return "board/detail";
	}

	@GetMapping("/board/image")
	@ResponseBody
	public ResponseEntity<Resource> getBoardImage(@RequestParam("path") String filePath) {
		try {
			Path imagePath = Paths.get(filePath).normalize();
			Resource resource = new UrlResource(imagePath.toUri());
			if (!resource.exists()) {
				return ResponseEntity.notFound().build();
			}
			String contentType = Files.probeContentType(imagePath);
			if (contentType == null) {
				contentType = "application/octet-stream";
			}

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)) // 동적으로 Content-Type 설정
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	private static void createDirectoryWithPermissions(String dirPath) throws IOException {
		Path path = Paths.get(dirPath);
		if (Files.notExists(path)) {
			Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr-xr-x");
			Files.createDirectories(path, PosixFilePermissions.asFileAttribute(permissions));
		}
		try {
			changeOwnerAndGroup(dirPath, "tomcat", "tomcat");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void changeOwnerAndGroup(String dirPath, String owner, String group) throws IOException {
		String command = String.format("chown -R %s:%s %s", owner, group, dirPath);
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("bash", "-c", command);
		processBuilder.inheritIO();

		Process process = processBuilder.start();
		try {
			int exitCode = process.waitFor();
			if (exitCode != 0) {
				throw new IOException("Failed to change owner and group for directory: " + dirPath);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt(); // 추가 권장 사항
		}
	}
}
