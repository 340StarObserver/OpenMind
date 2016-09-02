//1. 注册的post请求
function signupPost(username, password, name, college){
	
	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 1,
	  	username: username,
	  	password: hex_md5(password),
	  	realname: name,
	  	department: college
	  },


	  beforeSend: function(){

	  },
	  success: function(data, textStatus, xhr) {
	  	dealSignupReturn(data, username, password);
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
}

//2.登录的post请求
function loginPost(username,password){

	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 2,
	  	username: username,
	  	password: hex_md5(password) },
	  beforeSend: function(){

	  },
	  success: function(data, textStatus, xhr) {
	  	dealLoginReturn(data, username);
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
	
}

//3.设置头像的post请求
function setHeadIconPost(headicon, token){
	var formData = new FormData();
	formData.append('head', headicon);
	formData.append('token', token);
	formData.append('action_id', 3);

	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  data: formData,
	  processData: false,
	  contentType: false,

	  beforeSend: function() {
	    
	  },
	  success: function(data, textStatus, xhr) {
	    dealHeadIconReturn(data);
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    
	  }
	});
	
}

//4.注销post
function logoutPost(){
	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 4},

	  beforeSend: function(){

	  },
	  success: function(data, textStatus, xhr) {
	    dealLogoutReturn();
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
	
}

//5.新建项目的post

function newProjectPost(name,  files_name_string, files, labels_string, links, intro, token){
	var formData = new FormData();
	formData.append('action_id', 5);
	formData.append('proj_name', name);

	for (var i = 0; i < files.length; i++) {
		formData.append( "file"+(i+1), files[i]);
	}

	formData.append("file_names", files_name_string);
	formData.append("labels", labels_string);
	formData.append("links", links);
	formData.append("introduction", intro);
	formData.append("token", token);

	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  data: formData,
	    processData: false,
	  contentType: false,

	  beforeSend: function() {
	    
	  },
	  success: function(data, textStatus, xhr) {
	    dealNewProjectReturn(data);
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
	
}

//6. 在已有的项目上分享新的收获post
function uploadFilesPost(id, files_name_string, files, token){

	var formData = new FormData();
	for (var i = 0; i < files.length; i++) {
		formData.append( "file"+(i+1), files[i]);
	}

	formData.append("file_name",files_name_string);
	formData.append("action_id", 6);
	formData.append("proj_id", id);
	formData.append("token", token);

	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  processData: false,
	  contentType: false,
	  data: formData,

	  beforeSend: function() {
	    
	  },
	  success: function(data, textStatus, xhr) {
	    dealUploadFilesReturn();

	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
	
}

//7. 获取项目概要信息的post请求
/*第一次请求*/
function getProjBriefPostFirst(){
	console.log("first get project brief info");
	var timestamp = new Date().getTime();
	
	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 7,
	  	page_size: 5,
	  	time_max: timestamp
	  },
	  beforeSend: function(){

	  },
	  success: function(data, textStatus, xhr) {
	  	dealProjBriefReturnFirst(data);
	    
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});

}

  /*第二次及以后的请求*/
function getProjBriefPost(){
	var projects = projectBrief[ projectBrief.length-1 ]
	var timestamp = projects[ projects.length-1 ]["pub_time"];
	
	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 7,
	  	page_size: 5,
	  	time_max: timestamp
	  },
	  beforeSend: function(){

	  },
	  success: function(data, textStatus, xhr) {
	  	dealProjBriefReturn(data);
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
}



//8. 浏览自己的所有项目的概要信息
var ownProjBrief = [];

function getOwnProjPost(){
	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 8},

	  beforeSend: function() {
	    
	  },
	  success: function(data, textStatus, xhr) {
	    dealOwnProjReturn(data);
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
	
}

//9. 浏览一个项目的详细信息的post请求
function getProjDetail(id){
	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 9,
	  	proj_id: id},
	  beforeSend: function() {
	    
	  },
	  success: function(data, textStatus, xhr) {
	    dealProjDetailReturn(data);
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
	
}

//10. 同步数据post
function updateDataPost(token){
	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 10,
	  	token: token},
	  beforeSend: function() {
	    
	  },
	  success: function(data, textStatus, xhr) {
	    
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
	
}

//11. 查看我的活跃记录的post请求
function getActiveDegreePost(month, num){
	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 11,
	  	month: month,	//201608
	  	num: num},
	  beforeSend: function() {
	    //called when complete
	  },
	  success: function(data, textStatus, xhr) {
	    dealActiveDegreeReturn(data);
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
	
}

//12. 发表评论和建议的post请求
function commentPost(proj_id, proj_name, own_usr, parent_id, content){
	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 12,
	  	proj_id: proj_id,
	  	proj_name: proj_name,
	  	own_usr: own_usr,
	  	parent_id: parent_id},

	  beforeSend: function() {
	    
	  },
	  success: function(data, textStatus, xhr) {
	    dealCommentReturn(data);
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
	
}

//13. 查看与我相关的消息的post请求
var ownNewsArray = [];

//第一次请求消息
function getNewsPostFirst(){
	var timestamp = new Date().getTime();
	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 13,
	  	time_max: timestamp
	  },
	  beforeSend: function(){

	  },
	  success: function(data, textStatus, xhr) {
	  	dealNewsReturnFirst(data);
	    
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
}

//第二次及以后请求消息
function getNewsPost(){

	var news = newsArray[ newsArray.length-1 ]
	var timestamp = news[ news.length-1 ]["time"];
	
	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 13,
	  	time_max: timestamp
	  },
	  beforeSend: function(){

	  },
	  success: function(data, textStatus, xhr) {
	  	dealNewsReturn(data);
	    
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
}


//14. 查看投票中的项目
function getVotingProjPost(){
	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 14},

	  beforeSend: function() {
	    //called when complete
	  },
	  success: function(data, textStatus, xhr) {
	    dealVotingProjReturn(data);
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
	
}

//15. 为喜爱的项目投票
function voteForProjPost(id){
	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 13,
	  	proj_id: id},

	  beforeSend: function() {
	    //投票按钮失效
	    disableBtn("#vote-btn");

	  },
	  success: function(data, textStatus, xhr) {
	    dealVoteReturn(data);
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
}