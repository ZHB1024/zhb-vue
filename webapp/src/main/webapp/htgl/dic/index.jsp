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
            <breadcrumb-item>字典管理</breadcrumb-item> 
            <breadcrumb-item>字典信息</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', background: '#fff'}">
          <i-form inline method="post" action="" ref="formValidate">
        	
        		<form-item prop="category">
        			<i-select name="category" clearable filterable v-model="formParm.category" @on-change="getDicType" style="width: 150px;" placeholder="请选择字典类别">
                        	<i-option v-bind:value="item.value" v-for="item in categoryParm">
                        		{{item.value}}
                        	</i-option>
                	</i-select>
                </form-item>
                
                <form-item prop="name">
                  		<i-input type="text" name="name" v-model="formParm.name" :maxlength="15" placeholder="请输入名称"></i-input>
                </form-item >
                	
        		<form-item prop="type">
        			<i-select name="type" clearable v-model="formParm.type" ref="typeRef" style="width: 150px;" placeholder="请选择类型">
                        	<i-option v-bind:value="item.value" :key="item.value" v-for="item in typeParm">
                        		{{item.value}}
                        	</i-option>
                	</i-select>
                </form-item>
                
                <form-item>
                    	<i-button type="primary" @click="search()" > 查   询 </i-button>
                </form-item>
                	
                <form-item>
                    	<i-button type="primary" to="/htgl/dicinfocontroller/toupload">新增字典</i-button> 
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
    	categoryParm:[] ,
    	typeParm:[] ,
    	formParm:{
	        category:'',
	        name:'',
	        type:''
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
                title: '字典类别',
                key: 'category',
                minWidth: 100,
                sortable:true
            },
            {
                title: '名称',
                key: 'name',
                minWidth: 100
            },
            {
                title: '简称',
                key: 'name2',
                minWidth: 100
            },
            {
                title: '英文名称',
                key: 'name3',
                minWidth: 100
            },{
                title: '类型',
                key: 'type',
                minWidth: 100
            },
            {
                title: '排序',
                key: 'orderIndex',
                minWidth: 100
            },
            {
                title: '描述',
                key: 'remark',
                minWidth: 100
            },
            {
                title: '状态',
                key: 'deleteFlagName',
                minWidth: 100
            }
            /* ,
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
    	    axios.get('<%=ctxPath %>/htgl/dicinfocontroller/getdicinfopage/api'),
    	    axios.get('<%=ctxPath %>/htgl/dicinfocontroller/getdiccategory/api')
    	  ]).then(axios.spread(function (dicinfoResp,diccategoryResp) {
    		  myVue.tableDatas = dicinfoResp.data.data.result;
    		  flushPage(dicinfoResp.data.data);
    		  myVue.categoryParm = diccategoryResp.data.data;
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
    	  param.append("category",myVue.formParm.category); 
    	  param.append("name",myVue.formParm.name); 
    	  param.append("type",myVue.formParm.type); 
    	  axios.post('<%=ctxPath %>/htgl/dicinfocontroller/getdicinfopage/api', param)
    		  .then(function (response) {
    			  if(response.data.flag){
    				  myVue.tableDatas = response.data.data.result;
    				  flushPage(response.data.data);
    				  myVue.$forceUpdate();
                  }else{
                	  myVue.$Message.error({
                          content: response.data.errorMessages,
                          duration: 3,
                          closable: true
                      });
                  }
    		  })
      },
      //点击页码查询
      searchPage:function (page) {
    	  let param = new URLSearchParams(); 
    	  param.append("category",myVue.formParm.category); 
    	  param.append("name",myVue.formParm.name); 
    	  param.append("type",myVue.formParm.type); 
    	  param.append("pageSize",myVue.pageParm.pageCount); 
    	  param.append("currentPage",page); 
    	  axios.post('<%=ctxPath %>/htgl/dicinfocontroller/getdicinfopage/api', param)
    		  .then(function (response) {
    			  if(response.data.flag){
    				  myVue.tableDatas = response.data.data.result;
    				  flushPage(response.data.data);
    				  myVue.$forceUpdate();
                  }else{
                	  myVue.$Message.error({
                          content: response.data.errorMessages,
                          duration: 3,
                          closable: true
                      });
                  }
    		  })
      },
      //改变每页大小 
      changePageSize:function(pageSize){
    	  let param = new URLSearchParams(); 
    	  param.append("category",myVue.formParm.category); 
    	  param.append("name",myVue.formParm.name); 
    	  param.append("type",myVue.formParm.type); 
    	  param.append("pageSize",pageSize); 
    	  param.append("currentPage",1); 
    	  axios.post('<%=ctxPath %>/htgl/dicinfocontroller/getdicinfopage/api', param)
    		  .then(function (response) {
    			  if(response.data.flag){
    				  myVue.tableDatas = response.data.data.result;
    				  flushPage(response.data.data);
    				  myVue.$forceUpdate();
                  }else{
                	  myVue.$Message.error({
                          content: response.data.errorMessages,
                          duration: 3,
                          closable: true
                      });
                  }
    		  })
      },
      getDicType:function(category){
    	    let param = new URLSearchParams(); 
     	  	param.append("category",category); 
     	  	axios.post('<%=ctxPath %>/htgl/dicinfocontroller/getdictype/api', param)
     		  	.then(function (response) {
     			  	if(response.data.flag){
     			  		myVue.typeParm=response.data.data;
     			  		myVue.$refs.typeRef.clearSingleSelect();
     				  	myVue.$forceUpdate();
                   }else{
                 	  myVue.$Message.error({
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
