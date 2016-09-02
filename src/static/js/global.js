$(document).ready(function() {
	//取消.btn点击后的自动获取焦点
	$(".btn").click(function(event) {
		$(this).blur();
	});

	// $("#next-btn").click(function(event) {
	// 	$(this).blur();
	// });

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
	setTimeout("hideWarningTips()",2000);
}

//隐藏顶部警告提示
function hideWarningTips(){
	$(".warning-tips").fadeOut('slow');
}

//时间戳转yyyy-mm-dd;
function dataFormat(nS) {     
   return new Date(parseInt(nS*1000)).toLocaleString().split(" ")[0].replace(/\//g,"-");
}

//判断是否是图片文件
function isImage(fileType){
    return (fileType.match('^image')!=null);
}

//图片文件大小是否符合标准
function isSizeValid(fileSize){
	return ((fileSize/1024)<200);
}

function dealLogoutReturn(data){
	clearCookie();
	
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

function Tree(){
	this.root_node = new Node();

	this.add = function(path, leaf, time, url){
		var paths = path.split("/");
		var string = "";
		var pointer = this.root_node;

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
				pointer.leaf = true;
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
			console.log("string "+string)
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
}

function Node(){
	
	this.child = new Array();
	this.path = "";
	this.leaf = false;
	this.url="";
}