<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible">
    <title>회원가입</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- JS -->
    <script src="/static/js/signup_4.js"></script>

    <!-- CSS -->
    <link href="/static/css/signup.css" rel="stylesheet" type="text/css">

    <!-- ION-ICON -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>

<body>
<div class="body">
    <h3>환영합니다</h3>
    <span class="terms">
        회원가입이 완료되었습니다.
    </span>
    <form>
        <input type="submit" value="확인">
    </form>
</div>
</body>

</html>