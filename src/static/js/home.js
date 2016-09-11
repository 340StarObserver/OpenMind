/* Created by WuXiaobao on Aug.28 2016
   js for pages of home*/

var currentPage ;
var currentVotingPage ;

//存储所有项目的概要信息,每个元素对应一页的信息
var projectBrief=[];

$(document).ready(function() {

	init();

	$("#prev-btn").click(function(event) {

		if( $(this).hasClass('disabled') )
			return false;
		
		enableBtn("#next-btn");

		if( --currentPage == 0 ){
			//清空内存存储数组
			projectBrief.length =0 ;

			//上一页按钮失效
			// disableBtn('#prev-btn');
			getProjBriefPostFirst();
			return false;
		}

		scrollToTop();

		updateProjectPage( projectBrief[currentPage] );

	});

	
	$(document).on('click', '#voting-prev-btn', function(event) {
		if( $(this).hasClass('disabled') )
			return false;
		scrollToTop();

	
		enableBtn("#voting-next-btn");

		if( --currentVotingPage == 0){
			disableBtn("#voting-prev-btn");
		}

	
		updateProjectPage( votingProject[currentVotingPage] );

	});

	$("#next-btn").click(function(event) {
		
		if( $(this).hasClass('disabled') )
			return false;

		scrollToTop();

		//最大索引 < 当前索引+1, 需要post请求
		if( projectBrief.length-1 < currentPage+1){
			//发post请求
			getProjBriefPost();
		}
		else{
			
			//从当前存储中选择下一页
			var data = projectBrief[ ++currentPage ];
			updateProjectPage( data );
			//前一页有效
			enableBtn("#prev-btn");

			//如果当前更新的项目数小于5,下一页按钮失效
			if ( data.length < 5 )
				disableBtn("#next-btn");
		}
		
	});

	$(document).on('click', '#voting-next-btn', function(event) {
		if( $(this).hasClass('disabled') )
			return false;

		scrollToTop();
		
		var data = votingProject[ ++currentVotingPage ];
		updateProjectPage( data );

		if(votingProject.length-1 == currentVotingPage ){
			disableBtn("#voting-next-btn");
		}

	});

	$(document).on('click', '.project-name', function(event) {
		var id = $(this).attr('id');
		location.href = "/detail?id="+id;
	});

});


function init(){

	//获取第一页概要信息
	getProjBriefPostFirst();
	
}

function updateProjectPage(data){

	var html="";
	//解析包含多个项目简要信息的json数组
	for (var i = 0; i < data.length; i++) {
		html += getProjItemHtml( data[i] );
	}

	//替换html
	$(".project-list").html( html );
}

function dealProjBriefReturn(data){

	if( data.length <5 ){
		//项目数小于5时，下一页按钮失效
		disableBtn("#next-btn");
		//返回的data为空
		if (data.length == 0)
			return false;
	}

	/* 有项目概要信息可以更新*/

	enableBtn("#prev-btn");
	currentPage++;


	//将新获得的项目概要信息json数组储存
	projectBrief.push(data);
	//更新页面
	updateProjectPage(data);
}

function dealProjBriefReturnFirst(data){
	if( data.length <5 ){
		//项目数小于5时，下一页按钮失效
		disableBtn("#next-btn");
		//返回的data为空
		if (data.length == 0){
			//TODO 提示暂无项目

			return false;
		}
	}

	/* 有项目概要信息可以更新*/
	currentPage=0;
	disableBtn("#prev-btn");

	//将新获得的项目概要信息json数组储存
	projectBrief.push(data);
	//更新页面
	updateProjectPage(data);
}

//获取每个项目简要信息生成的html
function getProjItemHtml(project){

	if( project['own_head'] == "0"){
		project['own_head'] = "../static/res/image/icon.png";
	}

	var html='<li class="panel panel-default project-list-item">'+
				'<div class="panel-body">'+
				    '<div class="item-title-wrapper clearfix">'+
				    '<div class="item-title-left">'+'<i class="fa fa-star" aria-hidden="true"></i>'+
				    	'<span class="project-name" id='+ project["_id"] +'>'+ project['proj_name'] +'</span>';
	
	var labels = project['labels'];
	//循环添加label
	for (var i = 0; i < labels.length; i++) {
		html += '<span class="project-label">'+ labels[i] +'</span>';
	}
	
	html += '</div><div class="item-title-right">'+
    			'<span class="create-time">'+ formatDate( project['pub_time'] )+'</span>'+
    			'</div></div><div class="brief-intro-wrapper clearfix">'+
                            '<div class="author-info">'+
                                '<img class="author-icon" src="'+ project['own_head'] +'" alt="author-icon">'+
                                '<div class="author-name">'+ project['own_usr'] +'</div></div>'+
                            '<div class="brief-intro">'+ project['introduction'] +'</div></div></div></li>';
    
    return html;
}

//按钮有效函数
function enableBtn(selector){
	$(selector).removeClass('disabled');
}

//按钮失效函数
function disableBtn(selector){
	$(selector).addClass('disabled');
}   			    					