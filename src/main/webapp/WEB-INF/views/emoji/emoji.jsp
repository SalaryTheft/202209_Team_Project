<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>AlliO</title>

    <!-- JS -->
    <script src="/static/js/common.js"></script>

    <!-- CSS -->
    <link href="/static/css/emoji.css" rel="stylesheet" type="text/css">
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/common.css" rel="stylesheet" type="text/css">
    <script>
        function test() {
            if (!confirm("이모티콘을 일괄삭제 합니다.")) {
                alert("취소를 누르셨습니다.");
            } else {

                alert("확인을 누르셨습니다.");
            }
        }
    </script>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp" %>
<div class="emoji-wrapper">
    <form>
        <input type="hidden" name="userUuid" value="${user.userUuid }">
    <div class="title-div">
        <h1>이모지리스트</h1>
        <div class="emoji-btn">
            <a class="wide-btn" onclick="modal_large('/emoji/edit')">이모티콘 등록</a>
                <input type="submit" class="wide-btn" value="이모티콘 삭제"></input>
        </div>
    </div>
    <div class="list-div">
        <ul class="emoji-list">
            <c:forEach items="${emojiList }" var="emoji">
            <li class="emoji-package">
                <a class="emoji-link" href="#">
                    <img src="${emoji.emojiData}" class="thumb_img" alt="이모티콘" width="140" height="140">
                    <strong class="emoji-name">${emoji.emojiName }</strong>
                </a>
            </li>
            </c:forEach>
        </ul>
    </div>
    </form>
</div>
<%@include file="/WEB-INF/views/include/modal.jsp" %>
</body>
</html>