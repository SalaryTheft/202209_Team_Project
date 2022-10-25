document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("input[type='color']").addEventListener("change", function (e) {
        document.documentElement.style.setProperty("--highlight-color", e.target.value);
    });

    document.querySelector(".board-icon").addEventListener("click", function (e) {
        document.querySelector("input[name='boardIconRaw']").click();
    });

    document.querySelector(".board-background").addEventListener("click", function (e) {
        document.querySelector("input[name='boardBackgroundRaw']").click();
    });

    document.querySelector("input[name='boardIconRaw']").addEventListener("change", function (e) {
        let reader = new FileReader();
        reader.onload = function () {
            let image = new Image();
            image.src = reader.result;
            image.onload = function () {
                let canvas = document.createElement("canvas");
                let ctx = canvas.getContext("2d");
                canvas.height = 150;
                canvas.width = image.width * (150 / image.height);
                ctx.drawImage(image, 0, 0, canvas.width, canvas.height);
                let dataURL = canvas.toDataURL("image/jpeg", 0.8);
                document.querySelector(".board-icon").style.backgroundImage = "url(" + dataURL + ")";
                document.querySelector("input[name='boardIcon']").value = dataURL;
            }
        }
        reader.readAsDataURL(e.target.files[0]);
    });

    document.querySelector("input[name='boardBackgroundRaw']").addEventListener("change", function (e) {
        let reader = new FileReader();
        reader.onload = function () {
            let image = new Image();
            image.src = reader.result;
            image.onload = function () {
                let canvas = document.createElement("canvas");
                let ctx = canvas.getContext("2d");
                canvas.height = 500;
                canvas.width = image.width * (500 / image.height);
                ctx.drawImage(image, 0, 0, canvas.width, canvas.height);
                let dataURL = canvas.toDataURL("image/jpeg", 0.8);
                document.querySelector(".board-background").style.backgroundImage = "url(" + dataURL + ")";
                document.querySelector("input[name='boardBackground']").value = dataURL;
            }
        }
        reader.readAsDataURL(e.target.files[0]);
    });
});