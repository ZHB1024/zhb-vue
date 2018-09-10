

function goto_register() {
	$("#register-username").val("");
	$("#register-password").val("");
	$("#register-repassword").val("");
	$("#register-code").val("");
	$("#tab-2").prop("checked", true);
}

function goto_login() {
	$("#login-username").val("");
	$("#login-password").val("");
	$("#tab-1").prop("checked", true);
}

function goto_forget() {
	$("#forget-username").val("");
	$("#forget-password").val("");
	$("#forget-code").val("");
	$("#tab-3").prop("checked", true);
}

//登录
function login() {
	var username = $("#login-username").val(), password = $("#login-password")
			.val(), validatecode = null, flag = false;
	//判断用户名密码是否为空
	if (username == "") {
		$.pt({
			target : $("#login-username"),
			position : 'r',
			align : 't',
			width : 'auto',
			height : 'auto',
			content : "用户名不能为空"
		});
		flag = true;
	}
	if (password == "") {
		$.pt({
			target : $("#login-password"),
			position : 'r',
			align : 't',
			width : 'auto',
			height : 'auto',
			content : "密码不能为空"
		});
		flag = true;
	}
	//用户名只能是15位以下的字母或数字
	var regExp = new RegExp("^[a-zA-Z0-9_]{1,15}$");
	if (!regExp.test(username)) {
		$.pt({
			target : $("#login-username"),
			position : 'r',
			align : 't',
			width : 'auto',
			height : 'auto',
			content : "用户名必须为15位以下的字母或数字"
		});
		flag = true;
	}

	if (flag) {
		return false;
	} else {//登录
		//调用后台登录验证
		let param = new URLSearchParams(); 
  	    param.append("userName",username); 
  	    param.append("password",password); 
  	    axios.post('/logincontroller/login/api', param)
  		  .then(function (response) {
  			  if(response.data.flag){
  				var redirectUrl = $("input[name='redirectUrl']").val().trim();
  				if(null == redirectUrl || "" == redirectUrl){
  					window.location.href='/htgl/userinfocontroller/toindex';
  			    }else{
  			    	window.location.href= redirectUrl;
  			    }
  			  }else{
  				  myVue.$Message.error({
                      content: response.data.errorMessages,
                      duration: 3,
                      closable: true
                  });
  			  }
  		  })
	}
}

//注册
function register() {
	var username = $("#register-username").val(), password = $(
			"#register-password").val(), repassword = $("#register-repassword")
			.val(), code = $("#register-code").val(), flag = false, validatecode = null;
	//判断用户名密码是否为空
	if (username == "") {
		$.pt({
			target : $("#register-username"),
			position : 'r',
			align : 't',
			width : 'auto',
			height : 'auto',
			content : "用户名不能为空"
		});
		flag = true;
	}
	if (password == "") {
		$.pt({
			target : $("#register-password"),
			position : 'r',
			align : 't',
			width : 'auto',
			height : 'auto',
			content : "密码不能为空"
		});
		flag = true;
	} else {
		if (password != repassword) {
			$.pt({
				target : $("#register-repassword"),
				position : 'r',
				align : 't',
				width : 'auto',
				height : 'auto',
				content : "两次输入的密码不一致"
			});
			flag = true;
		}
	}
	//用户名只能是15位以下的字母或数字
	var regExp = new RegExp("^[a-zA-Z0-9_]{1,15}$");
	if (!regExp.test(username)) {
		$.pt({
			target : $("#register-username"),
			position : 'r',
			align : 't',
			width : 'auto',
			height : 'auto',
			content : "用户名必须为15位以下的字母或数字"
		});
		flag = true;
	}
	//检查用户名是否已经存在
	//调后台代码检查用户名是否已经被注册

	//检查注册码是否正确
	//调后台方法检查注册码，这里写死为11111111
	if (code != '11111111') {
		$.pt({
			target : $("#register-code"),
			position : 'r',
			align : 't',
			width : 'auto',
			height : 'auto',
			content : "注册码不正确"
		});
		flag = true;
	}

	if (flag) {
		return false;
	} else {//注册
		spop({
			template : '<h4 class="spop-title">注册成功</h4>即将于3秒后返回登录',
			position : 'top-center',
			style : 'success',
			autoclose : 3000,
			onOpen : function() {
				var second = 2;
				var showPop = setInterval(function() {
					if (second == 0) {
						clearInterval(showPop);
					}
					$('.spop-body').html(
							'<h4 class="spop-title">注册成功</h4>即将于' + second
									+ '秒后返回登录');
					second--;
				}, 1000);
			},
			onClose : function() {
				goto_login();
			}
		});
		return false;
	}
}

//重置密码
function forget() {
	var username = $("#forget-username").val(), password = $("#forget-password")
			.val(), code = $("#forget-code").val(), flag = false, validatecode = null;
	//判断用户名密码是否为空
	if (username == "") {
		$.pt({
			target : $("#forget-username"),
			position : 'r',
			align : 't',
			width : 'auto',
			height : 'auto',
			content : "用户名不能为空"
		});
		flag = true;
	}
	if (password == "") {
		$.pt({
			target : $("#forget-password"),
			position : 'r',
			align : 't',
			width : 'auto',
			height : 'auto',
			content : "密码不能为空"
		});
		flag = true;
	}
	//用户名只能是15位以下的字母或数字
	var regExp = new RegExp("^[a-zA-Z0-9_]{1,15}$");
	if (!regExp.test(username)) {
		$.pt({
			target : $("#forget-username"),
			position : 'r',
			align : 't',
			width : 'auto',
			height : 'auto',
			content : "用户名必须为15位以下的字母或数字"
		});
		flag = true;
	}
	//检查用户名是否存在
	//调后台方法

	//检查注册码是否正确
	if (code != '11111111') {
		$.pt({
			target : $("#forget-code"),
			position : 'r',
			align : 't',
			width : 'auto',
			height : 'auto',
			content : "注册码不正确"
		});
		flag = true;
	}

	if (flag) {
		return false;
	} else {//重置密码
		spop({
			template : '<h4 class="spop-title">重置密码成功</h4>即将于3秒后返回登录',
			position : 'top-center',
			style : 'success',
			autoclose : 3000,
			onOpen : function() {
				var second = 2;
				var showPop = setInterval(function() {
					if (second == 0) {
						clearInterval(showPop);
					}
					$('.spop-body').html(
							'<h4 class="spop-title">重置密码成功</h4>即将于' + second
									+ '秒后返回登录');
					second--;
				}, 1000);
			},
			onClose : function() {
				goto_login();
			}
		});
		return false;
	}
}