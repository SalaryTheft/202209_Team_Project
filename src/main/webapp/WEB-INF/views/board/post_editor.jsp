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
    <script src="/static/js/post_editor.js"></script>

    <!-- CSS -->
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/common.css" rel="stylesheet" type="text/css">
    <link href="/static/css/board.css" rel="stylesheet" type="text/css">
    <style>
        /* BOARD CUSTOM THEME */
        <c:if test="${board.boardColor ne null}">
        :root {
            --highlight-color: ${board.boardColor};
        }

        </c:if>
        <c:if test="${board.boardIcon ne null}">
        .board-icon {
            background-image: url(${board.boardIcon});
        }

        </c:if>
        <c:if test="${board.boardBackground ne null}">
        .board-top > .board-background {
            background-image: url(${board.boardBackground});
            background-size: cover;
            /*background-position: center;*/
            background-repeat: no-repeat;
            width: 100%;
            height: 0;
            padding-top: min(66.64%, 200px);
        }

        </c:if>
    </style>

    <!-- ION-ICON -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

    <!-- QUILL EDITOR -->
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
    <link href="/static/css/quill.css" rel="stylesheet">
</head>

<body>
<%@include file="/WEB-INF/views/include/header.jsp" %>
<div class="body">
    <div class="content-area">
        <div class="post-area">
            <form class="post-editor" method="post">
                <h3>????????? ${post eq null ? '??????' : '??????'}</h3>
                <hr>
                <div class="post-tabs">
                    <c:forEach items="${boardTabs}" var="tab" varStatus="status">
                        <input type="radio" name="postTab" value="${tab}"
                               id="tab${tab}" ${post.postTab eq tab ? 'checked' : ''}
                            ${post eq null && status.index eq 0 ? 'checked' : ''}>
                        <label for="tab${tab}">${tab}</label>
                    </c:forEach>
                </div>
                <input type="text" name="postTitle" placeholder="????????? ???????????????" value="${post.postTitle}" maxlength="50" required>
                <div id="editor" style="min-height: 300px">${post.postContent}</div>
                <input type="submit" value="${post eq null ? '??????' : '??????'}">
                <div style="display: none">
                    <input type="hidden" name="postContent" value="">
                </div>
            </form>
            <script>
                let editor = new Quill('#editor', {
                    modules: {
                        toolbar: [
                            ['bold', 'italic', 'underline', 'strike'],
                            [{
                                'align': []
                            }],
                            ['clean'],
                            ['link', 'image', 'video']
                        ]
                    },
                    placeholder: '?????? ????????? ????????? ???????????????.',
                    theme: 'snow'
                });
                document.querySelector("input[name='postContent']").value = editor.root.innerHTML;
            </script>
        </div>
        <div class="side-list">
            <%@include file="/WEB-INF/views/board/board_side.jsp" %>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/include/modal.jsp" %>
</body>
</html>