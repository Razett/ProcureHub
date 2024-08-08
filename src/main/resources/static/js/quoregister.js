$(document).ready(function () {
    var selectedSuggestionIndex = -1;
    var materialAcount = 2;
    var totalMaterial = 1;

    // 자재 추가 버튼 클릭 이벤트 리스너
    $('#add-material').on('click', function (event) {
        event.preventDefault();

        // 새로운 자재 입력 행 생성
        var newRow = `<tr class="material-row" id="materialDetail${materialAcount}" name="materialDetail${materialAcount}">
                <td><input class="form-control materialCode" id="materialCode${materialAcount}" type="text" name="materialCode${materialAcount}"
                           placeholder="자재 코드" autocomplete="off" required>
                    <div id="autocomplete-suggestions${materialAcount}" class="list-group"></div>
                </td>
                <td><input class="form-control materialName" id="materialName${materialAcount}" type="text" name="materialName${materialAcount}" placeholder="자재명" autocomplete="off">
                    <div id="autocomplete-suggestions-name${materialAcount}" class="list-group"></div>
                </td>
                <td><input class="form-control materialSpec bgc-grey-100" id="materialSpec${materialAcount}" type="text" name="materialSpec${materialAcount}" placeholder="규격" readonly></td>
                <td><input class="form-control materialStock bgc-grey-100" id="materialStock${materialAcount}" type="number" name="materialStock${materialAcount}" placeholder="현 재고 수량" readonly></td>
                <td><input type="number" name="quantity${materialAcount}" id="quantity${materialAcount}" class="form-control" placeholder="월 납품 수량" required></td>
                <td><input type="number" name="unitPrice${materialAcount}" id="unitPrice${materialAcount}" class="form-control" placeholder="단가" required></td>
                <td><input type="number" name="totalPrice${materialAcount}" id="totalPrice${materialAcount}" class="form-control" placeholder="총 금액" required></td>
                <td><input type="number" name="leadTime${materialAcount}" id="leadTime${materialAcount}" class="form-control" placeholder="일 단위 L/T" required></td>
                <td><a href="#" id="remove-material${materialAcount}" class="remove-material form-control bgc-grey-100" data-bs-toggle="tooltip" data-bs-placement="top" data-bs-original-title="삭제"><i class="c-red-700 ti-trash fa-1-25x"></i></a></td>
            </tr>`;

        // 행을 10개까지만 추가
        if (totalMaterial <= 10) {
            $('#quotationMtrlTable .quotationMtrlTbody').append(newRow);
            materialAcount++;
            totalMaterial++;
        } else {
            alert('최대 10개의 자재만 추가할 수 있습니다.');
        }
    });

    // 자재 삭제 버튼 클릭 이벤트 리스너
    $(document).on('click', '.remove-material', function () {
        $(this).closest('tr').remove();
        totalMaterial--;
    });

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
                            var suggestion = $('<div class="list-group-item"></div>')
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
                        var suggestion = $('<div class="list-group-item"></div>')
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
    function fetchMaterialSuggestions(mtrlno, inputElement) {
        $.ajax({
            url: '/rest/material/search',
            type: 'GET',
            data: { mtrlno: mtrlno },
            success: function (data) {
                var suggestions = $(inputElement).siblings('.list-group');
                suggestions.empty();
                selectedSuggestionIndex = -1;

                if (data.length > 0) {
                    data.forEach(function (item) {
                        var suggestion = $('<div class="list-group-item"></div>')
                            .html(`자재코드 : ${item.mtrlno}<br>자재이름: ${item.name}<br>자재그룹명:${item.materialGroup.name}`);
                        suggestion.on('click', function () {
                            selectMaterialCodeSuggestion(item, inputElement);
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

    // 자재명 자동완성 요청을 보내는 함수
    function fetchMaterialNameSuggestions(name, inputElement) {
        $.ajax({
            url: '/rest/material/searchByName',
            type: 'GET',
            data: { name: name },
            success: function (data) {
                var suggestions = $(inputElement).siblings('.list-group');
                suggestions.empty();
                selectedSuggestionIndex = -1;

                if (data.length > 0) {
                    data.forEach(function (item) {
                        var suggestion = $('<div class="list-group-item"></div>')
                            .html(`자재코드 : ${item.mtrlno}<br>자재이름: ${item.name}<br>자재그룹명:${item.materialGroup.name}`);
                        suggestion.on('click', function () {
                            selectMaterialNameSuggestion(item, inputElement);
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

    // 사업자 등록번호 입력 필드에 입력 이벤트 리스너 추가
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
    $(document).on('input', '.materialCode', function () {
        var query = $(this).val();
        if (query.length > 0) {
            fetchMaterialSuggestions(query, this);
        } else {
            $(this).siblings('.list-group').empty().hide();
        }
    });

    // 자재명 입력 필드에 입력 이벤트 리스너 추가
    $(document).on('input', '.materialName', function () {
        var query = $(this).val();
        if (query.length > 0) {
            fetchMaterialNameSuggestions(query, this);
        } else {
            $(this).siblings('.list-group').empty().hide();
        }
    });

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
    // 자재코드 제안 항목 선택 시 동작하는 함수
    function selectMaterialCodeSuggestion(item, inputElement) {
        var idSuffix = $(inputElement).attr('id').replace('materialCode', '');
        $(inputElement).val(item.mtrlno);
        $('#materialName' + idSuffix).val(item.name);
        $('#materialSpec' + idSuffix).val(item.standard);
        $('#materialStock' + idSuffix).val(item.quantity);
        $(inputElement).siblings('.list-group').empty().hide();
    }

    // 자재명 제안 항목 선택 시 동작하는 함수
    function selectMaterialNameSuggestion(item, inputElement) {
        var idSuffix = $(inputElement).attr('id').replace('materialName', '');
        $(inputElement).val(item.name);
        $('#materialCode' + idSuffix).val(item.mtrlno);
        $('#materialSpec' + idSuffix).val(item.standard);
        $('#materialStock' + idSuffix).val(item.quantity);
        $(inputElement).siblings('.list-group').empty().hide();
    }
    // 엔터 및 방향키 이벤트 리스너 추가
    $(document).on('keydown', '.materialCode, .materialName', function (event) {
        var suggestions = $(this).siblings('.list-group').children('.list-group-item');
        if (event.key === 'Enter') {
            event.preventDefault();
            if (selectedSuggestionIndex >= 0 && selectedSuggestionIndex < suggestions.length) {
                suggestions.eq(selectedSuggestionIndex).click();
            }
        } else if (event.key === 'ArrowDown') {
            event.preventDefault();
            if (selectedSuggestionIndex < suggestions.length - 1) {
                selectedSuggestionIndex++;
                suggestions.removeClass('active');
                suggestions.eq(selectedSuggestionIndex).addClass('active');
                suggestions.eq(selectedSuggestionIndex)[0].scrollIntoView({ behavior: 'smooth', block: 'nearest' });
            }
        } else if (event.key === 'ArrowUp') {
            event.preventDefault();
            if (selectedSuggestionIndex > 0) {
                selectedSuggestionIndex--;
                suggestions.removeClass('active');
                suggestions.eq(selectedSuggestionIndex).addClass('active');
                suggestions.eq(selectedSuggestionIndex)[0].scrollIntoView({ behavior: 'smooth', block: 'nearest' });
            }
        }
    });

    $('#contractorName').on('keydown', function (event) {
        var suggestions = $('#autocomplete-suggestions .list-group-item');
        if (event.key === 'Enter') {
            event.preventDefault();
            if (selectedSuggestionIndex >= 0 && selectedSuggestionIndex < suggestions.length) {
                suggestions.eq(selectedSuggestionIndex).click();
            }
        } else if (event.key === 'ArrowDown') {
            event.preventDefault();
            if (selectedSuggestionIndex < suggestions.length - 1) {
                selectedSuggestionIndex++;
                suggestions.removeClass('active');
                suggestions.eq(selectedSuggestionIndex).addClass('active');
                suggestions.eq(selectedSuggestionIndex)[0].scrollIntoView({ behavior: 'smooth', block: 'nearest' });
            }
        } else if (event.key === 'ArrowUp') {
            event.preventDefault();
            if (selectedSuggestionIndex > 0) {
                selectedSuggestionIndex--;
                suggestions.removeClass('active');
                suggestions.eq(selectedSuggestionIndex).addClass('active');
                suggestions.eq(selectedSuggestionIndex)[0].scrollIntoView({ behavior: 'smooth', block: 'nearest' });
            }
        }
    });

    $('#corno').on('keydown', function (event) {
        var suggestions = $('#autocomplete-suggestionsConNums .list-group-item');
        if (event.key === 'Enter') {
            event.preventDefault();
            if (selectedSuggestionIndex >= 0 && selectedSuggestionIndex < suggestions.length) {
                suggestions.eq(selectedSuggestionIndex).click();
            }
        } else if (event.key === 'ArrowDown') {
            event.preventDefault();
            if (selectedSuggestionIndex < suggestions.length - 1) {
                selectedSuggestionIndex++;
                suggestions.removeClass('active');
                suggestions.eq(selectedSuggestionIndex).addClass('active');
                suggestions.eq(selectedSuggestionIndex)[0].scrollIntoView({ behavior: 'smooth', block: 'nearest' });
            }
        } else if (event.key === 'ArrowUp') {
            event.preventDefault();
            if (selectedSuggestionIndex > 0) {
                selectedSuggestionIndex--;
                suggestions.removeClass('active');
                suggestions.eq(selectedSuggestionIndex).addClass('active');
                suggestions.eq(selectedSuggestionIndex)[0].scrollIntoView({ behavior: 'smooth', block: 'nearest' });
            }
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
        if (!$(event.target).closest('.materialCode').length && !$(event.target).closest('.list-group').length) {
            $('.list-group').empty().hide();
        }
        if (!$(event.target).closest('.materialName').length && !$(event.target).closest('.list-group').length) {
            $('.list-group').empty().hide();
        }
    });

    // 견적서 폼 제출 시
    $('#quotationForm').submit(function (event) {
        event.preventDefault();

        var title = $('input[name="title"]').val();
        var content = $('textarea[name="content"]').val();

        if (!title) {
            alert('견적 제목을 입력하세요.');
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

                var materialRows = $('#quotationMtrlTable .quotationMtrlTbody tr');
                var quotationMtrlDataArray = [];

                materialRows.each(function () {
                    var rowId = $(this).attr('id').replace('materialDetail', '');
                    var materialId = $('#materialCode' + rowId).val();
                    var quantity = $('#quantity' + rowId).val();
                    var unitPrice = $('#unitPrice' + rowId).val();
                    var totalPrice = $('#totalPrice' + rowId).val();
                    var leadTime = $('#leadTime' + rowId).val();

                    if (materialId && quantity && unitPrice && totalPrice && leadTime) {
                        var quotationMtrlData = {
                            quotationId: quotationId,
                            materialId: materialId,
                            quantity: quantity,
                            unitprice: unitPrice,
                            totalprice: totalPrice,
                            leadtime: leadTime
                        };
                        quotationMtrlDataArray.push(quotationMtrlData);
                    }
                });

                if (quotationMtrlDataArray.length > 0) {
                    saveQuotationMtrl(quotationMtrlDataArray);
                } else {
                    alert('자재 정보를 입력하세요.');
                }

                window.location.href = `/contractor/quoread?qtno=${quotationId}`;
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
            url: 'http://localhost:8081/uploadFile',
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
    function saveQuotationMtrl(quotationMtrlDataArray) {
        $.ajax({
            url: 'http://localhost:8080/quotationMtrl/save',
            type: 'POST',
            data: JSON.stringify(quotationMtrlDataArray),
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