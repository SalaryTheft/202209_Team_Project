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
    <script src="/static/js/settings_email_1.js"></script>

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
<form method="post" action="/settings/email/2">
    <div class="body">
        <h3 class="title">이메일 주소 변경</h3>
        <hr>
        <h3>새 이메일 주소</h3>
        <p>변경할 이메일 주소를 입력해주세요.</p>
        <p>인증번호가 확인되면 계정의 이메일 주소가 변경됩니다.</p>
        <input type="email" name="userNewEmail" required>
        <span class="email-hint"></span>
        <div class="footer">
            <input type="button" name="cancel" class="cancel" value="취소" onclick="parent.location.reload()">
            <input type="submit" value="다음">
        </div>
    </div>
</form>
</body>
</html>
