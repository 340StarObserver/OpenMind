var proj_id, 
	proj_name, 
	own_usr,
	own_name,
	head_comment_array=[],
	comments=[];

// var labels = [];
// var links = [];

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
	'introduction' : "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\r\naaaaaaaa",
	'shares':[

	],

	'comments' :  [
	{  
                        "id"        : "akfja3",  
                        "parent_id" : "0",
                        "send_usr"  : "seven",  
                        "send_name" : "LvYang",  
                        "send_head" : '0',  
                    
                        "recv_usr"  : "xxxx",  
                        "recv_name" : "yyyy",  
                    
                        "time"      : 1445599887,  
                        "content"   : "this is the first comment"  
    },
    {  
                        "id"        : "fa3gad",  
                        "parent_id" : "akfja3",  
                    
                        "send_usr"  : "leo",  
                        "send_name" : "shangjun",  
                        "send_head" : '0',  
                    
                        "recv_usr"  : "xxxx",  
                        "recv_name" : "yyyy",  
                    
                        "time"      : 1446633221,  
                        "content"   : "this is the second comment"  
    },
    {  
                        "id"        : "fad",  
                        "parent_id" : "0",  
                    
                        "send_usr"  : "dddd",  
                        "send_name" : "shangjun",  
                        "send_head" : '0',  
                    
                        "recv_usr"  : "xxxx",  
                        "recv_name" : "yyyy",  
                    
                        "time"      : 1446633221,  
                        "content"   : "this is a great idea."  
    }, 
    {  
                        "id"        : "faddddd",  
                        "parent_id" : "fad",  
                    
                        "send_usr"  : "dddd",  
                        "send_name" : "3d",  
                        "send_head" : '0',  
                    
                        "recv_usr"  : "dddd",  
                        "recv_name" : "shangjun",  
                    
                        "time"      : 1446633221,  
                        "content"   : "ddd hello "  
    }
]
};

var exampleComments = [
	{  
                        "id"        : "akfja3",  
                        "parent_id" : "0",
                        "send_usr"  : "seven",  
                        "send_name" : "LvYang",  
                        "send_head" : '0',  
                    
                        "recv_usr"  : "xxxx",  
                        "recv_name" : "yyyy",  
                    
                        "time"      : 1445599887,  
                        "content"   : "this is the first comment"  
    },
    {  
                        "id"        : "fa3gad",  
                        "parent_id" : "akfja3",  
                    
                        "send_usr"  : "leo",  
                        "send_name" : "shangjun",  
                        "send_head" : '0',  
                    
                        "recv_usr"  : "xxxx",  
                        "recv_name" : "yyyy",  
                    
                        "time"      : 1446633221,  
                        "content"   : "this is the second comment"  
    },
    {  
                        "id"        : "fad",  
                        "parent_id" : "akfja3",  
                    
                        "send_usr"  : "dddd",  
                        "send_name" : "shangjun",  
                        "send_head" : '0',  
                    
                        "recv_usr"  : "xxxx",  
                        "recv_name" : "yyyy",  
                    
                        "time"      : 1446633221,  
                        "content"   : "this is the second comment"  
    },
     {  
                        "id"        : "fad11",  
                        "parent_id" : "0",  
                    
                        "send_usr"  : "dddd",  
                        "send_name" : "shangjun",  
                        "send_head" : '0',  
                    
                        "recv_usr"  : "xxxx",  
                        "recv_name" : "yyyy",  
                    
                        "time"      : 1446633221,  
                        "content"   : "this is the second comment"  
    }
];

var cReturn = {
	result : 'true',
	comment :{
		id : 'newone',
		parent_id: '0',
		send_usr: 'wxb',
		send_name: 'wxb',
		send_head: '0',
		recv_usr: 'xxx',
		recv_name: 'yyy',
		time: new Date().getTime(),
		content: 'this is a new sentence'
	}
};

$(document).ready(function() {

	// init();

	showProjDetail( example );

	$(".project-link").hover(function() {
		var address = $(this).attr('href');
		var left = $(this).position().left;

		$(this).siblings('.link-address').css('left', left).text( address ).fadeIn('slow');
		
	}, function() {
		$(this).siblings('.link-address').fadeOut('fast');
		
	});

	$(document).on('click', '.more-comment-btn', function(event) {
		
		console.log('comment-btn');

		var follow = $(this).parent().siblings('.item-follow');
		follow.find('.control-label').html('回复<span class="control-label-username"></span>&nbsp;:&nbsp;' );

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
		var label = $(this).parents(".follow-list").siblings('.reply-input-container').find('.control-label');
		label.html( '回复<span class="control-label-username">'+ username +'</span>&nbsp;:&nbsp;' );
		console.log( username );
	});

	$(document).on('click', '.reply-btn', function(event) {
		console.log( $(this).attr('id')  );
		var index = ($(this).attr('id')).split('-')[1];
		
		var wrapper = $(this).siblings('.reply-input-wrapper');
		
		var parent_id = head_comment_array[index]['id'];

		console.log( "parent_id:"+parent_id );
		// var parent_id = $(this).parents('.item-title').attr('id');

		//获取回复的用户名
		var recv_usr = wrapper.children('.control-label').children('.control-label-username').text();
		// console.log("recv_usr " + recv_usr );

		if( recv_usr == ''){
			recv_usr = head_comment_array[index]['send_usr'];
		}

		console.log( recv_usr );

		//获取回复内容
		var content = wrapper.find('.reply-input').val();
		//检查回复内容是否为空
		if( content == ''){
			showWarningTips("请输入回复内容");
			return false;
		}

		console.log( parent_id );
		//发送请求
		// commentPost
		console.log(proj_id, proj_name, own_usr, own_name, recv_usr, '', parent_id, content)
		dealCommentReturn( cReturn );
	});
});

function init(){
	var params = parseURL( location.href )["params"];
	var id = params["id"];
	console.log(id);

	getProjDetailPost( id );	
}

function dealProjDetailReturn(data){
	if( data["result"] == false){
		//TODO 没有找到该项目
		alert("没有找到项目");
		location.href = "home.html" ;
 		return false;
	}

	showProjDetail(data);
}

function showProjDetail(project){
	$(".project-name").text(project['proj_name']);
	//TODO 设置投票区域
	

	var labels = project["labels"],
		html="";

	for (var i = 0; i < labels.length; i++) {
		html += '<span class="project-label">' + labels[i] + '</span>';
	}

	$(".project-label-wrapper").html(html);

	$(".project-intro-container").html(project['introduction']);

	var links = project["links"];
	html="";

	for (var i = 0; i < links.length; i++) {
		html += '<a class="project-link "'+'href="'+ links[i]['address']+'">'+ links[i]["description"] +'</a>';
	}

	html += '<span class="link-address"></span>';

	$('.project-links-container').html(html);

	showComments( project['comments'] );
	
	console.log( project['comments'] );

	//记录数据
	proj_id = project['_id'];
	proj_name = project['proj_name'];
	own_usr = project['own_usr'];
	own_name = project['own_name'];

}

function showComments(comments){
	$('.comment-list').html('');

	var head_comment_index = 0;
	
	for (var i = 0; i < comments.length; i++) {
		console.log(comments[i]);

		if( comments[i]['parent_id'] == '0' ){
			head_comment_array.push({
				"id" : comments[i]['id'],
				"send_usr": comments[i]['send_usr']
			});

			// head_comment_array['"'+comments[i]['id']+'"'] = comments[i]['send_usr'];

			console.log('add head comment');
			addHeadComment( comments[i], head_comment_index );
			head_comment_index++;
		}
		else{
			console.log( 'add follow comment' );
			addFollowComment( comments[i] );
		}
	}

	console.log( head_comment_array );

}

function addHeadComment(comment, index){

	if( comment['send_head'] == '0' )
		comment['send_head'] = '../static/res/image/icon.png';

	var html = '<li class="comment-item">'+
						'<div id='+ comment['id'] +' class="item-title clearfix">'+
							'<div class="title-left commenter-icon">'+
								'<img src="'+ comment['send_head'] +'" alt="icon">'+
							'</div>'+
							'<div class="title-right">'+
								'<div class="right-top clearfix">'+
									'<div class="top-left">'+
											'<span class="commenter-username">'+ comment['send_usr'] +'</span>发表评论:'+
									'</div>'+
									'<div class="comment-time">'+
											dateFormat(comment['time'])+
									'</div>'+
								'</div>'+
								'<div class="comment-content">'+
									comment['content']+
								'</div>'+
							'</div>'+
						'</div>'+
						'<div class="item-follow">'+
							'<ul class="follow-list">'+
							'</ul>'+
							'<div class="reply-input-container clearfix">'+
									'<div class="reply-input-wrapper form-group is-empty">'+
						                '<label for="textArea" class="control-label"></label>'+        
						                 '<textarea class="form-control reply-input" maxlength="150" rows="3"></textarea>'+
						                  '<span class="help-block">不超过150字</span>'+
						              '</div>'+
						             '<button type="submit" class="btn btn-primary reply-btn" id="reply-'+ index + '">回复</button>'+
							'</div>'+
						'</div>'+
						'<div class="item-bottom clearfix">'+
							'<button type="submit" class="btn btn-primary more-comment-btn">查看更多评论</button>'+
						'</div>'+
					'</li>' ;
	
	$('.comment-list').append(html);

}

function addFollowComment(comment){
	var html = '<li id="'+ comment['id'] +'" class="follow-item clearfix">'+
									'<div class="item-left">'+
										'<span class="child-commenter">'+ comment['send_usr'] +'</span>回复<span class="parent-commenter">'+ comment['recv_usr'] +'</span>'+
										'<span class="comment-content">'+ comment['content'] +'</span>'+
									'</div>'+
									'<div class="item-right">'+
										'<span class="item-reply-btn">回复</span>'+
										'<span class="comment-time">'+
											dateFormat( comment['time'] )+
										'</span>'+
									'</div>'+
								'</li>';

	var parent_id = comment['parent_id'];
	$('#'+parent_id).siblings('.item-follow').children('.follow-list').append(html);
}

function dealCommentReturn(data){
	if( data['result'] == false){
		var errorReason;
		switch( data['reason'] ){
			case 1:
				errorReason = "未登录";
				break;
			case 2:
				errorReason = "不存在该项目";
				break;
			default:
			errorReason = "信息错误";
		}
	    
	    //显示错误信息
	    showWarningTips(errorReason);
	}else{
		//添加到评论区
		var comment = data['comment'];
		var parent_id = comment['parent_id'];

		if( parent_id == 0){
			head_comment_array.push({
				"id": comment['id'],
				"send_usr": comment['send_usr']
			});
			addHeadComment( comment, head_comment_array.length-1 );
		}
		else{
			addFollowComment(comment);
		}
	}
}