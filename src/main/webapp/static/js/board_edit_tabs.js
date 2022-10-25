function get_tab_names() {
    let result = [];
    document.querySelectorAll(".tab").forEach(function (tab) {
        result.push(tab.querySelector(".tab-name").textContent);
    });
    return result;
}

function update_tab_order() {
    let boardTabs = [];
    get_tab_names().forEach(function (tabName, index) {
        boardTabs.push({
                tabName: tabName,
                tabOrder: index + 1
            });
        }
    );
    let xhr = new XMLHttpRequest();
    let data = {
        action: "reorder",
        boardTabs: boardTabs
    };
    console.log(data);
    xhr.onload = function () {
        let data = JSON.parse(xhr.responseText);
        if (data.result === "success") {
            window.location.reload();
        } else {
            alert(data.message);
        }
    }
    xhr.open("POST", window.location.href);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(data));
}

document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("#add_tab_btn").addEventListener("click", function () {
        let new_tab_name = prompt("추가할 탭 이름을 입력하세요.").trim();
        console.log(new_tab_name);
        if (new_tab_name === null || new_tab_name === "") {
            alert("탭 이름은 공백일 수 없습니다.");
        } else {
            let tab_names = get_tab_names();
            if (tab_names.includes(new_tab_name)) {
                alert("'" + new_tab_name + "'탭은 이미 존재합니다.");
            } else {
                let xhr = new XMLHttpRequest();
                let data = {
                    action: "insert",
                    tabName: new_tab_name
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

    document.querySelectorAll(".tab").forEach(function (tab) {
        tab.querySelector(".tab-edit").addEventListener("click", function () {
            let old_tab_name = tab.querySelector(".tab-name").textContent;
            let new_tab_name = prompt("탭 이름을 입력하세요.", old_tab_name).trim();
            if (new_tab_name === null || new_tab_name === "") {
                alert("탭 이름은 공백일 수 없습니다.");
            } else {
                let tab_names = get_tab_names();
                if (tab_names.includes(new_tab_name)) {
                    alert("'" + new_tab_name + "'탭은 이미 존재합니다.");
                } else {
                    let xhr = new XMLHttpRequest();
                    let data = {
                        action: "update",
                        oldTabName: old_tab_name,
                        newTabName: new_tab_name
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
        tab.querySelector(".tab-delete").addEventListener("click", function () {
            let tab_name = tab.querySelector(".tab-name").textContent;
            if (confirm("정말로 '" + tab_name + "' 탭을 삭제하시겠습니까?")) {
                let xhr = new XMLHttpRequest();
                let data = {
                    action: "delete",
                    tabName: tab_name
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
        });
        tab.querySelector(".tab-move-up").addEventListener("click", function () {
            let prev_tab = tab.previousElementSibling;
            if (prev_tab !== null) {
                let tab_name = tab.querySelector(".tab-name").textContent;
                let prev_tab_name = prev_tab.querySelector(".tab-name").textContent;
                tab.querySelector(".tab-name").textContent = prev_tab_name;
                prev_tab.querySelector(".tab-name").textContent = tab_name;
                update_tab_order();
            }
        });

        tab.querySelector(".tab-move-down").addEventListener("click", function () {
            let next_tab = tab.nextElementSibling;
            if (next_tab !== null) {
                let tab_name = tab.querySelector(".tab-name").textContent;
                let next_tab_name = next_tab.querySelector(".tab-name").textContent;
                tab.querySelector(".tab-name").textContent = next_tab_name;
                next_tab.querySelector(".tab-name").textContent = tab_name;
                update_tab_order();
            }
        });
    });
});