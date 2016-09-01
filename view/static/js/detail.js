var labels = [];
var links = [];

var example = {
	"_id" : 1,
	"proj_name" : "Bilibili guichu",
	"own_usr" : "LvYang",
	"own_name" : "吕神",
	"own_head" : "0",
	"pub_time" : 1445599887,
	"labels" : ['123', '123'],
	"links" : [
		{"address": "https://github.com/bobxwu/", "description": "github仓库"},
		{"address": "https://github.com/bobxwu/", "description": "github仓库"}
	],
	introduction : "aaaaaaaaaaaaaaaaaaaaaaaaaa"

};

$(document).ready(function() {

	// var tree = new Tree();
	// tree.add( "docs/data/a.jpg", 1445599887, "");
	// tree.add( "docs/b.jpg", 1445599887, "");
	// tree.add( "res/image/a.jpg", 1445599887, "");
	// console.log( tree.root_node );

	showProjDetail( example );

	$(".project-link").hover(function() {
		var address = $(this).attr('href');
		var left = $(this).position().left;

		$(this).siblings('.link-address').css('left', left).text( address ).fadeIn('slow');
		
	}, function() {
		$(this).siblings('.link-address').fadeOut('fast');
		
	});

	
	$(".more-comment-btn").click(function(event) {

		var follow = $(this).parent().siblings('.item-follow');
		follow.find('.control-label').text('回复');

		if( follow.css('display') === 'none' ){
			$(this).text( '收起更多评论' );
		}
		else{
			$(this).text('查看更多评论');
		}

		follow.toggle('fast');
	});

	$(document).on("click", ".item-reply-btn", function(){
		//获得评论者用户名
		var username = ( $(this).parent().siblings('.item-left').children('.child-commenter') ).text();
		// var username = $(this).parent().siblings('.item-left').childern('.child-commenter').text();
		var  label =$(this).parents(".follow-list").siblings('.reply-input-container').find('.control-label');
		label.text( ("回复" + username) );
	});

});

function init(){
	var params = parseURL( location.href )["params"];
	var id = params["id"];
	console.log(id);

	getProjDetail( id );	
}

function dealProjDetailReturn(data){
	if( data["result"] == false){
		//TODO 没有找到该项目
		return false;
	}

	showProjDetail(data);

}

function showProjDetail(project){
	$(".project-name").text(project['proj_name']);
	//TODO 设置投票

	var labels = project["labels"],
		html="";
	for (var i = 0; i < labels.length; i++) {
		html += '<span class="project-label">' + labels[i] + '</span>';
	}

	$(".project-label-wrapper").html(html);

	$(".project-intro-container").text( project['introduction'] );

	var links = project["links"];
	html="";
	for (var i = 0; i < links.length; i++) {
		html += '<a class="project-link "'+'href="'+ links[i]['address']+'">'+ links[i]["description"] +'</a>';

		// html += '<a class="project-link "'+'href="'+ links[i]['address']+'">'+ links[i]["description"] +'<span class="link-address">'+
	 //    		 			links[i]["address"]+
	 //    		 		'</span></a>';
	}

	html += '<span class="link-address"></span>';

	$('.project-links-container').html(html);
}

function showComments(comments){

}

var exampleFiles = [
	{
		name: "docs/data/a.jpg", time: 1445599887,url:""
	},
	{
		name: "docs/b.jpg", time: 1445599887,url:""
	},
	{
		name: "docs/e.txt", time: 1445599887,url:""
	}

	];
