
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" type="image/x-icon"
	href="${path}/resources/assets/img/favicon.ico" />
<!-- Font Awesome icons (free version)-->
<script src="https://use.fontawesome.com/releases/v5.13.0/js/all.js"
	crossorigin="anonymous"></script>
<!-- Google fonts-->
<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700"
	rel="stylesheet" type="text/css" />
<link
	href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic"
	rel="stylesheet" type="text/css" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="${path }/resources/css/styles.css" rel="stylesheet" />
</head>
<body>
	<header class="masthead text-black text-center">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
						<div class="card-header text-white"
							style="background-color: #01DFD7;">게시글 등록</div>
						<div class="card-body">

							<form id="boardform">

								<div class="form-group">
									<label>작성자</label> <input type="text" class="form-control"
										name="writer" value="${detail.writer}">
								</div>

								<div class="form-group">
									<label>제목</label> <input type="text" class="form-control"
										name="title" value="${detail.title}">
								</div>

								<div class="form-group">
									<label>내용</label>
									<textarea class="form-control" rows="5" name="content"
										placeholder="게시글 내용을 입력하세요.">${detail.content}</textarea>
								</div>
								<div class="form-group">
									<c:if test="${not empty detail.filepath}">
										<img src="${path}/board/board/image?path=${detail.filepath}"
											alt="첨부 이미지" width="300" height="300">
									</c:if>
								</div>
								&nbsp;&nbsp; <a class="btn form-control"
									href="${path}/board/boardfile"
									style="cursor: pointer; margin-top: 0; height: 40px; color: white; background-color: #01A9DB; border: 0px solid #388E3C; opacity: 0.8">목록</a>
							</form>


						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
</body>
</html>