var tree = new Tree();
var pointer = tree.root_node;

var proj_id,
	files=[];

$(document).ready(function() {
	var params = parseURL( location.href )["params"];
	proj_id = params["id"];

	getProjDetailPost(proj_id);
	// dealProjDetailReturn( example );

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
		
	});

	$(document).on('click', '.catalog-root', function(event) {
		changeFileConstruct('');
	});

	$(document).on('click', '.catalog-item>.catalog-item-name', function(event) {
		
		var index = $(this).parent().index();
		var path ='';

		for (var i = 1; i <= index; i++) {
			path += '/'+ ( $(".file-catalog>li").eq(i).text() );
		}

		path = path.substring(1);
		changeFileConstruct( path );
	});

	$(document).on('click', '.file-item-name', function(event) {
		var item_name = $(this).text();
		var path = pointer.path +"/"+ item_name; // /docs/image
		path = path.substring(1);
		changeFileConstruct(path);

	});

	$('#edit-confirm-btn').click(function(event) {
		if( files.length == 0){
			showWarningTips('请上传新文件');
			return false;
		}

		var files_name_string='',
			files_array=[];

		for (var i = 0; i < files.length; i++) {
			files_name_string += files[i]["name"]+",";
			files_array.push( files[i]["file"] );
		}

		files_name_string = files_name_string.substring(0, files_name_string.length-1);
		
		var token = getCookie("token");

		editProjPost( proj_id, files_name_string, files_array, token);
	});

});

function dealProjDetailReturn(data){

	$('#name-input').val( data['proj_name'] );
	$('#intro-input').val( data['introduction'] );

	
	//添加标签
	var labels = data['labels'];
	for (var i = 0; i < labels.length; i++) {
		addLabelItem( labels[i] );
	}

	//添加链接
	var links = data['links'];
	for (var i = 0; i < links.length; i++) {
		addLinkItem( links[i]['description'], links[i]['address'] );
	}

	//添加文件
	var shares = data['shares'];
	for (var i = 0; i < shares.length; i++) {
		tree.add( shares[i]['name'] ,true, shares[i]['time'], shares[i]['url'] );
	}

	changeFileConstruct('');

}

function addFile(filename, file){
	var path = pointer.path+'/'+filename;
	path = path.substring(1, path.length);

	//添加到树结构
	var timestamp = new Date().getTime();
	tree.add(path, true, timestamp );

	//添加到内存

	files.push({
		"name" : path,
		"file" : file
	});

}

function addDirectory(name){

	//文件夹名字不为空
	var name = $("#directory-name-input").val() ;
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

	}

	$(".file-list").html(html);
}


function changeFileConstruct(path){
	var node = tree.find(path);   //   docs/image

	if( node != null){
		if( node.leaf == true){
			//是文件
			return;
		}


		pointer = node;

		changeCatalog(path);
		showFileList();

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

function dealEditProjReturn(data){
	if( data['result'] == false){
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
	else{
		setCookie("token", data['token'], 7);
		location.href = '/ownproj';
	}
}