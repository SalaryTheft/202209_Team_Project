let hasFetchedNotiComment = false;

document.addEventListener("DOMContentLoaded", function () {
    // 페이지 로딩 시 게시글 표시
    fetch(location.pathname + "/json")
        .then(function (response) {
            if (response.ok) {
                return response.json();
            }
        }).then(function (data) {
        try {
            drawPost(data);
        } catch (e) {
            alert("게시글이 삭제되었거나 존재하지 않습니다.");
            history.back();
        }
    });

    // 페이지 로딩 시 댓글 정보 요청
    load_comment();

    // 댓글 정렬 방식 변경 시 댓글 목록 초기화 후 댓글 정보 요청
    document.querySelector("#comment_order_form select").addEventListener("change", function () {
        document.querySelector(".comment-list").innerHTML = "";
        load_comment();
    });

    // 더 불러오기 버튼
    document.querySelector(".comment-list-more").addEventListener("click", function () {
        load_comment();
    });

    // 댓글 작성
    document.querySelector("#comment_form").addEventListener("submit", function (e) {
        e.preventDefault();
        let comment_content = document.querySelector("#comment_form input[name='commentContent']");
        let tmp = document.createElement("div");
        tmp.innerHTML = comment_content.value;
        if (tmp.innerText.trim().length < 2) {
            alert("댓글은 2자 이상 입력해야 합니다.");
            return;
        } else {
            tmp.remove();
        }
        fetch(location.pathname + "/comment/write", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "commentContent=" + comment_content.value
        }).then(function (response) {
            if (response.ok) {
                return response.json();
            }
        }).then(function (data) {
            drawComment(data, 0);
            document.querySelector("#comment_form input[name='commentContent']").value = "";
            document.querySelector("#editor > div.ql-editor").innerHTML = "";
            if (document.querySelectorAll(".comment-list > .comment").length === 1) {
                document.querySelector(".comment-list-hint").innerText = "마지막 댓글입니다.";
            }
            location.href = "#" + data["commentUuid"];
        })
    });
});

/**
 * 스크롤을 페이지 끝까지 내렸을 때 댓글 정보 요청
 */
function feed_scroll() {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
        load_comment();
    }
}

/**
 * 댓글 정보 불러오기
 */
function load_comment() {
    // 댓글 로딩 중 스크롤로 인한 중복 호출 방지
    document.removeEventListener("scroll", feed_scroll);
    let order = document.querySelector("#comment_order_form select[name='order']").value;
    let timestamp = document.querySelector(".comment-list > .comment:last-child .comment-timestamp")?.getAttribute("title");
    if (timestamp === undefined) {
        timestamp = "";
    }
    let params = new URLSearchParams(new URL(location.href).search);
    if (params.has("comment_timestamp") && !hasFetchedNotiComment) {
        timestamp = params.get("comment_timestamp");
        hasFetchedNotiComment = true;
        let hint_div = document.createElement("div");
        hint_div.classList.add("comment-noti-redirect-hint");
        hint_div.innerHTML = `
        </p>전체 댓글을 보려면&nbsp;<span onclick="location.href = location.pathname + '#comments';">이곳을 누르세요.</span>   </p>
        `;
        document.querySelector(".comment-list").prepend(hint_div);
    }
    fetch(location.pathname + "/comment?timestamp={}&order={}".format(timestamp, order))
        .then(function (response) {
            if (response.ok) {
                return response.json();
            }
        }).then(function (data) {
        if (data.length === 0) {
            let hint = document.querySelector(".comment-list-hint");
            if (document.querySelector(".comment-list").children.length === 0) {
                hint.innerText = "작성된 댓글이 없습니다.";
            } else {
                hint.innerText = "마지막 댓글입니다.";
            }
        } else {
            for (let i = 0; i < data.length; i++) {
                drawComment(data[i], 1);
            }
            document.addEventListener("scroll", feed_scroll);
        }
    });
}