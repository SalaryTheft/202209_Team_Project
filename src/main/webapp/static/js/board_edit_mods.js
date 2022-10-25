function get_mod_names() {
    let result = [];
    document.querySelectorAll(".mod-name").forEach(function (tab) {
        result.push(tab.innerText);
    });
    return result;
}

document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("#add_mod_btn").addEventListener("click", function () {
        let userNickname = prompt("부관리자 권한을 부여할 사용자의 닉네임을 입력하세요.").trim();
        if (userNickname === null || userNickname === "") {
            alert("닉네임은 공백일 수 없습니다.");
        } else {
            let mod_names = get_mod_names();
            if (mod_names.includes(userNickname)) {
                alert("'" + userNickname + "'님은 이미 부관리자입니다.");
            } else {
                let xhr = new XMLHttpRequest();
                let data = {
                    action: "insert",
                    userNickname: userNickname
                }
                xhr.onload = function () {
                    let data = JSON.parse(xhr.responseText);
                    if (data["result"] === "success") {
                        window.location.reload();
                    } else {
                        alert(data["message"]);
                    }
                }
                xhr.open("POST", window.location.href);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.send(JSON.stringify(data));
            }
        }
    });

    document.querySelectorAll(".mod-delete").forEach(function (btn) {
        btn.addEventListener("click", function (e) {
            let userNickname = e.target.parentElement.parentElement.querySelector(".mod-name").innerText;
            if (!confirm("정말 '" + userNickname + "'님의 부관리자 권한을 제거하시겠습니까")) {
                return;
            }
            let xhr = new XMLHttpRequest();
            let data = {
                action: "delete",
                userNickname: userNickname
            }
            xhr.onload = function () {
                let data = JSON.parse(xhr.responseText);
                if (data["result"] === "success") {
                    alert("삭제 성공");
                    window.location.reload();
                } else {
                    alert(data["message"]);
                }
            }
            xhr.open("POST", window.location.href);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.send(JSON.stringify(data));
        });
    });

    document.querySelector(".mod-transfer").addEventListener("click", function () {
        if (!confirm("게시판의 주관리자 권한을 다른 부관리자에게 위임하면 일반 사용자가 됩니다. 계속하시겠습니까?")) {
            return;
        }
        let userNickname = prompt("이전할 부관리자의 닉네임을 입력하세요.").trim();
        if (userNickname === null || userNickname === "") {
            alert("닉네임은 공백일 수 없습니다.");
        } else {
            let mod_names = get_mod_names();
            if (mod_names.includes(userNickname)) {
                let xhr = new XMLHttpRequest();
                let data = {
                    action: "update",
                    userNickname: userNickname
                }
                xhr.onload = function () {
                    let data = JSON.parse(xhr.responseText);
                    if (data["result"] === "success") {
                        alert("주관리자를 '" + userNickname + "'님으로 이전했습니다.");
                        parent.location.reload();
                    } else {
                        alert(data["message"]);
                    }
                }
                xhr.open("POST", window.location.href);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.send(JSON.stringify(data));
            } else {
                alert("'" + userNickname + "'님은 부관리자가 아니거나 존재하지 않는 사용자입니다..");
            }
        }
    });


});