<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>오류</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- CSS -->
    <link href="/static/css/error.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="body">
    <div class="error">
        <h3>페이지가... 없어?!</h3>
        <img src="/static/img/404.png" alt="">
        <h1>404</h1>
        <p>페이지 찾을 수 없음</p>
        <a href="/">홈으로</a>
        <a onclick="history.back()">이전으로</a>
    </div>
</div>
</body>
</html>