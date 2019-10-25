$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null) {
        return null;
    }
    return decodeURI(results[1]) || 0;
}

$(document).ready(function () {
    let ordercode = $.urlParam('ordercode')
    $('#order-code').append(ordercode);
})