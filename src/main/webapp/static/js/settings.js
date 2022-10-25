document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".settings > .tabs > .tab").forEach(function (tab) {
        tab.addEventListener("click", function () {
            document.querySelector(".settings > .tabs > .tab.active").classList.remove("active");
            tab.classList.add("active");
            document.querySelector(".settings > .tab-contents > .tab-content.active").classList.remove("active");
            document.querySelector(".settings > .tab-contents > .tab-content[data-tab='" + tab.dataset.tab + "']").classList.add("active");
            history.replaceState(null, null, "#" + tab.dataset.tab);
        });
    });

    // URL에 #이 있으면 해당 탭으로 이동
    let url = new URL(window.location.href);
    let hash = url.hash;
    if (hash) {
        let tab = document.querySelector(hash);
        if (tab) {
            tab.click();
        }
    }

    document.querySelectorAll(".setting img").forEach(function (img) {
        img.addEventListener("click", function () {
            let input = img.parentNode.querySelector("input");
            input.click();
        });
    });

    let nicknameCheckTimer;
    document.querySelector("#user_nickname_input").addEventListener("input", function () {
        clearTimeout(nicknameCheckTimer);
        nicknameCheckTimer = setTimeout(function () {
            checkNickname();
        }, 1000);
    });

    let descCheckTimer;
    document.querySelector("#user_desc_input").addEventListener("input", function () {
        clearTimeout(descCheckTimer);
        descCheckTimer = setTimeout(function () {
            checkDesc();
        }, 1000);
    });

    let colorTimer;
    document.querySelector("#user_color_input").addEventListener("input", function (event) {
        clearTimeout(colorTimer);
        document.querySelector(".color-code").innerText = event.target.value.toUpperCase();
        colorTimer = setTimeout(function () {
            checkColor();
        }, 1000);
    });

    document.querySelector("#user_icon_input").addEventListener("change", function (e) {
        let reader = new FileReader();
        reader.onload = function () {
            let image = new Image();
            image.src = reader.result;
            image.onload = function () {
                let canvas = document.createElement("canvas");
                let ctx = canvas.getContext("2d");
                canvas.height = 150;
                canvas.width = image.width * (150 / image.height);
                ctx.drawImage(image, 0, 0, canvas.width, canvas.height);
                let dataURL = canvas.toDataURL("image/jpeg", 0.8);
                fetch("/settings/icon", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: "userIcon=" + encodeURIComponent(dataURL)
                }).then(function (response) {
                    return response.json();
                }).then(function (data) {
                    if (data["result"] === "success") {
                        document.querySelector("#user_icon").src = dataURL;
                        toast_msg("프로필 사진이 변경되었습니다.", "lightgreen");
                    } else {
                        toast_msg("프로필 사진 변경에 실패했습니다.", "lightcoral");
                    }
                });
            }
        }
        reader.readAsDataURL(e.target.files[0]);
    });

    document.querySelector("#user_background_input").addEventListener("change", function (event) {
        let reader = new FileReader();
        reader.onload = function () {
            let image = new Image();
            image.src = reader.result;
            image.onload = function () {
                let canvas = document.createElement("canvas");
                let ctx = canvas.getContext("2d");
                canvas.height = 500;
                canvas.width = image.width * (500 / image.height);
                ctx.drawImage(image, 0, 0, canvas.width, canvas.height);
                let dataURL = canvas.toDataURL("image/jpeg", 0.8);
                fetch("/settings/background", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: "userBackground=" + encodeURIComponent(dataURL)
                }).then(function (response) {
                    return response.json();
                }).then(function (data) {
                    if (data["result"] === "success") {
                        document.querySelector("#user_background").src = dataURL;
                        toast_msg("배경 사진이 변경되었습니다.", "lightgreen");
                    } else {
                        toast_msg("배경 사진 변경에 실패했습니다.", "lightcoral");
                    }
                });
            }
        }
        reader.readAsDataURL(event.target.files[0]);
    });
});

function checkNickname() {
    let nickname_input = document.querySelector("#user_nickname_input");
    nickname_input.value = nickname_input.value.trim();
    let nickname = nickname_input.value;
    if (nickname.length < 2) {
        toast_msg("닉네임을 2글자 이상 입력해주세요.", "lightcoral");
        return;
    }
    fetch("/settings/nickname", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "userNickname=" + nickname
    }).then(function (response) {
        return response.json();
    }).then(function (data) {
        if (data["result"] === "success") {
            toast_msg("닉네임이 변경되었습니다.", "lightgreen");
        } else {
            toast_msg("사용할 수 없는 닉네임입니다.", "lightcoral");
        }
    });
}

function checkDesc() {
    let desc_input = document.querySelector("#user_desc_input");
    desc_input.value = desc_input.value.trim();
    let desc = desc_input.value;
    fetch("/settings/desc", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "userDesc=" + desc
    }).then(function (response) {
        return response.json();
    }).then(function (data) {
        if (data["result"] === "success") {
            toast_msg("소개글이 변경되었습니다.", "lightgreen");
        } else {
            toast_msg("소개글 변경에 실패했습니다.", "lightcoral");
        }
    });
}

function toast_msg(msg, color) {
    let old_toasts = document.querySelectorAll(".toast-wrap");
    if (old_toasts.length > 0) {
        old_toasts.forEach(function (toast) {
            toast.remove();
        });
    }

    // Android Style Toast Message
    let toast_wrap = document.createElement("div");
    toast_wrap.className = "toast-wrap";
    toast_wrap.style.position = "fixed";
    toast_wrap.style.top = "0";
    toast_wrap.style.left = "0";
    toast_wrap.style.width = "100vw";
    toast_wrap.style.height = "100vh";
    toast_wrap.style.display = "flex";
    toast_wrap.style.justifyContent = "center";
    toast_wrap.style.alignItems = "center";
    toast_wrap.style.zIndex = "9999";
    toast_wrap.style.transition = "all 0.5s ease-in-out";
    toast_wrap.style.pointerEvents = "none";

    let toast = document.createElement("div");
    toast.className = "toast";
    toast.style.padding = "10px 20px";
    toast.style.marginBottom = "30px";
    toast.style.backgroundColor = color;
    toast.style.color = "#000";
    toast.style.borderRadius = "9999px";
    toast.style.fontSize = "1rem";
    toast.style.textAlign = "center";
    toast.style.fontWeight = "500";
    toast.style.boxShadow = "0 0 10px rgba(128, 128, 128, 0.5)";
    toast.innerText = msg;

    toast_wrap.appendChild(toast);
    document.body.appendChild(toast_wrap);

    setTimeout(function () {
        toast_wrap.style.opacity = "0";
        setTimeout(function () {
            toast_wrap.remove();
        }, 500);
    }, 4000);
}

function checkColor() {
    fetch("/settings/color", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "userColor=" + document.querySelector("#user_color_input").value.toUpperCase()
    }).then(function (response) {
        return response.json();
    }).then(function (data) {
        if (data["result"] === "success") {
            toast_msg("색상이 변경되었습니다.", "lightgreen");
        } else {
            toast_msg("색상 변경에 실패했습니다.", "lightcoral");
        }
    });
}