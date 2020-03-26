<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style.css">
<style type="text/css">table { width: 80%; }</style></head><body>
<!-- <script type="text/javascript">
		opener.document.location.href="이동경로"
		self.close();
</script>
 -->

<table border="1">
	<caption><h2>게시판 상세내역</h2></caption>
	<tr><td width="50">번호</td><td>${board.num}</td></tr>
	<tr><td>제목</td><td>${board.subject}</td></tr>
	<tr><td>작성자</td><td>${board.writer}</td></tr>
	<tr><td>작성일</td><td>${board.reg_date}</td></tr>
	<tr><td>조회수</td><td>${board.readcount}</td></tr>
	<tr><td>IP</td><td>${board.ip}</td></tr>
	<tr><td>이메일</td><td>${board.email}</td></tr>
	<tr><td>내용</td><td><pre>${board.content}</pre></td></tr>
	<tr><td colspan="2">
	<input type="button" value="수정" 
	            onclick="location.href='updateForm.do?num=${board.num}&pageNum=${pageNum}'">

	<input type="button" value="답변작성" 
	            onclick="location.href='writeForm.do?num=${board.num}&pageNum=${pageNum}'">
    <input type="button" value="삭제 " onclick="window.open('deleteForm.do?num=${board.num}&pageNum=${pageNum}','삭제','width=430,height=500,location=no,status=no,scrollbars=yes')">
	<%-- <input type="button" value="삭제"
	            onclick="location.href='deleteForm.do?num=${board.num}&pageNum=${pageNum}'"> --%>
	<input type="button" value="목록"
			    onclick="location.href='list.do?pageNum=${pageNum}'"></td></tr>
</table></body></html>