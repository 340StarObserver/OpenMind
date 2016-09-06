var fileUrl,
	filename,
	suffix;

$(document).ready(function() {

	var params = parseURL( location.href )["params"];
	fileUrl = params["fileUrl"];
	if( fileUrl == null){

		alert("没有找到文件");
		window.close();
	}

	filename = getFilename( fileUrl );
	suffix = getSuffix( fileUrl );

	console.log("filename "+filename);
	console.log("suffix "+suffix);


	$('.file-name-container').text( filename );

	if( suffix == 'md'){
		//md文件
		fileGet(fileUrl);

	}
	else if( suffix== 'jpg' || suffix=='png' ){
		
		//图片文件
		$('.file-container').html('<img src="'+ fileUrl +'" alt="'+ filename +'">');
	}
	else{
		
		//pdf文件
		var html =  '<iframe src="'+ fileUrl +'" frameborder="0"></iframe>';
		$('.file-container').html(html);

	}
	
	//调整iframe高度
});

function dealFileReturn(data){
	
	if( suffix == 'md'){
		dealMDReturn(data);
	}
	else if( suffix == ''){

	}
	else if( suffix == '' ){

	}
}

function dealMDReturn(data){

	var converter = new Markdown.Converter();
    var mdHtml = converter.makeHtml(data);
    $('.file-container').html(html);

}