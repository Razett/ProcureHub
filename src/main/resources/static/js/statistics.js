// const url = 'http://m-it.iptime.org:8030';
const url = '';

var StatisticsService = (function (){

    function getPrdcPlanStat(callback) {
        $.ajax({
            method: 'GET',
            url: url + '/rest/utils/prdcPlanDoughnut',
            contentType: 'application/json',
            success: function (data) {
                if (callback) {
                    console.log(data);
                    callback(data);
                }
            }
        });
    }

    return {
        getPrdcPlanStat:getPrdcPlanStat
    };
})();