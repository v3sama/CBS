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

$('input.cc-num').payment('formatCardNumber');
$('input.cc-date').payment('formatCardExpiry');

$(document).ready(function () {

    //Đổ data lên phần xác nhận
    let dataOrder = $.getMovieData();
    console.log("dataOrder" + dataOrder)
    $('#confirm-seat').append(dataOrder.ghe.toString())
    $('#confirm-date').append(dataOrder.ngay);
    $('#confirm-session').append(dataOrder.suatchieu);
    $('#tong-tien').append(dataOrder.amount);

    //Đổ user card info lên phần payment method
    let userCard = $.getUserCardInfo();
    if (userCard.length > 0){
        $('#user-card-number').append(userCard.card_no)
        $('#user-card-date-exp').append(userCard.card_date)
    }
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
    });
    return dataOrderObj
}

function clickValidateCheckout() {
    let cardNumber = $('#user-card-number').val().length
    let expiry = $('#user-card-date-exp').val().length
    let cvv = $('#user-card-cvv').val().length
    console.log(cardNumber)
    console.log(expiry)
    console.log(cvv)
    if ($("#card").prop("checked")) {
        if (cardNumber === 19 && expiry === 9 && cvv === 3) {
            confirmCheckout()
        }else {
            alert("Please enter card info")
        }
    }else {
        confirmCheckout()
    }
}

function confirmCheckout() {
    let orderid = $.urlParam('code')
    let paymentmethod = ""
    let cardInfo = []
    if ($("#cash").prop("checked")) {
        paymentmethod = "cash"
    }else {
        paymentmethod = "card"
    }
    if (paymentmethod === "card"){
        cardInfo.push($.trim($('#user-card-number').val()))
        cardInfo.push($.trim($('#user-card-date-exp').val()))
        cardInfo.push($.trim($('#user-card-cvv').val()))
    }

    let data = {"order" :orderid, "payment" : paymentmethod, "cardinfo" : cardInfo}
    let postData = JSON.stringify(data);
    console.log(postData)
    $.ajax({
        type: "post",
        url: "http://localhost:8080/api/checkout",
        data: postData,
        async: false,
        contentType: "application/json",
        success: function (data) {
            console.log(data)
            if (data.length>0){
                window.location.href = "http://localhost:8080/booksuccess?ordercode="+data;
            }
        }
    })
}
