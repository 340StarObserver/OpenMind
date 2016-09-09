var data=[
	{
		who_usr: 'LvYang',
		who_name: 'LvYang',
		who_head: '0',
		time: 10,
		proj_id: 'ffa',
		proj_name: 'Bilibili guichu',
		action_id: 0,
		content: '别测试了，发布会讲什么'
	},
	{
		who_usr: 'LvYang',
		who_name: 'LvYang',
		who_head: '0',
		time: 10,
		proj_id: 'ffa',
		proj_name: 'Bilibili guichu',
		action_id: 0,
		content: '别测试了，发布会讲什么'
	},
	{
		who_usr: 'LvYang',
		who_name: 'LvYang',
		who_head: '0',
		time: 1467676799,
		proj_id: 'ffa',
		proj_name: 'Bilibili guichu',
		action_id: 0,
		content: '别测试了，发布会讲什么'
	}

];

$(document).ready(function() {
	
  	$(document).on('click', '.project-name', function(event) {
  		var id = $(this).attr('id');
  		location.href = '/detail?id='+id;
  	});
	
	getMessagePost();

});

function dealMessageReturn(data){
	console.log(data.length);

	for (var i = 0; i < data.length; i++)
		appendMessageItem( data[i] );
	
	if( data.length != 0 )
		time_max = data[ data.length-1 ]['time'];

	if( data.length < 2 ){
		console.log( "length<2");
		$('#no-more').css('display', 'block').fadeIn('fast');
	}else{
		console.log( "length>=2" );
		$(window).scroll( moreMessage );
	}

	console.log(time_max);
}

function appendMessageItem(data){

	if( data['who_head']=='0')
		data['who_head']='../static/res/image/icon.png';

	var html = '<li class="panel panel-default message-item clearfix">'+
						'<div class="author-icon"><img src="'+ data['who_head'] +'" alt="author-icon"></div>'+
						'<div class="item-right clearfix">'+
							'<div class="right-top">'+
								'<div class="top-left">'+
									'<span class="username">'+ data['who_usr'] +'</span>在'+
									'<span id="'+ data['proj_id'] +'" class="project-name">'+ data['proj_name'] +'</span>中回复了你:'+
								'</div>'+
								'<span class="reply-time">'+ formatDateHM(data['time']) +'</span>'+
							'</div>'+
							'<div class="right-bottom">'+
								data['content']+
							'</div>'+
						'</div>'+
				'</li>';

	$('.message-list').append(html);
}

function moreMessage(){
	console.log("more message");

	if( $(window).scrollTop() + $(window).height()  >= $('body').height() ){
	    	$(window).off('scroll');
	    	getMessagePost();
	 }
}