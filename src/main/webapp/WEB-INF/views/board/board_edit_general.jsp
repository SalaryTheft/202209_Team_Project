<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>게시판 정보 수정</title>
    <meta name="viewport" content="width=device-width, user-scale=no, initial-scale=1.0">

    <!-- JS -->
    <script src="/static/js/board_edit.js"></script>

    <!-- CSS -->
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/board_modal.css" rel="stylesheet" type="text/css">
    <style>
        <c:if test="${board.boardColor ne null}">
        :root {
            --highlight-color: ${board.boardColor};
        }

        </c:if>
        <c:if test="${board.boardIcon ne null}">
        .board-icon {
            background-image: url(${board.boardIcon});
        }

        </c:if>
        <c:if test="${board.boardBackground ne null}">
        .board-top > .board-background {
            background-image: url(${board.boardBackground});
            background-size: cover;
            background-repeat: no-repeat;
            width: 100%;
            height: 0;
            padding-top: min(66.64%, 200px);
        }

        </c:if>
    </style>
</head>
<body>
<form method="post">
    <div class="body">
        <h3 class="title">게시판 설정</h3>
        <hr>
        <h3>게시판 설명</h3>
        <p>게시판에 대한 설명입니다.</p>
        <input type="text" name="boardDesc" required minlength="4" maxlength="50" value="${board.boardDesc}">
        <h3>테마 색상</h3>
        <p>게시판 테마에 사용될 색상입니다.</p>
        <div class="color-picker-wrap">
            <input type="color" name="boardColor" required value="${board.boardColor ne null ? board.boardColor : '#0079D3'}">
            <span id="color_code">${board.boardColor ne null ? board.boardColor : '#0079D3'}</span>
        </div>
        <h3>아이콘 · 배경 이미지</h3>
        <input type="file" name="boardIconRaw" accept="image/*" style="display: none;">
        <input type="hidden" name="boardIcon" value="${board.boardIcon}">
        <input type="file" name="boardBackgroundRaw" accept="image/*" style="display: none;">
        <input type="hidden" name="boardBackground" value="${board.boardBackground}">
        <p>아래 미리보기 화면 중 변경할 부분을 클릭하세요.</p>
        <div class="board-top">
            <div class="board-background reverse-blur"></div>
            <div class="board-info">
                <div class="board-icon reverse-blur"></div>
                <div class="board-detail">
                    <h1>${board.boardName}</h1>
                    <p>/r/<span id="board_id">${board.boardId}</span></p>
                </div>
            </div>
            <div class="board-tabs">
                <a class="active">전체</a>
                <c:forEach items="${boardTabs}" var="tab">
                    <a>${tab}</a>
                </c:forEach>
                <a href="/r/${board.boardId}/edit/tabs" id="edit_tab">탭 편집</a>
            </div>
        </div>
        <div class="footer">
            <input type="button" name="cancel" class="cancel" value="취소" onclick="parent.location.reload()">
            <input type="submit" value="저장">
        </div>
    </div>
</form>
</body>
</html>
