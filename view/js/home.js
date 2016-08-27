
$(document).ready(function() {
	init();
});

function init(){
	updateProjectPage();
}


function updateProjectPage(){

	var data = getProjectBriefPost();
	if( data.length <5 ){
	    //TODO 项目数小于5时，下一页按钮失效
		disableNextBtn();
		if (data.length == 0)
			return false;
	}else{
		//将新获得的项目概要信息json数组储存
	    projectBrief.push(data);
	}

	//解析包含多个项目简要信息的json数组
	var project,html;
	for( project in data){
		html += getProjectItemHtml(project["_id"], project["proj_name"], 
						   project["own_usr"], project["own_name"], 
						   project["pub_time"], project["labels"], project["introduction"]);
	
	}

	//替换html
	$(".project-list").html( html );
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
    			
    			    				
    			    						
    			    						
    			
    			    					