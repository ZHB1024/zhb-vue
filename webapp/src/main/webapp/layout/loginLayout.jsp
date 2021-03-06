<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
  String ctxPath = request.getContextPath();
%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

<title><tiles:insertAttribute name="title" /></title>


<link rel="stylesheet" href="<%=ctxPath%>/login/css/normalize.css">
<link rel="stylesheet" href="<%=ctxPath%>/login/css/login.css">
<link rel="stylesheet" href="<%=ctxPath%>/login/css/sign-up-login.css">
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/login/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=ctxPath%>/login/css/inputEffect.css" />
<link rel="stylesheet" href="<%=ctxPath%>/login/css/tooltips.css" />
<link rel="stylesheet" href="<%=ctxPath%>/login/css/spop.min.css" />

<script src="<%=ctxPath%>/login/js/jquery.min.js"></script>
<script src="<%=ctxPath%>/login/js/snow.js"></script>
<script src="<%=ctxPath%>/login/js/jquery.pure.tooltips.js"></script>
<script src="<%=ctxPath%>/login/js/spop.min.js"></script>

<script src="<%=ctxPath%>/login/js/login.js"></script>

<link rel="stylesheet" type="text/css" href="https://unpkg.com/iview@3.0.0/dist/styles/iview.css">
<script src="https://code.jquery.com/jquery-1.12.4.js" integrity="sha256-Qw82+bXyGq6MydymqBxNPYTaUXXq7c8v3CwiYwLLNXU=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
<script type="text/javascript" src="https://unpkg.com/iview@3.0.0/dist/iview.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>

<script>	
	(function() {
		// trim polyfill : https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/Trim
		if (!String.prototype.trim) {
			(function() {
				// Make sure we trim BOM and NBSP
				var rtrim = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g;
				String.prototype.trim = function() {
					return this.replace(rtrim, '');
				};
			})();
		}

		[].slice.call( document.querySelectorAll( 'input.input__field' ) ).forEach( function( inputEl ) {
			// in case the input is already filled..
			if( inputEl.value.trim() !== '' ) {
				classie.add( inputEl.parentNode, 'input--filled' );
			}

			// events:
			inputEl.addEventListener( 'focus', onInputFocus );
			inputEl.addEventListener( 'blur', onInputBlur );
		} );

		function onInputFocus( ev ) {
			classie.add( ev.target.parentNode, 'input--filled' );
		}

		function onInputBlur( ev ) {
			if( ev.target.value.trim() === '' ) {
				classie.remove( ev.target.parentNode, 'input--filled' );
			}
		}
	})();
	
	$(function() {	
		$('#login #login-password').focus(function() {
			$('.login-owl').addClass('password');
		}).blur(function() {
			$('.login-owl').removeClass('password');
		});
		$('#login #register-password').focus(function() {
			$('.register-owl').addClass('password');
		}).blur(function() {
			$('.register-owl').removeClass('password');
		});
		$('#login #register-repassword').focus(function() {
			$('.register-owl').addClass('password');
		}).blur(function() {
			$('.register-owl').removeClass('password');
		});
		$('#login #forget-password').focus(function() {
			$('.forget-owl').addClass('password');
		}).blur(function() {
			$('.forget-owl').removeClass('password');
		});
	});
	
</script>
<style type="text/css">

html{width: 100%; height: 100%;}
body{
	background-repeat: no-repeat;
	background-position: center center #2D0F0F;
	background-color: #00BDDC;
	background-image: url(/login/images/snow.jpg);
	background-size: 100% 100%;
}
.snow-container { position: fixed; top: 0; left: 0; width: 100%; height: 100%; pointer-events: none; z-index: 100001; }

</style>

</head>

<body>
        <tiles:insertAttribute name="body" />
</body>

</html>