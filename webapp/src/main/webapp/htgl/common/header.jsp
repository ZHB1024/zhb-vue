<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.zhb.forever.framework.util.PropertyUtil" %>
<%
	String ctxPath = request.getContextPath();
%>

<i-header id="app_header"> 
        <i-menu mode="horizontal" theme="dark" active-name="1">
          <div class="layout-logo"><%=PropertyUtil.getSystemLogoName()%></div>
          <div class="layout-nav" style="text-align: right;">
              <Menu-item name="2" :to="personalinfo">欢迎: {{realname}}</Menu-item>
              <Menu-item name="3" :to="personalinfo"><img :src="headUrl" width="60" height="60"/></Menu-item>
              <Menu-item name="4" :to="loginout"> <Icon type="ios-log-out"></Icon> 退出 </Menu-item>
          </div>
        </i-menu> 
</i-header>

<script>
new Vue({
	el: '#app_header',
    data:function (){
    	return {
    		personalinfo:'<%=ctxPath%>/htgl/userinfocontroller/toselfinfo',
        	loginout:'<%=ctxPath%>/htgl/userinfocontroller/exit',
        	realname:'',
        	headUrl:''
    	}
    },
    mounted: function () {
    },
    beforeCreate: function(){
  	  axios({
  		  method:'get',
  		  url:'/htgl/userinfocontroller/getselfinfo/api',
  		  responseType:'json'
  		}).then((response) => {
  			this.realname=response.data.data.realName;
  			this.headUrl='<%=ctxPath%>/htgl/attachmentinfocontroller/getoriginalattachmentinfo?id=' + response.data.data.lobId;
  		});
    },
    created: function () {
    }
});
</script>