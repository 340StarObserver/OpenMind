var tree = new Tree();
var parent;  //当前节点的父节点
var pointer = tree.root_node; //指向当前节点
var url = "";  //当前文件目录的url
var files = [];

$(document).ready(function() {

	$("#new-directory-btn").click(function(event) {
		$(".modal.new-directory-dialog").fadeIn('slow');
		
	});

	$("#new-file-btn").click(function(event) {
		$(".modal.new-file-dialog").fadeIn('slow');
	});

	$(document).on('click', '.modal .close-btn', function(event) {
		$(".modal").fadeOut('fast');
	});

	$(document).on('click', '.modal.new-file-dialog .confirm-btn', function(event) {
		var filename = $("#file-name-input").val();
		//检查文件是否为空
		var file = $("#file-input")[0].files[0] ;	

		//文件名和文件都不为空
		addFile(filename, file);
	});

	addFile("a.jpg", "");
	
	//showFileCatalog();

});

function addFile(path, file){
	//添加到树结构
	var timestamp = new Date().getTime();
	tree.add(path, timestamp, "");

	files.push({
		"name" : path,
		"file" : file
	});
	
	console.log(files);
}

function addDirectory(directory){

}

function showFileCatalog(){

	var nodes = pointer.child;
	var html = '';
	for (var i = 0; i < nodes.length; i++) {
		var names = (nodes[i].path).split("/");

		var name = names[ names.length-1 ];
		html += getFileItemHtml(name);
		console.log("add file: "+name);
	}

	$(".file-list").html(html);
}

function getFileItemHtml(filename){
	var html = '<li class="file-item">'+ filename +'</li>';
	return html;
}