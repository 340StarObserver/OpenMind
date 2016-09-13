$(document).ready(function() {
	getOwnProjPost();
	
	$(document).on('click', '.option-item.edit-project', function(event) {
		
		var id = $(this).parent().attr("id");
		location.href = "/editproj?id="+id;
	});
		
	$(document).on('click', '.setting-btn', function(event) {
		$(".option-list").slideUp('fast');
		$(this).siblings('.option-list').slideDown('fast');
		return false;
	});
	
	$(document).click(function(event) {
		$(".option-list").slideUp('fast');
	});
});

//deal return of own projects
function dealOwnProjReturn(data){

	if( data.length == 0){
		//TODO 显示暂无项目
		$('.project-list-container').html('<h1>暂无任何项目</h1>');
		return false;
	}

	var html="";

	//解析包含多个项目简要信息的json数组
	for (var i = 0; i < data.length; i++) {
		html += getProjectItemHtml( data[i], i+1 );
	}

	//替换html
	$(".project-list").html( html );

}

//return the html item of each project
function getProjectItemHtml(project, index){

	var html='<tr>'+
			'<td>'+ index +'</td>'+
			'<td>'+ project['proj_name']+'</td>'+
			'<td>'+ formatDate(project['pub_time'])+ '</td>'+
			'<td class="setting-btn">'+
			'<a href="javascript:void(0)" class="btn btn-primary setting-btn">设置</a>'+
			'<ul id="'+ project['_id'] +'" class="option-list">'+
			'<li class="option-item edit-project">'+
			'添加收获'+
			'</li>'+
			'<li class="option-item delete">'+
			'删除'+
			'</li>'+
			'</ul>'+
			'</td>'+
			'</tr>';

	return html;
}