document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("#comment_form").addEventListener("submit", function (e) {
        e.preventDefault();
        fetch(location.href, {
            method: "POST",
            cache: "no-cache",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams({
                "commentContent": e.target.querySelector("input[name=commentContent]").value
            })
        }).then(function (response) {
            return response.json();
        }).then(function (data) {
            if (!data["error"]) {
                if (location.href.endsWith("/edit")) {
                    let originalComment = parent.document.getElementById(data["commentUuid"]);
                    originalComment.querySelector(".comment-body").innerHTML = data["commentContent"];
                    parent.modal_large_close();
                } else {
                    console.log(data["commentParentUuid"]);
                    let comment_depth = 2;
                    let parentComment = parent.document.getElementById(data["commentParentUuid"]);
                    if (parentComment.classList.contains("depth-2")) {
                        comment_depth = 3;
                    }
                    parent.drawComment(data, comment_depth);
                    parent.modal_large_close();
                }
            } else {
                alert(data["message"]);
            }
        });
    });
});