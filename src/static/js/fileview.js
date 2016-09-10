var fileUrl,
	filename,
	suffix;

$(document).ready(function() {

	var params = parseURL( location.href )["params"];
	fileUrl = params["fileUrl"];
	filename = decodeURI( params["filename"] );
	
	console.log(filename);

	if( fileUrl == null){

		alert("没有找到文件");
		window.close();
	}

	suffix = getSuffix( fileUrl );

	console.log("filename "+filename);
	console.log("suffix "+suffix);

	$('.file-name-container').text( filename );

	if( suffix== 'jpg' || suffix=='png' ){
		//图片文件
		$('.file-container').html('<img src="'+ fileUrl +'" alt="'+ filename +'">');
	}
	else if( suffix=='pdf'){
		$('.file-container').html(
			' <iframe src="'+ fileUrl +'" frameborder="0"></iframe>'
		);
	}
	else if( suffix =='txt' || suffix=='md' || suffix=='conf' || suffix=='config' || suffix=='bat'
		|| suffix=='sh' || suffix=='cpp' || suffix=='c' || suffix=='h' || suffix=='java' || suffix=='php'
		|| suffix=='py' || suffix=='html' || suffix=='css' || suffix=='js'  || suffix=='cs' || suffix=='rb' ){
		
		fileGet(fileUrl);
	}
	else{
		location.href = fileUrl;
	}

});

function dealFileReturn(data){
	
	if( suffix == 'md'){
		dealMDReturn(data);
	}
	else{
		$(".file-container").html(data);
	}
}

function dealMDReturn(data){

    var converter = new Markdown.Converter();
    var mdHtml = converter.makeHtml(data);
    $('.file-container').html(mdHtml);
}