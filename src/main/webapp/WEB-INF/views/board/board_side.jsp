<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="side about">
    <div class="about-title">
        <h3>게시판 소개</h3>
    </div>
    <div class="about-desc">
        ${board.boardDesc eq null ? board.boardName += " 게시판입니다." : board.boardDesc}
        <br>
        <span class="since-date">
            <ion-icon name="calendar-number-outline"></ion-icon>
            ${board.boardRegDate} 시작
        </span>
    </div>
    <hr>
    <div class="about-stats">
        <span class="subscriber-count">
            <span class="number">${board.boardSubCount}</span><br>
            구독자
        </span>
        <span class="post-count">
            <span class="number">${board.boardPostCount}</span><br>
            게시글
        </span>
    </div>
</div>
<img style="display: none; width: 100%; border: 1px solid var(--border-color); border-radius: 4px; margin-bottom: 5px;"
     onload="this.style.display = 'inline';"
     src="https://ad.allio.ga/ad?adtype=${post ne null ? 'post' : 'board'}&target=${post ne null ? post.boardId += '/' += post.postUuid : board.boardId}">
<c:if test="${boardMods.size() ne 0}">
    <div class="side mod">
        <div class="mod-title">
            <h3>게시판 관리자</h3>
        </div>
        <div class="mod-list">
            <c:forEach items="${boardMods}" var="m">
                <div class="mod-item">
                    <div class="mod-icon" onclick="location.href='/u/${m.user.userNickname}'">
                        <img src="/u/${m.user.userNickname}/icon" alt="">
                    </div>
                    <div class="mod-info">
                        <a class="mod-name" href="/u/${m.user.userNickname}">${m.user.userNickname}</a>
                        <span class="mod-role">${m.modRole eq 'MOD' ? '주관리자' : '부관리자'}</span>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${user.userUuid eq boardMods.get(0).userUuid}">
                <a class="wide-btn" onclick="modal_large('/r/${board.boardId}/edit/general')">게시판 설정</a>
                <a class="wide-btn" onclick="modal_large('/r/${board.boardId}/edit/tabs')">탭 설정</a>
                <a class="wide-btn" onclick="modal_large('/r/${board.boardId}/edit/mods')">관리자 설정</a>
            </c:if>
        </div>
    </div>
</c:if>
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
    <button class="top-btn" onclick="window.scrollTo(0, 0)">맨위로</button>
</div>