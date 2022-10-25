<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible">
    <title>회원가입</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- JS -->
    <script src="/static/js/signup_2.js"></script>

    <!-- CSS -->
    <link href="/static/css/signup.css" rel="stylesheet" type="text/css"
          media="screen">

    <!-- ION-ICON -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
    <c:if test="${not empty error}">
        <script>alert("${error}");</script>
    </c:if>
</head>

<body>
<div class="body">
    <h3>인증번호 입력</h3>
    <span class="terms">
        이메일로 발송된 인증번호 8자리(대문자 및 숫자)를 입력해주세요.
    </span>
    <span class="terms">
        인증번호는 10분간 유효합니다.
    </span>
    <form method="post" action="/signup/3">
        <input type="text" name="authCode" placeholder="인증번호" minlength="8" maxlength="8" required>
        <span id="authCodeHint"></span>
        <input type="submit" value="계속하기">
    </form>
</div>
</body>

</html>