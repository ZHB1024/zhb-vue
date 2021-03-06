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
            <breadcrumb-item><a href="">首页</a></breadcrumb-item> 
            <breadcrumb-item>用户管理</breadcrumb-item> 
            <breadcrumb-item>新增用户</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', minHeight: '428px', background: '#fff'}">
          <div style="width:100%;">
            <div style="width: 600px;margin-left:  auto;margin-right:  auto;">
            	<i-form method="post" action="" ref="formValidate" :model="userInfo" :rules="ruleValidate" :label-width="80">
                	<input type="hidden" name="id" v-model="userInfo.id"/>
                	
                	<form-item label="用户名" prop="userName">
                  		<i-input type="text" name="userName" v-model="userInfo.userName" :maxlength="15" placeholder="请输入用户名"></i-input>
                	</form-item >
                	
                	<form-item label="邮箱" prop="email">
                  		<i-input type="text" name="email" v-model="userInfo.email" :maxlength="30" placeholder="请输入邮箱"></i-input>
                	</form-item >
                	
                	<form-item label="密码" prop="password">
                		<Tooltip content="默认密码为123456，可自行修改" placement="right-start">
                		   <i-input type="password" name="password" v-model="userInfo.password" :maxlength="15" placeholder="请输入密码"></i-input>
                	    </Tooltip>
                	</form-item >
                	
                	<form-item label="确认密码" prop="confirmPassword">
                		<Tooltip content="默认密码为123456，可自行修改" placement="right-start">
                  		  <i-input type="password" name="confirmPassword" v-model="userInfo.confirmPassword" :maxlength="15" placeholder="请输入确认密码"></i-input>
                  		</Tooltip>
                	</form-item >
                	
                  <form-item align="center">
                		<i-button type="primary" @click="handleSubmit('formValidate')">确 定</i-button>
                        <i-button @click="handleReset('formValidate')" style="margin-left: 8px">重置</i-button>
                  </form-item>
                  
                </i-form>
            </div>
          </div>
        </i-content>
        
    </Layout>
</div>


<script type="text/javascript">
var myVue =  new Vue({
	  el: '#app_content',
	  data:{
		  userInfo:{
			  id:'',
			  userName:'',
			  password:'123456',
			  confirmPassword:'123456'
		  },
	      ruleValidate: {
	    	     userName: [
	    		    { required: true, message: '请填写用户名', trigger: 'change' }
	    		  ] ,
	    	     email: [
	    		    { required: true, message: '请填写邮箱', trigger: 'change' }
	    		  ] ,
	    		  password: [
		    		    { required: true, message: '请填写密码', trigger: 'change' }
		    	  ],
		    	  confirmPassword: [
		    		    { required: true, message: '请填写确认密码', trigger: 'change' }
		    	  ]
	    		  
	      } 
	    
	  },
	  created: function () {
	 },
	  methods:{
		   handleSubmit:function (name) {
			   myVue.$refs[name].validate((valid) => {
				   if (valid){
					   if(myVue.userInfo.password != myVue.userInfo.confirmPassword){
						   myVue.$Message.warning({
                               content: "密码与确认密码必须相同",
                               duration: 3,
                               closable: true
                           });
						   return false;
					   }
					    let param = new URLSearchParams(); 
		          	  	param.append("id",myVue.userInfo.id); 
		          	  	param.append("userName",myVue.userInfo.userName); 
		          	  	param.append("email",myVue.userInfo.email); 
		          	  	param.append("password",myVue.userInfo.password); 
		          	  	param.append("confirmPassword",myVue.userInfo.confirmPassword); 
		          	  	axios.post('<%=ctxPath%>/htgl/userinfocontroller/adduserinfo/api', param)
		          		  	 .then(function (response) {
		          			  	 if(response.data.flag){
		          			  		myVue.$Message.success({
		                                content: "添加成功",
		                                duration: 3,
		                                closable: true
		                            });
		          			  	    window.location.href='<%=ctxPath%>/htgl/userinfocontroller/toindex';
		                        }else{
		                      	  myVue.$Message.error({
		                                content: response.data.errorMessages,
		                                duration: 3,
		                                closable: true
		                            });
		                        }
		          		  })
				   }
	            })
	                
	        },
	        handleReset:function (name) {
	        	myVue.$refs[name].resetFields();
	        }
	  }
});
</script>