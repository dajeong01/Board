
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
										name="writer">

								</div>



								<div class="form-group">

									<label>제목</label> <input type="text" class="form-control"
										name="title">

								</div>

								<div class="form-group">

									<label>내용</label>

									<textarea class="form-control" rows="5" name="content"
										placeholder="게시글 내용을 입력하세요."></textarea>

								</div>

								<div class="form-group">

									<label>파일첨부</label> <input type="file" name="fileName"
										style="margin-right: 750px; width: 225px;">

								</div>

								<button type="button" id="submitBtn" value="등록"
									class="btn form-control"
									style="background-color: #0404B4; margin-top: 0; height: 40px; color: white; border: 0px solid #f78f24; opacity: 0.8">등록</button>

								&nbsp;&nbsp; <a class="btn form-control"
									href="${path}/board/boardfile"
									style="cursor: pointer; margin-top: 0; height: 40px; color: white; background-color: #01A9DB; border: 0px solid #388E3C; opacity: 0.8">취소</a>

							</form>

						</div>

					</div>

				</div>

			</div>

		</div>

	</header>

</body>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

		$("#submitBtn").click(function() {

			var formData = new FormData($("#boardform")[0]);

			$.ajax({

				type : "POST",

				url : "${path}/board/write",

				data : formData,

				processData : false,

				contentType : false,

				success : function(response) {

					//alert(‎response);

					if (response === "success") {

						alert("게시글 등록 성공하였습니다.");

						window.location.href = "${path}/board/boardfile";

					} else {

						alert("등록 실패하였습니다. 다시 시도해 주세요");

					}

				},

				error : function(xhr, status, error) {

					alert("서버 오류로 인해 등록 실패하였습니다");

				}

			})

		});

	});
</script>

</html>