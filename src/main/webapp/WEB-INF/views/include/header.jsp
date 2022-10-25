<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header">
    <div class="logo-area">
        <h2 style="font-style:italic; font-weight: 500;"><a href="/">AlliO</a></h2>
    </div>
    <div class="header-search-area">
        <form action="/search" method="get">
            <ion-icon name="search-outline"></ion-icon>
            <input type="text" name="keyword" placeholder="통합검색" value="${paging ne null ? paging.keyword : ''}">
            <div class="search-modal">
                <!-- 인기검색어 // 자동완성 -->
            </div>
        </form>
    </div>
    <div class="buttons-area">
        <div style="${user ne null ? 'display:none' : ''}">
            <a class="round-btn extrawide" id="header_login_btn" href="/login">로그인</a>
            <a class="round-btn extrawide filled" id="header_signup_btn" href="/signup">회원가입</a>
        </div>
        <div class="noti-btn">
            <ion-icon name="notifications-outline"></ion-icon>
            <span class="noti-count">0</span>
        </div>
        <div class="noti-modal slider closed">
            <embed src="/noti">
        </div>
        <div class="profile-btn">
            <ion-icon name="person-outline"></ion-icon>
            <span class="profile-name">${user ne null ? user.userNickname : '손님'}</span>
            <ion-icon name="chevron-down-outline"></ion-icon>
        </div>
        <div class="profile-modal slider closed">
            <c:if test="${user ne null}">
                <div class="menu-item category">
                    <ion-icon name="person-circle-outline"></ion-icon>
                    내 정보
                </div>
                <a class="menu-item submenu" href="/u/${user.userNickname}">프로필</a>
                <a class="menu-item submenu" href="/settings">사용자 설정</a>
                <div class="menu-divider"></div>
            </c:if>
            <a class="menu-item menu" href="#">
                <ion-icon name="megaphone-outline"></ion-icon>
                고객센터
            </a>
            <a class="menu-item menu" href="#">
                <ion-icon name="happy-outline"></ion-icon>
                이용약관
            </a>
            <a class="menu-item menu" href="#">
                <ion-icon name="glasses-outline"></ion-icon>
                개인정보처리방침
            </a>
            <a class="menu-item menu" href="#">
                <ion-icon name="code-slash-outline"></ion-icon>
                소개
            </a>
            <div class="menu-divider"></div>
            <c:if test="${user ne null}">
                <a class="menu-item menu" href="/logout">
                    <ion-icon name="log-out-outline"></ion-icon>
                    로그아웃
                </a>
            </c:if>
            <c:if test="${user eq null}">
                <a class="menu-item menu" onclick="modal('/login')">
                    <ion-icon name="log-in-outline"></ion-icon>
                    로그인
                </a>
                <a class="menu-item menu" onclick="modal('/signup')">
                    <ion-icon name="person-add-outline"></ion-icon>
                    회원가입
                </a>
            </c:if>
        </div>
    </div>
</div>