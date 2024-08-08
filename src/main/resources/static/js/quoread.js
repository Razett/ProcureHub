$(document).ready(function () {
    var selectedSuggestionIndex = -1;
    var materialId = $("#inputquoCode").val();

    // 파일을 읽어와서 내용을 화면에 표시하는 함수
    function readFileAndDisplay() {
        var fileInput = $('input[name="file"]')[0];
        var files = fileInput.files;

        if (files.length > 0) {
            var file = files[0];
            var reader = new FileReader();

            reader.onload = function (e) {
                var fileContent = e.target.result;
                displayFileContent(fileContent);
            };

            reader.readAsDataURL(file); // 파일을 Data URL로 읽음 (이미지 파일 등 미리보기에 적합)
        }
    }

    // 파일 내용을 화면에 표시하는 함수
    function displayFileContent(fileContent) {
        var img = $('<img>').attr('src', fileContent).css({
            'height': '180px',
            'object-fit': 'cover'
        });
        $('#fileDisplay').html(img);
    }

    // 파일 선택 시 파일을 읽어오는 이벤트 핸들러
    $('input[name="file"]').on('change', function (e) {
        readFileAndDisplay();
    });
});