document.addEventListener("DOMContentLoaded", function (event) {
    document.querySelector("form").addEventListener("submit", function (event) {
        event.preventDefault();
        parent.window.location.reload();
    });
});