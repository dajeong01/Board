<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" type="image/x-icon" href="${path}/resources/assets/img/favicon.ico" />
        <!-- Font Awesome icons (free version)-->
        <script src="https://use.fontawesome.com/releases/v5.13.0/js/all.js" crossorigin="anonymous"></script>
        <!-- Google fonts-->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="${path}/resources/css/styles.css" rel="stylesheet" />
</head>
<body>
<div class="container">
<div class="row">
<div class="col-lg-2">
</div>
<div class="col-lg-8">
<div class="panel-body" style="margin-top: 40px;">
<h2 class="page-header"><span style="color: #ff52a0;">
 <a href="${path}/board/boardWriter" class="btn btn-primary" style="float: right;">글쓰기</a>
                  <br>
                </span>
            </h2>
            <table class="table table-hover" style="margin-top: 20px;">
          <thead>
          <tr style="background-color: #A901DB; color: white;  border: 0px solid #f78f24;">
          <th>번호</th>
<th>제목</th>
<th>작성자</th>
<th>첨부파일유무</th>
<th>등록일자</th>
</tr>
</thead>
<c:forEach var="boardList" items="${boardList}">
<tr style="color:#ff5xa0;">
<td>${boardList.idx}</td>
<td><a href="${path}/board/boardDetail/${boardList.idx}">${boardList.title}</a></td>
<td>${boardList.writer}</td>
<td>${boardList.file_existence}</td>
<td>${boardList.regdate}</td>
</tr>
</c:forEach>

          </table>
         
         
          </div>
      </div>
  </div>
  </div>

</body>
</html>