<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판</title>
<link rel="stylesheet" href="style.css" type="text/css">
<style type="text/css">
table {
	width: 100%;
}
</style>
</head>
<body>
	<h2>게시판</h2>
	<form action="searchPro.do?pageNum=${pageNum}" method="post">
		<tr>
			<td></td>
			<select name="sellBuy" id="sellBuy" onchange="searchSellbuy">
				<option value="all">전체</option>
				<option value="sell">팝니다</option>
				<option value="buy">삽니다</option>
			</select>
			<!-- ${search} 는 검색창을 통하여 검색한 값을 유지하기 위함. -->
			<input type="text" name="search" value="${search }"></td>
			<td><input type="submit" value="검색"></td>
		</tr>
	</form>
	<table>
		<tr>
			<td><a href="writeForm.do">글쓰기</a></td>
		</tr>
	</table>
	<table>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>이메일</th>
			<th>IP</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
		<c:if test="${totCnt > 0 }">
			<c:forEach var="board" items="${list }">
				<tr>
					<td>${startNum }</td>
					<td class="left" width=200><c:if
							test="${board.readcount > 20}">
							<img src='images/hot.gif'>
						</c:if> <c:if test="${board.re_level > 0}">
							<img src='images/level.gif' width="${board.re_level*10}">
							<%--       	board.re_level  ${board.re_level} --%>
							<img src='images/re.gif'>
						</c:if> <a href='content.do?num=${board.num}&pageNum=${currentPage}'>
							${board.subject}</a></td>
					<td>${board.writer}</td>
					<td><a href="mailto:${board.email}">${board.email}</a></td>
					<td>${board.ip}</td>
					<td>${board.reg_date}</td>
					<td>${board.readcount}</td>
				</tr>
				<c:set var="startNum" value="${startNum - 1 }" />
			</c:forEach>
		</c:if>
		<c:if test="${totCnt == 0 }">
			<tr>
				<td colspan=7>데이터가 없네</td>
			</tr>
		</c:if>
	</table>

	<!-- 하위의 SearchYN 의 경우 검색 여부에 따라 링크 주소가 다르기 떄문에 If 조건 삽입. -->
	<div style="text-align: center;">
		<c:if test="${startPage > blockSize }">
			<c:if test="${SearchYN == 0}">
				<a href='list.do?pageNum=${startPage-blockSize}'>[이전]</a>
			</c:if>
			<c:if test="${SearchYN == 1}">
				<a
					href='searchPro.do?pageNum=${startPage-blockSize}&search=${search}'>[이전]</a>
			</c:if>
		</c:if>
		<c:forEach var="i" begin="${startPage}" end="${endPage}">
			<c:if test="${SearchYN == 0}">
				<a href='list.do?pageNum=${i}'>[${i}]</a>
			</c:if>
			<c:if test="${SearchYN == 1}">
				<a href='searchPro.do?pageNum=${i}&search=${search}'>[${i}]</a>
			</c:if>
		</c:forEach>
		<c:if test="${endPage < pageCnt }">

			<a href='list.do?pageNum=${startPage+blockSize}'>[다음]</a>

		</c:if>
	</div>
</body>
</html>