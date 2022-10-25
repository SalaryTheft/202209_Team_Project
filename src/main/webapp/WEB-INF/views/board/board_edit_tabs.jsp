<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>게시판 탭 수정</title>
    <meta name="viewport" content="width=device-width, user-scale=no, initial-scale=1.0">

    <!-- JS -->
    <script src="/static/js/board_edit_tabs.js"></script>

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
            /*background-position: center;*/
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
        <h3 class="title">게시판 탭 설정</h3>
        <hr>
        <p>탭 설정은 수정 즉시 반영됩니다.</p>
        <div class="tabs-edit">
            <c:forEach var="tabName" items="${tabs}">
                <div class="tab" draggable="true">
                    <span class="tab-name">${tabName}</span>
                    <span class="tab-move-up">위로</span>
                    <span class="tab-move-down">아래로</span>
                    <span class="tab-edit">수정</span>
                    <span class="tab-delete">삭제</span>
                </div>
            </c:forEach>
        </div>
        <div class="tab-add">
            <div class="tab-add-btn">
                <span id="add_tab_btn">추가</span>
            </div>
        </div>
        <div class="footer">
            <input type="button" name="cancel" class="cancel" value="취소" onclick="parent.location.reload()">
            <input type="button" class="done" value="완료" onclick="parent.location.reload()">
        </div>
    </div>
</form>
</body>
</html>
