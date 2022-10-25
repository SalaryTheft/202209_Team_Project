<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script src="/static/js/index.js"></script>
    <script src="/static/js/board_dom.js"></script>

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
        <div class="post-area">
            <div class="post-order">
                <form id="post_order_form">
                    <input type="radio" name="order" value="best" id="best" checked>
                    <label for="best" title="7일 이내 작성된 게시글 중 추천이 높은 순서">
                        <ion-icon name="flame"></ion-icon>
                        인기순
                    </label>
                    <input type="radio" name="order" value="new" id="recent">
                    <label for="recent" title="전체 게시판의 최신 게시글">
                        <ion-icon name="sparkles-outline"></ion-icon>
                        최신순
                    </label>
                    <input type="radio" name="order" value="sub" id="sub">
                    <label for="sub" title="구독한 게시판의 최신 게시글">
                        <ion-icon name="newspaper-outline"></ion-icon>
                        구독
                    </label>
                </form>
            </div>
            <div class="post-list"></div>
            <div class="post-list-end">
                <span class="post-list-hint">게시글을 불러오는 중입니다.</span>
                <span class="post-list-more">더 불러오기</span>
            </div>
        </div>
        <div class="side-list">
            <c:if test="${bestBoardList.size() gt 0}">
                <div class="side rank">
                    <div class="rank-title">
                        <h2>주간 인기 게시판</h2>
                    </div>
                    <div class="rank-list">
                        <c:forEach begin="0" end="${bestBoardList.size() - 1}" var="i">
                            <div class="board" data-board-id="${bestBoardList.get(i).boardId}">
                                <span class="board-rank">${i+1}</span>
                                <div class="board-icon">
                                    <img src="/r/${bestBoardList.get(i).boardId}/icon" alt="">
                                </div>
                                <span class="board-name">
                                        ${bestBoardList.get(i).boardName}
                                </span>
                                <span class="board-stats">
                                <span>${bestBoardList.get(i).boardPostCount}<span>새 게시글</span></span>
                            </span>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
            <div class="side intro">
                <h3>홈 화면</h3>
                <p>당신만의 AlliO 홈 화면입니다. 마음에 드는 게시판을 구독하거나 새 게시판을 만들어보세요.</p>
                <hr>
                <a class="wide-btn" id="new_board_modal_btn">게시판 만들기</a>
                <a class="wide-btn" href="/r/list">전체 게시판 목록</a>
            </div>
            <div class="side terms">
                <div>
                    <div>
                        <a href="#">이용약관</a><br>
                        <a href="#">개인정보처리방침</a>
                    </div>
                    <div>
                        <a href="#">고객센터</a><br>
                        <a>&nbsp;</a>
                    </div>
                </div>
                <hr>
                <a href="#">AlliO와 Team MVC에 대하여</a>
                <hr>
                <p>Team MVC © 2022. 모든 권리 보유</p>
            </div>
            <div class="top-btn-wrapper">
                <button class="top-btn" onclick="window.scrollTo(0, 0);">맨위로</button>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/include/modal.jsp" %>
</body>

</html>