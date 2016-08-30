var labels = [];
var links = [];

$(document).ready(function() {

	$(".project-link").hover(function() {
		
		//添加tip内容
		var tooltip = $(this).siblings('.tooltip');
		var link = $(this).text();

		tooltip.text("123123");
		
		var left = $(this).position().left;

		$(this).siblings('.tooltip').css('left', left);
		$(this).siblings(".tooltip").fadeIn('slow');

	}, function() {
		$(this).siblings(".tooltip").hide();
	});

});

function init(){
	var params = parseURL( location.href )["params"];
	var id = params["id"];

	getProjDetail( id );
}

function dealProjDetailReturn(data){
	if( data["result"] == false){
		//TODO 没有找到项目
		return false;
	}

	
}