<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>AlliO</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <!-- JS -->
    <script src="/static/js/common.js"></script>
    <script src="/static/js/settings.js"></script>

    <!-- CSS -->
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/common.css" rel="stylesheet" type="text/css">
    <link href="/static/css/settings.css" rel="stylesheet" type="text/css">

    <!-- ION-ICON -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>

<body>
<%@include file="/WEB-INF/views/include/header.jsp" %>
<div class="body">
    <div class="settings">
        <h2>사용자 설정</h2>
        <div class="tabs">
            <div class="tab active" data-tab="account" id="account">
                <span>계정 정보</span>
            </div>
            <div class="tab" data-tab="profile" id="profile">
                <span>프로필</span>
            </div>
        </div>
        <div class="tab-contents">
            <div class="tab-content active" data-tab="account">
                <h2 class="account-setting">계정 설정</h2>
                <h3 class="account-preference">계정 기본 설정</h3>
                <div class="setting">
                    <div class="setting-left">
                        <div class="title">
                            <h3>이메일 주소</h3>
                        </div>
                        <p class="desc">${user.userEmail}</p>
                    </div>
                    <div class="btn-area">
                        <div class="change-btn-area">
                            <button class="change-btn" onclick="modal_large('/settings/email')">변경</button>
                        </div>
                    </div>
                </div>
                <div class="setting">
                    <div class="setting-left">
                        <div class="title">
                            <h3>비밀번호 변경</h3>
                        </div>
                        <p class="desc">
                            암호는 8자 이상이어야 합니다.
                        </p>
                    </div>
                    <div class="btn-area">
                        <div class="change-btn-area">
                            <button class="change-btn" onclick="modal_large('/settings/password')">변경</button>
                        </div>
                    </div>
                </div>
                <h3 class="account-preference">회원 탈퇴</h3>
                <div class="setting">
                    <div class="setting-left">
                        <div class="title">
                            <h3>회원 탈퇴</h3>
                        </div>
                        <p class="desc">
                            회원 탈퇴 시 모든 정보가 즉시 삭제되며 복구할 수 없습니다.
                        </p>
                    </div>
                    <div class="btn-area">
                        <div class="change-btn-area">
                            <button class="change-btn red" onclick="modal_large('/settings/deactivate')">탈퇴</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-content" data-tab="profile">
                <h2 class="account-setting">프로필 설정</h2>
                <h3 class="account-preference">프로필 기본 정보</h3>
                <div class="setting">
                    <div class="setting-top">
                        <div class="title">
                            <h3>닉네임 변경</h3>
                        </div>
                        <p class="desc">
                            다른 사람에게 보여질 닉네임입니다.
                        </p>
                    </div>
                    <input type="text" id="user_nickname_input" placeholder="닉네임" maxlength="15"
                           class="nickname-input" value="${user.userNickname}" required>
                </div>
                <div class="setting">
                    <div class="setting-left">
                        <div class="title">
                            <h3>자기소개</h3>
                        </div>
                        <p class="desc">
                            사용자 프로필에 표시될 자기소개를 입력해주세요.
                        </p>
                    </div>
                    <input type="text" id="user_desc_input" placeholder="자기소개" maxlength="200"
                           class="about-input" value="${user.userDesc}">
                </div>
                <h3 class="account-preference">프로필 이미지 및 색상</h3>
                <div class="setting">
                    <div class="setting-top">
                        <div class="title">
                            <h3>프로필 사진 및 배경사진</h3>
                        </div>
                        <p class="desc">
                            사진을 변경하려면 해당 사진을 클릭하세요.
                        </p>
                    </div>
                    <div class="user-img-selector-wrap">
                        <div class="user-icon-wrap">
                            <img src="/u/${user.userNickname}/icon" id="user_icon">
                            <input type="file" id="user_icon_input" accept="image/*" style="display: none">
                        </div>
                        <div class="user-background-wrap">
                            <img src="/u/${user.userNickname}/background" id="user_background">
                            <input type="file" id="user_background_input" accept="image/*" style="display: none">
                        </div>
                    </div>
                    <div class="setting-top">
                        <div class="title">
                            <h3>프로필 색상</h3>
                        </div>
                        <p class="desc">
                            사용자 프로필 페이지에 사용될 테마 색상입니다.
                        </p>
                    </div>
                    <div class="user-color-wrap">
                        <input type="color" id="user_color_input" value="${user.userColor}">
                        <span class="color-code">${user.userColor}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/include/modal.jsp" %>
</body>

</html>