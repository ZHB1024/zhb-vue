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
            <breadcrumb-item>授权管理</breadcrumb-item> 
            <breadcrumb-item>新增授权</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', minHeight: '428px', background: '#fff'}">
          <div style="width:100%;">
            <div style="width: 600px;margin-left:  auto;margin-right:  auto;">
            	<i-form method="post" action="" ref="formValidate" :model="authority" :rules="ruleValidate" :label-width="80">
                	
                	<form-item label="用户" prop="userId">
                	  <i-select name="userId" v-model="authority.userId" placeholder="请选择用户">
                        	<i-option v-bind:value="item.id" v-for="item in userParm">
                        		{{item.userName}}--{{item.realName}}
                        	</i-option>
                    	</i-select>
                	</form-item>
                	
                	<form-item label="功能" prop="functionId">
                	  <i-select name="functionId" multiple v-model="authority.functionId" placeholder="请选择功能">
                        	<i-option v-bind:value="item.id" v-for="item in functionParm">
                        		{{item.name}}--{{item.parentName}}
                        	</i-option>
                    	</i-select>
                	</form-item>
                	
                	<form-item label="添加/移除">
                    <!-- true-value="true" false-value="false" -->
                    <i-switch name="opt"  v-model="authority.opt"  size="large">
                      <span slot="open">添加</span>
                      <span slot="close">移除</span>
                    </i-switch>
                  </form-item>
                	
                  <form-item align="center">
                		<Row>
                      		<i-col span="4" offset="6">
                        		<i-button type="primary" @click="handleSubmit('formValidate')">确 定</i-button>
                      		</i-col>
                    	</Row>
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
		  authority:{
			  userId:'',
			  functionId:'',
			  opt:true
		  },
		  userParm:[],
		  functionParm:[],
	      ruleValidate: {
	    	      userId: [
	    		    { required: true, message: '请选择用户', trigger: 'change' }
	    		  ] ,
	    		  functionId: [
	    		    { required: true, type:"array", message: '请选择功能', trigger: 'change' }
	    		  ] 
	    		  
	      } 
	    
	  },
	  created: function () {
		  axios.all([
	    	    axios.get('<%=ctxPath %>/htgl/userinfocontroller/getuserinfo/api'),
	    	    axios.get('<%=ctxPath %>/htgl/functioninfocontroller/getchildfunctions/api')
	    	  ]).then(axios.spread(function (userResp,functionResp) {
	    		  myVue.userParm = userResp.data.data;
	    		  myVue.functionParm = functionResp.data.data;
	    	  }));
	 },
	  methods:{
		   handleSubmit:function (name) {
			   myVue.$refs[name].validate((valid) => {
				   if (valid){
					    let param = new URLSearchParams(); 
		          	  	param.append("userId",myVue.authority.userId); 
		          	  	param.append("functionId",myVue.authority.functionId); 
		          	  	param.append("opt",myVue.authority.opt); 
		          	  	axios.post('<%=ctxPath%>/htgl/authoritycontroller/addauthority/api', param)
		          		  	 .then(function (response) {
		          			  	 if(response.data.flag){
		          			  		myVue.$Message.success({
		                                content: "添加成功",
		                                duration: 3,
		                                closable: true
		                            });
		          			  	    window.location.href='<%=ctxPath%>/htgl/authoritycontroller/toindex';
		                        }else{
		                      	  myVue.$Message.warning({
		                                content: response.data.errorMessages,
		                                duration: 3,
		                                closable: true
		                            });
		                        }
		          		  })
				   }
	            })
	                
	        }
	  }
});
</script>