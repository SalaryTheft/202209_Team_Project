let isEmailValid = false;

document.addEventListener("DOMContentLoaded", function (event) {
    let emailCheckTimer;
    document.querySelector("input[name='userNewEmail']").addEventListener("input", function () {
        clearTimeout(emailCheckTimer);
        emailCheckTimer = setTimeout(function () {
            checkEmail();
        }, 500);
    });

    document.querySelector("form").addEventListener("submit", function (event) {
        event.preventDefault();
        if (isEmailValid) {
            event.target.submit();
        } else {
            alert("이메일을 확인해주세요.");
        }
    });
});

function checkEmail() {
    let email = document.querySelector("input[name='userNewEmail']").value;
    let emailHint = document.querySelector(".email-hint");
    if (email.length >= 5) {
        if (email.includes("@") &&
            email.includes(".") &&
            email.indexOf("@") < email.lastIndexOf(".") &&
            email.substring(email.lastIndexOf(".") + 1).length >= 2) {
            fetch("/signup/check_email", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    userEmail: email
                })
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                if (data["result"] === "success") {
                    emailHint.innerText = "사용 가능한 이메일입니다.";
                    emailHint.style.color = "lightgreen";
                    isEmailValid = true;
                } else {
                    emailHint.innerText = "이미 사용 중인 이메일입니다.";
                    emailHint.style.color = "lightcoral";
                    isEmailValid = false;
                }
            });
        } else {
            emailHint.innerText = "이메일 형식이 올바르지 않습니다.";
            emailHint.style.color = "lightcoral";
            isEmailValid = false;
        }
    } else {
        emailHint.innerText = "";
        isEmailValid = false;
    }
}