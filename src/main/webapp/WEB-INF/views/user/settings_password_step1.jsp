<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>비밀번호 변경</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <!-- JS -->
    <script src="/static/js/settings_password.js"></script>

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
        <h3 class="title">비밀번호 변경</h3>
        <hr>
        <h3>현재 비밀번호</h3>
        <p>지금 사용 중인 비밀번호를 입력해주세요.</p>
        <input type="password" placeholder="현재 비밀번호" name="userOldPw" maxlength="20" required>
        <h3>변경할 비밀번호</h3>
        <p>새로운 비밀번호를 8자 이상 20자 이하로 입력해주세요.</p>
        <input type="password" placeholder="새 비밀번호" name="userPw" minlength="8" maxlength="20" required>
        <span class="new-pw-hint"></span>
        <h3>변경할 비밀번호 확인</h3>
        <p>새로운 비밀번호를 한번 더 입력해주세요.</p>
        <input type="password" placeholder="새 비밀번호 확인" name="userPwConfirm" minlength="8" maxlength="20" required>
        <span class="new-pw-confirm-hint"></span>
        <div class="footer">
            <input type="button" name="cancel" class="cancel" value="취소" onclick="parent.location.reload()">
            <input type="submit" value="변경">
        </div>
    </div>
</form>
</body>
</html>
