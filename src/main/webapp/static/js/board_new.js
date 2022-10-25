document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("input[name='cancel']").addEventListener("click", function (e) {
        e.preventDefault();
        parent.document.querySelector("#modal_large").style.display = "none";
        return false;
    });
    let form = document.querySelector("form");
    form.addEventListener("submit", function (e) {
        e.preventDefault();
        form.querySelectorAll("input[type='text']").forEach(function (input) {
            input.value = input.value.trim();
            if (input.value.length === 0) {
                alert("내용을 입력해주세요.");
                input.focus();
                return false;
            }
        });
        let formData = new FormData(form);
        let boardName = formData.get("boardName");
        let boardId = formData.get("boardId");
        let boardDesc = formData.get("boardDesc");

        let xhr = new XMLHttpRequest();
        let data = {
            boardName: boardName,
            boardId: boardId,
            boardDesc: boardDesc
        }
        console.log(data);
        xhr.onload = function () {
            let data = JSON.parse(xhr.responseText);
            if (data["result"] === "success") {
                parent.location.href = "/r/" + form.querySelector("input[name='boardId']").value;
            } else {
                alert(data["message"]);
            }
        }
        xhr.open("POST", "/r/new");
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(JSON.stringify(data));
    });
});