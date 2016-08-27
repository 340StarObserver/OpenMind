//存储所有项目的概要信息,每个元素对应一页的信息
var projectBrief=new Array();
// var data = [{
// 	"pub_time":122222,
// 	"name": "unknown"
// }]

// var d=[];

// projectBrief.push(data);
// projectBrief.push(d);
// console.log( projectBrief.length[] );

$(document).ready(function() {
	init();

});

function init(){
	getProjectBrief();
}

function getProjectBriefPost(){
	var timestamp;
	if( projectBrief.length == 0)
		timestamp = new Date().getTime();
	else{
		var projects = projectBrief[ projectBrief.length-1 ]
		timestamp = projects[ projects.length-1 ]["pub_time"];
	}
	
	jQuery.ajax({
	  url: '/path/to/file',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 5,
	  	time_max: timestamp
	  },
	  success: function(data, textStatus, xhr) {
	  	return data;
	    
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);

	  }
	});
	
}

function getProjectBrief(){
	var data = getProjectBriefPost();
	if( data.length<5 ){
	    //TODO 下一页按钮失效
		disableNextBtn();
	    return false;
	}else{
		//将新获得的项目概要信息json数组储存
	    projectBrief.push(data);
	}

	//更新项目概要信息页面
	updateProjectPage(data);
}

function updateProjectPage(data){
	//解析5个项目简要信息的json数组
	var project;
	for( project in data){
		var html = getProjectItemHtml(project["_id"], project["proj_name"], 
						   project["own_usr"], project["own_name"], 
						   project["pub_time"], project["labels"], project["introduction"]);

		$(".project-list").html( html );
	}
}

//获取每个项目简要信息生成的html
function getProjectItemHtml(id, proj_name, owner_username, owner_name, 
							pub_time, labels, introduction){
	var html='<li class="panel panel-default project-list-item">'+
				'<div class="panel-body">'+
				    '<div class="item-title-wrapper clearfix">'+
				    '<div class="item-left">'+
				    	'<span class="item-name">'+proj_name+'</span>';
	//循环添加label
	var label;
	for( label in labels){
		html += '<span class="project-label label label-info">'+ label +'</span>';
	}
	
	html += '</div><div class="item-right">'+
    			'<span class="author">'+ owner_username +'</span>'+
    			'<span class="create-time">'+ dataFormat( pub_time )+'</span>'+
    			'</div></div><div class="brief-intro">'+introduction+'</div></div></li>'
    return html;
}


function disablePreviousBtn(){

}

function disableNextBtn(){

}
    			
    			    				
    			    						
    			    						
    			
    			    					