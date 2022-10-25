document.addEventListener("DOMContentLoaded", function () {
    load_post();

    // 더 불러오기 버튼
    document.querySelector(".post-list-more").addEventListener("click", function () {
        load_post();
    });

    // 탭 버튼
    let tabs = document.querySelectorAll(".board-tabs > a");
    tabs.forEach(function (tab) {
        tab.addEventListener("click", function (e) {
            e.preventDefault();
            let url = new URL(location.href);
            let tab_name = tab.textContent;
            url.searchParams.set("tab", tab_name);
            if (tab_name === "전체") {
                url.searchParams.delete("tab");
            }
            history.pushState(null, null, url);
            tabs.forEach(function (tab) {
                tab.classList.remove("active");
            });
            tab.classList.add("active");
            document.querySelector(".post-list").innerHTML = "";
            document.querySelector(".post-list-hint").innerText = "불러오는 중...";
            load_post();
        });
    });
});

function feed_scroll() {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
        load_post();
    }
}

function load_post() {
    document.removeEventListener("scroll", feed_scroll);
    let timestamp = document.querySelector(".post:last-child .post-timestamp")?.getAttribute("title");
    if (timestamp === undefined) {
        timestamp = "";
    }
    let tab_name = document.querySelector(".board-tabs > a.active")?.textContent;
    if (tab_name === "전체" || tab_name === undefined) {
        tab_name = "";
    }
    fetch(location.pathname + "/post?timestamp={}&tab={}".format(timestamp, tab_name))
        .then(function (response) {
            if (response.ok) {
                return response.json();
            }
        }).then(function (data) {
        let hint = document.querySelector(".post-list-hint");
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
    });
}