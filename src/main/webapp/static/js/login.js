document.addEventListener("DOMContentLoaded", function () {
    function googleLogin() {
        let origin = window.location.origin;
        parent.window.location.href =
            "https://accounts.google.com/o/oauth2/v2/auth?" +
            "client_id=20390837998-jq3950i3l7qsqtbe91oe39p1q4umm8t3.apps.googleusercontent.com&" +
            "redirect_uri=" + origin + "/login/google/oauth&" +
            "response_type=code&" +
            "scope=email&" +
            "access_type=offline";
    }

    document.getElementById("googleLoginBtn").addEventListener("click", function (e) {
        e.preventDefault();
        let xhr = new XMLHttpRequest();
        let redirect_uri = parent.window.location.href;
        if (window.self === window.top) { // 로그인 창이 모달창이 아닌 경우
            redirect_uri = document.referrer;
        }
        xhr.open("GET", "/set_redirect_uri?redirect_uri=" + encodeURIComponent(redirect_uri), true);
        xhr.onload = function () {
            googleLogin();
        }
        xhr.send();
    });

    document.querySelector("form.login-form").addEventListener("submit", (e) => {
        e.preventDefault();
        let xhr = new XMLHttpRequest();
        let data = {
            userEmail: document.querySelector("input[name='userEmail']").value,
            userPw: document.querySelector("input[name='userPw']").value
        }
        xhr.onload = function () {
            let data = JSON.parse(xhr.responseText);
            if (data["result"] === "success") {
                // if there is a praent window
                if (parent.window.location.href !== window.location.href) {
                    parent.window.location.reload();
                } else {
                    history.back();
                }
            } else {
                alert(data["message"]);
            }
        }
        xhr.open("POST", "/login");
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(JSON.stringify(data));
    });
});