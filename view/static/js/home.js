var currentPage ;
var currentVotingPage ;
//存储所有项目的概要信息,每个元素对应一页的信息
var projectBrief=[];
var votingProject = [];


var example =[
	[{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "http://openmind.oss-cn-shanghai.aliyuncs.com/sharedfiles/20160826/ver2-2-small.png",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
	},{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "http://openmind.oss-cn-shanghai.aliyuncs.com/sharedfiles/20160826/ver2-2-small.png",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
	},{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "http://openmind.oss-cn-shanghai.aliyuncs.com/sharedfiles/20160826/ver2-2-small.png",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
	},{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "http://openmind.oss-cn-shanghai.aliyuncs.com/sharedfiles/20160826/ver2-2-small.png",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
	},{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "http://openmind.oss-cn-shanghai.aliyuncs.com/sharedfiles/20160826/ver2-2-small.png",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
	}],
	[{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "http://openmind.oss-cn-shanghai.aliyuncs.com/sharedfiles/20160826/ver2-2-small.png",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
	}],
	[{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "http://openmind.oss-cn-shanghai.aliyuncs.com/sharedfiles/20160826/ver2-2-small.png",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
	}],
	
];

var votingExample = [
	{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "http://openmind.oss-cn-shanghai.aliyuncs.com/sharedfiles/20160826/ver2-2-small.png",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
	},
	{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "http://openmind.oss-cn-shanghai.aliyuncs.com/sharedfiles/20160826/ver2-2-small.png",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
	},
	{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "http://openmind.oss-cn-shanghai.aliyuncs.com/sharedfiles/20160826/ver2-2-small.png",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
	},
	{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "http://openmind.oss-cn-shanghai.aliyuncs.com/sharedfiles/20160826/ver2-2-small.png",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
	},
	{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "http://openmind.oss-cn-shanghai.aliyuncs.com/sharedfiles/20160826/ver2-2-small.png",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
	},
	{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "http://openmind.oss-cn-shanghai.aliyuncs.com/sharedfiles/20160826/ver2-2-small.png",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
	},
	{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "http://openmind.oss-cn-shanghai.aliyuncs.com/sharedfiles/20160826/ver2-2-small.png",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
	}
];

var t = 0;

$(document).ready(function() {

	init();

	$(".voting-project-toggle").click(function(event) {
		t++;
		if( t == 2 ){
			t=0;
			updateProjectPage( votingProject[currentVotingPage] );
			console.log ( "show voting projects" );
		}
		else{
			updateProjectPage( votingProject[currentVotingPage] );
		}
		// return false;
	});

	$("#prev-btn").click(function(event) {

		if( $(this).hasClass('disabled') )
			return false;
		
		scrollToTop();
		updateProjectPage( projectBrief[--currentPage] );

		enableBtn("#next-btn");

		if( currentPage == 0 ){
			//上一页按钮失效
			disableBtn('#prev-btn');
		}

		console.log("从当前存储中选择上一页");
	});

	
	$(document).on('click', '#voting-prev-btn', function(event) {
		if( $(this).hasClass('disabled') )
			return false;
		scrollToTop();

		console.log("#voting-prev click");
		enableBtn("#voting-next-btn");

		if( --currentVotingPage == 0){
			disableBtn("voting-prev-btn");
		}

		console.log("从当前存储中选择正在投票的上一页");
		updateProjectPage( votingProject[currentVotingPage] );

	});

	$("#next-btn").click(function(event) {
		
		if( $(this).hasClass('disabled') )
			return false;

		scrollToTop();

		//最大索引 < 当前索引+1, 需要post请求
		if( projectBrief.length-1 < currentPage+1){

			console.log("post请求加载下一页");

			//发post请求
			//dealProjBriefReturn( example[0] );
			getProjBriefPost();
		}
		else{
			console.log("从当前存储中选择下一页");
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
		location.href = "detail.html?id="+id;
	});

});


function init(){

	//获取第一页概要信息
	getProjBriefPostFirst();
	
	//dealProjBriefReturn( example[0] );
	
	//获取所有正在投票的项目
	//getVotingProjPost();

	//使上一页按钮失效
	disableBtn('#prev-btn');
	disableBtn('#voting-prev-btn');

	currentPage = 0;
	currentVotingPage = 0;

	//查看是否有cookie
	if( getCookie("token") != null ){
		//显示用户名
		//$(".navbar-username").html("张三");
		$(".navbar-username").html( getCookie("username") );
		
		$(".nav-list").show();
		$(".login-btn").hide();

	}else{
		console.log("no cookie");

		//显示登录按钮
		$(".login-btn").show();
		$(".nav-list").hide();
		
	}

	
}

function updateProjectPage(data){

	var html="";
	//解析包含多个项目简要信息的json数组
	for (var i = 0; i < data.length; i++) {
		html += getProjectItemHtml( data[i] );

		// html += getProjectItemHtml(data[i]["_id"], data[i]["proj_name"], 
		// 	data[i]["own_usr"], data[i]["own_name"], data[i]["own_head"],
		// 	data[i]["pub_time"], data[i]["labels"], data[i]["introduction"]);
		
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
	
	console.log( "need to update:"+data );

	enableBtn("#prev-btn");
	currentPage++;

	// //post请求下一页
	// if( projectBrief.length-1 < currentPage+1){
	// 	currentPage++;

	// 	//不是第一次请求
	// 	if( projectBrief.length > 0)
	// 		enableBtn("#prev-btn");
	// }

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
	console.log( "need to update first time:"+data );

	// currentPage=0;
	// disableBtn("#prev-btn");

	//将新获得的项目概要信息json数组储存
	projectBrief.push(data);
	//更新页面
	updateProjectPage(data);
}

//获取每个项目简要信息生成的html
function getProjectItemHtml(project){

	if( project['own_head'] == "0"){
		project['own_head'] = "../static/res/image/icon.png";
	}

	var html='<li class="panel panel-default project-list-item">'+
				'<div class="panel-body">'+
				    '<div class="item-title-wrapper clearfix">'+
				    '<div class="item-title-left">'+'<i class="fa fa-star" aria-hidden="true"></i>'+
				    	'<span class="project-name" id='+ project["id"] +'>'+ project['proj_name'] +'</span>';
	
	var labels = project['labels'];
	//循环添加label
	for (var i = 0; i < labels.length; i++) {
		html += '<span class="project-label">'+ labels[i] +'</span>';
	}
	
	html += '</div><div class="item-title-right">'+
    			'<span class="create-time">'+ dataFormat( project['pub_time'] )+'</span>'+
    			'</div></div><div class="brief-intro-wrapper clearfix">'+
                            '<div class="author-info">'+
                                '<img class="author-icon" src="'+ project['own_head'] +'" alt="author-icon">'+
                                '<div class="author-name">'+ project['own_username'] +'</div></div>'+
                            '<div class="brief-intro">'+ project['introduction'] +'</div></div></div></li>';
    
    return html;
}

function dealVotingProjReturn(data){
	if( data.length == 0){
		//TODO 显示暂无正在投票的项目

		return false;
	}

	var projects = [];
	// console.log( "data.length:"+data.length );
	for (var i = 0; i < data.length; i++) {
		projects.push( data[i] );

		if( (i+1) % 5 == 0){
			// console.log( projects ); 
			votingProject.push(projects);
			projects = [];

		}
	}

	//添加余下的
	votingProject.push( projects );

	disableBtn("#voting-prev-btn");
}

function enableBtn(selector){
	$(selector).removeClass('disabled');
}

function disableBtn(selector){
	$(selector).addClass('disabled');
}   			    					