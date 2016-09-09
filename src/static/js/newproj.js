// var token = getCookie('token');
// console.log( token );
// if( token == null){
// 	location.href = 'home.html';
// }

var tree = new Tree();
var pointer = tree.root_node; //指向当前节点
var files = []; 	//filename:file
var labels= []; 	//name of label
var links = []; 	//链接

$(document).ready(function() {

	changeFileConstruct('');

	$("#new-directory-btn").click(function(event) {
		$(".modal.new-directory-dialog").fadeIn('slow');
		clearInput('#directory-name-input');
		$('#directory-name-input').focus();
	});

	$("#new-file-btn").click(function(event) {
		$(".modal.new-file-dialog").fadeIn('slow');
		$(".modal.new-file-dialog input").val('');

	});

	$(document).on('click', '.modal .close-btn', function(event) {
		$(".modal").fadeOut('fast');
	});

	$(document).on('click', '.modal.new-directory-dialog .confirm-btn', function(event) {
		addDirectory();

	});

	$(document).on('change', '#file-input', function(event) {
		console.log("file input change");
		var file = $("#file-input")[0].files[0];
		if( file == null){
			$(this).addClass('has-error');
			return false;
		}
		var input = $("#file-name-input");
		if( input.val() == ''){
			input.val(file.name);
		}

	});

	$(document).on('click', '.modal.new-file-dialog .confirm-btn', function(event) {
		var filename = $("#file-name-input").val();
		//检查文件是否为空
		var file = $("#file-input")[0].files[0] ;	
		console.log(file);

		if( file == null){
			showWarningTips("请选择文件");
			return false;
		}

		if( filename == ''){
			showWarningTips( '请输入文件名字' );
			return false;
		}

		//检查文件名是否合法
		if( !checkFileName(filename)){
			console.log( filename );
			showWarningTips('文件名不合法，只能包含中文，英文数字和_，且不能以.开头或结尾')
			return false;
		}

		//检查文件大小是否合法
		if( !checkFileSize(file.size, 16*1024) ){
			$('#file-input').addClass('has-error');
			showWarningTips('文件大小不能超过16M');
			return false;
		}

		//重复名字
		var nodes = pointer.child;
		for (var i = 0; i < nodes.length; i++) {
			var paths = (nodes[i].path).split('/') ;
			if( filename == paths[ paths.length-1] ){
				showWarningTips("重复名字");
				return false;
			}
			
		}

		addFile(filename, file);

		$('.modal').fadeOut('fast');
		showFileList();
		console.log( tree.root_node );
		console.log( pointer.child );
	});

	$(document).on('click', '.catalog-root', function(event) {
		changeFileConstruct('');
	});

	$(document).on('click', '.catalog-item>.catalog-item-name', function(event) {
		
		var index = $(this).parent().index();
		var path ='';
		console.log("index "+index);

		for (var i = 1; i <= index; i++) {
			path += '/'+ ( $(".file-catalog>li").eq(i).text() );
		}

		path = path.substring(1);
		changeFileConstruct( path );
	});

	$(document).on('click', '.file-item-name', function(event) {
		var item_name = $(this).text();
		// console.log("pointer.path" + pointer.path );
		var path = pointer.path +"/"+ item_name; // /docs/image
		path = path.substring(1);

		console.log( "path"+path );
		changeFileConstruct(path);

	});

	$("#label-confirm-btn").click(function(event) {
		addLabel();
	});

	$('#label-input').keyup(function(event) {
		if( event.keyCode == 13){
			addLabel();
		}
	});

	$(document).on('keyup', '#directory-name-input', function(event) {
		if( event.keyCode == 13){
			addDirectory();
		}
	});

	$('#url-input').keyup(function(event) {
		if( event.keyCode == 13){
			addLink();
		}
	});

	$('#link-confirm-btn').click(function(event) {
		addLink();
	});

	//删除链接
	$(document).on('click', '.project-link>.close', function(event) {
		var parent = $(this).parent();
		var linkIndex = parent.index();
		parent.remove();
		links.splice(linkIndex, 1);
		console.log(links);
	});

	$(document).on('mouseover', '.project-label,.project-link,.file-item', function(event) {
		$(this).children('.close').fadeIn('fast');
	});

	$(document).on('mouseleave', '.project-label,.project-link,.file-item', function(event){
		$(this).children('.close').fadeOut('slow');
	});

	//删除标签
	$(document).on('click', '.project-label>.close', function(event) {
		
		var labelIndex = $(this).parent().index();
		$(this).parent().remove();

		//从数组中删除
		labels.splice(labelIndex, 1);
		console.log(labels);

	});

	
	$(document).on('click', '.file-item>.close', function(event) {
		console.log("delete file or directory");
		var item_name = $(this).siblings('.file-item-name').text();
		var path = pointer.path +"/"+ item_name; // /docs/image
		path = path.substring(1, path.length);

		tree.delete(path);
		console.log( tree.root_node );

		console.log( "path"+path );
		showFileList();

		return false;
	});

	$("#publish-btn").click(function(event) {
		var token = getCookie('token');

		if( token == null ){
			location.href = '/home';
			return false;
		}

		var name = $("#name-input").val();
		var intro = $("#intro-input").val();
		
		//检查各项输入合法性
		if( name=='' || intro==''){
			showWarningTips('请填写项目名称和简介');
			return false;
		}

		var files_name_string = '',
			labels_string = '',
			files_array=[];
		
		if( files.length != 0){
			
			for (var i = 0; i < files.length; i++) {
				files_name_string += files[i]["name"]+",";
				files_array.push( files[i]["file"] );
			}

			files_name_string = files_name_string.substring(0, files_name_string.length-1);
		}

		if(labels.length != 0){
			for (var i = 0; i < labels.length; i++) {
				labels_string += labels[i]+',';
			}

			labels_string = labels_string.substring(0, labels_string.length-1);
		}

		var links_jsonstring = JSON.stringify( links );
		
		console.log( 'name' + name);
		console.log('intro' + intro);
		console.log("links "+links_jsonstring);
		console.log("labels_string "+labels_string);
		console.log("files_string "+ files_name_string );
		console.log("files_array" + files_array );

		newProjectPost(name, files_name_string, files_array, labels_string, links_jsonstring, intro, token);

	});
	
});

function addFile(filename, file){
	var path = pointer.path+'/'+filename;
	path = path.substring(1, path.length);

	console.log(path);

	//添加到树结构
	var timestamp = new Date().getTime();
	tree.add(path, true, timestamp );

	console.log( file );

	//添加到内存
	console.log(files );

	files.push({
		"name" : path,
		"file" : file
	});

	console.log( files );
	console.log( files.length );

}

function addDirectory(name){

	//文件夹名字不为空
	var name = $("#directory-name-input").val() ;
	console.log(name);
	if( name == '' ){
		showWarningTips("请输入文件夹名字");
		return false;
	}

	//重复文件夹名字
	var nodes = pointer.child;
	for (var i = 0; i < nodes.length; i++) {
		var paths = (nodes[i].path).split('/') ;
		if( name == paths[ paths.length-1] ){
			showWarningTips("重复名字");
			return false;
		}
	}

	//检查文件夹名是否合法
	if( !checkDirectoryName(name) ){
		showWarningTips('文件夹名不合法,只能包含中文，英文和数字');
		return false;
	}

	var path = pointer.path+ "/" + name;  //  /docs/image
	path = path.substring(1);
	tree.add(path, false);

	//更新目录结构
	showFileList();
	console.log( tree.root_node );
	$('.modal').fadeOut('fast');
}

function showFileList(){

	var nodes = pointer.child;
	var html = '';
	for (var i = 0; i < nodes.length; i++) {
		var names = (nodes[i].path).split("/");
		var name = names[ names.length-1 ];

		if( nodes[i].leaf == false ){  //如果是文件夹
			html += '<li class="file-item"><span class="file-item-name directory">'+ name +'</span><span class="close">×</span></li>';
		}		
		else{// 如果是文件
			html += '<li class="file-item"><span class="file-item-name file">'+ name +'</span><span class="close">×</span></li>'
		}
		
		console.log("add item: "+name);
	}

	$(".file-list").html(html);
}


function changeFileConstruct(path){
	var node = tree.find(path);   //   docs/image
	console.log ( "changeFileConstrut" + path );

	if( node != null){
		if( node.leaf == true){
			//是文件
			console.log("is a file");
			return;
		}

		console.log("是文件夹");

		pointer = node;

		changeCatalog(path);
		showFileList();
		console.log(pointer);

	}else{
		showWarningTips("改变目录结构出错");
	}
}

function getFileItemHtml(filename){
	var html = '<li class="file-item"><span class="file-item-name">'+ filename +'</span><span class="close">×</span></li>';
	return html;
}

function changeCatalog(path){
	
	if( path == ''){
		//根目录
		$(".file-catalog").html( '<li class="active">..<li>' )
		return;
	}

	console.log("change catalog: "+path);
	var paths = path.split("/");

	var n = paths.length;
	var html ='<li class="catalog-root">..</li>';

	for (var i = 0; i < n-1; i++) {
		html += '<li class="catalog-item"><a class="catalog-item-name" href="javascript:void(0)">'+ paths[i] +'</a></li>';
	}

	html += '<li class="active">'+ paths[n-1] +'</li>';
	
	$(".file-catalog").html(html);

	$(".file-catalog>li:last-child").addClass('active');
}

function addLabelItem(label){
	var html = '<li class="project-label">'+ label +'<span class="close">×</span></li>';
	$('.labels-container').append(html);
}

function addLinkItem(remark, url){
	var html = '<li class="project-link">'+
							'<span class="link-remark">'+ remark +'</span>'+
							'<span class="link-url">'+ url + '</span>'+
							'<span class="close">×</span>'+
						'</li>';
	$('.links-container').append(html);
}

function dealNewProjectReturn(data){
	if( data['result'] == true ){
		setCookie("token", data['token'], 7);
		location.href = '/home';
	}else{
		var errorReason;
		switch( data['reason'] ){
			case 1:
				errorReason = "未登录";
				break;
			case 2:
				errorReason = "令牌错误";
				break;
			default:
			errorReason = "信息错误";
		}
	    
	    //显示错误信息
	    showWarningTips(errorReason);
	}
}

function addLabel(){
	var label = $("#label-input").val();
		
		if( label == '' ){
			showWarningTips('请输入标签');
			return false;
		}

		if( labels.length == 5){
			showWarningTips('不能超过5个标签');
			return false;
		}

		if( $.inArray(label, labels) != -1 ){
			showWarningTips('存在重复标签');
			return false;
		}

		addLabelItem(label);
		//存入数组
		labels.push(label);
		clearInput('#label-input');
		$('#label-input').focus();
}

function addLink(){

	var remark = $("#remark-input").val();
	var url = $("#url-input").val();
	if( remark=='' || url==''){
		showWarningTips("请填写完成链接信息");
		return false;
	}

	//检查是否链接重复
	for (var i = 0; i < links.length; i++) {
		if( remark == links[i]['description'] || url == links[i]['address'] ){
			showWarningTips('存在重复链接');
			return false;
		}
	}

	if( links.length == 5){
		showWarningTips('链接数量不得超过5个');
		return false;
	}

	clearInput('#url-input');
	clearInput('#remark-input');

	links.push({
		'address' : url,
		'description': remark
	});

	addLinkItem(remark, url);
	console.log( links.length );
	$('#remark-input').focus();
}