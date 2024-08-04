const server_url = 'http://192.168.1.25:8081';
$(document).ready(function () {
    var selectedSuggestionIndex = -1;

    // 회사명 자동완성 요청을 보내는 함수
    function fetchSuggestions(query) {
        $.ajax({
            url: '/contractor/search',
            type: 'GET',
            data: {name: query},
            success: function (data) {
                var suggestions = $('#autocomplete-suggestions');
                suggestions.empty(); // 이전 검색 결과 비우기
                selectedSuggestionIndex = -1; // 초기화

                if (data.length > 0) {
                    var filteredData = data.filter(function (item) {
                        return item.name.charAt(0).toLowerCase() === query.charAt(0).toLowerCase();
                    });

                    if (filteredData.length > 0) {
                        filteredData.sort(function (a, b) {
                            return a.name.localeCompare(b.name, undefined, {sensitivity: 'case'});
                        });

                        filteredData.reverse().forEach(function (item) {
                            var suggestion = $('<div class="autocomplete-suggestion"></div>')
                                .html(`회사명: ${item.name} 사업자등록번호: ${item.corno}`);
                            suggestion.on('click', function () {
                                selectSuggestion(item);
                            });
                            suggestions.append(suggestion);
                        });
                        suggestions.show();
                    } else {
                        suggestions.hide();
                    }
                } else {
                    suggestions.hide();
                }
            }
        });
    }

    // 자재명 자동완성 요청을 보내는 함수
    function fetchMaterialSuggestions(mtrlno) {
        $.ajax({
            url: '/rest/material/search',
            type: 'GET',
            data: {mtrlno: mtrlno},
            success: function (data) {
                var suggestions = $('#autocomplete-suggestions2');
                suggestions.empty(); // 이전 검색 결과 비우기
                selectedSuggestionIndex = -1; // 초기화

                        data.reverse().forEach(function (item) {
                            var suggestion2 = $('<div class="autocomplete-suggestion2"></div>')
                                .html(`자재코드 : ${item.mtrlno} 자재이름: ${item.name} 자재그룹명:${item.materialWarehouse.name}`);
                            suggestion2.on('click', function () {
                                selectMaterialSuggestion(item);
                            });
                            suggestions.append(suggestion2);
                        });
                        suggestions.show();
            }
        });
    }

    // 자재 정보를 가져오는 함수
    function fetchMaterialDetails(mtrlno) {
        $.ajax({
            url: '/rest/material/getMaterialDetailsByCode',
            type: 'GET',
            data: {mtrlno: mtrlno},
            success: function (data) {
                if (data) {
                    displayMaterialDetails(data);
                } else {
                    alert('자재 정보를 찾을 수 없습니다.');
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error('자재 정보를 가져오는 데 실패했습니다:', textStatus, errorThrown);
                alert('자재 정보를 가져오는 데 실패했습니다.');
            }
        });
    }

    // 자재 정보를 화면에 표시하는 함수
    function displayMaterialDetails(material) {
        console.log(material);
        $('#materialDetail').val(`자재코드: ${material.mtrlno}, 자재명: ${material.name}, 그룹명: ${material.materialGroup.name}, 수량: ${material.quantity}, 규격:${material.standard}`);
    }

    // 회사명 제안 항목 선택 시 동작하는 함수
    function selectSuggestion(item) {
        $('#contractorName').val(item.name);
        $('#corno').val(item.corno);
        var address1 = (item.address1);
        var address2 = (item.address2);
        var address = address1 + address2;
        $('#address').val(address || '');
        $('#phone').val(item.phone || '');
        $('#mngrName').val(item.mngrName || '');
        $('#mngrPhone').val(item.mngrPhone || '');
        $('#mngrAddress').val(item.mngrAddress || '');
        $('#autocomplete-suggestions').empty().hide();
    }

    // 자재명 제안 항목 선택 시 동작하는 함수
    function selectMaterialSuggestion(item) {
        $('#materialCode').val(item.mtrlno);
        $('#materialDetail').val(`자재코드: ${item.mtrlno}, 자재명: ${item.name}, 그룹명: `);
        $('#autocomplete-suggestions2').empty().hide();
    }

    // 입력 이벤트 리스너
    $('#contractorName').on('input', function () {
        var query = $(this).val();
        if (query.length > 0) {
            fetchSuggestions(query);
        } else {
            $('#autocomplete-suggestions').empty().hide();
        }
    });

    $('#materialCode').on('input', function () {
        var query = $(this).val();
        if (query.length > 0) {
            fetchMaterialSuggestions(query);
        } else {
            $('#autocomplete-suggestions2').empty().hide();
        }
    });

    // 엔터 및 방향키 이벤트 리스너
    $('#contractorName').on('keydown', function (event) {
        var suggestions = $('#autocomplete-suggestions .autocomplete-suggestion');
        if (event.key === 'Enter') {
            event.preventDefault();
            if (selectedSuggestionIndex >= 0 && selectedSuggestionIndex < suggestions.length) {
                suggestions.eq(selectedSuggestionIndex).click();
            }
        } else if (event.key === 'ArrowDown') {
            event.preventDefault();
            if (selectedSuggestionIndex < suggestions.length - 1) {
                selectedSuggestionIndex++;
                suggestions.removeClass('selected');
                suggestions.eq(selectedSuggestionIndex).addClass('selected');
            }
        } else if (event.key === 'ArrowUp') {
            event.preventDefault();
            if (selectedSuggestionIndex > 0) {
                selectedSuggestionIndex--;
                suggestions.removeClass('selected');
                suggestions.eq(selectedSuggestionIndex).addClass('selected');
            }
        }
    });

    $('#materialCode').on('keydown', function (event) {
        var suggestions = $('#autocomplete-suggestions2 .autocomplete-suggestion2');
        if (event.key === 'Enter') {
            event.preventDefault();
            if (selectedSuggestionIndex >= 0 && selectedSuggestionIndex < suggestions.length) {
                suggestions.eq(selectedSuggestionIndex).click();
            }
        } else if (event.key === 'ArrowDown') {
            event.preventDefault();
            if (selectedSuggestionIndex < suggestions.length - 1) {
                selectedSuggestionIndex++;
                suggestions.removeClass('selected');
                suggestions.eq(selectedSuggestionIndex).addClass('selected');
            }
        } else if (event.key === 'ArrowUp') {
            event.preventDefault();
            if (selectedSuggestionIndex > 0) {
                selectedSuggestionIndex--;
                suggestions.removeClass('selected');
                suggestions.eq(selectedSuggestionIndex).addClass('selected');
            }
        }
    });

    // 자재코드 엔터 이벤트 리스너
    $('#materialCode').on('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            fetchMaterialDetails($(this).val());
        }
    });

    // 견적서 폼 제출 시
    $('#quotationForm').submit(function (event) {
        event.preventDefault();

        var title = $('input[name="title"]').val();
        var content = $('input[name="content"]').val();
        var quantity = $('input[name="quantity"]').val();
        var unitPrice = $('input[name="unitPrice"]').val();
        var totalPrice = $('input[name="totalPrice"]').val();
        var leadTime = $('input[name="leadTime"]').val();

        if (!title || !content || !quantity || !unitPrice || !totalPrice || !leadTime) {
            alert('모든 필수 항목을 입력해주세요.');
            return;
        }

        var quotationData = {
            title: title,
            content: content,
            status: 0,
            contractor: {
                corno: $('input[name="corno"]').val()
            },
            emp: {
                empno: $('input[name="empno"]').val()
            }
        };
        $.ajax({
            url: 'http://localhost:8080/quotation/save',
            type: 'POST',
            data: JSON.stringify(quotationData),
            contentType: 'application/json',
            success: function (response) {
                var quotationId = response;

                var files = $('input[name="file"]')[0].files;
                if (files.length > 0) {
                    uploadFilesWithQuotationId(quotationId);
                }

                var materialId = $('#materialCode').val();
                var quotationMtrlData = {
                    quotationId: quotationId,
                    materialId: materialId,
                    quantity: quantity,
                    unitprice: unitPrice,
                    totalprice: totalPrice,
                    leadtime: leadTime
                };
                saveQuotationMtrl(quotationMtrlData);
                window.location.href = '/contractor/quolist';
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error('견적서 저장 실패:', textStatus, errorThrown);
                alert('견적서 저장에 실패했습니다.');
            }
        });
    });

    // 파일 업로드 함수
    function uploadFilesWithQuotationId(quotationId) {
        var fileData = new FormData();
        var files = $('input[name="file"]')[0].files;

        for (var i = 0; i < files.length; i++) {
            fileData.append('file', files[i]);
        }
        $.ajax({
            url: server_url + '/uploadFile',
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
            url: 'http://localhost:8080/quotationFile/save',
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

    // 견적 자재 정보 저장 함수
    function saveQuotationMtrl(quotationMtrlData) {
        $.ajax({
            url: 'http://localhost:8080/quotationMtrl/save',
            type: 'POST',
            data: JSON.stringify(quotationMtrlData),
            contentType: 'application/json',
            success: function (response) {
                console.log('견적 자재 정보 저장 성공:', response);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error('견적 자재 정보 저장 실패:', textStatus, errorThrown);
            }
        });
    }
});
