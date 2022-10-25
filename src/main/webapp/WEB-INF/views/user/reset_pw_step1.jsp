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
    <span class="terms">가입 시 사용한 이메일 주소를 입력해주세요. 본인 확인을 위한 인증번호가 발송됩니다.</span>
    <form method="post" action="/reset_password/2">
        <input type="email" name="userEmail" placeholder="이메일" minlength="5" maxlength="40" required>
        <input type="submit" value="계속하기">
    </form>
</div>
</body>

</html>