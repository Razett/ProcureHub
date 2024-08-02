$(document).ready(function () {
    var wrhscodeVerified = false;
    var wrhsnameVerified = false;

    var wrhscode = '';
    var wrhsname = '';

    const alertModal = $('#alertModal');

    $('#warehouseAddBtn').on('click', function (e) {
        e.preventDefault();

        wrhscodeVerified = false;
        wrhsnameVerified = false;

        $("#warehouseAddModal").modal('show');
    });

    var typingTimer; // Timer identifier
    var doneTypingInterval = 1500; // Time in milliseconds (1.5 seconds)
    var addWrhsSubmitButton = $('#addWrhsSubmit');
    var subWrhscode = $('#subWrhscode');

    $('#inputWrhscode').on('input', function () {
        wrhscodeVerified = false;
        if (!addWrhsSubmitButton.hasClass('disabled')) {
            addWrhsSubmitButton.addClass('disabled');
        }
        subWrhscode.text('창고 코드는 5자리 이상 30자리 미만의 영문자, 숫자, -만 입력 가능합니다.');
        if (!subWrhscode.hasClass('c-grey-700')) {
            subWrhscode.addClass('c-grey-700');
        }
        subWrhscode.removeClass('c-green-800').removeClass('c-red-700');

        wrhscode = $(this).val();

        clearTimeout(typingTimer); // Clear the previous timer

        if (wrhscode.length > 5) {
            // Set a new timer
            var isValid = materialWarehouseService.validateWrhscode(wrhscode);
            if (isValid) {
                typingTimer = setTimeout(function () {
                    console.log(wrhscode);
                    materialWarehouseService.verifyWrhscode(wrhscode, function (data) {
                        console.log(data);
                        if (data) {
                            wrhscodeVerified = true;
                            subWrhscode.text('사용 가능합니다.');
                            subWrhscode.addClass('c-green-800').removeClass('c-grey-700').removeClass('c-red-700');

                            if (enableAddWrhsUsbmitButton(wrhscodeVerified, wrhsnameVerified))
                                addWrhsSubmitButton.removeClass('disabled');
                        } else {
                            subWrhscode.text('이미 존재하는 창고 코드입니다. 다시 입력하세요.');
                            subWrhscode.addClass('c-red-800').removeClass('c-grey-700').removeClass('c-green-700');
                        }
                    });
                }, doneTypingInterval);
            } else {
                subWrhscode.text('입력 값이 올바르지 않습니다. 다시 입력하세요.');
                subWrhscode.addClass('c-red-800').removeClass('c-grey-700').removeClass('c-green-700');
            }
        } else {
            subWrhscode.text('창고 코드는 5자리 이상 30자리 미만의 영문자, 숫자, -만 입력 가능합니다.');
            subWrhscode.addClass('c-grey-700').removeClass('c-red-800').removeClass('c-green-700');
        }
    });

    $('#inputWrhsname').on('input', function () {
        wrhsnameVerified = false;
        if (!addWrhsSubmitButton.hasClass('disabled')) {
            addWrhsSubmitButton.addClass('disabled');
        }

        wrhsname = $(this).val();

        if (wrhsname.length > 0) {
            wrhsnameVerified = true;
        }

        if (enableAddWrhsUsbmitButton(wrhscodeVerified, wrhsnameVerified))
            addWrhsSubmitButton.removeClass('disabled');
    });

    addWrhsSubmitButton.on('click', function () {
        materialWarehouseService.registerWarehouse(wrhscode, wrhsname, function (data) {
            if (data) {
                var newRow = '<tr>' +
                    '<td class="col-md-3 text-center">' + data.wrhscode + '</td>' +
                    '<td class="col-md-8 text-center">' + data.wrhsname + '</td>' +
                    '<td class="col-md-1 text-center">' +
                    '<a href="#" class="px-2 py-2 wrhsUpdateButton" data-bs-toggle="tooltip" data-bs-placement="top" data-bs-original-title="수정하기">' +
                    '<i class="c-grey-900 ti-pencil-alt fa-1x"></i>' +
                    '</a>' +
                    '</td>' +
                    '</tr>';
                $('#warehouse_tbody').append(newRow);
                $('#warehouseAddModal').modal('hide');
                $('#alertModalContent').text('자재 창고가 등록되었습니다.');
                alertModal.modal('show');
            } else {
                $('#warehouseAddModal').modal('hide');
                $('#alertModalContent').text('자재 창고가 등록에 실패하였습니다. 관리자에게 문의하세요');
                alertModal.modal('show');
            }
        })
    });

    var currentWrhscode = '';
    var currentWrhsname = '';
    var updateWrhscode = '';
    var updateWrhsname = '';

    var $tr;
    var updateWrhsSubmitButton = $('#updateWrhsSubmit');
    var updateSubWrhscode = $('#subUpdateWrhscode');

    $('.wrhsUpdateButton').on('click', function (e) {
        e.preventDefault();

        wrhscodeVerified = true;
        wrhsnameVerified = false;

        $tr = $(this).closest('tr');

        currentWrhscode = $tr.find('td').eq(0).text();
        currentWrhsname = $tr.find('td').eq(1).text();
        updateWrhscode = currentWrhscode;
        updateWrhsname = currentWrhsname;

        $('#inputUpdateWrhscode').val(currentWrhscode);
        $('#inputUpdateWrhsname').val(currentWrhsname);

        $('#warehouseUpdateModal').modal('show');
    });

    $('#inputUpdateWrhscode').on('input', function () {
        wrhscodeVerified = false;
        if (!updateWrhsSubmitButton.hasClass('disabled')) {
            updateWrhsSubmitButton.addClass('disabled');
        }
        updateSubWrhscode.text('창고 코드는 5자리 이상 30자리 미만의 영문자, 숫자, -만 입력 가능합니다.');
        if (!updateSubWrhscode.hasClass('c-grey-700')) {
            updateSubWrhscode.addClass('c-grey-700');
        }
        updateSubWrhscode.removeClass('c-green-800').removeClass('c-red-700');

        updateWrhscode = $(this).val();

        clearTimeout(typingTimer); // Clear the previous timer

        if (updateWrhscode.length > 5) {
            // Set a new timer
            var isValid = materialWarehouseService.validateWrhscode(updateWrhscode);
            if (isValid) {
                typingTimer = setTimeout(function () {
                    materialWarehouseService.verifyWrhscode(updateWrhscode, function (data) {
                        console.log(data);
                        if (data) {
                            wrhscodeVerified = true;
                            updateSubWrhscode.text('사용 가능합니다.');
                            updateSubWrhscode.addClass('c-green-800').removeClass('c-grey-700').removeClass('c-red-700');

                            if (enableAddWrhsUsbmitButton(wrhscodeVerified, wrhsnameVerified))
                                updateWrhsSubmitButton.removeClass('disabled');
                        } else {
                            if (updateWrhscode === currentWrhscode) {
                                wrhscodeVerified = true;
                                updateSubWrhscode.text('창고 코드는 5자리 이상 30자리 미만의 영문자, 숫자, -만 입력 가능합니다.');
                                updateSubWrhscode.addClass('c-grey-700').removeClass('c-red-800').removeClass('c-green-700');
                            }
                            updateSubWrhscode.text('이미 존재하는 창고 코드입니다. 다시 입력하세요.');
                            updateSubWrhscode.addClass('c-red-800').removeClass('c-grey-700').removeClass('c-green-700');
                        }
                    });
                }, doneTypingInterval);
            } else {
                updateSubWrhscode.text('입력 값이 올바르지 않습니다. 다시 입력하세요.');
                updateSubWrhscode.addClass('c-red-800').removeClass('c-grey-700').removeClass('c-green-700');
            }
        } else {
            updateSubWrhscode.text('창고 코드는 5자리 이상 30자리 미만의 영문자, 숫자, -만 입력 가능합니다.');
            updateSubWrhscode.addClass('c-grey-700').removeClass('c-red-800').removeClass('c-green-700');
        }
    });

    $('#inputUpdateWrhsname').on('input', function () {
        wrhsnameVerified = false;
        if (!updateWrhsSubmitButton.hasClass('disabled')) {
            updateWrhsSubmitButton.addClass('disabled');
        }

        updateWrhsname = $(this).val();

        if (updateWrhsname.length > 0) {
            wrhsnameVerified = true;
        }

        if (enableAddWrhsUsbmitButton(wrhscodeVerified, wrhsnameVerified))
            updateWrhsSubmitButton.removeClass('disabled');
    });

    updateWrhsSubmitButton.on('click', function () {
        materialWarehouseService.updateWarehouse(updateWrhscode, updateWrhsname, function (data) {
            if (data) {
                $tr.find('td').eq(0).text(data.wrhscode);
                $tr.find('td').eq(1).text(data.wrhsname);

                $('#warehouseUpdateModal').modal('hide');
                $('#alertModalContent').text('자재 창고가 수정되었습니다.');
                alertModal.modal('show');
            } else {
                $('#warehouseUpdateModal').modal('hide');
                $('#alertModalContent').text('자재 창고가 등록에 실패하였습니다. 관리자에게 문의하세요');
                alertModal.modal('show');
            }
        })
    });

    $('#deleteWrhsButton').on('click', function (e) {
        e.preventDefault();

        $('#warehouseUpdateModal').modal('hide');
        $('#deleteModal').modal('show');
    });

    $('#deleteModalCancel').on('click', function (e) {
        e.preventDefault();

        $('#deleteModal').modal('hide');
        $('#warehouseUpdateModal').modal('show');
    });

    $('#deleteModalSubmit').on('click', function (e) {
        e.preventDefault();

        materialWarehouseService.deleteWarehouse(currentWrhscode, function (data) {
            if (data) {
                $tr.remove();
                $('#deleteModal').modal('hide');
                $('#alertModalContent').text('삭제되었습니다.');
                alertModal.modal('show');
            } else {
                $('#deleteModal').modal('hide');
                $('#alertModalContent').text('삭제에 실패하였습니다. 관리자에게 문의하세요.');
                alertModal.modal('show');
            }
        });
    });

    function enableAddWrhsUsbmitButton(verified1, verified2) {
        return verified1 && verified2;
    }
});
