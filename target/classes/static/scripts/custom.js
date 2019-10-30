
//format number 
function formatNumber(id) {
    var item = $(id).text();
    var num = Number(item).toLocaleString('en');
    if (Number(item) < 0) {
        num = '(' + num.replace('-', '') + ')';
        $(id).addClass('negMoney');
    } else {
        $(id).addClass('enMoney');
    }

    $(id).text(num);
}