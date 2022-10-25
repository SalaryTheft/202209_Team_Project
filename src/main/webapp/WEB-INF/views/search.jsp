<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>AlliO</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <c:if test="${user ne null}">
        <meta name="user_nickname" content="${user.userNickname}">
    </c:if>
    <!-- JS -->
    <script src="/static/js/common.js"></script>
    <script src="/static/js/search.js"></script>

    <!-- CSS -->
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/common.css" rel="stylesheet" type="text/css">
    <link href="/static/css/board.css" rel="stylesheet" type="text/css">

    <!-- ION-ICON -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>

<body>
<%@include file="/WEB-INF/views/include/header.jsp" %>
<div class="body">
    <div class="content-area">
        <div class="search-area">
            <div class="search-category">
                <a href="/search?category=posts&keyword=${paging.keyword}"
                   class="${param.category eq null || param.category == 'posts' ? 'active' : ''}">게시글</a>
                <a href="/search?category=comments&keyword=${paging.keyword}"
                   class="${param.category == 'comments' ? 'active' : ''}">댓글</a>
                <a href="/search?category=boards&keyword=${paging.keyword}"
                   class="${param.category == 'boards' ? 'active' : ''}">게시판</a>
                <a href="/search?category=users&keyword=${paging.keyword}"
                   class="${param.category == 'users' ? 'active' : ''}">사용자</a>
            </div>
            <div class="search-options">
                <c:if test="${param.category eq null || param.category == 'posts' || param.category == 'comments'}">
                    <label>
                        <select name="range" id="range">
                            <option value="all" ${param.range eq null || param.range eq 'all' ? 'selected' : ''}>
                                전체 기간
                            </option>
                            <option value="1d" ${param.range eq '1d' ? 'selected' : ''}>1일</option>
                            <option value="3d" ${param.range eq '3d' ? 'selected' : ''}>3일</option>
                            <option value="1w" ${param.range eq '1w' ? 'selected' : ''}>1주</option>
                            <option value="1m" ${param.range eq '1m' ? 'selected' : ''}>1달</option>
                            <option value="3m" ${param.range eq '3m' ? 'selected' : ''}>3달</option>
                            <option value="6m" ${param.range eq '6m' ? 'selected' : ''}>6달</option>
                            <option value="1y ${param.range eq '1y' ? 'selected' : ''}">1년</option>
                        </select>
                    </label>
                    <label>
                        <select name="type" id="type" style="width: auto">
                            <option value="title" ${param.type eq null || param.type eq 'title' ? 'selected' : ''}>제목</option>
                            <option value="content" ${param.type eq 'content' ? 'selected' : ''}>내용</option>
                            <option value="writer" ${param.type eq 'writer' ? 'selected' : ''}>작성자</option>
                        </select>
                    </label>
                </c:if>
                <span>
                검색 결과
                <span><fmt:formatNumber value="${paging.totalRows}" type="number" pattern="#,###"/></span>개
            </span>
            </div>
            <c:if test="${param.category eq null || param.category eq 'posts'}">
                <div class="search-result-list">
                    <c:forEach items="${postList}" var="post">
                        <div class="search-result">
                            <div class="result-top">
                                <a href="/r/${post.board.boardId}">
                                    <img src="/r/${post.board.boardId}/icon" alt="">
                                    <span>${post.board.boardName}</span>
                                </a>
                                &nbsp;·&nbsp;
                                <a href="/u/${post.user.userNickname}">${post.user.userNickname}</a>
                                &nbsp;·&nbsp;
                                <span title="${post.postTimestamp}">${post.postTimeStampShort}</span>
                            </div>
                            <a href="/r/${post.boardId}/post/${post.postUuid}">${post.postTitle}</a>
                            <span style="background-color: ${post.board.boardColor}">${post.postTab}</span>
                            <p class="result-content">${post.postContentTextShort}</p>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${param.category eq 'comments'}">
                <div class="search-result-list">
                    <c:forEach items="${commentList}" var="comment">
                        <div class="search-result">
                            <div class="result-top">
                                <a href="/r/${comment.board.boardId}">
                                    <img src="/r/${comment.board.boardId}/icon" alt="">
                                    <span>${comment.board.boardName}</span>
                                </a>
                                &nbsp;·&nbsp;
                                <a href="/u/${comment.user.userNickname}">${comment.user.userNickname}</a>
                                &nbsp;·&nbsp;
                                <span title="${comment.commentTimestamp}">${comment.commentTimestampShort}</span>
                            </div>
                            <a href="/r/${comment.board.boardId}/post/${comment.postUuid}">
                                <ion-icon name="return-down-forward-outline"></ion-icon>
                                ${comment.commentContentTextShort}
                            </a>
                            <p class="result-content">${comment.post.postTitle}</p>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${param.category eq 'boards'}">
                <div class="search-result-list">
                    <c:forEach items="${boardList}" var="board">
                        <div class="search-result">
                            <a class="search-board-title" href="/r/${board.boardId}">
                                <img src="/r/${board.boardId}/icon" alt="">${board.boardName}
                            </a>
                            <p class="result-content">${board.boardDesc}</p>
                            <div class="result-top">
                                <span>구독자 ${board.boardSubCount}</span>
                                &nbsp;·&nbsp;
                                <span>게시글 ${board.boardPostCount}</span>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${param.category eq 'users'}">
                <div class="search-result-list">
                    <c:forEach items="${userList}" var="user">
                        <div class="search-result">
                            <a class="search-user-nickname" href="/u/${user.userNickname}">
                                <img src="/u/${user.userNickname}/icon" alt="">${user.userNickname}
                            </a>
                            <p class="result-content">${user.userDesc}</p>
                            <div class="result-top">
                                <span>글 ${user.userPostCount}</span>
                                &nbsp;·&nbsp;
                                <span>댓글 ${user.userCommentCount}</span>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <div class="paging">
                <c:if test="${paging.page gt 1}">
                    <a href="/search?category=${param.category}&keyword=${paging.keyword}&page=1"
                       data-page="1">처음</a>
                    <a href="/search?category=${param.category}&keyword=${paging.keyword}&page=${paging.page - 1}"
                       data-page="${paging.page - 1}">이전</a>
                </c:if>
                <c:forEach begin="${paging.firstPage}" end="${paging.lastPage}" var="i">
                    <a href="/search?category=${param.category}&keyword=${paging.keyword}&page=${i}"
                       data-page="${i}" class="${paging.page eq i ? 'active' : ''}">${i}</a>
                </c:forEach>
                <c:if test="${paging.page lt search.totalPages}">
                    <a href="/search?category=${param.category}&keyword=${paging.keyword}&page=${paging.page + 1}"
                       data-page="${paging.page + 1}">다음</a>
                    <a href="/search?category=${param.category}&keyword=${paging.keyword}&page=${paging.totalPages}"
                       data-page="${paging.totalPages}">끝</a>
                </c:if>
            </div>
        </div>
        <div class="side-list">
            <%@include file="/WEB-INF/views/board/board_list_side.jsp" %>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/include/modal.jsp" %>
</body>

</html>