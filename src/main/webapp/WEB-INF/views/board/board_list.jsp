<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>게시판 목록 - AlliO</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="board_id" content="${board.boardId}">
    <c:if test="${user ne null}">
        <meta name="user_nickname" content="${user.userNickname}">
    </c:if>

    <!-- JS -->
    <script src="/static/js/common.js"></script>
    <script src="/static/js/board_list.js"></script>

    <!-- CSS -->
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/common.css" rel="stylesheet" type="text/css">
    <link href="/static/css/board_list.css" rel="stylesheet" type="text/css">

    <!-- ION-ICON -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp" %>
<div class="body">
    <div class="content-area">
        <div class="board-list-area">
            <div class="board-list-index">
                <h1>전체 게시판 목록</h1>
                <p>색인 바로가기</p>
                <div>
                    <c:forEach items="${indexList}" var="index">
                        <a href="#${index.matches('[0-9]+') ? '#' : index}" class="board-list-index-item">${index}</a>
                    </c:forEach>
                </div>
            </div>
            <c:forEach items="${boardList}" var="board" varStatus="status">
                <c:if test="${status.index eq 0 || board.boardNameIndex ne boardList[status.index-1].boardNameIndex}">
                    <h2 id="${board.boardNameIndex eq '#' ? 'digit' : board.boardNameIndex}">${board.boardNameIndex}</h2>
                    <hr>
                </c:if>
                <a class="board" data-board-id="${board.boardId}" href="/r/${board.boardId}">
                    <img src="/r/${board.boardId}/icon" alt="">
                    <div class="board-name">
                        <h3>${board.boardName}</h3>
                        <p>r/${board.boardId}</p>
                    </div>
                    <div class="board-stats">
                        <span>${board.boardSubCount} <span>구독자</span></span>
                        <span>${board.boardPostCount} <span>게시글</span></span>
                    </div>
                    <span class="board-subscribe-btn${board.boardSubscribed ? ' active' : ''}"
                          onclick="subscribe(this)">${board.boardSubscribed ? '구독중' : '구독'}</span>
                </a>
            </c:forEach>
        </div>
        <div class="side-list">
            <%@include file="/WEB-INF/views/board/board_list_side.jsp" %>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/include/modal.jsp" %>
</body>
</html>