/* Created by WuXiaobao on Aug.26 2016
   js for all pages */

$(document).ready(function() {

	//查看是否有cookie
	if( getCookie("username") != null ){
		//显示用户名
		var username = getCookie('username');
	
		$('#navbar-username').html(username+'<b class="caret"></b>');
		$('#navbar-login-btn').remove();
	}else{
	
		$('.navbar-header').css('float', 'left');
		$('.navbar-collapse.collapse').remove();
		$('.navbar-header .navbar-toggle').remove();
		$('.navbar .login-btn').show();	
	}

	$(document).on("click", "#logout-btn", function(){
		logoutPost();
	});

	//取消.btn点击后的自动获取焦点
	$(document).on('click', '.btn', function(event) {
		$(this).blur();	
	});

	//回到顶部按钮点击事件 和 导航栏底部阴影添加事件
	$(window).scroll(function() {
		
		if( $("body").scrollTop() == 0 ){
			$("#returntop-btn").fadeOut();
			$(".navbar").removeClass('shadow-bottom');
		}
		else{
			$("#returntop-btn").fadeIn();
			$(".navbar").addClass('shadow-bottom');
		}
	});

});

//滚动到顶部
function scrollToTop(){
	 var speed=200;
     $("body").animate({ scrollTop: 0 }, speed);
}


//在顶部显示警告提示
function showWarningTips(tips){
	$(".warning-tips .btn-warning").html(tips);
	$(".warning-tips").fadeIn('fast');
	setTimeout("hideWarningTips()",3000);
}

//隐藏顶部警告提示
function hideWarningTips(){
	$(".warning-tips").fadeOut('slow');
}

//时间戳转yyyy-mm-dd;
function formatDate(nS) {     
   return new Date(parseInt(nS*1000)).toLocaleString().split(" ")[0].replace(/\//g,"-");
}

//时间戳转yyyy-mm-dd hh-mm
function formatDateHM(nS){

	var date = new Date(parseInt(nS*1000));
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var h = date.getHours();
	var m = date.getMinutes();

	return year+'-'+month+'-'+day+' '+h+':'+m;
}

//判断是否是图片文件
function isImage(fileType){
    return (fileType.match('^image')!=null);
}

//文件大小是否符合标准 fileSize以B为单位, Size以KB为单位
function checkFileSize(fileSize, size){
	return ((fileSize/1024) < size);
}

function checkDirectoryName(name){
	return ( name.match('^[\u4e00-\u9fa5a-zA-Z0-9]+$') != null );
}

function checkFileName(filename){
	return ( filename.match('^[\u4e00-\u9fa5a-zA-Z0-9][\u4e00-\u9fa5_a-zA-Z0-9\.]+[\u4e00-\u9fa5a-zA-Z0-9]$') != null );
}

//注销
function dealLogoutReturn(data){
	clearCookie();
	location.href = '/home';
}

//解析URL函数
function parseURL(url) { 
	var a = document.createElement('a'); 
	a.href = url; 
	return { 
	source: url, 
	protocol: a.protocol.replace(':',''), 
	host: a.hostname, 
	port: a.port, 
	query: a.search, 
	params: (function(){ 
	var ret = {}, 
	seg = a.search.replace(/^\?/,'').split('&'), 
	len = seg.length, i = 0, s; 
	for (;i<len;i++) { 
	if (!seg[i]) { continue; } 
	s = seg[i].split('='); 
	ret[s[0]] = s[1]; 
	} 
	return ret; 
	})(), 
	file: (a.pathname.match(/\/([^\/?#]+)$/i) || [,''])[1], 
	hash: a.hash.replace('#',''), 
	path: a.pathname.replace(/^([^\/])/,'/$1'), 
	relative: (a.href.match(/tps?:\/\/[^\/]+(.+)/) || [,''])[1], 
	segments: a.pathname.replace(/^\//,'').split('/') 
	}; 
}

//清除input
function clearInput(selector){
	$(selector).val('');
}

//文件前缀树结构
function Tree(){
	this.root_node = new Node();

	this.add = function(path, leaf, time, url){
		var paths = path.split("/");
		var string = "";
		var pointer = this.root_node;

		console.log( tree.root_node );
		
		for(var i=0; i<paths.length; i++){
			string += "/"+paths[i];
			var flag = false;
			for(var j=0; j< (pointer.child).length; j++){
				if( string == (pointer.child)[j].path ){
					pointer = (pointer.child)[j];
					flag = true;
					break;
				}
			}

			if( flag == false){
				var node = new Node();
				node.path = string;
				(pointer.child).push( node );
				pointer = node;
			}

			if( leaf == true && i == (paths.length-1) ){
				pointer.leaf = leaf;
				pointer.url = url;
				pointer.time = time;
			}

		}
	}

	this.find = function(path){

		if( path == ''){
			return this.root_node;
		}

		var paths   = path.split("/");
		var string  = '';
		var pointer = this.root_node;
		
		for (var i = 0; i < paths.length; i++) {
			string += "/"+paths[i];
			console.log("string "+string);
			var flag = false;
			for (var j = 0; j < pointer.child.length; j++) {
				if( string == (pointer.child)[j].path ){
					pointer = (pointer.child)[j];
					flag = true;
					break;
				}
			}

			if( flag == false){
				console.log("cannot find "+path);
				return null;

			}

			if( i == paths.length-1){
				return pointer;
			}

		}

	}

	//删除
	this.delete = function(path){
		var paths   = path.split("/");
		var string  = '';
		var pointer = this.root_node;
		var parent = '';

		for (var i = 0; i < pointer.child.length; i++) {
			string += "/"+paths[i];
			console.log("string "+string);

			var flag = false;
			var j;

			for ( j = 0; j < pointer.child.length; j++) {
				if( string == (pointer.child)[j].path ){
					parent = pointer;
					pointer = (pointer.child)[j];
					flag = true;
					break;
				}
			}

			if( flag == false){
				console.log("cannot find "+path);
				return false;

			}

			if( i == paths.length-1 ){
				(parent.child).splice(j,1);
				return true;
			}
		}

	}
}

//文件前缀树结点
function Node(){
	
	this.child = new Array();
	this.path = "";
	this.leaf = false;
	this.url="";
}

//url获取文件后缀
function getSuffix(url){

	var urls = url.split('/');
	var filename = urls[ urls.length-1 ];
	var names = filename.split('.');
	var suffix = names[ names.length-1 ];
	return suffix;
}