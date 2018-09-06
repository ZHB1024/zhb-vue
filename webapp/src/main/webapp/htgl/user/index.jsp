<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%
String ctxPath = request.getContextPath();
%>

<style>
li {list-style-type:none;}
</style>

<div id="app_content" >
    <Layout :style="{padding: '0 20px 20px'}"> 
        <Breadcrumb :style="{margin: '24px 0'}"> 
            <breadcrumb-item>首页</breadcrumb-item> 
            <breadcrumb-item>用户管理</breadcrumb-item> 
            <breadcrumb-item>用户信息</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', background: '#fff'}">
          <i-form inline method="post" action="<%=ctxPath %>/jb/worktime/searchworkrecord/api" ref="formValidate">
        	
        		<!-- <form-item prop="workdate">
                  		<Date-picker  type="daterange"  name="workdate"  v-model="formParm.workdate"  format="yyyy-MM-dd"  placeholder="加班日期" style="width: 200px">
                    	</Date-picker>  
                </form-item > -->
        	    
        		<form-item prop="content">
        			<i-select name="content" v-model="formParm.content" style="width: 150px;" placeholder="请选择加班内容">
        					<i-option value="" >
                        		全部
                        	</i-option>
                        	<i-option v-bind:value="optContent.id" v-for="optContent in contentParm">
                        		{{optContent.content}}
                        	</i-option>
                	</i-select>
                </form-item>
                
        		<form-item prop="status">
        			<i-select name="status" v-model="formParm.status" style="width: 150px;" placeholder="请选择审核状态">
        					<i-option value="" >
                        		全部
                        	</i-option>
                        	<i-option v-bind:value="checkStatus.index" v-for="checkStatus in statusParm">
                        		{{checkStatus.name}}
                        	</i-option>
                	</i-select>
                </form-item>
                
                <form-item>
                    	<i-button type="primary" @click="search()" > 查   询 </i-button>
                </form-item>
                	
                <form-item>
                    	<i-button type="primary" to="/jb/worktime/toaddrecord">新增加班记录</i-button> 
                </form-item>
                
        	</i-form>
        	
        	
        	<i-table border :columns="columns1" :data="tableDatas"></i-table> 
        	<Page :total="pageParm.totalCount" :current="pageParm.currentPage" :page-size="pageParm.pageCount" @on-change="searchPage" @on-page-size-change="changePageSize" show-total show-sizer show-elevator/>
        </i-content>
        
    </Layout>
</div>


<script>
var myVue = new Vue({
    el: '#app_content',
    data:{
    	contentParm:[] ,
    	statusParm:[] ,
    	formParm:{
	        content:'',
	        detail:'',
	        workdate:'',
	        status:''
	  	},
	  	pageParm:{
	  		currentPage:1,
	        pageCount:20,
	        totalCount:0
	  	},
    	tableDatas:[],
    	columns1:[
    		{
                title: '序号',
                type:"index",
                width: 70
            },
            {
                title: '姓名',
                key: 'realName',
                minWidth: 100,
                sortable:true
            },
            {
                title: '用户名',
                key: 'userName',
                minWidth: 100
            },
            {
                title: '性别',
                key: 'sex',
                minWidth: 100
            },
            {
                title: '身份证号',
                key: 'identityCard',
                minWidth: 100
            }/* ,
            {
                title: '操作',
                key: 'action',
                width: 150,
                align: 'center',
                render: (h, params) => {
                	var status = myVue.tableDatas[params.index].status,upFlag,delFlag;
                	if(status != 1 && status != 0){
                		upFlag = 'disabled';
                		delFlag = 'disabled';
                	}
                	return h('div', [
                        h('i-button', {
                            props: {
                                type: 'primary',
                                size: 'small',
                                disabled:upFlag
                            },
                            style: {
                                marginRight: '5px'
                            },
                            on: {
                                click: () => {
                                	myVue.modify(params.index)
                                }
                            }
                        }, '修改'),
                        h('i-button', {
                            props: {
                                type: 'error',
                                size: 'small',
                                disabled:delFlag
                            },
                            on: {
                                click: () => {
                                	  myVue.remove(params.index)
                                }
                            }
                        }, '删除')
                    ]);
                }
            } */
        ]
    },
    created: function () {
    	axios.all([
    	    axios.get('<%=ctxPath %>/htgl/userinfocontroller/searchuserinfo/api')
    	  ]).then(axios.spread(function (userinfoResp) {
    		  myVue.tableDatas = userinfoResp.data.data;
    		  //flushPage(workRecordResp.data.data);
    	  }));
    },
    methods: {
    	
        modify: function (index) {
    		window.location.href='<%=ctxPath%>/jb/worktime/touprecord?workRecordId='+myVue.tableDatas[index].id;
      	},
       remove: function (index) {
        this.$Modal.confirm({
            title: '提示',
            content: '您确定要删除么？',
            onOk:function(){
            	window.location.href='<%=ctxPath%>/jb/worktime/delrecord?workRecordId='+myVue.tableDatas[index].id;
            },
            onCancel:function(){
            }
         });
        
       },
       //查询按钮
       search:function () {
    	  let param = new URLSearchParams(); 
    	  param.append("contentId",myVue.formParm.content); 
    	  param.append("detail",myVue.formParm.detail); 
    	  param.append("workdate",myVue.formParm.workdate); 
    	  param.append("status",myVue.formParm.status); 
    	  axios.post('<%=ctxPath %>/jb/worktime/searchworkrecord/api', param)
    		  .then(function (response) {
    			  if(response.data.flag){
    				  myVue.tableDatas = response.data.data.result;
    				  flushPage(response.data.data);
    				  myVue.$forceUpdate();
                  }else{
                	  myVue.$Message.info({
                          content: response.data.errorMessages,
                          duration: 3,
                          closable: true
                      });
                  }
    		  })
      },
      //分页查询
      searchPage:function (page) {
    	  let param = new URLSearchParams(); 
    	  param.append("contentId",myVue.formParm.content); 
    	  param.append("detail",myVue.formParm.detail); 
    	  param.append("workdate",myVue.formParm.workdate); 
    	  param.append("status",myVue.formParm.status); 
    	  param.append("pageSize",myVue.pageParm.pageCount); 
    	  param.append("currentPage",page); 
    	  axios.post('<%=ctxPath %>/jb/worktime/searchworkrecord/api', param)
    		  .then(function (response) {
    			  if(response.data.flag){
    				  myVue.tableDatas = response.data.data.result;
    				  flushPage(response.data.data);
    				  myVue.$forceUpdate();
                  }else{
                	  myVue.$Message.info({
                          content: response.data.errorMessages,
                          duration: 3,
                          closable: true
                      });
                  }
    		  })
      },
      changePageSize:function(pageSize){
    	  let param = new URLSearchParams(); 
    	  param.append("contentId",myVue.formParm.content); 
    	  param.append("detail",myVue.formParm.detail); 
    	  param.append("workdate",myVue.formParm.workdate); 
    	  param.append("status",myVue.formParm.status); 
    	  param.append("pageSize",pageSize); 
    	  param.append("currentPage",1); 
    	  axios.post('<%=ctxPath %>/jb/worktime/searchworkrecord/api', param)
    		  .then(function (response) {
    			  if(response.data.flag){
    				  myVue.tableDatas = response.data.data.result;
    				  flushPage(response.data.data);
    				  myVue.$forceUpdate();
                  }else{
                	  myVue.$Message.info({
                          content: response.data.errorMessages,
                          duration: 3,
                          closable: true
                      });
                  }
    		  })
      }
      
    }
});
//刷新分页信息
function flushPage(page){
	myVue.pageParm.totalCount = page.totalCount;
	myVue.pageParm.pageCount = page.pageCount;
	myVue.pageParm.currentPage = page.currentPage;
};
</script>
