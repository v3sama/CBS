
// document.addEventListener('DOMContentLoaded', function () {
//     const listTab = document.querySelectorAll(".tab-slider--nav li");
//     const content = document.querySelectorAll(".tab-slider--body");

//     content.forEach(element => {
//         element.style.display = "none";
//     });
//     document.querySelectorAll(".tab-slider--body")[0].style.display = "block";

//     toggleTab(listTab, content);
// });

// const toggleTab = (listTab, content) => {
//     listTab.forEach(singleTab => {
//         singleTab.addEventListener("click", function () {
//             content.forEach(body => {
//                 body.style.display= "none";
//             });
//             let activeTab = singleTab.attributes.rel.value;
//             //cho content vao`
//             document.getElementById(activeTab).style.display = "block";            
//             if (activeTab === "sap-chieu") {
//                 document.querySelector('.tab-slider--tabs').classList.add('slide');
//             } else {
//                 document.querySelector('.tab-slider--tabs').classList.remove('slide');
//             }
//             listTab.forEach(tab => {
//                 tab.classList.remove('active');
//             });
//             this.classList.add('active');
//         })
//     });
// }
getIndexMovieData();
$("document").ready(function () {
    // khởi tạo slick
    initSlick();
    trailerBox();
    initSelectize();
    // getIndexMovieData();
    $('#sap-chieu').hide();
    hoverMobieBlock();
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tabcontent[0].style.display = "block";
    tablinks = document.getElementsByClassName("tablinks");
    tablinks[0].classList.add('active');    

});

// ĐÓNG MỞ BOX TRAILER

function trailerBox() {
    $('.box-trailer').click(function (e) {
        e.preventDefault();
        $('.modal-box-trailer').show();
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
function initSlick() {
    $('.slick-bla').slick({
        dots: true,
        infinite: false,
        arrow: true,
        speed: 300,
        slidesToShow: 4,
        slidesToScroll: 4
    });
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
    document.getElementById(cityName).style.display = "block";	//     document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";	//     evt.currentTarget.className += " active";
}

function getIndexMovieData(){
    const url = "http://localhost:8080/api/moviehehe";
    fetch(url).then((resp) => resp.json()).then(function (data) {
        console.log(data)
    })
}