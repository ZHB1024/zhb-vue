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
            <breadcrumb-item>图标管理</breadcrumb-item> 
            <breadcrumb-item>图标信息</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', background: '#fff'}">
          <i-form inline method="post" action="" ref="formValidate">
               
                <form-item>
                    	<i-button type="primary" to="/htgl/iconinfocontroller/toadd">新增图标</i-button> 
                </form-item>
                
        	</i-form>
        	
        	
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
                title: '操作',
                key: 'action',
                width: 150,
                align: 'center',
                render: (h, params) => {
                	return h('div', [
                        h('i-button', {
                            props: {
                                type: 'primary',
                                size: 'small'
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
                                size: 'small'
                            },
                            on: {
                                click: () => {
                                	  myVue.remove(params.index)
                                }
                            }
                        }, '删除')
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
       }
    }
});
</script>
