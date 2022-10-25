<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>이메일 주소 변경</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <!-- CSS -->
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/settings_modal.css" rel="stylesheet" type="text/css">

    <!-- ION-ICON -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
    <c:if test="${not empty error}">
        <script>alert("${error}");</script>
    </c:if>
</head>
<body>
<form method="post">
    <div class="body">
        <h3 class="title">회원 탈퇴</h3>
        <hr>
        <h3>회원 탈퇴 시 확인사항</h3>
        <p>회원 탈퇴 시 모든 정보가 삭제되며, 복구가 불가능합니다.</p>
        <p>단 회원님이 작성한 게시글, 댓글 등의 정보는 삭제되지 않습니다.</p>
        <input type="password" name="userPw" maxlength="20" required>
        <div class="footer">
            <input type="button" name="cancel" class="cancel" value="취소" onclick="parent.location.reload()">
            <input type="submit" value="탈퇴">
        </div>
    </div>
</form>
</body>
</html>
