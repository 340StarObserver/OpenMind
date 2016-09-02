var token = getCookie('token');
console.log( token );
if( token == null){
	location.href = '/home';
}


var tree = new Tree();
var parent;  //当前节点的父节点
var pointer = tree.root_node; //指向当前节点
var url = "";  		//当前文件目录的url
var files = []; 	//filename:file
var labels= []; 	//name of label
var links = []; 	//remark : content of remark


$(document).ready(function() {

	changeFileConstruct('');

	$("#new-directory-btn").click(function(event) {
		$(".modal.new-directory-dialog").fadeIn('slow');
		
	});

	$("#new-file-btn").click(function(event) {
		$(".modal.new-file-dialog").fadeIn('slow');
		$("#file-input").val('');
		$("#file-name-input").val('');

	});

	$(document).on('click', '.modal .close-btn', function(event) {
		$("#directory-name-input").val('');
		$(".modal").fadeOut('fast');
	});

	$(document).on('click', '.modal.new-directory-dialog .confirm-btn', function(event) {
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

		addDirectory(name);

		$('.modal').fadeOut('fast');
	});

	$(document).on('change', '#file-input', function(event) {
		console.log("fiile input change");
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
			showWarningTips( 请输入文件名字 );
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

		console.log(file);
		//符合要求
		addFile(filename, file);

		$('.modal').fadeOut('fast');
		showFileList();
	});

	$(document).on('click', '.catalog-root', function(event) {
		
		changeFileConstruct('');
	});

	$(document).on('click', '.catalog-item>.item-name', function(event) {
		
		var index = $(this).parent().index();
		var path ='';
		console.log("index "+index);

		for (var i = 0; i <= index; i++) {
			// console.log ("catalog text " + $("<div class="file-catalog"><li></li></div>").eq(i).text()  );
			path += '/'+ ( $(".file-catalog>li").eq(i).text() );
		}

		console.log( path );
		path = path.substring(4, path.length);
		console.log(path);
		changeFileConstruct( path );
	});

	$(document).on('click', '.file-item', function(event) {
		var item_name = $(this).text();
		// console.log("pointer.path" + pointer.path );
		var path = pointer.path +"/"+ item_name; // /docs/image
		path = path.substring(1, path.length);

		console.log( "path"+path );	
		changeFileConstruct(path);

	});

	$("#label-confirm-btn").click(function(event) {
		
		var label = $("#label-input").val();
		
		if( label == '' ){
			showWarningTips('请输入标签');
			return false;
		}


		addLabel(label);
		//存入数组
		labels.push(label);
		clearInput('#label-input');

		//TODO最多输入5个标签
	});

	$('#link-confirm-btn').click(function(event) {
		var remark = $("#remark-input").val();
		var url = $("#url-input").val();
		if( remark=='' || url==''){
			showWarningTips("请填写完成链接信息");
			return false;
		}

		//检查是否链接重复
		for (var i = 0; i < links.length; i++) {
			if( remark == links[i].remark || url == links[i].url ){
				return false;
			}
		}

		

		clearInput('#url-input');
		clearInput('#remark-input');

		links.push({
			'address' : url,
			'description': remark
		});

		addLink(remark, url);
		console.log( links.length );

	});

	//TODO 删除标签

	//TODO 删除链接

	$("#publish-btn").click(function(event) {
		var token = getCookie('token');

		if( token== null ){
			return false;
		}

		var name = $("#name-input").val();
		var intro = $("#intro-input").val();
		//TODO 检查各项输入合法性

		var files_name_string = '',
			labels_string = '',
			file_array=[];
		
		if( files.length != 0){
			
			for (var i = 0; i < files.length; i++) {
				files_name_string += files[i]["name"]+",";
				file_array.push( files[i]["file"] );
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
		console.log("file_array" + file_array );

		newProjectPost(name, files_name_string, file_array, labels_string, links_jsonstring, intro, token);

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
	var path = pointer.path+ "/" + name;  //  /docs/image
	path = path.substring(1, path.length);

	tree.add(path, false);

	//更新目录结构
	showFileList();
}

function showFileList(){

	var nodes = pointer.child;
	var html = '';
	for (var i = 0; i < nodes.length; i++) {
		var names = (nodes[i].path).split("/");
		var name = names[ names.length-1 ];

		// if( nodes[i].leaf == false )  //如果是文件夹
		// else // 如果是文件
		html += getFileItemHtml(name);

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
	var html = '<li class="file-item">'+ filename +'</li>';
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
		html += '<li class="catalog-item"><a class="item-name" href="javascript:void(0)">'+ paths[i] +'</a></li>';
	}

	html += '<li class="active">'+ paths[n-1] +'</li>';
	
	$(".file-catalog").html(html);
	// $(".file-catalog").prepend( <li class> )
	$(".file-catalog>li:last-child").addClass('active');
}

function addLabel(label){
	var html = '<li class="project-label">'+ label +'</div>';
	$('.labels-container').append(html);
}

function addLink(remark, url){
	var html = '<li class="project-link">'+
							'<span class="link-remark">'+ remark +'</span>'+
							'<span class="link-url">'+ url + '</span>'+
						'</li>';
	$('.links-container').append(html);
}

function clearInput(id){
	$(id).val('');
}

function dealNewProjectReturn(data){
	if( data['result'] == true ){
		setCookie("token", data['token'], 7);
		alert('发布成功');
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