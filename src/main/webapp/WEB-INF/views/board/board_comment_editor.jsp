<%request.setCharacterEncoding("utf-8");%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>댓글 ${comment ne null ? '편집' : '답글 작성'}</title>
    <meta name="viewport" content="width=device-width, user-scale=no, initial-scale=1.0">

    <!-- JS -->
    <script src="/static/js/board_comment_form.js"></script>

    <!-- CSS -->
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/board_modal.css" rel="stylesheet" type="text/css">
    <style>
        <c:if test="${board.boardColor ne null}">
        :root {
            --highlight-color: ${board.boardColor};
        }
        </c:if>
    </style>

    <!-- QUILL EDITOR -->
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
    <link href="/static/css/quill.css" rel="stylesheet">
</head>
<body>
<form id="comment_form" method="post">
    <div class="body">
        <h3 class="title">댓글 ${comment ne null ? '편집' : '답글 작성'}</h3>
        <hr>
        <div style="display: none">
            <input type="hidden" name="commentContent" value="">
        </div>
        <div id="editor" class="comment">${comment.commentContent}</div>
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
                input.value = document.querySelector("#comment_form #editor > div.ql-editor").innerHTML;
            });
            document.querySelector("input[name='commentContent']").value = editor.root.innerHTML;
        </script>
    </div>
    <div class="footer">
        <input type="button" name="cancel" class="cancel" value="취소" onclick='parent.modal_large_close()'>
        <input type="submit" value="저장">
    </div>
</form>
</body>
</html>
