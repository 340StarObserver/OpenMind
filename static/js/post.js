//1. 注册的post请求
function signupPost(username, password, name, college, headIcon){
	var formData = new FormData(); //写入头像
	
	formData.append('head', headIcon);
	formData.append("action_id", 1);
	formData.append("username", username);
	formData.append("password", hex_md5(password));
	formData.append("realname", name);
	formData.append("department", college);

	jQuery.ajax({
	  url: '/action',
	  type: 'POST',
	  data: formData,
	  processData:false,
	  contentType:false,

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

//获取项目概要信息的post请求
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
	  beforeSend: function(){

	  },
	  success: function(data, textStatus, xhr) {
	  	dealProjectBriefReturn(data);
	    
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
}


//新增一个项目post请求
function newProjectPost(){
	jQuery.ajax({
	  url: '/path/to/file',
	  type: 'POST',
	  dataType: 'xml/html/script/json/jsonp',
	  data: {param1: 'value1'},
	  beforeSend: function() {
	    //called when complete
	  },
	  success: function(data, textStatus, xhr) {
	    dealNewProjectReturn(data);
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    //called when there is an error
	  }
	});
	
}

//浏览自己的所有项目的概要信息
function getOwnProjPost(){
	jQuery.ajax({
	  url: '/path/to/file',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 6},

	  beforeSend: function() {
	    //called when complete
	  },
	  success: function(data, textStatus, xhr) {
	    dealGetOwnProjReturn(data);
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    //called when there is an error
	  }
	});
	
}

//07. 浏览一个项目的详细信息的post请求
function getProjDetail(id){
	jQuery.ajax({
	  url: '/path/to/file',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 7,
	  	proj_id: id},
	  beforeSend: function() {
	    
	  },
	  success: function(data, textStatus, xhr) {
	    dealProjDetailReturn(data);
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    //called when there is an error
	  }
	});
	
}

//09. 查看我的活跃记录的post请求
function getActiveDegreePost(month, num){
	jQuery.ajax({
	  url: '/path/to/file',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 9,
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

//10. 发表评论和建议的post请求
function commentPost(proj_id, proj_name, own_usr, parent_id, content){
	jQuery.ajax({
	  url: '/path/to/file',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 10,
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

//11. 查看与我相关的消息的post请求
var newsArray = new Array();
function getNewsPost(){
	var timestamp;
	if( news.length == 0)
		timestamp = new Date().getTime();
	else{
		var news = newsArray[ newsArray.length-1 ]
		timestamp = news[ news.length-1 ]["time"];
	}
	
	jQuery.ajax({
	  url: '/path/to/file',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 11,
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

//12. 查看投票栏
function getVotingProjPost(){
	jQuery.ajax({
	  url: '/path/to/file',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 12},

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

//13. 为喜爱的项目投票
function voteForProjPost(id){
	jQuery.ajax({
	  url: '/path/to/file',
	  type: 'POST',
	  dataType: 'json',
	  data: {
	  	action_id: 13,
	  	proj_id: id},

	  beforeSend: function() {
	    //called when complete
	  },
	  success: function(data, textStatus, xhr) {
	    dealVoteReturn(data);
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    showWarningTips(textStatus);
	  }
	});
}