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
    <script src="/static/js/signup.js"></script>

    <!-- CSS -->
    <link href="/static/css/signup.css" rel="stylesheet" type="text/css"
          media="screen">

    <!-- ION-ICON -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
    <c:if test="${not empty email}">
        <script>alert("가입된 회원이 없어 회원가입 페이지로 이동합니다.");</script>
    </c:if>
    <c:if test="${not empty error}">
        <script>alert("${error}");</script>
    </c:if>
</head>

<body>
<div class="body">
    <h3>회원가입</h3>
    <span class="terms">계속하시면 <a href="#">사용자 약관</a>과
            <a href="#">개인정보 처리방침</a>에
            동의하는 것으로 간주합니다.
        </span>
    <a class="google-auth-btn" id="googleLoginBtn" href="#">
        <ion-icon name="logo-google"></ion-icon>
        Google 계정으로 계속하기
    </a>
    <p class="divider">
        <span>또는</span>
    </p>
    <form method="post" action="/signup/2">
        <input type="email" name="userEmail" placeholder="이메일" minlength="5" maxlength="40"
               value="${email}${signupUser.userEmail}">
        <span id="emailHint"></span>
        <input class="disabled" type="submit" value="계속하기">
    </form>

    <span class="login-hint">이미 계정이 있으신가요? <a href="#">로그인</a></span>
</div>
</body>

</html>