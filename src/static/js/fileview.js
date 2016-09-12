var fileUrl,
	filename,
	suffix;

$(document).ready(function() {

	var params = parseURL( location.href )["params"];
	fileUrl = params["fileUrl"];
	filename = decodeURI( params["filename"] );
	
	if( fileUrl == null){

		alert("没有找到文件");
		window.close();
	}

	suffix = getSuffix( fileUrl );

	$('.file-name-container').text( filename );

	if( suffix== 'jpg' || suffix=='png' || suffix=='gif' ){
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
		|| suffix=='py' || suffix=='html' || suffix=='css' || suffix=='js'  || suffix=='cs' || suffix=='rb'
                || suffix=='xml' ){
		
		fileGet(fileUrl);
	}
	else{
		location.href = fileUrl;
	}

});

function dealFileReturn(data){
	
	data = data.replace( /</g, "&lt;");
	data = data.replace( />/g, "&gt;");

	if( suffix == 'md'){
		dealMDReturn(data);
	}
	else{
		$(".file-container").html( data  );
	}
}

function dealMDReturn(data){

    var converter = new Markdown.Converter();
    var mdHtml = converter.makeHtml(data);
    $('.file-container').html(mdHtml);
}
