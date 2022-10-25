<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<script>
    alert("비밀번호가 초기화되었습니다. 로그인 화면으로 이동합니다.");
    location.href = "/login";
</script>