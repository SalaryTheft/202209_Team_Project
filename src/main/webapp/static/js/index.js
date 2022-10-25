let best_post_list = [];
let best_post_page = 1;
document.addEventListener("DOMContentLoaded", function () {
    fetch("./feed/best")
        .then(function (response) {
            if (response.ok) {
                return response.json();
            }
        }).then(function (data) {
        best_post_list = data;
    }).then(function () {
        load_post();
    });

    // 게시판 만들기 버튼 모달
    document.querySelector("#new_board_modal_btn").addEventListener("click", function () {
        modal_large("/r/new");
    });

    // 메인 피드 정렬 방식
    document.querySelector("#post_order_form").addEventListener("change", function () {
        best_post_page = 1;
        let orders = document.querySelectorAll("#post_order_form > label > ion-icon");
        orders.forEach(function (order) {
            if (!order.name.includes("-outline")) {
                order.name = order.name + "-outline";
            }
        })
        document.querySelector(".post-order > form > input[type='radio']:checked + label > ion-icon").name = document.querySelector(".post-order > form > input[type=\"radio\"]:checked + label > ion-icon").name.replace("-outline", "");
        document.querySelector(".post-list-hint").innerText = "게시글을 불러오는 중입니다.";
        if (document.querySelector("#post_order_form input[name='order']:checked").value === "best") {
            fetch("./feed/best")
                .then(function (response) {
                    if (response.ok) {
                        return response.json();
                    }
                }).then(function (data) {
                best_post_list = data;
            }).then(function () {
                document.querySelector(".post-list").innerHTML = "";
                load_post();

            });
        } else {
            document.querySelector(".post-list").innerHTML = "";
            load_post();
        }
    });

    // 인기 게시판 클릭시
    document.querySelectorAll(".rank-list > .board").forEach(function (board) {
        board.addEventListener("click", function () {
            location.href = "/r/{}".format(board.getAttribute("data-board-id"));
        });
    });
});

function feed_scroll() {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
        load_post();
    }
}

async function load_post() {
    document.removeEventListener("scroll", feed_scroll);
    let timestamp = document.querySelector(".post:last-child .post-timestamp")?.getAttribute("title");
    if (timestamp === undefined) {
        timestamp = "";
    }
    let hint = document.querySelector(".post-list-hint");
    let order = document.querySelector("#post_order_form input[name='order']:checked").value;
    if (order !== "best") {
        console.log("order: " + order);
        fetch("./feed/{}?timestamp={}".format(order, timestamp))
            .then(function (response) {
                if (response.ok) {
                    return response.json();
                }
            }).then(function (data) {
            if (data?.result === "fail") {
                hint.innerText = data["message"];
            } else {
                if (data.length === 0) {
                    if (document.querySelector(".post-list").children.length === 0) {
                        hint.innerText = "작성된 글이 없습니다.";
                    } else {
                        hint.innerText = "마지막 글입니다.";
                    }
                } else {
                    for (let i = 0; i < data.length; i++) {
                        drawPost(data[i]);
                    }
                    if (data.length < 5) {
                        hint.innerText = "마지막 글입니다.";
                    }
                    document.addEventListener("scroll", feed_scroll);
                }
            }
        });
    } else {
        console.log("order(best): " + order);
        if (best_post_list.length === 0) {
            hint.innerText = "작성된 글이 없습니다.";
        } else {
            let start = (best_post_page - 1) * 5;
            let end = best_post_page * 5;
            if (end > best_post_list.length) {
                end = best_post_list.length;
            }
            for (let i = start; i < end; i++) {
                let post = await fetch("./feed/best?uuid={}".format(best_post_list[i]));
                let data = await post.json();
                drawPost(data);
            }
            if (end === best_post_list.length) {
                hint.innerText = "마지막 글입니다.";
            }
            best_post_page++;
            document.addEventListener("scroll", feed_scroll);
        }
    }
}