<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>${post.postTitle} - AlliO</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="board_id" content="${board.boardId}">
    <c:if test="${user ne null}">
        <meta name="user_nickname" content="${user.userNickname}">
    </c:if>

    <!-- JS -->
    <script src="/static/js/common.js"></script>
    <script src="/static/js/board_dom.js"></script>
    <script src="/static/js/post_view.js"></script>

    <!-- CSS -->
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/common.css" rel="stylesheet" type="text/css">
    <link href="/static/css/board.css" rel="stylesheet" type="text/css">
    <c:if test="${board.boardColor ne null}">
        <style>
            :root {
                --highlight-color: ${board.boardColor};
            }
        </style>
    </c:if>

    <!-- ION-ICON -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

    <!-- QUILL EDITOR -->
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
    <link href="/static/css/quill.css" rel="stylesheet">
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp" %>
<div class="board-top">
    <div class="board-background">
        <c:if test="${board.boardColor ne null}">
            <img src="/r/${board.boardId}/background" alt=" ">
        </c:if>
    </div>
    <div class="board-info">
        <div class="board-icon">
            <img src="/r/${board.boardId}/icon" alt="">
        </div>
        <div class="board-detail">
            <h1>${board.boardName}</h1>
            <p>r/<span id="board_id">${board.boardId}</span></p>
        </div>
        <a class="subscribe-btn ${isSubscribed ne null && isSubscribed eq true ? 'active' : ''}"
           onclick="subscribe(this)">${isSubscribed ne null && isSubscribed eq true ? '구독중' : '구독'}</a>
    </div>
    <div class="board-tabs">
        <a href="/r/${board.boardId}">전체</a>
        <c:forEach items="${boardTabs}" var="tab">
            <a href="/r/${board.boardId}?tab=${tab}" class="${post.postTab eq tab ? 'active' : ''}">${tab}</a>
        </c:forEach>
    </div>
</div>
<div class="body">
    <div class="content-area">
        <div class="post-area">
            <div class="post-list"></div>
            <div class="post-comments" id="comments">
                <form id="comment_form" method="post">
                    <c:if test="${user ne null}">
                        <p><span class="user-nickname">${user.userNickname}</span>으로 댓글 작성</p>
                        <input type="hidden" name="commentContent">
                        <div id="editor" style="min-height: 100px"></div>
                        <input type="submit" value="작성">
                        <script>
                            let editor = new Quill('#editor', {
                                modules: {
                                    toolbar: [
                                        ['bold', 'italic', 'underline', 'strike'],
                                        ['clean'],
                                        ['link']
                                    ]
                                },
                                placeholder: '말은 자신을 비추는 거울입니다.',
                                theme: 'snow'
                            });
                            document.querySelector("#editor").addEventListener("DOMSubtreeModified", function () {
                                let input = document.querySelector("#comment_form input[name='commentContent']");
                                input.value = document.querySelector("#comment_form > #editor > div.ql-editor").innerHTML;
                            });
                        </script>
                    </c:if>
                    <c:if test="${user eq null}">
                        <p><span class="underline" onclick="modal('/login')">로그인</span> 후 댓글 작성이 가능합니다.</p>
                    </c:if>
                </form>
                <form id="comment_order_form">
                    <select name="order">
                        <option value="asc">등록 순</option>
                        <option value="desc">최신 순</option>
                    </select>
                </form>
                <div class="comment-list"></div>
                <div class="comment-list-end">
                    <span class="comment-list-hint"></span>
                    <span class="comment-list-more">더 불러오기</span>
                </div>
            </div>
        </div>
        <div class="side-list">
            <%@include file="/WEB-INF/views/board/board_side.jsp" %>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/include/modal.jsp" %>
</body>
</html>