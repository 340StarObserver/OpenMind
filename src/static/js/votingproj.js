var votingExample = [
	{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "0",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊",
			"score": 13,
			"alive": true,
			"ever_voted": true
	},
	{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "0",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊",
			"score": 13,
			"alive": false,
			"ever_voted": false
	},
	{
			"_id":1,
			"proj_name": "Android FlappyBird",
			"own_usr":　"wxb",
			"own_name": "wxb",
			"pub_time": 1445599887,
			"own_head": "0",
			"labels": ["label1","label2","label3"],
			"introduction": "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊",
			"score": 13,
			"alive": true,
			"ever_voted": false
	}
];

$(document).ready(function() {
	
	getVotingProjPost();

	// dealVotingProjReturn(votingExample);

	$(document).on('click', '.vote-btn', function(event) {
		var vote_btn = $(this);
		var id = $(this).parent().siblings('.item-title-left').children('.project-name').attr('id');
		
		votePost(id, vote_btn);
		// dealVoteReturn(data, vote_btn);
	});

	$(document).on('click', '.vote-btn.voted', function(event) {
		
	});

});

function dealVotingProjReturn(data){
	

	for (var i = 0; i < data.length; i++) {
		var html =  getProjItemHtml( data[i] );
		$(".project-list").append(html);
	}

	// 如果没有登录
	if( getCookie('token') == null )
		$(".vote-btn").remove();
}

function getProjItemHtml(project){

	if( project['own_head'] == '0')
		project['own_head']="../static/res/image/icon.png";

	var html = '<li class="panel panel-default project-list-item">'+
            '<div class="panel-body">'+
              '<div class="item-title-wrapper clearfix">'+
                '<div class="item-title-left">'+
                  '<i class="fa fa-star" aria-hidden="true"></i>'+
                  '<span class="project-name" id="'+ project['_id'] +'">'+ project['proj_name'] +'</span>';

    var labels = project['labels'];
    for (var i = 0; i < labels.length; i++)
    	html += '<span class="project-label">'+ labels[i] +'</span>';
    

    html += '</div><div class="item-title-right">';
    
    if( project['alive'] )
	    if( project['ever_voted'] )
	    	html += '<a href="javascript:void(0)" class="btn btn-default vote-btn voted"></a>';
	    else
	    	html += '<a href="javascript:void(0)" class="btn btn-default vote-btn unvoted"></a>';

   	html += '<strong class="vote-number" data-unit="票">'+ project['score'] +'</strong>&nbsp;|&nbsp;'+
   			'<span class="create-time">'+ formatDate( project['pub_time'] ) +'</span>'+
                '</div>'+
              '</div>'+
              '<div class="brief-intro-wrapper clearfix">'+
                '<div class="author-info">'+
                  '<img class="author-icon" src="'+ project['own_head'] +'" alt="author-icon">'+
                  '<div class="author-name">'+ project['own_usr'] +'</div>'+
                '</div>'+
                '<div class="brief-intro">'+ project['introduction'] +'</div>'+
              '</div>'+
            '</div>'+
          '</li>';

	return html;
}

function dealVoteReturn(data, vote_btn){
	
	if(data['result'] == false ){
		if( data['reason'] == 2 ){
			vote_btn.removeClass('disabled').removeClass('voted').addClass('unvoted');
			var vote_number = vote_btn.siblings('.vote-number');
			vote_number.text( parseInt( vote_number.text() ) - 1 );
			return;
		}

		var errorReason;
		switch(data['reason']){
			case 1:
				errorReason = "未登录";
				break;
			case 3:
				errorReason = "该项目不处于投票状态";
				break;
			case 4:
				errorReason = "票数用光";
				break;
			default:
				errorReason = '信息错误';
		}

		vote_btn.removeClass('disabled');
		showWarningTips(errorReason);
	}
	else{
		vote_btn.removeClass('unvoted').removeClass('disabled').addClass('voted');
		var vote_number = vote_btn.siblings('.vote-number');
		vote_number.text( parseInt( vote_number.text() ) + 1 );
	}
}