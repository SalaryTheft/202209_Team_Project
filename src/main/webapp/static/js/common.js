/**
 * 문자열 포맷팅 Python 스타일로 구현
 */
String.prototype.format = function () {
    let i = 0, args = arguments;
    return this.replace(/{}/g, function () {
        return typeof args[i] != 'undefined' ? args[i++] : '';
    });
};

/**
 * 요소 내용이 넘치는지 여부 반환
 * @param elem 요소
 * @returns {boolean} 넘치면 true, 아니면 false
 */
function isOverflow(elem) {
    let overflow = elem.style.overflow;
    if (!overflow || overflow === "visible") {
        elem.style.overflow = "hidden";
    }
    let result = elem.clientWidth < elem.scrollWidth || elem.clientHeight < elem.scrollHeight;
    elem.style.overflow = overflow;
    return result;
}


// 모달 열기 함수(모달창에 url 표시)
function modal(url) {
    let modal = document.querySelector("#modal");
    modal.querySelector("embed").src = url;
    modal.style.display = "flex";
}

function modal_large(url) {
    let modal = document.querySelector("#modal_large");
    modal.querySelector("embed").src = url;
    modal.style.display = "flex";
}

function modal_large_close() {
    document.querySelector("#modal_large").style.display = "none";
}

// 알림 수 설정(embed 내에서 호출)
function setNotiCount(count) {
    let noti_count_span = document.querySelector(".noti-count");
    if (count > parseInt(noti_count_span.innerText)) {
        noti_count_span.closest(".noti-btn").classList.add("alert");
        setTimeout(function () {
            noti_count_span.closest(".noti-btn").classList.remove("alert");
        }, 200);
    }
    noti_count_span.innerText = count;
}

// 게시글 본문이 넘칠 경우 그라데이션 효과 및 더보기 버튼 표시
function setOverflowBtns() {
    document.querySelectorAll(".post-body").forEach((item) => {
        if (isOverflow(item)) {
            item.classList.add("overflow");

            let more_btn = document.createElement("button");
            more_btn.classList.add("more-btn");
            more_btn.innerHTML = "더보기";
            more_btn.onclick = function () {
                alert("ㅎㅇ");
            };
            item.parentElement.appendChild(more_btn);
        }

    });
}

document.addEventListener("DOMContentLoaded", function () {
    // 더보기 표시
    setOverflowBtns();

    // 모달 리디렉션
    let urlParams = new URLSearchParams(window.location.search);
    let modal_target = urlParams.get("modal");
    if (modal_target) {
        modal(modal_target);
        parent.history.replaceState(null, null, parent.location.pathname);
    }
    let modal_large_target = urlParams.get("modal_large");
    if (modal_large_target) {
        modal_large(modal_large_target);
        parent.history.replaceState(null, null, parent.location.pathname);
    }

    // 모달 닫기 버튼
    document.querySelector("#modal_close_btn").addEventListener("click", function () {
        document.querySelector("#modal").style.display = "none";
    });

    document.querySelector("#modal_large_close_btn").addEventListener("click", function () {
        document.querySelector("#modal_large").style.display = "none";
    });

    // 모달 배경 클릭 시 닫기
    document.querySelector("#modal").addEventListener("click", function () {
        document.querySelector("#modal").style.display = "none";
    });

    document.querySelector("#modal_large").addEventListener("click", function () {
        document.querySelector("#modal_large").style.display = "none";
    });

    // 로그인 버튼 모달
    document.querySelector("#header_login_btn").addEventListener("click", function (e) {
        e.preventDefault();
        modal("/login");
    });

    // 회원가입 버튼 모달
    document.querySelector("#header_signup_btn").addEventListener("click", function (e) {
        e.preventDefault();
        modal("/signup");
    });

    // 검색 아이콘 클릭 시 검색 인풋에 포커스
    document.querySelector(".header-search-area>form>ion-icon").addEventListener("click", function (e) {
        document.querySelector(".header-search-area>form>input").focus();
    });

    // 프로필 버튼 클릭 시 모달 창 토글
    document.querySelector(".profile-btn").addEventListener("click", function () {
        if (!document.querySelector(".noti-modal").classList.contains("closed")) {
            document.querySelector(".noti-btn").click();
            setTimeout(function () {
                document.querySelector(".profile-btn").click();
            }, 200);
            return false;
        }
        document.querySelector(".profile-modal").classList.toggle("closed");
        document.querySelector(".profile-btn").classList.toggle("active");
    });

    // 알림 버튼 클릭 시 모달 창 토글
    document.querySelector(".noti-btn").addEventListener("click", function (event) {
        if (!document.querySelector(".profile-modal").classList.contains("closed")) {
            document.querySelector(".profile-btn").click();
            setTimeout(function () {
                document.querySelector(".noti-btn").click();
            }, 200);
            return false;
        }
        if (!event.target.classList.contains("active")) {
            document.querySelector(".noti-modal > embed").src = "/noti";
        }
        document.querySelector(".noti-modal").classList.toggle("closed");
        document.querySelector(".noti-btn").classList.toggle("active");
    });

    document.querySelector("input[name='keyword']").addEventListener("focus", function () {
        if (!document.querySelector(".profile-modal").classList.contains("closed")) {
            document.querySelector(".profile-btn").click();
        }
        if (!document.querySelector(".noti-modal").classList.contains("closed")) {
            document.querySelector(".noti-btn").click();
        }
    });
});
