$(document).ready(function() {
	//取消.btn点击后的自动获取焦点
	$(".btn").click(function(event) {
		$(this).blur();
	});
});

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

function isSizeValid(fileSize){
	return ((fileSize/1024)<200);
}