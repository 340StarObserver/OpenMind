$(document).ready(function() {

	$("#signin-toggle-btn").click(function(event) {
		// 改变登录注册切换按钮的内容
		if( $(".signin-box").css('display') == "none" ){
			$(this).html("返回登录");
		}
		else {
			$(this).html("注册");
		}

		$(".signin-box").toggle();
		$(".login-box").toggle();		
	});

	 $(document).on('keyup',function(event){
          if(event.keyCode === 13){
            if( $(".signin-box").css('display') == "block" ){
				
				signin();
			}else{
				login();
			}
          }


    });


	$("#login-btn").click(function(event) {
		login();
	});

	$("#signin-btn").click(function(event) {
		signin();

	});

	$("#signin-username-input").focus(function(event) {
		$(this).siblings(".control-label").html("用户名格式为数字，字母");
		$(this).parent().removeClass('has-error');
	});

	$("#signin-password-input").focus(function(event) {
		$(this).siblings(".control-label").html("密码格式为数字，字母");
		$(this).parent().removeClass('has-error');
	});

	$("#signin-username-input").blur(function(event) {
		if(!checkUsername()){
			$(this).siblings(".control-label").html("用户名格式错误");
			$(this).parent().addClass('has-error');
		}
		else{
			$(this).parent().removeClass("has-error");
			$(this).siblings('.control-label').html("用户名");
		}
	});

	$("#signin-password-input").blur(function(event) {
		if( checkPassword() ){
			$(this).parent().removeClass("has-error");
			$(this).siblings('.control-label').html("密码");
		}
		else{
			$(this).parent().addClass("has-error");
			$(this).siblings(".control-label").html("密码格式错误");
		}
	});

	$("#signin-confirm-password-input").blur(function(event) {
		var password = $(this).val();
		if( ! confirmPassword(password) ){
			//两个密码不一致
			$(this).parent().addClass('has-error');
			$(this).siblings('.control-label').html("两次密码不一致");
		}
		else{
			$(this).parent().removeClass('has-error');
			$(this).siblings('.control-label').html("确认密码");
		}		
	});

	$("#signin-name-input").focus(function(event) {
		$(this).parent().removeClass('has-error');
		$(this).siblings('.control-label').html("姓名");
	});

	$("#signin-name-input").blur(function(event) {
		if( $(this).val() == ""){
			$(this).parent().addClass('has-error');
			$(this).siblings('.control-label').html("姓名不能为空");
		}
	});
});

function checkUsername(username){
	
	 return false;
}

function checkPassword(password){

	return false;
}

function loginPost(){

	jQuery.ajax({
	  url: '/path/to/file',
	  type: 'POST',
	  dataType: 'xml/html/script/json/jsonp',
	  data: {param1: 'value1'},
	  beforeSend: function(){

	  },
	  complete: function(xhr, textStatus) {
	    //called when complete
	  },
	  success: function(data, textStatus, xhr) {
	    //called when successful
	  },
	  error: function(xhr, textStatus, errorThrown) {
	    //called when there is an error
	  }
	});
	
}

function signinPost(username, password, name, college){

}

function showWarningTips(tips){
	$(".warning-tips .btn-warning").html(tips);
	$(".warning-tips").fadeIn('fast');
	setTimeout("hideWarningTips()",2000);
}

function hideWarningTips(){
	$(".warning-tips").fadeOut('slow');
}

function confirmPassword(){
	return ( $("#signin-password-input").val() == $("#signin-confirm-password-input").val() );
}

function login(){
	var username = $("#login-username-input").val(),
		password = $("#login-password-input").val();
	//检查用户名和密码
	if( !checkUsername(username) || !checkPassword(password)){
		showWarningTips("请检查用户名和密码");
		return false;
	}
	loginPost(username,password);
}

function signin(){
	if( ! confirmPassword() ){
			showWarningTips("两次密码不一致");
			return false;
	}

	var username = $(".signin-username-input").val(),
		password = $(".signin-password-input").val(),
		name = $("#signin-name-input").val(),
		college = $("#signin-college-select").val();

	if(! checkUsername(username) || !checkPassword(password) || (name=="") ){
		showWarningTips("请检查用户名,姓名和密码");
		return false;
	}

	signinPost(username,password,name,college);
}