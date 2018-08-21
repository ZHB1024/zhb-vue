<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String ctxPath = request.getContextPath();
%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" i-content="width=device-width, initial-scale=1, maximum-scale=1">
<title><tiles:insertAttribute name="title" /></title>
<!-- import stylesheet -->
<link rel="stylesheet" type="text/css" href="https://unpkg.com/iview@3.0.0/dist/styles/iview.css">
<style>
.layout {
	border: 1px solid #d7dde4;
	background: #f5f7f9;
	position: relative;
	border-radius: 4px;
	overflow: hidden;
}

.layout-logo {
	width: 100px;
	height: 30px;
/* 	background: #5b6270; */
	border-radius: 3px;
	float: left;
	position: relative;
	top: 0px;
	left: 20px;
  color:white;
  font-size:25px
}


.layout-nav {
	width: 185px;
	margin: 0 auto;
	margin-right: 20px;
}
html,body{height:100%}
</style>

<script src="https://code.jquery.com/jquery-1.12.4.js" integrity="sha256-Qw82+bXyGq6MydymqBxNPYTaUXXq7c8v3CwiYwLLNXU=" crossorigin="anonymous"></script>
<!-- 开发环境版本，包含了有帮助的命令行警告 -->
<!-- import Vue.js -->
<script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
<!-- import iView -->
<script type="text/javascript" src="https://unpkg.com/iview@3.0.0/dist/iview.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>

</head>
<body>
    <div id="app_header">
        <i-header> 
        <i-menu mode="horizontal" theme="dark" active-name="1">
          <div class="layout-logo">学信网</div>
          <div class="layout-nav" style="text-align: right;">
              <Menu-item name="3" :to="personalinfo"> <Icon type="ios-person"></Icon> 信息 </Menu-item>
              <Menu-item name="4" :to="layouturl"> <Icon type="ios-log-out"></Icon> 退出 </Menu-item>
          </div>
        </i-menu> 
        </i-header>
    </div>
    <div style="height: 100%">
        <%-- <jsp:include page="/common/leftMenu2.jsp" flush="true" />  --%>
        <tiles:insertAttribute name="body" />
    </div>
<script>
new Vue({
    el: '#app_header',
    data:{
    	personalinfo:'<%=ctxPath%>/common/personal/index',
    	layouturl:'<%=ctxPath%>/logout'
    },
    created: function () {
    	
      console.log("header初始化--加载中...TODO")
    }
});
</script>
</body>
</html>