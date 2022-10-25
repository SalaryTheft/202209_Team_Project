<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible">
    <title>알림</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- JS -->
    <script src="<c:url value="/static/js/noti.js"/>"></script>

    <!-- CSS -->
    <link href="/static/css/style.css"
          rel="stylesheet" type="text/css" media="screen">
    <link href="/static/css/noti.css"
          rel="stylesheet" type="text/css" media="screen">

    <!-- ION-ICON -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>
<body>
<div class="body">
    <div class="noti-list">
        <c:forEach items="${notiList}" var="noti">
            <div class="noti-item ${noti.notiReadYn eq 'N' ? '' : 'read'}"
                 data-uuid="${noti.notiUuid}"
                 data-url="/r/${noti.commentPost.boardId}/post/${noti.postUuid}${noti.commentTimestamp ne null ? '?comment_timestamp=' += noti.commentTimestamp : ''}#comments">
                <div class="profile-img">
                    <img src="/u/${noti.commentUser.userNickname}/icon">
                </div>
                <div class="noti-content">
                    <div class="noti-content-top">
                        <div class="noti-nickname">${noti.commentUser.userNickname}</div>
                        <div class="noti-timestamp" title="${noti.notiTimestamp}"></div>
                    </div>
                    <div class="noti-comment">
                        <p>${noti.commentContent}</p>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:if test="${notiList.size() eq 0 or notiList eq null}">
            <div class="noti-hint">
                <c:if test="${user eq null}">
                    <p>로그인 후 알림을 받아보세요.</p>
                </c:if>
                <c:if test="${user ne null}">
                    <p>알림이 없습니다.</p>
                </c:if>
            </div>
        </c:if>
    </div>
    <div class="noti-footer">
        <a href="#" id="mark_all_as_read_btn">
            <ion-icon name="checkmark-done-outline"></ion-icon>
            <span>모두 읽음</span>
        </a>
        <a href="#" id="delete_all_btn">
            <ion-icon name="trash-outline"></ion-icon>
            <span>전체 삭제</span>
        </a>
    </div>
</div>
</body>
</html>