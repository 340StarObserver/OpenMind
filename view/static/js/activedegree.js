var date = new Date();
var num = 4;

$(document).ready(function() {

	getActiveDegree();
	$('#more-btn').click(function(event) {
		getActiveDegree(num);

	});
});


function getActiveDegree(){
	var month = transformDate(date);
	getActiveDegreePost(month, num);
}

function dealActiveDegreeReturn(data){
	for (var i = 0; i < num; i++) {
		if( i< data.length)
			addMonthList( data[i] );
		else
			addMonthListEmpty();

		//当前月份减1
		date.setMonth( date.getMonth()-1 );
	}

}

function addMonthList(data){
	var active = data["active"],
		html='<li class="degree-list-item">'+
           	 '<span class="month">'+
              data['month']+
            '</span>'+
            '<div class="square-list">';

	var days_num = daysInMonth(date); 

	for (var i = 0; i < days_num; i++)
		html += '<i></i>';

	html += '</div></li>';

	var list_item = $(html);
	var degree, d_class;
	var i_array = list_item.children('.square-list').children('i');

	for (var i = 0; i < active.length; i++) {
		degree = active[i]['degree'];

		if( degree > 0 && degree <=10)
			d_class = 'd-1';
		else if(　degree>10 && degree<=30 )
			d_class = 'd-2';
		else if( degree > 30 && degree <=60)
			d_class='d-3';
		else if( degree > 60 && degree <=100)
			d_class='d-4';

		i_array.eq( active[i]['day']-1 ).addClass(d_class);
	}

	$('.active-degree-list').append( list_item );

}

function addMonthListEmpty(){
	var html='<li class="degree-list-item">'+
           	 '<span class="month">'+
              transformDate(date)+
            '</span>'+
            '<div class="square-list">';

	var days_num = daysInMonth(date); 

	for (var i = 0; i < days_num; i++)
		html += '<i></i>';

	html += '</div></li>';
	var list_item = $(html);
	$('.active-degree-list').append( list_item );
}

function transformDate(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	if(m<10)
		m = '0'+m;
	else
		m+='';
	return parseInt(y+m);
}

function daysInMonth(date){
	return new Date(date.getFullYear(), date.getMonth(), 0).getDate();
}