$(document).ready(function () {
    var selectedSuggestionIndex = -1;

    // 회사명 자동완성 요청을 보내는 함수
    function fetchSuggestions(query) {
        $.ajax({
            url: '/contractor/findByNameContaining',
            type: 'GET',
            data: { name: query },
            success: function (data) {
                var suggestions = $('#autocomplete-suggestions');
                suggestions.empty();
                selectedSuggestionIndex = -1;

                if (data.length > 0) {
                    var filteredData = data.filter(function (item) {
                        return item.name.charAt(0).toLowerCase() === query.charAt(0).toLowerCase();
                    });

                    if (filteredData.length > 0) {
                        filteredData.sort(function (a, b) {
                            return a.name.localeCompare(b.name, undefined, { sensitivity: 'case' });
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

    // 사업자 등록번호로 자동완성
    function fetchConNumSuggestions(corno) {
        $.ajax({
            url: '/contractor/findByCornoContaining',
            type: 'GET',
            data: { corno: corno },
            success: function (data) {
                var suggestions = $('#autocomplete-suggestionsConNums');
                suggestions.empty();
                selectedSuggestionIndex = -1;

                if (data.length > 0) {
                    data.forEach(function (item) {
                        var suggestion = $('<div class="autocomplete-suggestion"></div>')
                            .html(`사업자등록번호: ${item.corno} 회사명: ${item.name}`);
                        suggestion.on('click', function () {
                            selectConNumSuggestion(item);
                        });
                        suggestions.append(suggestion);
                    });
                    suggestions.show();
                } else {
                    suggestions.hide();
                }
            }
        });
    }

    // 자재코드 자동완성 요청을 보내는 함수
    function fetchMaterialSuggestions(mtrlno) {
        $.ajax({
            url: '/rest/material/search',
            type: 'GET',
            data: { mtrlno: mtrlno },
            success: function (data) {
                var suggestions = $('#autocomplete-suggestions2');
                suggestions.empty();
                selectedSuggestionIndex = -1;

                if (data.length > 0) {
                    data.forEach(function (item) {
                        var suggestion = $('<div class="autocomplete-suggestion"></div>')
                            .html(`자재코드 : ${item.mtrlno} 자재이름: ${item.name} 자재그룹명:${item.materialGroup.name}`);
                        suggestion.on('click', function () {
                            selectMaterialSuggestion(item);
                        });
                        suggestions.append(suggestion);
                    });
                    suggestions.show();
                } else {
                    suggestions.hide();
                }
            }
        });
    }

// 회사명 제안 항목 선택 시 동작하는 함수
    function selectSuggestion(item) {
        $('#contractorName').val(item.name);
        $('#corno').val(item.corno);
        var address1 = item.address1 || '';
        var address2 = item.address2 || '';
        var address = address1 + ' ' + address2; // 주소1과 주소2를 결합
        $('#address').val(address);
        $('#phone').val(item.phone || '');
        $('#autocomplete-suggestions').empty().hide();
    }

// 사업자 등록번호 제안 항목 선택 시 동작하는 함수
    function selectConNumSuggestion(item) {
        $('#corno').val(item.corno);
        $('#contractorName').val(item.name);
        var address1 = item.address1 || '';
        var address2 = item.address2 || '';
        var address = address1 + ' ' + address2; // 주소1과 주소2를 결합
        $('#address').val(address);
        $('#phone').val(item.phone || '');
        $('#autocomplete-suggestionsConNums').empty().hide();
    }

    // 자재명 제안 항목 선택 시 동작하는 함수
    function selectMaterialSuggestion(item) {
        $('#materialCode').val(item.mtrlno);
        $('#materialDetail').val(`자재코드: ${item.mtrlno}, 자재명: ${item.name}, 그룹명: ${item.materialGroup.name}`);
        $('#autocomplete-suggestions2').empty().hide();
    }

    // corno 입력 필드에 입력 이벤트 리스너 추가
    $('#corno').on('input', function () {
        var query = $(this).val();
        if (query.length > 0) {
            fetchConNumSuggestions(query);
        } else {
            $('#autocomplete-suggestionsConNums').empty().hide();
        }
    });

    // 회사명 입력 필드에 입력 이벤트 리스너 추가
    $('#contractorName').on('input', function () {
        var query = $(this).val();
        if (query.length > 0) {
            fetchSuggestions(query);
        } else {
            $('#autocomplete-suggestions').empty().hide();
        }
    });

    // 자재코드 입력 필드에 입력 이벤트 리스너 추가
    $('#materialCode').on('input', function () {
        var query = $(this).val();
        if (query.length > 0) {
            fetchMaterialSuggestions(query);
        } else {
            $('#autocomplete-suggestions2').empty().hide();
        }
    });

    // 엔터 및 방향키 이벤트 리스너 추가
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

    $('#corno').on('keydown', function (event) {
        var suggestions = $('#autocomplete-suggestionsConNums .autocomplete-suggestion');
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
        var suggestions = $('#autocomplete-suggestions2 .autocomplete-suggestion');
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

    // 자동완성 목록 외부를 클릭했을 때 자동완성 목록 숨기기
    $(document).on('click', function (event) {
        if (!$(event.target).closest('#contractorName').length && !$(event.target).closest('#autocomplete-suggestions').length) {
            $('#autocomplete-suggestions').empty().hide();
        }
        if (!$(event.target).closest('#corno').length && !$(event.target).closest('#autocomplete-suggestionsConNums').length) {
            $('#autocomplete-suggestionsConNums').empty().hide();
        }
        if (!$(event.target).closest('#materialCode').length && !$(event.target).closest('#autocomplete-suggestions2').length) {
            $('#autocomplete-suggestions2').empty().hide();
        }
    });

    // 견적서 폼 제출 시
    $('#quotationForm').submit(function (event) {
        event.preventDefault();

        var title = $('input[name="title"]').val();
        var content = $('textarea[name="content"]').val();
        var quantity = $('input[name="quantity"]').val();
        var unitPrice = $('input[name="unitPrice"]').val();
        var totalPrice = $('input[name="totalPrice"]').val();
        var leadTime = $('input[name="leadTime"]').val();

        if (!title || !quantity || !unitPrice || !totalPrice || !leadTime) {
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
            url: 'http://localhost:8080/uploadFile',
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
