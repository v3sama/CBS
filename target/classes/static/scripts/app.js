$("document").ready(function () {
<<<<<<< HEAD
   //  render tên user đang lỗi
     $.renderUser();
    initSlickTop();
=======
    // render tên user đang lỗi
    $.renderUser();
>>>>>>> refs/remotes/origin/master

    //lấy data phim lên tab đang chiếu - sắp chiếu
    getIndexMovieData();
    initSlickTab();

    //lấy danh sách rạp lên bảng
    //tạo bảng lịch chiếu
    getCinemaList()
    createDataSchedule()


    trailerBox();
    initSelectize();
    $('#sap-chieu').hide();
    hoverMobieBlock();

    let tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tabcontent[0].style.display = "block";
    let tablinks = document.getElementsByClassName("tablinks");
    tablinks[0].classList.add('active')
});

// khai báo biến toàn cục
let cinemaList = [];
let movieList = [];
let schedule = [];


// ĐÓNG MỞ BOX TRAILER
function trailerBox() {
    $('.box-trailer').click(function (e) {
        e.preventDefault();
        var href = $(this).attr('href');
        $( "iframe" ).remove( ".traillerifram" );
        $('.modal-content').append('<iframe class="traillerifram" width="650" height="350" src="' + href + '" frameborder="0"></iframe>');
        $('.modal-box-trailer').show()
    });

    // var span = $('.close')[0];

    $('.close').click(function () {
        $('.modal-box-trailer').hide();
    })
}

function hoverMobieBlock() {
    $('.movie-block').hover(function (e) {
        // console.log($(this).find('.info-detail'));
        $(this).find('.info-detail').toggleClass('hoverHide');
        $(this).find('.movie-block-hover').toggleClass('hoverShow');
        $(this).find('.thumbnail img').toggleClass('blurImg');
    });
}

// MOVIE TAB

$(".tab-slider--nav li").click(function () {
    $('.slick-bla').slick('refresh');
    $(".tab-slider--body").hide();
    var activeTab = $(this).attr("rel");
    $("#" + activeTab).show();
    if ($(this).attr("rel") == "sap-chieu") {
        $('.tab-slider--tabs').addClass('slide');
        $('#sap-chieu').removeClass('inactive')
        $('#sap-chieu').addClass('active')
    } else {
        $('.tab-slider--tabs').removeClass('slide');
    }
    $(".tab-slider--nav li").removeClass("active");
    $(this).addClass("active");
    hoverMobieBlock();
});

// KHỞI TẠO SLICK
function initSlickTab() {
    $('.slick-bla').slick({
        dots: true,
        infinite: false,
        arrow: true,
        speed: 300,
        slidesToShow: 4,
        slidesToScroll: 4
    });
}

function initSlickTop() {
    $('.carousel').slick({
        dots: false,
        arrow: true,
        speed: 600,
        autoplay: true,
        autoplaySpeed: 3500,
        slidesToShow: 1,
        slidesToScroll: 1,
        prevArrow: '<button type="button" class="prevCarousel"><i class="fas fa-chevron-left"></i></button>',
        nextArrow: '<button type="button" class="nextCarousel"><i class="fas fa-chevron-right"></i></button>'
    });
}

function initSelectize() {
    $('#select-beast').selectize({
        create: true,
        sortField: {
            field: 'text',
            direction: 'asc'
        },
        dropdownParent: 'body'
    });
}

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
    cityName.style.display = "block";	//     document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";	//     evt.currentTarget.className += " active";
}

function getCinemaList() {
    // let username = 'user';
    // let password = '123';
    $.ajax({
        type: "get",
        url: "http://localhost:8080/api/cinemaList?provinceId=1",
        // headers: {
        //     'authorization':'Basic '+ btoa(username + ":" + password),
        // },
        data: "data",
        async: false,
        dataType: "json",
        success: function (data) {
            cinemaList = data;
            $.each(data, function (index, itemData) {
                if (index == 0){
                    console.log(index)
                    $('#tab-locate-render').append('<button class="tablinks" onclick="openCity(event,cinema'+ itemData.cinemaId +')" id="defaultOpen">' +
                        '<img src="https://via.placeholder.com/50x50" alt="">' +
                        '<div class="locate-detail">' +
                        '<div class="locate-name">' + itemData.name + '</div>' +
                        '<div class="locate-address">' + itemData.address + '</div>' +
                        '<a href=""><i class="fas fa-long-arrow-alt-right"></i></a>' +
                        '</div>' +
                        '</button>')
                }else{
                    $('#tab-locate-render').append('<button class="tablinks" onclick="openCity(event,cinema'+ itemData.cinemaId +')">' +
                        '<img src="https://via.placeholder.com/50x50" alt="">' +
                        '<div class="locate-detail">' +
                        '<div class="locate-name">' + itemData.name + '</div>' +
                        '<div class="locate-address">' + itemData.address + '</div>' +
                        '<a href=""><i class="fas fa-long-arrow-alt-right"></i></a>' +
                        '</div>' +
                        '</button>')
                }
            })
        }
    });


}

function createDataSchedule() {
    $.each(cinemaList, function (index, itemData) {
        addSessionsToMovie(itemData.cinemaId)
        $('#tab-content-render').append('<div id="cinema'+ itemData.cinemaId +'" class="tabcontent"></div>')
        $.each(movieList, function (indexM, itemDataM) {
            $('#cinema'+itemData.cinemaId).append('<div class="tabcontent-row">' +
                                                '<div class="tab-movie-info">' +
                                                '<img src="http://placehold.it/50x50" alt="">' +
                                                '<p class="tab-movie-title">' +
                                                itemDataM.movie_title +
                                                '</p>' +
                                                '<p class="tab-movie-detail">' +
                                                 itemDataM.duration +'phút - cinema 8 - '+ itemDataM.avg_point +
                                                '</p>' +
                                                '</div>' +
                                                '<div class="tab-movie-session">' +
                                                '<div id="movie'+ itemData.cinemaId + itemDataM.movie_id + '" class="list-time">' +

                                                '</div>' +
                                                '</div>' +
                                                '</div>')
            $.each(itemDataM.sessions, function (indexS, itemDataS) {
                $('#movie' + itemData.cinemaId + itemDataM.movie_id).append(' <a href="datve?session='+ itemDataS.id +'">'+ itemDataS.time.substring(11, 16) +'</a>')
            })
        })

    })
}

function addSessionsToMovie(cinemaId) {
    $.each(movieList, function (index, itemData) {
        itemData.sessions = getSessionByCinemaData(itemData.movie_id, cinemaId)
    })
}

function getSessionByCinemaData(movieId, cinemaId) {
    var sessionList = new Object()
    $.ajax({
        type: "get",
        url: "http://localhost:8080/api/cinemaList1",
        data: {
            "provinceId": 1,
            "cinemaId": cinemaId,
            "movieId": movieId
        },
        async: false,
        dataType: "json",
        success: function (data) {
            sessionList = data
        }
    });
    return sessionList
}

function getIndexMovieData(){
    const imgplaceholder = "http://placehold.it/215x318"
    $.ajax({
        type: "get",
        url: "http://localhost:8080/api/movieShowing",

        data: "data",
        async: false,
        dataType: "json",
        success: function (data) {
            movieList = data;
            $.each(data, function (index, itemData) {
                let hinh = itemData.movie_image===null? imgplaceholder : itemData.movie_image;
                $('#slick-dang-chieu').append('<div class="movie-block">' +
                    '<div class="thumbnail">' +
                    '<img src="'+ hinh +'" alt="" class="movie-block-img">' +
                    '</div>' +
                    '<div class="info-detail">' +
                    '<div class="movie-block-title">' + itemData.movie_title + '</div>' +
                    '<div class="movie-block-detail">' +
                    '<div class="time">' + itemData.duration + ' phút</div>' +
                    '<div class="imdb">' + itemData.avg_point + ' rating</div>' +
                    '</div>' +
                    '</div>' +
                    '<div class="movie-block-hover">' +
                    '<div class="trailerBtn">' +
                    '<a class="box-trailer" href="'+ itemData.trailer_link+'">' +
                    '<i class="far fa-play-circle"></i>' +
                    '</a></div>' +
                    '<div class="button-container datveBtn">' +
                    '<a href="movie?id='+ itemData.movie_id +'" class="button">dat ngay</a>' +
                    '</div>' +
                    '</div>' +
                    '</div>')
            })

        }
    });

    $.ajax({
        type: "get",
        url: "http://localhost:8080/api/movieUpComing",

        data: "data",
        async: false,
        dataType: "json",
        success: function (data) {
            $.each(data, function (index, itemData) {
                let hinh = itemData.movie_image===null? imgplaceholder : itemData.movie_image;
                $('#slick-sap-chieu').append('<div class="movie-block">' +
                    '<div class="thumbnail">' +
                    '<a href="/loc"><img src="'+hinh+'" alt="" class="movie-block-img"></a>' +
                    '</div>' +
                    '<div class="info-detail">' +
                    '<div class="movie-block-title">' + itemData.movie_title + '</div>' +
                    '<div class="movie-block-detail">' +
                    '<div class="time">' + itemData.duration + ' phút</div>' +
                    '<div class="imdb">' + itemData.avg_point + ' rating</div>' +
                    '</div>' +
                    '</div>' +
                    '<div class="movie-block-hover">' +
                    '<div class="trailerBtn">' +
                    '<a class="box-trailer" href="#">' +
                    '<i class="far fa-play-circle"></i>' +
                    '</a></div>' +
                    '<div class="button-container datveBtn">' +
                    '<a href="#" class="button">dat ngay</a>' +
                    '</div>' +
                    '</div>' +
                    '</div>')
            })

        }
    });
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
<<<<<<< HEAD
    if (dataUser!==undefined){
=======
    if (dataUser !== undefined){
>>>>>>> refs/remotes/origin/master
        $('#user-acc-name').empty();
        $('#user-acc-name').append(dataUser.name);
        $('#user-acc-link').attr("href", "/profile") ;
        $('.nav-right').prepend(function () {
            return '<a style="float: right;" href=\"/logout\">Logout</a>';
        })
    }
}
