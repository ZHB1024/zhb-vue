<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%
String ctxPath = request.getContextPath();
%>
<div id="app_content" v-cloak style="height: 100%">
    <Layout :style="{padding: '0 20px 20px'}"> 
        <Breadcrumb :style="{margin: '24px 0'}"> 
            <breadcrumb-item>首页</breadcrumb-item> 
            <breadcrumb-item>功能管理</breadcrumb-item> 
            <breadcrumb-item>功能信息</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', background: '#fff'}">
        	<div class="filter-box clearfix">
                <i-button type="success" class="add-btn" to="/htgl/functioninfocontroller/toadd">新增功能</i-button>
            </div>
        	<i-table border :columns="columns1" :data="tableDatas" class="expand-table"></i-table> 
        </i-content>
        
    </Layout>
</div>

<script type="x-template" id="expand-detail">
<div>
  <Row v-for="record in row.children" class="expand-row">
    <i-col span="8">
      <span class="expand-key">功能名称: </span>
      <span class="expand-value">{{ record.name }}</span>
    </i-col>
    <i-col span="10">
      <span class="expand-key">访问路径: </span>
      <span class="expand-value">{{ record.path }}</span>
    </i-col>
    <i-col span="3">
      <span class="expand-key">顺序: </span>
      <span class="expand-value">{{ record.orderIndex }}</span>
    </i-col>

	<i-col span="2" align="center">
      <span class="expand-key"><i-button type="primary"  v-bind:update-id="record.id" size="small"  onclick="toUpdate(this)" > 修改 </i-button></span>
      <span class="expand-key"><i-button type="error"  v-bind:del-id="record.id" size="small" onclick="delRecord(this)" > 删除 </i-button></span>
    </i-col>

  </Row>
</div>
</script>
<!-- <i-col span="2" align="center">
      <span class="expand-key"><i-button type="primary" v-if="record.status==0||record.status==1" v-bind:update-id="record.id" size="small"  onclick="toUpdate(this)" > 修改 </i-button></span>
      <span class="expand-key"><i-button type="error" v-if="record.status==0||record.status==1" v-bind:del-id="record.id" size="small" onclick="delRecord(this)" > 删除 </i-button></span>

	  <span class="expand-key"><i-button type="primary" v-if="record.status!=0&&record.status!=1" disabled="disabled" v-bind:update-id="record.id" size="small"  onclick="toUpdate(this)" > 修改 </i-button></span>
      <span class="expand-key"><i-button type="error" v-if="record.status!=0&&record.status!=1" disabled="disabled" v-bind:del-id="record.id" size="small" onclick="delRecord(this)" > 删除 </i-button></span>
    </i-col> -->
<script>
Vue.component('newRow', {
	  props: ['row'],
	  template: '#expand-detail'
	});
var myVue = new Vue({
    el: '#app_content',
    data:{
    	tableDatas:[],
    	columns1:[
    		{
            	type: 'expand',
      			width: 100,
      			render: (h, params) => {
      			    return h("new-row", {
      			       props: {
      			    	  row: params.row
      			      }
      			    })
      			}
      		},{
                title: '序号',
                type:"index",
                width: 70
            },
            {
                title: '功能名称',
                key: 'name',
                minWidth: 100,
                sortable:true
            },
            {
                title: '访问路径',
                key: 'path',
                minWidth: 200
            },
            {
                title: '图标',
                key: 'icon',
                minWidth: 100
            },
            {
                title: '顺序',
                key: 'orderIndex',
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
    	    axios.get('<%=ctxPath %>/htgl/functioninfocontroller/getfunctions/api')
    	  ]).then(axios.spread(function (funinfoResp) {
    		  myVue.tableDatas = funinfoResp.data.data;
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
