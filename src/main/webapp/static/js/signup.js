let isFormValid = false;

document.addEventListener("DOMContentLoaded", function (event) {
    document.querySelector("form").addEventListener("submit", function (e) {
        if (!isFormValid) {
            e.preventDefault();
        }
        let confirm_btn = document.querySelector("input[type='submit']");
        confirm_btn.classList.add("disabled");
        confirm_btn.value = "잠시 기다려주세요...";

    });

    function googleLogin() {
        let origin = window.location.origin;
        let url =
            "https://accounts.google.com/o/oauth2/v2/auth?" +
            "client_id=20390837998-jq3950i3l7qsqtbe91oe39p1q4umm8t3.apps.googleusercontent.com&" +
            "redirect_uri=" + origin + "/login/google/oauth&" +
            "response_type=code&" +
            "scope=email&" +
            "access_type=offline";
        parent.window.location.href = url;
    }

    document.getElementById("googleLoginBtn").addEventListener("click", function (e) {
        e.preventDefault();
        let xhr = new XMLHttpRequest();
        xhr.open("GET", "/set_redirect_uri?redirect_uri=" + parent.location.href, true);
        xhr.onload = function () {
            googleLogin();
        }
        xhr.send();
    });

    function checkEmail() {
        let email = document.querySelector("input[name='userEmail']").value;
        let emailHint = document.querySelector("#emailHint");
        let confirmBtn = document.querySelector("input[type='submit']");
        if (email.length >= 5) {
            if (email.includes("@") &&
                email.includes(".") &&
                email.indexOf("@") < email.lastIndexOf(".") &&
                email.substring(email.lastIndexOf(".") + 1).length >= 2) {
                checkEmailAjax(email);
            } else {
                emailHint.innerText = "이메일 형식이 올바르지 않습니다.";
                emailHint.style.color = "red";
                confirmBtn.classList.add("disabled");
                isFormValid = false;
            }
        } else {
            emailHint.innerText = "";
            confirmBtn.classList.add("disabled");
            isFormValid = false;
        }
    }

    // DOM 로딩 완료 시 이메일 중복 검사(Gmail 자동 입력)
    checkEmail();

    let emailCheckTimer;
    document.querySelector("input[name='userEmail']").addEventListener("input", function () {
        clearTimeout(emailCheckTimer);
        emailCheckTimer = setTimeout(function () {
            checkEmail();
        }, 300);
    });

    function checkEmailAjax(email) {
        let emailHint = document.querySelector("#emailHint");
        let confirmBtn = document.querySelector("input[type='submit']");
        let xhr = new XMLHttpRequest();
        let data = {
            userEmail: email
        }
        xhr.onload = function () {
            let data = JSON.parse(xhr.responseText);
            if (data["result"] === "success") {
                emailHint.innerText = "사용 가능한 이메일입니다.";
                emailHint.style.color = "green";
                confirmBtn.classList.remove("disabled");
                isFormValid = true;
            } else {
                emailHint.innerText = "이미 사용 중인 이메일입니다.";
                emailHint.style.color = "red";
                confirmBtn.classList.add("disabled");
                isFormValid = false;
            }
        }
        xhr.open("POST", "/signup/check_email");
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(JSON.stringify(data));
    }
});