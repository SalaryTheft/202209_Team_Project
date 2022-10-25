document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("input[name='emojiDataRaw']").addEventListener("change", function (e) {
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
                document.querySelector("input[name='emojiData']").value = dataURL;
                console.log(dataURL);
            }
        }
        reader.readAsDataURL(e.target.files[0]);
    });
});

//preview image
var imgTarget = $('.preview-image .upload-hidden');

imgTarget.on('change', function(){
    var parent = $(this).parent();
    parent.children('.upload-display').remove();

    if(window.FileReader){
        //image 파일만
        if (!$(this)[0].files[0].type.match(/image\//)) return;

        var reader = new FileReader();
        reader.onload = function(e){
            var src = e.target.result;
            parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img src="'+src+'" class="upload-thumb"></div></div>');
        }
        reader.readAsDataURL($(this)[0].files[0]);
    }

    else {
        $(this)[0].select();
        $(this)[0].blur();
        var imgSrc = document.selection.createRange().text;
        parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img class="upload-thumb"></div></div>');

        var img = $(this).siblings('.upload-display').find('img');
        img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""+imgSrc+"\")";
    }
});