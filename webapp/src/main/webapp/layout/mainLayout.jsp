<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%
  String ctxPath = request.getContextPath();
%>
<html>
<head>
<title>zhb</title>

<script type="text/javascript" src="<%=ctxPath%>/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/vue.js"></script>

</head>

<style scoped>
.layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
}
.layout-logo{
    width: 100px;
    height: 30px;
    background: #5b6270;
    border-radius: 3px;
    float: left;
    position: relative;
    top: 15px;
    left: 20px;
}
.layout-nav{
    width: 420px;
    margin: 0 auto;
    margin-right: 20px;
}
</style>

<body>

  <Layout>
  <div id="app_header">
        <Header> 
        <Menu mode="horizontal" theme="dark" active-name="1">
          <div class="layout-logo">zhb</div>
          <div class="layout-nav" style="text-align: right;">
              <MenuItem name="3" :to="personalinfo"> <!-- <Icon type="ios-person"></Icon> --> 信息 </MenuItem>
              <MenuItem name="4" :to="layouturl"> <!-- <Icon type="ios-log-out"></Icon> --> 退出 </MenuItem>
          </div>
        </Menu> 
        </Header>
    </div>
   </Layout>
   
   <%-- <Layout>
    <div style="height: 100%">
        <jsp:include page="/common/leftMenu2.jsp" flush="true" /> 
        content
    </div>
    </Layout> --%>

</body>

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
</html>