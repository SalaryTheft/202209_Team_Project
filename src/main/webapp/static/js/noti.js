document.addEventListener("DOMContentLoaded", function () {
    setTimeout(function () {
        location.reload();
    }, 1000 * 30); // 30초마다 새로고침

    if (window.self !== window.top) {
        let noti_count = document.querySelectorAll(".noti-item:not(.read)").length;
        parent.setNotiCount(noti_count);
    }

    document.querySelectorAll(".noti-item").forEach(function (noti) {
        let noti_timestamp = noti.querySelector(".noti-timestamp");
        let noti_timestamp_date = new Date(noti_timestamp.getAttribute("title"));
        let noti_timestamp_diff = new Date() - noti_timestamp_date;
        if (noti_timestamp_diff < 0) {
            noti_timestamp.innerText = "방금 전";
        } else if (noti_timestamp_diff < 60000) {
            noti_timestamp.innerText = Math.floor(noti_timestamp_diff / 1000) + "초 전";
        } else if (noti_timestamp_diff < 3600000) {
            noti_timestamp.innerText = Math.floor(noti_timestamp_diff / 60000) + "분 전";
        } else if (noti_timestamp_diff < 86400000) {
            noti_timestamp.innerText = Math.floor(noti_timestamp_diff / 3600000) + "시간 전";
        } else if (noti_timestamp_diff < 2592000000) {
            noti_timestamp.innerText = Math.floor(noti_timestamp_diff / 86400000) + "일 전";
        } else if (noti_timestamp_diff < 31536000000) {
            noti_timestamp.innerText = Math.floor(noti_timestamp_diff / 2592000000) + "달 전";
        } else {
            noti_timestamp.innerText = Math.floor(noti_timestamp_diff / 31536000000) + "년 전";
        }

        noti.addEventListener("click", function () {
            let uuid = noti.getAttribute("data-uuid");
            fetch("/noti/markasread", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: "uuid=" + uuid
            }).then(function (response) {
                return response.json();
            }).then(function (json) {
                if (json.result === "success") {
                    document.querySelectorAll(".noti-item").forEach(function (noti) {
                        noti.classList.add("read");
                    });
                } else {
                    alert("알림 읽음 처리에 실패했습니다.");
                }
            }).then(function () {
                parent.document.querySelector(".noti-btn").click();
                let url = noti.getAttribute("data-url");
                if (window.self !== window.top) {
                    window.top.location.href = url;
                } else {
                    window.location.href = url;
                }
            });
        });
    });

    document.querySelector("#mark_all_as_read_btn").addEventListener("click", function () {
        fetch("/noti/markasread", {
            method: "POST"
        }).then(function (response) {
            return response.json();
        }).then(function (json) {
            if (json.result === "success") {
                document.querySelectorAll(".noti-item").forEach(function (noti) {
                    noti.classList.add("read");
                });
            }
            location.reload();
        });
    });

    document.querySelector("#delete_all_btn").addEventListener("click", function () {
        fetch("/noti/delete", {
            method: "POST"
        }).then(function (response) {
            return response.json();
        }).then(function (json) {
            if (json.result === "success") {
                document.querySelectorAll(".noti-item").forEach(function (noti) {
                    noti.remove();
                });
                let hint = document.createElement("div");
                hint.classList.add("noti-hint");
                hint.innerHTML = "<p>알림이 없습니다.</p>";
                document.querySelector(".noti-list").appendChild(hint);
            }
            location.reload();
        });
    });
});