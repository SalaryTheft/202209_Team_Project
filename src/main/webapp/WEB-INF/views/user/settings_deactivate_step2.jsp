<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<script>
    alert("회원 탈퇴가 완료되었습니다. 이용해주셔서 감사합니다.");
    parent.location.href = "/";
</script>