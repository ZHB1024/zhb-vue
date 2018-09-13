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
            <breadcrumb-item><a href="/">首页</a></breadcrumb-item> 
            <breadcrumb-item>图标管理</breadcrumb-item> 
            <breadcrumb-item>图标信息</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', background: '#fff'}">
        	<div class="filter-box clearfix">
                <i-button type="success" class="add-btn" to="/htgl/iconinfocontroller/toadd">新增图标</i-button>
            </div>
        	<i-table border :columns="columns1" :data="tableDatas"></i-table> 
        </i-content>
        
    </Layout>
</div>


<script>
var myVue = new Vue({
    el: '#app_content',
    data:{
    	tableDatas:[],
    	columns1:[
    		{
                title: '序号',
                type:"index",
                width: 70
            },
            {
                title: '图标名称',
                key: 'name',
                minWidth: 100,
                sortable:true
            },
            {
                title: '图标代码',
                key: 'value',
                minWidth: 100
            },
            {
                title: '状态',
                key: 'deleteFlagName',
                minWidth: 100
            },
            {
                title: '创建时间',
                key: 'createTime',
                minWidth: 100,
                sortable:true
            },
            {
                title: '修改时间',
                key: 'updateTime',
                minWidth: 100,
                sortable:true
            },
            {
                title: '操作',
                key: 'action',
                width: 200,
                align: 'center',
                render: (h, params) => {
                	var status = myVue.tableDatas[params.index].deleteFlag,openFlag,delFlag;
                	if(status == 0 ){
                		openFlag = 'disabled';
                	}
                	if(status == 1 ){
                		delFlag = 'disabled';
                	}
                	return h('div', [
                        h('i-button', {
                            props: {
                                type: 'primary',
                                size: 'small',
                                disabled:delFlag
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
                            style: {
                                marginRight: '5px'
                            },
                            on: {
                                click: () => {
                                	  myVue.remove(params.index)
                                }
                            }
                        }, '删除'),
                        h('i-button', {
                            props: {
                                type: 'primary',
                                size: 'small',
                                disabled:openFlag
                            },
                            on: {
                                click: () => {
                                	  myVue.open(params.index)
                                }
                            }
                        }, '恢复')
                    ]);
                }
            } 
        ]
    },
    created: function () {
    	axios.all([
    	    axios.get('<%=ctxPath %>/htgl/iconinfocontroller/geticoninfo/api')
    	  ]).then(axios.spread(function (iconinfoResp) {
    		  myVue.tableDatas = iconinfoResp.data.data;
    	  }));
    },
    methods: {
        modify: function (index) {
    		window.location.href='<%=ctxPath%>/htgl/iconinfocontroller/toupdate?id='+myVue.tableDatas[index].id;
      	},
       remove: function (index) {
    	   this.$Modal.confirm({
               title: '提示',
               content: '您确定要删除这个图标么？',
               onOk:function(){
               	let param = new URLSearchParams(); 
             	    param.append("id",myVue.tableDatas[index].id); 
             	    param.append("deleteFlag",1); 
             	    axios.post('<%=ctxPath %>/htgl/iconinfocontroller/deloropenicon/api', param)
       		         .then(function (response) {
       			  		if(response.data.flag){
       			  			myVue.$Message.success({
                                   content: "删除成功",
                                   duration: 3,
                                   closable: true
                               });
       				 		 myVue.tableDatas = response.data.data;
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
               onCancel:function(){
               }
            });
       },
       open:function(index){
    	   this.$Modal.confirm({
               title: '提示',
               content: '您确定要恢复这个图标么？',
               onOk:function(){
               	let param = new URLSearchParams(); 
             	    param.append("id",myVue.tableDatas[index].id); 
             	    param.append("deleteFlag",0); 
             	    axios.post('<%=ctxPath %>/htgl/iconinfocontroller/deloropenicon/api', param)
       		         .then(function (response) {
       			  		if(response.data.flag){
       			  			myVue.$Message.success({
                                   content: "开通成功",
                                   duration: 3,
                                   closable: true
                               });
       				 		 myVue.tableDatas = response.data.data;
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
               onCancel:function(){
               }
            });
       }
    }
});
</script>
