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


function confirmCheckout() {
    // let dataGhe = gomGhe()
    // let sessionid = $.urlParam('session')
    // let Data = {"sesson" : sessionid, "dataghe":dataGhe}
    // let postData = JSON.stringify(Data)
    let orderid = $.urlParam('code')
    let paymentmethod = "";
    if ($("#cash").prop("checked")) {
        paymentmethod = "cash";
    }else {
        paymentmethod = "card";
    }

    let data = {"order" :orderid, "payment" : paymentmethod}


    $.ajax({
        type: "post",
        url: "http://localhost:8080/api/checkout",
        data: postData,
        async: false,
        contentType: "application/json",
        success: function (data) {
            if (data.length>0){
                window.location.href = "http://localhost:8080/confirmVe?code=" + data;
            }
        }
    })
}