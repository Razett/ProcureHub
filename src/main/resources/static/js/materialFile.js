const server_url = 'https://file.glkids.site';
const url = '';

$(document).ready(function () {
    var selectedSuggestionIndex = -1;
    var MaterialId = $("#inputMtrlCode").val()
    // 파일 업로드 함수
    function uploadMatrialFile (MaterialId) {
        var fileData = new FormData();
        var files = $('input[name="file"]')[0].files;



        for (var i = 0; i < files.length; i++) {
            fileData.append('file', files[i]);
        }
        $.ajax({
            url: server_url + '/materialFile',
            type: 'POST',
            data: fileData,
            processData: false,
            contentType: false,
            success: function (response) {
                var metadata = {
                    name: response.name,
                    url: response.url,
                    uuid: response.uuid,
                    qtno: quotationId
                };
                sendFileMetadataToMainServer(metadata);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error('파일 업로드 실패:', textStatus, errorThrown);
                alert('파일 업로드에 실패했습니다.');
            }
        });


    }


    // 메타데이터를 메인 서버로 전송하는 함수
    function sendFileMetadataToMainServer(metadata) {
        $.ajax({
            url: url + '/quotationFile/save',
            type: 'POST',
            data: JSON.stringify(metadata),
            contentType: 'application/json',
            success: function (response) {
                window.location.href = '/contractor/quolist';
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error('파일 메타데이터 저장 실패:', textStatus, errorThrown);
                alert('파일 메타데이터 저장에 실패했습니다.');
            }
        });
    }
});
