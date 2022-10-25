<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible">
    <title>비밀번호 초기화</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- JS -->
    <script src="/static/js/reset_pw_2.js"></script>

    <!-- CSS -->
    <link href="/static/css/signup.css" rel="stylesheet" type="text/css" media="screen">

    <!-- ION-ICON -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
    <c:if test="${not empty error}">
        <script>alert("${error}");</script>
    </c:if>
</head>

<body>
<div class="body">
    <h3>비밀번호 초기화</h3>
    <span class="terms">이메일 주소로 발송된 인증번호(대문자 및 숫자 8자리)와 변경할 비밀번호를 입력하세요.</span>
    <form method="post" action="/reset_password/3">
        <input type="text" name="authCode" placeholder="인증번호" minlength="8" maxlength="8" required>
        <input type="password" name="userPw" placeholder="비밀번호" minlength="8" maxlength="20" required>
        <span class="pw-hint"></span>
        <input type="password" name="userPwConfirm" placeholder="비밀번호 확인" minlength="8" maxlength="20" required>
        <span class="pw-confirm-hint"></span>
        <input type="submit" value="계속하기">
    </form>
</div>
</body>

</html>