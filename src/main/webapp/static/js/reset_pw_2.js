document.addEventListener("DOMContentLoaded", function (event) {
    document.querySelector("form input[name='userPw']").addEventListener("change", function (event) {
        let hint = document.querySelector(".pw-hint");
        if (this.value.length < 8 || this.value.length > 20) {
            hint.innerText = "비밀번호는 8자 이상 20자 이하로 입력해주세요.";
            hint.style.color = "lightcoral";
        } else {
            hint.innerText = "사용 가능한 비밀번호입니다.";
            hint.style.color = "lightgreen";
        }
    });

    document.querySelector("form input[name='userPwConfirm']").addEventListener("change", function (event) {
        let hint = document.querySelector(".pw-confirm-hint");
        if (this.value !== document.querySelector("form input[name='userPw']").value) {
            hint.innerText = "비밀번호가 일치하지 않습니다.";
            hint.style.color = "lightcoral";
        } else {
            hint.innerText = "비밀번호가 일치합니다.";
            hint.style.color = "lightgreen";
        }
    });

    document.querySelector("form").addEventListener("submit", function (event) {
        event.preventDefault();
        event.target.querySelectorAll("input").forEach(function (input) {
            input.value = input.value.trim();
        });
        let userPw = event.target.querySelector("input[name='userPw']").value;
        let userPwConfirm = event.target.querySelector("input[name='userPwConfirm']").value;
        if (userPw !== userPwConfirm) {
            alert("비밀번호가 일치하지 않습니다.");
        } else {
            event.target.submit();
        }
    });
});