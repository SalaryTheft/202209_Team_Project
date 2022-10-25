<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>이메일 주소 변경</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <!-- JS -->
    <script src="/static/js/settings_email_2.js"></script>

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
<form method="post" action="/settings/email/3">
    <div class="body">
        <h3 class="title">이메일 주소 변경</h3>
        <hr>
        <h3>인증번호 입력</h3>
        <p>이메일로 발송된 인증번호 8자리(대문자 및 숫자)를 입력해주세요.</p>
        <p>인증번호는 10분간 유효합니다.</p>
        <input type="text" name="authCode" placeholder="인증번호" minlength="8" maxlength="8" required>
        <div class="footer">
            <input type="button" name="cancel" class="cancel" value="취소" onclick="parent.location.reload()">
            <input type="submit" value="다음">
        </div>
    </div>
</form>
</body>
</html>
