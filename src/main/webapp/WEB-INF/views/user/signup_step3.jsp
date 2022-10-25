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
    <script src="/static/js/signup_3.js"></script>

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
    <h3>사용자 정보</h3>
    <span class="terms">
        다른 사용자에게 보여줄 닉네임과 로그인에 사용할 비밀번호를 입력해주세요.
    </span>
    <span class="terms">
        닉네임을 포함한 회원정보는 회원가입 후 언제든지 변경할 수 있습니다.
    </span>
    <form method="post" action="/signup/4">
        <input type="text" name="userNickname" placeholder="닉네임">
        <span id="nicknameHint"></span>
        <input type="password" name="userPw" placeholder="비밀번호">
        <span id="passwordHint"></span>
        <input type="password" name="userPwConfirm" placeholder="비밀번호 확인">
        <span id="passwordConfirmHint"></span>
        <input class="disabled" type="submit" value="계속하기">
    </form>
</div>
</body>

</html>