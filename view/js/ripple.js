//ripple effect
$(document).on("mousedown",".ripple-effect-button", function(event){
	var x = event.pageX - $(this).offset().left;
	var y = event.pageY - $(this).offset().top;
	
	$(this).children(".ripple-effect-container").css({
		"transform": "translate(-50%,-50%) translate("+x+"px,"+y+"px)",
		"width": "300px",
		"height": "300px",
		"opacity": "0.3"
	});
});

$(document).on("mouseleave",".ripple-effect-button",function(event){
	$(this).children(".ripple-effect-container").css({
		"width": "5px",
		"height": "5px",
		"opacity": "0"
	});
});