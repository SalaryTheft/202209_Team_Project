<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="side about">
    <div class="about-title">
        <h3>회원 소개</h3>
    </div>
    <div class="about-desc">
        ${profileUser.userDesc eq null ? "안녕하세요 " += profileUser.userNickname += "입니다." : profileUser.userDesc}
        <br>
        <span class="since-date">
            <ion-icon name="calendar-number-outline"></ion-icon>
            ${profileUser.userRegDate} 가입
        </span>
    </div>
    <hr>
    <div class="about-stats">
        <span class="subscriber-count">
            <span class="number">${profileUser.userPostCount}</span><br>
            게시글
        </span>
        <span class="post-count">
            <span class="number">${profileUser.userCommentCount}</span><br>
            댓글
        </span>
    </div>
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
    <button class="top-btn" onclick="window.scrollTo(0, 0)">맨위로</button>
</div>