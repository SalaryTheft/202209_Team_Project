let isNewPwdValid = false;
let isConfirmPwdValid = false;

document.addEventListener("DOMContentLoaded", function (event) {
    document.querySelector("form input[name='userPw']").addEventListener("input", function (event) {
        let pw_hint = document.querySelector(".new-pw-hint");
        // 8자 이상 20자 이하
        if (event.target.value.length < 8 || event.target.value.length > 20) {
            pw_hint.innerHTML = "8자 이상 20자 이하로 입력해주세요.";
            pw_hint.style.color = "lightcoral";
            isNewPwdValid = false;
        } else {
            pw_hint.innerHTML = "사용 가능한 비밀번호입니다.";
            pw_hint.style.color = "lightgreen";
            isNewPwdValid = true;
        }
    });

    document.querySelector("form input[name='userPwConfirm']").addEventListener("input", function (event) {
        let pw_hint = document.querySelector(".new-pw-confirm-hint");
        if (event.target.value !== document.querySelector("form input[name='userPw']").value) {
            pw_hint.innerHTML = "비밀번호가 일치하지 않습니다.";
            pw_hint.style.color = "lightcoral";
            isConfirmPwdValid = false;
        } else {
            pw_hint.innerHTML = "비밀번호가 일치합니다.";
            pw_hint.style.color = "lightgreen";
            isConfirmPwdValid = true;
        }
    });

    document.querySelector("form").addEventListener("submit", function (event) {
        event.preventDefault();
        event.target.querySelectorAll("input").forEach(function (input) {
            input.value = input.value.trim();
        });
        if (isNewPwdValid && isConfirmPwdValid && event.target.querySelector("input[name='userOldPw']").value !== "") {
            event.target.submit();
        } else {
            alert("비밀번호를 확인해주세요.");
        }
    });
});