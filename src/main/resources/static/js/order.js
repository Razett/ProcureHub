// const url = 'http://m-it.iptime.org:8030';
const url = '';

var OrderService = (function (){
    function readOrder(orderno, callback) {
        $.ajax({
            method: 'POST',
            url: '/rest/order/read',
            data: JSON.stringify({orderno: parseInt(orderno)}),
            contentType: 'application/json',
            success: function (data) {
                if (callback) {
                    console.log(data);
                    callback(data);
                }
            }
        });
    }

    function readInspection(orderno, callback) {
        console.log(orderno);
        console.log(JSON.stringify({orderno: parseInt(orderno)}));
        $.ajax({
            method: 'POST',
            url: url + '/rest/order/insread',
            data: JSON.stringify({orderno: parseInt(orderno)}),
            contentType: 'application/json',
            success: function (data) {
                if (callback) {
                    // console.log(data);
                    callback(data);
                }
            }
        });
    }

    function formatDateTime(dateString) {
        // 입력된 문자열을 Date 객체로 변환합니다.
        const date = new Date(dateString);

        // 날짜와 시간 포맷을 지정합니다.
        const options = {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            hour12: true, // 12시간 형식 사용
        };

        // 날짜 및 시간 형식으로 변환합니다.
        const formatter = new Intl.DateTimeFormat('ko-KR', options);
        const parts = formatter.formatToParts(date);

        // 원하는 형식으로 조합합니다.
        const formattedDate = `${parts.find(p => p.type === 'year').value}-${parts.find(p => p.type === 'month').value}-${parts.find(p => p.type === 'day').value} ${parts.find(p => p.type === 'dayPeriod').value} ${parts.find(p => p.type === 'hour').value}:${parts.find(p => p.type === 'minute').value}`;

        return formattedDate;
    }

    function formatDate(dateString) {
        // 입력된 문자열을 Date 객체로 변환합니다.
        const date = new Date(dateString);

        // 날짜와 시간 포맷을 지정합니다.
        const options = {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            hour12: true, // 12시간 형식 사용
        };

        // 날짜 및 시간 형식으로 변환합니다.
        const formatter = new Intl.DateTimeFormat('ko-KR', options);
        const parts = formatter.formatToParts(date);

        // 원하는 형식으로 조합합니다.
        const formattedDate = `${parts.find(p => p.type === 'year').value}-${parts.find(p => p.type === 'month').value}-${parts.find(p => p.type === 'day').value}`;

        return formattedDate;
    }

    return {
        readOrder:readOrder,
        readInspection:readInspection,
        formatDate:formatDate,
        formatDateTime:formatDateTime
    };
})();