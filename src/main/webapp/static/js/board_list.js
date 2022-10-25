function subscribe(elem) {
    let user_nickname = document.querySelector("meta[name='user_nickname']")?.getAttribute("content");
    if (user_nickname === undefined) {
        alert("로그인 후 이용해주세요.");
        return;
    }
    let board_id = elem.closest(".board").getAttribute("data-board-id");
    if (elem.classList.contains("active")) {
        fetch("/r/{}/unsubscribe".format(board_id), {
            method: "POST"
        }).then(function (response) {
            if (response.ok) {
                return response.json();
            }
        }).then(function (data) {
            if (data["result"] === "success") {
                elem.classList.remove("active");
                elem.textContent = "구독";
            } else {
                alert(data["message"]);
            }
        });
    } else {
        fetch("/r/{}/subscribe".format(board_id), {
            method: "POST"
        }).then(function (response) {
            if (response.ok) {
                return response.json();
            }
        }).then(function (data) {
            if (data["result"] === "success") {
                elem.classList.add("active");
                elem.textContent = "구독중";
            } else {
                alert(data["message"]);
            }
        });
    }
}

/*
 * 구독 버튼 클릭 시 게시판 객체 이벤트 전파 방지
 */
document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll("a.board").forEach(function (elem) {
        elem.addEventListener("click", function (event) {
            if (event.target.classList.contains("board-subscribe-btn")) {
                event.preventDefault();
            }
        }, false);
    });
});