package com.example.board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

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
}
