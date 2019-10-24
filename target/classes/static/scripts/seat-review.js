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

