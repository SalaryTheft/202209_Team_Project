<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>게시판 관리자 수정</title>
    <meta name="viewport" content="width=device-width, user-scale=no, initial-scale=1.0">

    <!-- JS -->
    <script src="/static/js/board_edit_mods.js"></script>

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
        <h3 class="title">게시판 관리자 설정</h3>
        <hr>
        <p>관리자 설정은 수정 즉시 반영됩니다.</p>
        <div class="mod-list">
            <c:forEach items="${boardMods}" var="m">
                <div class="mod-item">
                    <div class="mod-profile">
                        <div class="mod-profile-img">
                            <c:if test="${m.user.userIcon ne null}">
                                <img src="${m.user.userIcon}" alt=" ">
                            </c:if>
                        </div>
                        <a class="mod-name">${m.user.userNickname}</a>
                    </div>
                    <div class="mod-action">
                        <span class="mod-role">${m.modRole eq 'MOD' ? '주관리자' : '부관리자'}</span>
                        <c:if test="${m.modRole eq 'MOD'}">
                            <span class="mod-transfer">위임</span>
                        </c:if>
                        <c:if test="${m.modRole eq 'SUBMOD'}">
                            <span class="mod-delete">삭제</span>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
            <div class="mod-item">
                <div class="mod-action">
                    <span class="mod-add" id="add_mod_btn">추가</span>
                </div>
            </div>
        </div>
    </div>
    <div class="footer">
        <input type="button" name="cancel" class="cancel" value="취소" onclick="parent.location.reload()">
        <input type="button" class="done" value="완료" onclick="parent.location.reload()">
    </div>
</form>
</body>
</html>
