document.addEventListener("DOMContentLoaded", function () {
    // on editor change (include backspace, delete, paste, etc.)
    document.querySelector("#editor").addEventListener("DOMSubtreeModified", function () {
        document.querySelector("form input[name='postContent']").value = document.querySelector("#editor > div.ql-editor").innerHTML;
    });

    document.querySelector("form").addEventListener("submit", function (e) {
        e.preventDefault();
        let post_content = document.querySelector("form input[name='postContent']");
        let tmp = document.createElement("div");
        tmp.innerHTML = post_content.value;
        if (tmp.innerText.trim().length < 2) {
            alert("게시글은 2자 이상 입력해야 합니다.");
            return;
        } else {
            tmp.remove();
        }
        e.target.submit();
    });
});