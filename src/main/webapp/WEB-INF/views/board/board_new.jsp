<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>새 게시판 만들기</title>
    <meta name="viewport" content="width=device-width, user-scale=no, initial-scale=1.0">

    <!-- JS -->
    <script src="/static/js/board_new.js"></script>

    <!-- CSS -->
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/board_modal.css" rel="stylesheet" type="text/css">
</head>
<body>
<c:if test="${user ne null}">
<form method="post">
    <div class="body">
        <h3 class="title">새 게시판 만들기</h3>
        <hr>
        <h3>게시판 이름</h3>
        <p>게시판의 주제를 대표하는 이름입니다.<br>
            게시판 이름은 게시판이 생성된 이후 변경할 수 없습니다.</p>
        <input type="text" name="boardName" required minlength="1" maxlength="15">
        <h3>게시판 아이디</h3>
        <p>게시판 주소<span class="dimm">(예를 들어 /r/cats)</span>에 사용될 아이디입니다.<br>
            게시판 아이디는 게시판이 생성된 이후 변경할 수 없습니다.</p>
        <input type="text" name="boardId" required minlength="3" maxlength="20" pattern="[a-z0-9]+" title="영문과 숫자만 사용할 수 있습니다.">
        <h3>게시판 설명</h3>
        <p>게시판에 대한 설명입니다. <br>
        </p>
        <input type="text" name="boardDesc" required minlength="4" maxlength="50">
    </div>
    <div class="footer">
        <input type="button" name="cancel" class="cancel" value="취소">
        <input type="submit" value="만들기">
    </div>
</form>
</c:if>
<c:if test="${user eq null}">
    <div class="body">
        <h3 style="text-align: center; line-height: 450px">로그인 후 이용해주세요</h3>
    </div>
</c:if>
</body>
</html>
