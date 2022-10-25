document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".paging a").forEach(function (a) {
        a.addEventListener("click", function (e) {
            e.preventDefault();
            // URL page 파라메터 변경 후 이동
            let target_page = e.target.getAttribute("data-page");
            let url = new URL(location.href);
            url.searchParams.set("page", target_page);
            location.href = url.toString();
        });
    });

    document.querySelector(".search-options select[name='range']")?.addEventListener("change", function (e) {
        let url = new URL(location.href);
        url.searchParams.set("range", e.target.value);
        url.searchParams.set("page", "1");
        location.href = url.toString();
    });

    document.querySelector(".search-options select[name='type']")?.addEventListener("change", function (e) {
        let url = new URL(location.href);
        url.searchParams.set("type", e.target.value);
        url.searchParams.set("page", "1");
        location.href = url.toString();
    });
});