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
            <breadcrumb-item>功能管理</breadcrumb-item> 
            <breadcrumb-item>新增图标</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', minHeight: '428px', background: '#fff'}">
          <div style="width:100%;">
            <div style="width: 600px;margin-left:  auto;margin-right:  auto;">
            	<i-form method="post" action="" ref="formValidate" :model="iconInfo" :rules="ruleValidate" :label-width="80">
                	<input type="hidden" name="id" v-model="iconInfo.id"/>
                	
                	<form-item label="图标名称" prop="name">
                  		<i-input type="text" name="name" v-model="iconInfo.name" :maxlength="15" placeholder="请输入图标名称"></i-input>
                	</form-item >
                	
                	<form-item label="图标代码" prop="value">
                		   <i-input type="text" name="value" v-model="iconInfo.value" :maxlength="50" placeholder="请输入图标代码"></i-input>
                	</form-item >
                	
                	
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
		  iconInfo:{
			  id:'',
			  name:'',
			  value:''
		  },
	      ruleValidate: {
	    	     name: [
	    		    { required: true, message: '请填写图标名称', trigger: 'change' }
	    		  ] ,
	    		  value: [
		    		    { required: true, message: '请填写图标代码', trigger: 'change' }
		    	  ]
	    		  
	      } 
	    
	  },
	  created: function () {
	 },
	  methods:{
		   handleSubmit:function (name) {
			   myVue.$refs[name].validate((valid) => {
				   if (valid){
					    let param = new URLSearchParams(); 
		          	  	param.append("id",myVue.iconInfo.id); 
		          	  	param.append("name",myVue.iconInfo.name); 
		          	  	param.append("value",myVue.iconInfo.value); 
		          	  	axios.post('<%=ctxPath%>/htgl/userinfocontroller/adduserinfo/api', param)
		          		  	 .then(function (response) {
		          			  	 if(response.data.flag){
		          			  		myVue.$Message.success({
		                                content: "添加成功",
		                                duration: 3,
		                                closable: true
		                            });
		          			  	    window.location.href='<%=ctxPath%>/htgl/userinfocontroller/touserinfo';
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
	                
	        }
	  }
});
</script>