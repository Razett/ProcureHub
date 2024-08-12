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

    function verifyGrpcode(grpcode, callback) {
        $.ajax({
            method: 'get',
            url: url + '/rest/material/verify/grpcode?grpcode=' + grpcode,
            contentType: 'application/json',
            success: function (data) {
                if (callback) {
                    callback(data);
                }
            }
        });
    }

    function registerGroup(grpcode, gname, pGrpcode, callback) {
        $.ajax({
            method: 'POST',
            url: url + '/rest/material/groupregister',
            contentType: 'application/json',
            data: JSON.stringify({ grpcode: grpcode, pgrpcode: pGrpcode, name: gname }),
            success: function (data) {
                if (callback) {
                    callback(data);
                }
            }
        });
    }

    function updateGroupname(grpcode, name, callback) {
        $.ajax({
            method: 'POST',
            url: url + '/rest/material/groupupdate',
            contentType: 'application/json',
            data: JSON.stringify({ grpcode: grpcode, name: name }),
            success: function (data) {
                if (callback) {
                    callback(data);
                }
            }
        });
    }

    return {
        getChildGroup:getChildGroup,
        verifyGrpcode:verifyGrpcode,
        registerGroup:registerGroup,
        updateGroupname:updateGroupname
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
        var regex = /^[a-zA-Z0-9\-]{1,30}$/;
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

    function updateWarehouse(wrhscode, wrhsname, callback) {
        $.ajax({
            method: 'POST',
            url: url + '/rest/material/warehouseupdate',
            contentType: 'application/json',
            data: JSON.stringify({ wrhscode: wrhscode, wrhsname: wrhsname }),
            success: function (data) {
                if (callback) {
                    callback(data);
                }
            }
        });
    }

    function deleteWarehouse(wrhscode, callback) {
        $.ajax({
            method: 'POST',
            url: url + '/rest/material/warehousedelete',
            contentType: 'application/json',
            data: JSON.stringify({ wrhscode: wrhscode }),
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
        registerWarehouse: registerWarehouse,
        updateWarehouse: updateWarehouse,
        deleteWarehouse: deleteWarehouse
    };
})();