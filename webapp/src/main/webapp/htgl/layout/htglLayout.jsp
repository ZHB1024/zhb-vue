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

<link rel="stylesheet" type="text/css" href="https://unpkg.com/iview@3.0.0/dist/styles/iview.css">
<script src="https://code.jquery.com/jquery-1.12.4.js" integrity="sha256-Qw82+bXyGq6MydymqBxNPYTaUXXq7c8v3CwiYwLLNXU=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
<script type="text/javascript" src="https://unpkg.com/iview@3.0.0/dist/iview.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>


<!-- <link rel="stylesheet" type="text/css" href="/css/iview.css">
<script type="text/javascript" src="/js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="/js/vue.js"></script>
<script type="text/javascript" src="/js/iview.js"></script>
<script type="text/javascript" src="/js/axios.min.js"></script> -->


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

</head>

<body>
    <div id="app_header">
        <tiles:insertAttribute name="header" />
    </div>
    <div style="height: 100%">
        <tiles:insertAttribute name="left" />
        <tiles:insertAttribute name="body" />
    </div>
</body>

<script>
new Vue({
    el: '#app_header',
    data:{
    	<%-- personalinfo:'<%=ctxPath%>/common/personal/index',
    	layouturl:'<%=ctxPath%>/logout' --%>
    },
    created: function () {
    	
      console.log("header初始化--加载中...TODO")
    }
});
</script>

</html>