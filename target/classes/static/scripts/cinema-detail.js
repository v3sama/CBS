
$("document").ready(function () {
    $.renderUser();

    getCinemaData();
})


$.getUser = function() {
    let dataUser
    $.ajax({
            type : "get",
            url : "http://localhost:8080/api/UserSession",
            async : false,
            dataType : "json",
            success : function(data) {
                dataUser = data
            }
        })
    return dataUser;
}

$.renderUser = function() {
    dataUser = $.getUser();
    if (dataUser !== undefined) {
        $('#user-acc-name').empty();
        $('#user-acc-name').append(dataUser.name);
        $('#user-acc-link')
            .attr("href", "/profile");
        $('.nav-right')
            .prepend(
                function() {
                    return '<a style="float: right;" href=\"/logout\">Logout</a>';
                })
    }
};

$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null) {
        return null;
    }
    return decodeURI(results[1]) || 0;
}


let getCinemaData = function () {
    const id = $.urlParam("id")
    $.ajax({
        type : "get",
        url : "http://localhost:8080/api/cinemadetail",
        data: {
            "id" : id
        },
        async : false,
        dataType : "json",
        success : function(data) {
            $('#c-name').append(data.name)
            $('#c-add').append(data.address)
            let adden =encodeURIComponent(data.address);
            console.log(adden);
            $('.container-fluid').append('<div class="mapouter"><div class="gmap_canvas">' +
                '<iframe width="600" height="500" id="gmap_canvas" ' +
                'src="https://maps.google.com/maps?q='+ adden +'&t=&z=13&ie=UTF8&iwloc=&output=embed" ' +
                'frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>' +
                '<a href="https://www.embedgooglemap.net/blog/ipvanish-coupon/"></a></div>' +
                '<style>.mapouter{position:relative;text-align:right;height:500px;width:600px;}.gmap_canvas {overflow:hidden;background:none!important;height:500px;width:600px;}</style>' +
                '</div>')
        }
    })
}
