<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible">
    <title>로그인</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- JS -->
    <script src="/static/js/login.js"></script>

    <!-- CSS -->
    <link href="/static/css/login.css" rel="stylesheet" type="text/css">

    <!-- ION-ICON -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>

<body>
<div class="body">
    <form class="login-form" action="/login" method="post">
        <h3 class="login-title">로그인</h3>
        <span class="terms">회원가입시 <a href="#">사용자 약관</a>과
                <a href="#">개인정보 처리방침</a>에
                동의하는 것으로 간주합니다.
            </span>
        <div class="btn">
            <a class="google-auth-btn" id="googleLoginBtn" href="#">
                <ion-icon name="logo-google"></ion-icon>
                Google 계정으로 계속하기
            </a>
        </div>
        <p class="divider">
            <span>또는</span>
        </p>
        <input type="text" name="userEmail" placeholder="이메일 또는 닉네임">
        <input type="password" name="userPw" placeholder="비밀번호">
        <input type="submit" value="로그인">

        <span class="login-hint">
                <a href="/reset_password">닉네임</a> 혹은 <a href="/reset_password">비밀번호</a>를 잊어버리셨나요?
            </span>
        <span class="login-hint">
                AlliO가 처음이신가요? <a href="/signup">회원가입</a>
            </span>
    </form>
</div>
</body>

</html>