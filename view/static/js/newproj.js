var tree = new Tree();
var parent;  //当前节点的父节点
var pointer = tree.root_node; //指向当前节点
var url = "";  //当前文件目录的url
var files = [];

$(document).ready(function() {

	tree.add("docs/a.jpg", true, 1, '');
	tree.add("b", true, 1, '');
	tree.add("res/image/k.txt", true, 1, '');
	tree.add("res/kk/j", true, 1, '');
	tree.add("res/image/x/mm/k.txt", true, 1, '');
	
	pointer = tree.find("docs");
	addDirectory("new");

	console.log( pointer );
	// showFileList();
	changeFileConstruct('');

	$("#new-directory-btn").click(function(event) {
		$(".modal.new-directory-dialog").fadeIn('slow');
		
	});

	$("#new-file-btn").click(function(event) {
		$(".modal.new-file-dialog").fadeIn('slow');
	});

	$(document).on('click', '.modal .close-btn', function(event) {
		$(".modal").fadeOut('fast');
	});

	$(document).on('click', '.modal.new-directory-dialog .confirm-btn', function(event) {
		//文件夹名字不为空
		var name = $("#directory-name-input") ;
		if( name == '' ){
			showWarningTips("请输入文件夹名字");
			return false;
		}

		addDirectory(name);

	});

	$(document).on('click', '.modal.new-file-dialog .confirm-btn', function(event) {
		var filename = $("#file-name-input").val();
		//检查文件是否为空
		var file = $("#file-input")[0].files[0] ;	

		//如果文件名和文件都不为空
		// addFile(filename, file);
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

		// var direcotry = $(this).text();
		// var paths = (pointer.path).split("/");
		// paths.shift();

		// var path="";

		// for (var i = 0; i < paths.length; i++) {
		// 	path += '/'+paths[i];
		// 	if( paths[i] == direcotry ){
		// 		break;
		// 	}
		// }

		// path = path.substring(1, path.length);
		
		// changeFileConstruct( path );

		// var path = getCatalogPath();
		// console.log ( $(".file-catalog").children()[0] );
	});

	$(document).on('click', '.file-item', function(event) {
		var item_name = $(this).text();
		// console.log("pointer.path" + pointer.path );
		var path = pointer.path +"/"+ item_name; // /docs/image
		path = path.substring(1, path.length);

		console.log( "path"+path );	
		changeFileConstruct(path);

	});
	
});

function addFile(path, file){
	//添加到树结构
	var timestamp = new Date().getTime();
	tree.add(path, true, timestamp );

	//添加到内存
	files.push({
		"name" : path,
		"file" : file
	});
	
	console.log(files);
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

		// if( nodes[i].leaf == false )
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