var materialGroupService = (function (){

    var url = 'http://localhost:8080';

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