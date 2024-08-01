const url = 'http://localhost:8080';

var materialGroupService = (function (){

    function getChildGroup(grpcode, childSelect, endSelect) {
        childSelect.empty();
        childSelect.append('<option value=' + grpcode + ' selected="selected">...</option>');

        if(endSelect) {
            endSelect.empty();
            endSelect.append('<option value=' + grpcode + ' selected="selected">...</option>');
        }

        $.ajax({
            type: 'get',
            url: url + '/rest/material/childgroup?grpcode=' + grpcode,
            success: function (data) {
                $.each(data, function (i, val) {
                    console.log(val.grpcode + val.name);
                    childSelect.append('<option value=' + val.grpcode + '>' + val.name + '</option>');
                });
            }
        });
    }

    return {
        getChildGroup:getChildGroup
    };
})();

var materialWarehouseService = (function (){

    function verifyWrhscode(wrhscode, callback) {
        $.ajax({
            method: 'get',
            url: url + '/rest/material/verify/wrhscode?wrhscode=' + wrhscode,
            contentType: 'application/json',
            success: function (data) {
                if (callback) {
                    callback(data);
                }
            }
        });
    }

    function validateWrhscode(wrhscode) {
        var regex = /^[a-zA-Z0-9\-]{5,30}$/;
        return regex.test(wrhscode);
    }

    function registerWarehouse(wrhscode, wrhsname, callback) {
        $.ajax({
            method: 'POST',
            url: url + '/rest/material/warehouseregister',
            contentType: 'application/json',
            data: JSON.stringify({ wrhscode: wrhscode, wrhsname: wrhsname }),
            success: function (data) {
                if (callback) {
                    callback(data);
                }
            }
        });
    }

    return {
        verifyWrhscode: verifyWrhscode,
        validateWrhscode: validateWrhscode,
        registerWarehouse: registerWarehouse
    };
})();