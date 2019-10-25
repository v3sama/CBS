var card = document.getElementById("card");
var i;
card.addEventListener("click", function(){
    
    var content = document.getElementById("content");
    content.style.display = "block";
});

var cash = document.getElementById("cash");
cash.addEventListener("click", function () {
    
    var content = document.getElementById("content");
    content.style.display = "none";    
});

$(document).ready(function () {
    let dataOrder = $.getMovieData();
    console.log(dataOrder)
    $('#confirm-seat').append(dataOrder.ghe.toString())
    $('#confirm-date').append(dataOrder.ngay);
    $('#confirm-session').append(dataOrder.suatchieu);
})

$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null) {
        return null;
    }
    return decodeURI(results[1]) || 0;
}

$.getMovieData =  function (){
    let orderid = $.urlParam('code')
    let dataOrderObj
    $.ajax({
        type: "get",
        url: "http://localhost:8080/api/getMovieByOrder",
        data: {
            "order" : orderid
        },
        async: false,
        dataType: "json",
        success: function (data) {
            dataOrderObj = data
        }
    })
    return dataOrderObj
}

$.getUserCardInfo = function () {
    // let orderid = $.urlParam('code')
    let dataOrderObj
    $.ajax({
        type: "get",
        url: "http://localhost:8080/api/getUserCardInfo",
        // data: {
        //     "order" : orderid
        // },
        async: false,
        dataType: "json",
        success: function (data) {
            dataOrderObj = data
        }
    })
    return dataOrderObj
}