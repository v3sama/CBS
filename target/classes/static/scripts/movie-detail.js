// var pageIndex = Number(${pageIndex});
let locationPick = 1;
$("document").ready(function () {
    $.renderUser();
    $.getMovie();

    renderReview();
    let tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tabcontent[0].style.display = "block";
    let tablinks = document.getElementsByClassName("tablinks");
    tablinks[0].classList.add('active')


})

let username ="";
let citySelect ="";

function openCity(evt, cityName) {	// function openCity(evt, cityName) {
    // Declare all variables	//     // Declare all variables
    var i, tabcontent, tablinks;	//     var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them	//     // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");	//     tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {	//     for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";	//         tabcontent[i].style.display = "none";
    }	//     }

    // Get all elements with class="tablinks" and remove the class "active"	//     // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");	//     tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {	//     for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");	//         tablinks[i].className = tablinks[i].className.replace(" active", "");
    }	//     }

    // Show the current tab, and add an "active" class to the link that opened the tab	//     // Show the current tab, and add an "active" class to the link that opened the tab
    document.getElementById(cityName).style.display = "block";	//     document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";	//     evt.currentTarget.className += " active";
}

$.getUser = function () {
    let dataUser
    $.ajax({
        type: "get",
        url: "http://localhost:8080/api/UserSession",
        async: false,
        dataType: "json",
        success: function (data) {
            dataUser = data
        }
    })
    return dataUser;
}

$.renderUser = function () {
    dataUser = $.getUser();
    if (dataUser!==undefined){
        username = dataUser.name
        $('#user-acc-name').empty();
        $('#user-acc-name').append(dataUser.name);
        $('#user-acc-link').attr("href", "/user/"+dataUser.name) ;
        $('.nav-right').prepend(function () {
            return '<a style="float: right;" href=\"/logout\">Logout</a>';
        })
    }
}

$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null) {
        return null;
    }
    return decodeURI(results[1]) || 0;
}

$.getMovie = function () {
    const id = $.urlParam("id");
    const placeholder = "https://via.placeholder.com/215x318";
    let dataMovie
    $.ajax({
        type: "get",
        url: "http://localhost:8080/api/movieDetail",
        data: {
            "id" :id
        },
        async: false,
        dataType: "json",
        success: function (data) {
            console.log(data)
            dataMovie = data
        }
    });
    hinh = (dataMovie.movie_image === null || dataMovie.movie_image === "")? placeholder : dataMovie.movie_image;
    $("#movie-img").attr("src",hinh);
    $('.dataRe').append(dataMovie.date_release);
    $('.dataRateTy').append(dataMovie.rate_type);
    $('.dataName').append(dataMovie.movie_title);
    $('.dataInfoFra').append(dataMovie.duration + " - " + dataMovie.format_type);
    $('.dataDiem').append(dataMovie.avg_point + "/5");
    $('.dataDirec').append(dataMovie.director);

    let genres = JSON.parse(dataMovie.genres);
    let actors = JSON.parse(dataMovie.actors);
    $('.dataGenre').append(genres.join(', '));
    $('.dataActor').append(actors.join(', '));
    $('.dataFormat').append(dataMovie.format_type);
    $('.dataLang').append(dataMovie.language);
    $('.dataDesc').append(dataMovie.description);

}

let submitReview = function () {
    let content = $('#review-box').val()
    const movieid = $.urlParam("id");
    let star = $('input:radio[name="rating"]:checked').attr("id");
    data = {
        "content" :content,
        "star" : star,
        "movie" : movieid
    };
    // if (username === ""){
    //     window.location.href = "http://localhost:8080/login";
    // }else {
    $.ajax({
        type: "post",
        url: "http://localhost:8080/api/postReview",
        data: JSON.stringify(data),
        async: false,
        contentType: "application/json",
        success: function (data) {
            console.log(data)
            if (data === "davote"){
                alert("Bạn đã review lúc trước, không thể review lại");
            }
            if (data === "vui"){
                // alert("Review thành công");
                renderReview();
            }
            if (data === "dangnhap"){
                window.location.href = "http://localhost:8080/login";
            }
        }
    }).fail(function () {
        alert("He thong dang gap su co, xin vui long thu lai sau")
    });
    // }
}

let renderReview = function () {
    const movieid = $.urlParam("id");
    $('#content-reviews').empty();
    $.ajax({
        type: "get",
        url: "http://localhost:8080/api/ratingList",
        data: {
            "page" : 0,
            "movie" : movieid
        },
        async: true,
        contentType: "application/json",
        success: function (data) {
            $.each(data, function (index, itemData) {
                $('#content-reviews').append('<div class="user-reviews">' +
                    '<h3 class="user-review-name"> '+ itemData.uname +' </h3>' +
                    '<p class="user-review-content">'+ itemData.content +' </p>' +
                    '</div>')
            })

        }
    })

}

