let isNicknameValid = false;
let isPwValid = false;
let isPwConfirmValid = false;

document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("form").addEventListener("submit", function (event) {
        if (!isNicknameValid || !isPwValid || !isPwConfirmValid) {
            event.preventDefault();
        }
    });

    let nicknameCheckTimer;
    document.querySelector("input[name='userNickname']").addEventListener("input", function () {
        clearTimeout(nicknameCheckTimer);
        nicknameCheckTimer = setTimeout(function () {
            checkNickname();
        }, 300);
    });

    function checkNickname() {
        let nickname = document.querySelector("input[name='userNickname']").value;
        let nicknameHint = document.querySelector("#nicknameHint");
        let confirmBtn = document.querySelector("input[type='submit']");
        if (nickname.length >= 2) {
            checkNicknameAjax(nickname);
        } else {
            nicknameHint.innerText = "";
            confirmBtn.classList.add("disabled");
            isNicknameValid = false;
            enableConfirmBtn();
        }
    }

    function checkNicknameAjax(nickname) {
        let nicknameHint = document.querySelector("#nicknameHint");
        let confirmBtn = document.querySelector("input[type='submit']");
        let xhr = new XMLHttpRequest();
        let data = {
            userNickname: nickname
        }
        xhr.onload = function () {
            let data = JSON.parse(xhr.responseText);
            if (data["result"] === "success") {
                nicknameHint.innerText = "사용 가능한 닉네임입니다.";
                nicknameHint.style.color = "green";
                confirmBtn.classList.remove("disabled");
                isNicknameValid = true;
            } else {
                nicknameHint.innerText = "이미 사용 중인 닉네임입니다.";
                nicknameHint.style.color = "red";
                confirmBtn.classList.add("disabled");
                isNicknameValid = false;
            }
            enableConfirmBtn();
        }
        xhr.open("POST", "/signup/check_nickname");
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(JSON.stringify(data));
    }

    document.querySelector("input[name='userPw']").addEventListener("input", function () {
        checkPassword();
        checkPasswordConfirm();
    });

    document.querySelector("input[name='userPwConfirm']").addEventListener("input", function () {
        checkPasswordConfirm();
    });

    function checkPassword() {
        let password = document.querySelector("input[name='userPw']").value;
        let passwordHint = document.querySelector("#passwordHint");
        let confirmBtn = document.querySelector("input[type='submit']");
        if (password.length >= 8 && password.length <= 20) {
            passwordHint.innerText = "사용 가능한 비밀번호입니다.";
            passwordHint.style.color = "green";
            confirmBtn.classList.remove("disabled");
            isPwValid = true;
        } else {
            passwordHint.innerText = "비밀번호는 8자 이상 20글자 이하로 입력해주세요.";
            passwordHint.style.color = "red";
            confirmBtn.classList.add("disabled");
            isPwValid = false;
        }
        enableConfirmBtn();
    }

    function checkPasswordConfirm() {
        let password = document.querySelector("input[name='userPw']").value;
        let passwordConfirm = document.querySelector("input[name='userPwConfirm']").value;
        let passwordConfirmHint = document.querySelector("#passwordConfirmHint");
        let confirmBtn = document.querySelector("input[type='submit']");
        if (password === passwordConfirm) {
            passwordConfirmHint.innerText = "비밀번호가 일치합니다.";
            passwordConfirmHint.style.color = "green";
            confirmBtn.classList.remove("disabled");
            isPwConfirmValid = true;
        } else {
            passwordConfirmHint.innerText = "비밀번호가 일치하지 않습니다.";
            passwordConfirmHint.style.color = "red";
            confirmBtn.classList.add("disabled");
            isPwConfirmValid = false;
        }
        enableConfirmBtn();
    }

    function enableConfirmBtn() {
        let confirmBtn = document.querySelector("input[type='submit']");
        if (isNicknameValid && isPwValid && isPwConfirmValid) {
            confirmBtn.classList.remove("disabled");
        } else {
            confirmBtn.classList.add("disabled");
        }
    }
});