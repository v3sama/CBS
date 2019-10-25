$(document).ready(function () {
    var dataSeat = $.getSeatData(),
        $cart = $('#selected-seats'),
        $counter = $('#counter'),
        $total = $('#total'),
        sc = $('#seat-map').seatCharts({
            map: dataSeat.rowMap,
            seats: {
                v: {
                    price: dataSeat.priceVip,
                    classes: 'first-class', //your custom CSS class
                    category: 'Ghe Vip'
                },
                e: {
                    price: dataSeat.priceThuong,
                    classes: 'economy-class', //your custom CSS class
                    category: 'Ghe Thuong'
                },

            },
            naming: {
                rows: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O'],
                top: false,
                getLabel: function (character, row, column) {
                    return "";
                },
                getId  : function(character, row, column) {
                    return row + column;
                }
            },
            legend: {
                node: $('#legend'),
                items: [
                    ['v', 'available', 'Ghe Vip'],
                    ['e', 'available', 'Ghe Thuong'],
                    ['u', 'unavailable', 'Already Booked']
                ]
            },
            animate :false,
            click: function () {
                if (this.status() == 'available') {
                    //let's create a new <li> which we'll add to the cart items
                    // console.log(this.settings);
                    
                    $('<li>' + this.data().category + '  ' + this.settings.id + ': <b>' + this.data().price + '&#273;</b> <a href="#" class="cancel-cart-item">[huy]</a></li>')
                        .attr('id', 'cart-item-' + this.settings.id)
                        .data('seatId', this.settings.id)
                        .appendTo($cart);

                    /*
                     * Lets update the counter and total
                     *
                     * .find function will not find the current seat, because it will change its stauts only after return
                     * 'selected'. This is why we have to add 1 to the length and the current seat price to the total.
                     */
                    $counter.text(sc.find('selected').length + 1);
                    $total.text(recalculateTotal(sc) + this.data().price);
                    return 'selected';
                } else if (this.status() == 'selected') {
                    //update the counter
                    $counter.text(sc.find('selected').length - 1);
                    //and total
                    $total.text(recalculateTotal(sc) - this.data().price);
                    //remove the item from our cart
                    $('#cart-item-' + this.settings.id).remove();

                    //seat has been vacated
                    return 'available';
                } else if (this.status() == 'unavailable') {
                    //seat has been already booked
                    return 'unavailable';
                } else {
                    return this.style();
                }
            }
        });

    //this will handle "[cancel]" link clicks
    $('#selected-seats').on('click', '.cancel-cart-item', function () {
        //let's just trigger Click event on the appropriate seat, so we don't have to repeat the logic here
        sc.get($(this).parents('li:first').data('seatId')).click();
    });

    //let's pretend some seats have already been booked
    // sc.get(['A1', 'C8', 'C7', 'C6']).status('unavailable');
    sc.find('u.available').status('unavailable');

    dataMovie = $.getMovieDetail();
    $('#movie-title').append(dataMovie.tenphim);
    $('#ten-rap').append(dataMovie.rap);
    $('#ngay-chieu').append(dataMovie.ngay);
    $('#suat-chieu').append(dataMovie.suatchieu);
});

function recalculateTotal(sc) {
    var total = 0;

    //basically find every selected seat and sum its price
    sc.find('selected').each(function () {
        total += this.data().price;
    });

    return total;
}

function gomGhe(){
    let dataGhe = []
    $('#selected-seats > li').each(function () {
        dataGhe.push(this.id.slice(10, this.id.length))
    })
    // let sessionid = $.urlParam('session')
    // let Data = {"sesson" : sessionid, "dataghe":dataGhe}
    // let postData = JSON.stringify(Data)
    // console.log(postData)
    return dataGhe
}

$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null) {
        return null;
    }
    return decodeURI(results[1]) || 0;
}

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
};

$.getSeatData =  function getSeatMapData() {
    let $sessionid = $.urlParam('session')
    let dataObj
    $.ajax({
        type: "get",
        url: "http://localhost:8080/api/getSeat",
        data: {
            "session" : $sessionid
        },
        async: false,
        dataType: "json",
        success: function (data) {
            dataObj = data
        }
    })
    return dataObj
}

$.getMovieDetail = function () {
    let $sessionid = $.urlParam('session')
    let movieObj
    $.ajax({
        type: "get",
        url: "http://localhost:8080/api/getMovieBySession",
        data: {
            "session" : $sessionid
        },
        async: false,
        dataType: "json",
        success: function (data) {
            movieObj = data
        }
    })
    return movieObj
}

function checkReview() {
    let dataGhe = gomGhe();
    console.log(dataGhe)
    if (dataGhe.length == 0){
        alert("Vui long chon ghe")
    }else{
        confirmBook()
    }
}

function confirmBook() {
    let dataGhe = gomGhe()
    let sessionid = $.urlParam('session')
    let amount = $('#total').text()
    console.log(amount)
    let Data = {"sesson" : sessionid, "dataghe":dataGhe, "amount":amount}
    let postData = JSON.stringify(Data)
    $.ajax({
        type: "post",
        url: "http://localhost:8080/api/review",
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