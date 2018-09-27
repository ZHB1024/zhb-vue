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
            <breadcrumb-item><a href="<%=ctxPath%>/"><Icon type="md-home"></Icon></a></breadcrumb-item>
            <breadcrumb-item>附件管理</breadcrumb-item> 
            <breadcrumb-item>附件信息</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', background: '#fff'}">
          <i-form inline method="post" action="" ref="formValidate">
        	
                <form-item prop="fileName">
                  		<i-input type="text" clearable name="fileName" v-model="formParm.fileName" :maxlength="15" placeholder="请输入附件名称"></i-input>
                </form-item >
                	
        		<form-item prop="type">
        			<i-select name="type" clearable v-model="formParm.type" style="width: 150px;" placeholder="请选择附件类别">
                        	<i-option v-bind:value="item.value" :key="item.value" v-for="item in typeParm">
                        		{{item.key}}
                        	</i-option>
                	</i-select>
                </form-item>
                
                <form-item>
                    	<i-button type="primary" @click="search()" > 查   询 </i-button>
                </form-item>
                	
                <form-item>
                    	<i-button type="primary" to="/htgl/attachmentinfocontroller/toupload">新增附件</i-button> 
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
    	typeParm:[] ,
    	formParm:{
    		fileName:'',
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
            	title: '附件', 
            	key: 'thumbnailUrl',
            	render: (h, params) => {
            	    console.log(params.row)
            	    return h('div', {
            	      	attrs: {
            	        		style: 'width: 40px;height: 40px;'
            	      		},
            	    	}, [
            	        h('img', {
            	          props: {
            	            type: 'primary',
            	            size: 'small'
            	          },
            	          attrs: {
            	            src: params.row.thumbnailUrl, style: 'width: 40px;height: 40px;border-radius: 2px;'
            	          },
                          on: {
                              click: () => {
                              	  myVue.showOriginal(params)
                              }
                          }
            	        }),
            	      ]);
            	  }
            },
            {
                title: '附件名称',
                key: 'fileName',
                minWidth: 100,
                sortable:true
            },
            {
                title: '附件类型',
                key: 'type',
                minWidth: 100
            },
            /* {
                title: '内容类型',
                key: 'contentType',
                minWidth: 100
            },
            {
                title: '附件大小',
                key: 'fileSize',
                minWidth: 100,
                sortable:true
            },
            {
                title: '附件路径',
                key: 'filePath',
                minWidth: 150
            }, */
            {
                title: '创建时间',
                key: 'createTime',
                minWidth: 100,
                sortable:true
            },
            {
                title: '状态',
                key: 'deleteFlagName',
                minWidth: 100,
                sortable:true
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
    	    axios.get('<%=ctxPath %>/htgl/attachmentinfocontroller/getattachmentinfopage/api'),
    	    axios.get('<%=ctxPath %>/htgl/attachmentinfocontroller/getattachmenttype/api')
    	  ]).then(axios.spread(function (attachmentinfoResp,typeResp) {
    		  myVue.tableDatas = attachmentinfoResp.data.data.result;
    		  flushPage(attachmentinfoResp.data.data);
    		  myVue.typeParm = typeResp.data.data;
    	  }));
    },
    methods: {
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
    	  if(undefined == myVue.formParm.type){
    		  myVue.formParm.type = "";
    	  }
    	  param.append("fileName",myVue.formParm.fileName); 
    	  param.append("type",myVue.formParm.type);
    	  param.append("pageSize",myVue.pageParm.pageCount); 
   	   	  param.append("currentPage",1); 
    	  axios.post('<%=ctxPath %>/htgl/attachmentinfocontroller/getattachmentinfopage/api', param)
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
    	  param.append("fileName",myVue.formParm.fileName); 
    	  param.append("type",myVue.formParm.type); 
    	  param.append("pageSize",myVue.pageParm.pageCount); 
    	  param.append("currentPage",page); 
    	  axios.post('<%=ctxPath %>/htgl/attachmentinfocontroller/getattachmentinfopage/api', param)
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
    	  param.append("fileName",myVue.formParm.fileName); 
    	  param.append("type",myVue.formParm.type); 
    	  param.append("pageSize",pageSize); 
    	  param.append("currentPage",1); 
    	  axios.post('<%=ctxPath %>/htgl/attachmentinfocontroller/getattachmentinfopage/api', param)
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
      /*0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）*/
      /*弹出原图*/
      showOriginal:function(data){
   	   var showContent = '<div align="center"><img src="' + data.row.originalUrl  +'"/>';
   	   showContent += '</div>';
   	   debugger;
   	   layer.open({
   	        title: data.row.fileName,
   	        type: 1,
   	        //skin: 'layui-layer-rim', //加上边框
   	        area: ['600px', '600px'], //宽高
   	        content: showContent, 
   	        btn: ['确定'],
   	        success: function(layero, index){
   	        },
   	        yes: function(index, layero){
   	            layer.closeAll();
   	        }
   	    });
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
