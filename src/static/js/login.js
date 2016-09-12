if( getCookie("username") !=null ){
	
	location.href = "/home";
}

$(document).ready(function() {

	$("#signup-toggle-btn").click(function(event) {

		// 改变登录注册切换按钮的内容
		if( $(".signup-box").css('display') == "none" ){
			$(this).html("返回登录");
		}
		else {
			$(this).html("注册");
		}

		$(".signup-box").toggle();
		$(".login-box").toggle();		
	});

	$(document).on('keyup',function(event){
	 	// 按下回车键事件
	 	if(event.keyCode === 13){
	 		if( $(".signup-box").css('display') === "block" ){
	 			signup();
	 		}else{
	 			login();
	 		}
	 	}
	 });


	$("#login-btn").click(function(event) {
		login();
	});

	$("#signup-btn").click(function(event) {
		signup();

	});

	//用户名输入
	$("#signup-username-input").focus(function(event) {
		$(this).siblings(".control-label").html("用户名格式为3-15位数字或字母");
		$(this).parent().removeClass('has-error');
	});

	//密码输入
	$("#signup-password-input").focus(function(event) {
		$(this).siblings(".control-label").html("密码格式为6-16位数字或字母");
		$(this).parent().removeClass('has-error');
	});

	//用户名输入
	$("#signup-username-input").blur(function(event) {
		if(!checkUsername( $(this).val() )){
			$(this).siblings(".control-label").html("用户名格式错误");
			$(this).parent().addClass('has-error');
		}
		else{
			$(this).parent().removeClass("has-error");
			$(this).siblings('.control-label').html("用户名");
		}
	});

	//密码检查
	$("#signup-password-input").blur(function(event) {
		if( checkPassword( $(this).val() ) ){
			$(this).parent().removeClass("has-error");
			$(this).siblings('.control-label').html("密码");
		}
		else{
			$(this).parent().addClass("has-error");
			$(this).siblings(".control-label").html("密码格式错误");
		}
	});

	//确认密码检查
	$("#signup-confirm-password-input").blur(function(event) {
		var password = $(this).val();
		if( ! confirmPassword() ){
			//两个密码不一致
			$(this).parent().addClass('has-error');
			$(this).siblings('.control-label').html("两次密码不一致");
		}
		else{
			$(this).parent().removeClass('has-error');
			$(this).siblings('.control-label').html("确认密码");
		}		
	});

	//姓名检查
	$("#signup-name-input").focus(function(event) {
		$(this).parent().removeClass('has-error');
		$(this).siblings('.control-label').html("姓名");
	});

	//姓名检查
	$("#signup-name-input").blur(function(event) {
		if( $(this).val() == ""){
			$(this).parent().addClass('has-error');
			$(this).siblings('.control-label').html("姓名不能为空");
		}
	});

});

function checkUsername(username){
	return ( username.match('^[0-9a-zA-Z]{3,15}$') != null);
}

function checkPassword(password){
	return ( password.match('^[0-9a-zA-Z]{6,16}$') != null)
}

function confirmPassword(){
	return ( $("#signup-password-input").val() == $("#signup-confirm-password-input").val() );
}

//登录函数
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

//注册函数
function signup(){
	
	var username = $("#signup-username-input").val(),
	password = $("#signup-password-input").val(),
	confirmPassword = $("#signup-confirm-password-input").val(),
	name = $("#signup-name-input").val(),
	college = $("#signup-college-select").val();
	
	if( username=="" || password=="" || confirmPassword=="" || name==""){
		showWarningTips("请填写完整信息");
		return false;
	}

	if(! checkUsername(username) || !checkPassword(password) ){
		showWarningTips("请检查用户名,姓名和密码格式");
		return false;
	}

	if( password != confirmPassword ){
		showWarningTips("两次密码不一致");
		return false;
	}

	signupPost(username,password,name,college);
}

//处理注册请求返回函数
function dealSignupReturn(data, username, password){
	
	if( data['result'] == false ){
		var errorReason;
		switch( data['reason'] ){
			case 1:
			errorReason = "用户名或密码格式错误";
			break;
			case 2:
			errorReason = "信息不完整";
			break;
			case 3:
			errorReason = "用户名已经存在";
			break;
			default:
			errorReason = "信息错误";
		}
	    	//显示错误信息
	    	showWarningTips(errorReason);
	    }
	    else{
	    	//跳到登录框部分并填写好信息
	    	$(".signup-box").hide('fast', function() {
	    		$(".login-box").show();
	    		$("#login-username-input").val(username).parent().removeClass('is-empty');
	    		$("#login-password-input").val(password).parent().removeClass('is-empty');
	    	});
	    }
}

//处理登录请求返回函数
function dealLoginReturn(data, username){

	if(data["result"] == false){
		var errorReason;
		switch( data['reason'] ){
			case 1:
				errorReason = "用户名或密码格式错误";
				break;
			case 2:
				errorReason = "用户名或密码错误";
				break;
		default:
			errorReason = "信息错误";
	    }

		//显示错误信息
		showWarningTips(errorReason);
	}
	else{
		// 保存cookie, 期限为7天
		setCookie("username", username, 7);
		setCookie("realname", data["realname"], 7);
		setCookie("department", data["department"], 7);
		setCookie("signup_time", data["signup_time"], 7);
		setCookie("token", data["token"], 7);

		//跳转页面
		location.href="/home";
	}
}