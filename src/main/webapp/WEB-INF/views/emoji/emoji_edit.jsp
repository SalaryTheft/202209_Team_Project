<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>이모티콘 등록</title>
    <meta name="viewport" content="width=device-width, user-scale=no, initial-scale=1.0">

    <!-- JS -->
    <script src="/static/js/emoji.js"></script>

    <!-- CSS -->
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/board_modal.css" rel="stylesheet" type="text/css">

</head>
<body>
<form method="post">
    <div class="body">
        <h3 class="title">게시판 설정</h3>
        <hr>
        <h3>이모티콘 이름</h3>
        <p>이모티콘 이름을 정해주세요.</p>
        <input type="text" name="emojiName" required minlength="4" maxlength="50" value="">
        <h3>이모티콘 설명</h3>
        <p>이모티콘에 대한 간단한 설명을 작성해주세요.</p>
        <input type="text" name="emojiDesc" required minlength="4" maxlength="50" value="">
        <h3>이모티콘 업로드</h3>
        <p>이미지 파일만 업로드 가능합니다.</p>
        <input type="file" name="emojiDataRaw" accept="image/*">
        <input type="hidden" name="emojiData" value="">
        <div class="footer">
            <input type="button" name="cancel" class="cancel" value="취소" onclick="parent.location.reload()">
            <input type="submit" value="저장">
        </div>
    </div>
</form>
</body>
</html>