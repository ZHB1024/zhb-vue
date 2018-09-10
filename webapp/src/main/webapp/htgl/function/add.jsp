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
            <breadcrumb-item>新增功能</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', minHeight: '428px', background: '#fff'}">
          <div style="width:100%;">
            <div style="width: 600px;margin-left:  auto;margin-right:  auto;">
            	<i-form method="post" action="" ref="formValidate" :model="functionInfo" :rules="ruleValidate" :label-width="80">
                	
                	<form-item label="父节点" prop="parentId">
                	  <i-select name="parentId" v-model="functionInfo.parentId" placeholder="请选择父节点">
                        	<i-option v-bind:value="item.id" v-for="item in parentFunctions">
                        		{{item.name}}
                        	</i-option>
                    	</i-select>
                	</form-item>
                	
                	<form-item label="功能名称" prop="name">
                  		<i-input type="text" name="name" v-model="functionInfo.name" :maxlength="15" placeholder="请输入功能名称"></i-input>
                	</form-item >
                	
                	<form-item label="访问路径" prop="path">
                		   <i-input type="text" name="path" v-model="functionInfo.path" :maxlength="50" placeholder="请输入访问路径"></i-input>
                	</form-item >
                	
                	<form-item label="功能图标" prop="iconId">
                	  <i-select name="iconId" v-model="functionInfo.iconId" placeholder="请选择功能图标">
                        	<i-option v-bind:value="item.id" v-for="item in icons">
                        		{{item.name}}
                        	</i-option>
                    	</i-select>
                	</form-item>
                	
                	<form-item label="排序号" prop="orderIndex">
                  		<Input-number name="orderIndex" :min="1" :step="1" v-model="functionInfo.orderIndex"></Input-number>
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
		  functionInfo:{
			  parentId:'',
			  name:'',
			  path:''
			  iconId:'',
			  orderIndex:''
		  },
		  parentFunctions:[],
		  icons:[],
	      ruleValidate: {
	    	     name: [
	    		    { required: true, message: '请填写功能名称', trigger: 'change' }
	    		  ] ,
	    		  path: [
		    		    { required: true, message: '请填写访问路径', trigger: 'change' }
		    	  ],
		    	  orderIndex: [
		    		    { required: true, message: '请填写顺序号', trigger: 'change' }
		    	  ]
	    		  
	      } 
	    
	  },
	  created: function () {
		  axios.all([
	    	    axios.get('<%=ctxPath %>/htgl/functioninfocontroller/getparentfunctions/api'),
	    	    axios.get('<%=ctxPath %>/htgl/functioninfocontroller/getmaxorder/api'),
	    	    axios.get('<%=ctxPath %>/htgl/iconinfocontroller/geticoninfo/api')
	    	  ]).then(axios.spread(function (funinfoResp,maxOrderResp,iconResp) {
	    		  myVue.parentFunctions = funinfoResp.data.data;
	    		  myVue.functionInfo.orderIndex = maxOrderResp.data.data;
	    		  myVue.icons = iconResp.data.data;
	    	  }));
	 },
	  methods:{
		   handleSubmit:function (name) {
			   myVue.$refs[name].validate((valid) => {
				   if (valid){
					    let param = new URLSearchParams(); 
		          	  	param.append("parentId",myVue.functionInfo.parentId); 
		          	  	param.append("name",myVue.functionInfo.name); 
		          	  	param.append("path",myVue.functionInfo.path); 
		          	  	param.append("iconId",myVue.functionInfo.iconId); 
		          	  	param.append("orderIndex",myVue.functionInfo.orderIndex); 
		          	  	axios.post('<%=ctxPath%>/htgl/functioninfocontroller/addfunctioninfo/api', param)
		          		  	 .then(function (response) {
		          			  	 if(response.data.flag){
		          			  		myVue.$Message.success({
		                                content: "添加成功",
		                                duration: 3,
		                                closable: true
		                            });
		          			  	    window.location.href='<%=ctxPath%>/htgl/functioninfocontroller/toindex';
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