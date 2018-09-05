<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String ctxPath = request.getContextPath();
%>

<i-header id="app_header"> 
        <i-menu mode="horizontal" theme="dark" active-name="1">
          <div class="layout-logo">zhb</div>
          <div class="layout-nav" style="text-align: right;">
              <Menu-item name="3" :to="personalinfo"> <Icon type="ios-person"></Icon>欢迎: {{realname}}</Menu-item>
              <Menu-item name="4" :to="loginout"> <Icon type="ios-log-out"></Icon> 退出 </Menu-item>
          </div>
        </i-menu> 
</i-header>

<script>
new Vue({
	el: '#app_header',
    data:{
    	personalinfo:'<%=ctxPath%>/htgl/userinfocontroller/userinfo',
    	loginout:'<%=ctxPath%>/htgl/userinfocontroller/exit',
    	realname:''
    },
    beforeCreate: function(){
  	  axios({
  		  method:'get',
  		  url:'/htgl/userinfocontroller/realname/api',
  		  responseType:'json'
  		}).then((response) => {
  			this.realname=response.data.data;
  		});
    },
    created: function () {
    	<%-- axios.all([
    	    axios.get('<%=ctxPath%>/htgl/userinfocontroller/realname/api')
    	  ]).then(axios.spread(function (realNameResp) {
    		  this.realname = realNameResp.data.data;
    	  })); --%>
    }
});
</script>