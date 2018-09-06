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
            <breadcrumb-item>修改信息</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', minHeight: '428px', background: '#fff'}">
          <div style="width:100%;">
            <div style="width: 600px;margin-left:  auto;margin-right:  auto;">
            	<i-form method="post" action="" ref="formValidate" :model="userInfo" :rules="ruleValidate" :label-width="80">
                	<input type="hidden" name="id" v-model="userInfo.id"/>
                	
                	
                	<form-item label="用户名" prop="detail">
                  		<i-input type="text" disabled name="userName" v-model="userInfo.userName" :maxlength="100" placeholder="请输入加班详情"></i-input>
                	</form-item >
                	
                	<form-item label="姓名" prop="realName">
                  		<i-input type="text" name="realName" v-model="userInfo.realName" :maxlength="100" placeholder="请输入加班详情"></i-input>
                	</form-item >
                	
                	<form-item label="性别" prop="sex">
                  		<i-input type="text" name="sex" v-model="userInfo.sex" :maxlength="100" placeholder="请输入加班详情"></i-input>
                	</form-item >
                	
                	<form-item label="出生日期" prop="birthday">
                  		<Date-picker  name="birthday" type="date" :options="limitDate" v-model="userInfo.birthday"  format="yyyy-MM-dd"  placeholder="出生日期"  >
                    	</Date-picker>  
                	</form-item >
                	
                	<form-item label="身份证号" prop="identityCard">
                  		<i-input type="text" name="identityCard" v-model="userInfo.identityCard" :maxlength="100" placeholder="请输入加班详情"></i-input>
                	</form-item >
                	
                	<form-item label="国籍" prop="country">
                  		<i-input type="text" name="country" v-model="userInfo.country" :maxlength="100" placeholder="请输入加班详情"></i-input>
                	</form-item >
                	
                	<form-item label="民族" prop="nation">
                  		<i-input type="text" name="nation" v-model="userInfo.nation" :maxlength="100" placeholder="请输入加班详情"></i-input>
                	</form-item >
                	
                	<form-item label="毕业院校" prop="byyx">
                  		<i-input type="text" name="byyx" v-model="userInfo.byyx" search  :maxlength="100" placeholder="请输入加班详情"></i-input>
                	</form-item >
                	
                	<form-item label="电话" prop="mobilePhone">
                  		<i-input type="text" name="mobilePhone" v-model="userInfo.mobilePhone" :maxlength="100" placeholder="请输入加班详情"></i-input>
                	</form-item >
                	
                	<form-item label="邮箱" prop="email">
                  		<i-input type="text" name="email" v-model="userInfo.email" :maxlength="100" placeholder="请输入加班详情"></i-input>
                	</form-item >
                	
                	<!-- <form-item label="加班时间" prop="workdate">
                  		<Date-picker  :readonly="true" name="workDate" type="date" :options="limitDate" v-model="formParm.workdate"  format="yyyy-MM-dd"  placeholder="加班日期"  >
                    	</Date-picker>  
                	</form-item > -->
                	
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
var userInfoJson = <%=request.getAttribute("userInfoJson") %> ;
var myVue =  new Vue({
	  el: '#app_content',
	  data:{
		  userInfo:{
			  id:userInfoJson.id,
			  userName:userInfoJson.userName,
			  realName:userInfoJson.realName,
			  birthday:userInfoJson.birthday,
			  sex:userInfoJson.sex,
			  identityCard:userInfoJson.identityCard,
			  country:userInfoJson.country,
			  nation:userInfoJson.nation,
			  byyx:userInfoJson.byyx,
			  mobilePhone:userInfoJson.mobilePhone,
			  email:userInfoJson.email
		  },
		  limitDate:{
			  disabledDate (date) {
		             return date.valueOf() > Date.now() ;
		         }
		  },
	      ruleValidate: {
	    	     realName: [
	    		    { required: true, message: '请填写姓名', trigger: 'change' }
	    		  ] ,
	    		  birthday: [
	    		    { required: true,type:'date', message: '请选择出生日期', trigger: 'change' }
	    		  ]  ,
	    		  sex: [
		    		    { required: true, message: '请填写性别', trigger: 'change' }
		    	  ], 
		    	  identityCard: [
		    		    { required: true, message: '请填写身份证号', trigger: 'change' }
		    	  ],
		    	  country: [
		    		    { required: true, message: '请填写国籍', trigger: 'change' }
		    	  ],
		    	  nation: [
		    		    { required: true, message: '请选择民族', trigger: 'change' }
		    	  ],
		    	  byyx: [
		    		    { required: true, message: '请选择毕业院校', trigger: 'change' }
		    	  ],
		    	  mobilePhone: [
		    		    { required: true, message: '请填写手机号', trigger: 'change' }
		    	  ],
		    	  email: [
		    		    { required: true, message: '请填写邮箱', trigger: 'change' }
		    	  ]
	    		  
	      } 
	    
	  },
	  created: function () {
		  <%-- axios.all([
	    	    axios.get('<%=ctxPath %>/htgl/userinfocontroller/selfinfo/api')
	    	  ]).then(axios.spread(function (userinfoResp) {
	    		  myVue.userInfo = userinfoResp.data.data;
	    	  })); --%>
	 },
	  methods:{
		   handleSubmit:function (name) {
			   myVue.$refs[name].validate((valid) => {
				   if (valid){
					   let param = new URLSearchParams(); 
		          	  	param.append("id",myVue.userInfo.id); 
		          	  	param.append("userName",myVue.userInfo.userName); 
		          	  	param.append("realName",myVue.userInfo.realName); 
		          	  	param.append("sex",myVue.userInfo.sex); 
		          	  	param.append("identityCard",myVue.userInfo.identityCard); 
		          	  	param.append("birthdayString",myVue.userInfo.birthday); 
		          	  	param.append("country",myVue.userInfo.country); 
		          	  	param.append("nation",myVue.userInfo.nation); 
		          	  	param.append("byyx",myVue.userInfo.byyx); 
		          	  	param.append("mobilePhone",myVue.userInfo.mobilePhone); 
		          	  	param.append("email",myVue.userInfo.email); 
		          	  	debugger;
		          	  	axios.post('<%=ctxPath%>/htgl/userinfocontroller/updateselfinfo/api', param)
		          		  	 .then(function (response) {
		          			  	 if(response.data.flag){
		          			  		myVue.$Message.success({
		                                content: "修改成功",
		                                duration: 3,
		                                closable: true
		                            });
		          			  	    window.location.href='<%=ctxPath%>/htgl/userinfocontroller/toselfinfo';
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