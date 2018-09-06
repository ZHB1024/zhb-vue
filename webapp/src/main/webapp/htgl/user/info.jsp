<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%
String ctxPath = request.getContextPath();
%>

<style>
li {list-style-type:none;}
</style>

<div id="app_content" style="height: 100%">
    <Layout :style="{padding: '0 24px 24px', height: '100%'}"> 
        <Breadcrumb :style="{margin: '24px 0'}"> 
            <breadcrumb-item>首页</breadcrumb-item> 
            <breadcrumb-item>用户管理</breadcrumb-item> 
            <breadcrumb-item>个人信息</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', minHeight: '428px', background: '#fff'}">
            <Row style="background:#eee;padding:20px">
              <i-col span="11">
                  <Card :bordered="false">
                      <p slot="title">个人信息</p>
                      <a href="<%=ctxPath%>/htgl/userinfocontroller/toupdateselfinfo" slot="extra" >
			            <Icon type="ios-loop-strong"></Icon>
			            修改
			          </a>
			          
                      <Row>
                        <i-col span="12">
                          <p>用户名：{{userInfo.userName}}</p>
                          <p>姓名：{{userInfo.realName}}</p>
                          <p>性别：{{userInfo.sex}}</p>
                          <p>电话：{{userInfo.mobilePhone}}</p>
                          <p>邮箱：{{userInfo.email}}</p>
                        </i-col>
                        <i-col offset='6' span="6">
                          <Icon type="md-person" size='80'/>
                        </i-col>
                      </Row>
                  </Card>
              </i-col>
          </Row>
        </i-content>
        
    </Layout>
</div>


<script type="text/javascript">
var myVue =  new Vue({
	  el: '#app_content',
	  data:{
		  userInfo:{},
	  },
	  created: function () {
		  axios.all([
	    	    axios.get('<%=ctxPath %>/htgl/userinfocontroller/selfinfo/api')
	    	  ]).then(axios.spread(function (userInfoResp) {
	    		  myVue.userInfo = userInfoResp.data.data;
	    	  }));
	 },
	  methods:{
		   handleSubmit:function (name) {
			   
		   }
		
	  }
});
</script>