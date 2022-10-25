<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>${profileUser.userNickname} - AlliO</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <!-- JS -->
    <script src="/static/js/common.js"></script>

    <!-- CSS -->
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/common.css" rel="stylesheet" type="text/css">
    <link href="/static/css/board.css" rel="stylesheet" type="text/css">
    <c:if test="${user.userColor ne null}">
        <style>
            :root {
                --highlight-color: ${user.userColor};
            }
        </style>
    </c:if>

    <!-- ION-ICON -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp" %>
<div class="board-top">
    <div class="board-background">
        <c:if test="${profileUser.userBackground ne null}">
            <img src="/u/${profileUser.userNickname}/background" alt=" ">
        </c:if>
    </div>
    <div class="board-info">
        <div class="board-icon">
            <img src="/u/${profileUser.userNickname}/icon" alt="">
        </div>
        <div class="board-detail">
            <h1>${profileUser.userNickname}</h1>
            <p>u/<span id="board_id">${profileUser.userNickname}</span></p>
        </div>
    </div>
    <div class="board-tabs">
        <a href="/u/${profileUser.userNickname}?tab=posts"
           class="${param.tab eq null || param.tab eq 'posts' || param.tab eq '' ? 'active' : ''}">게시물</a>
        <a href="/u/${profileUser.userNickname}?tab=comments" class="${param.tab eq 'comments' ? 'active' : ''}">댓글</a>
    </div>
</div>
<div class="body">
    <div class="content-area">
        <div class="header-search-result">
            <c:if test="${param.tab eq null || param.tab eq 'posts' || param.tab eq ''}">
                <%-- 해당 회원이 작성한 글 보여주기(페이징 처리) --%>
                <c:if test="${postList.size() eq 0}">
                    <div class="empty" style="width: 100%; padding: 20px 0; text-align: center">
                        <p>작성한 게시글이 없습니다.</p>
                    </div>
                </c:if>
                <c:forEach items="${postList}" var="post">
                    <a class="row" href="/r/${post.boardId}/post/${post.postUuid}">
                        <span class="row-content">${post.postTitle} [${post.postCommentCount}]</span>
                        <span class="row-info">
                        <span class="row-board-info">[${post.board.boardName} | ${post.postTab}]</span>
                        <span class="row-timestamp"
                              title="${post.postTimestamp}">${post.postTimeStampShort}</span>
                    </span>
                    </a>
                </c:forEach>
            </c:if>
            <c:if test="${param.tab eq 'comments'}">
                <%-- 해당 회원이 작성한 댓글 보여주기(페이징 처리) --%>
                <c:if test="${commentList.size() eq 0}">
                    <div class="empty" style="width: 100%; padding: 20px 0; text-align: center">
                        <p>작성한 댓글이 없습니다.</p>
                    </div>
                </c:if>
                <c:forEach items="${commentList}" var="comment">
                    <a class="row"
                       href="/r/${comment.post.boardId}/post/${comment.postUuid}?comment_timestamp=${comment.commentTimestampOffset}">
                        <span class="row-content">${comment.commentContentText eq null || comment.commentContentText eq '' ? '미리보기 없음' : comment.commentContentText}</span>
                        <span class="row-info">
                        <span class="row-board-info">[${comment.board.boardName} | ${comment.post.postTab}] ${comment.post.postTitle}</span>
                        <span class="row-timestamp"
                              title="${comment.commentTimestamp}">${comment.commentTimestampShort}</span>
                    </span>
                    </a>
                </c:forEach>
            </c:if>
            <%-- 페이징 처리 --%>
            <div class="paging">
                <c:if test="${search.page gt 1}">
                    <a href="/u/${profileUser.userNickname}?tab=${param.tab ne null ? param.tab : "posts"}&page=1">처음</a>
                    <a href="/u/${profileUser.userNickname}?tab=${param.tab ne null ? param.tab : "posts"}&page=${search.page - 1}">이전</a>
                </c:if>
                <c:forEach begin="${search.firstPage}" end="${search.lastPage}" var="i">
                    <a href="/u/${profileUser.userNickname}?tab=${param.tab ne null ? param.tab : "posts"}&page=${i}"
                       class="${search.page eq i ? 'active' : ''}">${i}</a>
                </c:forEach>
                <c:if test="${search.page lt search.totalPages}">
                    <a href="/u/${profileUser.userNickname}?tab=${param.tab ne null ? param.tab : "posts"}&page=${search.page + 1}">다음</a>
                    <a href="/u/${profileUser.userNickname}?tab=${param.tab ne null ? param.tab : "posts"}&page=${search.totalPages}">끝</a>
                </c:if>
            </div>
        </div>
        <div class="side-list">
            <%@include file="/WEB-INF/views/user/profile_side.jsp" %>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/include/modal.jsp" %>
</body>
</html>