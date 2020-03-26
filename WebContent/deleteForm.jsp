<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title></head><body>


<h2>삭제하려면 암호를 입력하세요</h2>
<form action="deletePro.do">
    <input type="hidden" name="pageNum" value="${pageNum }">
    <input type="hidden" name="num" value="${num }">
    암호 : <input type="text" name="passwd"><p>
    <!-- <input type="submit" value="확인"> -->
    <%-- <input type = "submit" value="확인" onclick="(list.do?pageNum=${pageNum})"> --%>
    <input type = "submit" value = "확인" onclick="Location()">
    <!-- <input type = "submit" value="확인" onclick=opener.document.location.href="list.do?pageNum=${pageNum}"
        self.close();> -->
    <!-- 덧글은 지우면 리스트창으로 돌아가는데 본글을 지우면 리스트창으로 돌아가지만 삭제되기 전 화면이 떠서 새로고침을 눌러줘야함 -->
</form>
<script type="text/javascript">
    function Location(){
        opener.document.location.href="list.do?pageNum=${pageNum}"
        self.close();
        }
</script>

</body>
</html>