<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%
String ctxPath = request.getContextPath();
%>
<div id="app_content" v-cloak style="height: 100%">
    <Layout :style="{padding: '0 20px 20px'}"> 
        <Breadcrumb :style="{margin: '24px 0'}"> 
            <breadcrumb-item><a href="<%=ctxPath%>/"><Icon type="md-home"></Icon></a></breadcrumb-item>
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
      <span class="expand-value">{{ record.order }}</span>
    </i-col>

	<i-col span="3" align="center">
      <span class="expand-key"><i-button type="primary"  v-if="record.deleteFlag==0" v-bind:update-id="record.id" size="small"  onclick="toUpdate(this)" > 修改 </i-button></span>
      <span class="expand-key"><i-button type="error"  v-if="record.deleteFlag==0" v-bind:del-id="record.id" size="small" onclick="delRecord(this)" > 删除 </i-button></span>
      <span class="expand-key"><i-button type="primary"  v-if="record.deleteFlag==0" disabled="disabled" v-bind:open-id="record.id" size="small" onclick="openRecord(this)" >恢复 </i-button></span>

      <span class="expand-key"><i-button type="primary"  v-if="record.deleteFlag==1" disabled="disabled" v-bind:update-id="record.id" size="small"  onclick="toUpdate(this)" > 修改 </i-button></span>
      <span class="expand-key"><i-button type="error"  v-if="record.deleteFlag==1" disabled="disabled" v-bind:del-id="record.id" size="small" onclick="delRecord(this)" > 删除 </i-button></span>
      <span class="expand-key"><i-button type="primary"  v-if="record.deleteFlag==1" v-bind:open-id="record.id" size="small" onclick="openRecord(this)" >恢复 </i-button></span>

    </i-col>

  </Row>
</div>
</script>
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
                key: 'order',
                minWidth: 100
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
    	    axios.get('<%=ctxPath %>/htgl/functioninfocontroller/getfunctions/api')
    	  ]).then(axios.spread(function (funinfoResp) {
    		  myVue.tableDatas = funinfoResp.data.data;
    	  }));
    },
    methods: {
        modify: function (index) {
    		window.location.href='<%=ctxPath%>/htgl/functioninfocontroller/toupdate?id='+myVue.tableDatas[index].id;
      	},
       remove: function (index) {
    	   myVue.$Modal.confirm({
    	        title: '提示',
    	        content: '您确定要删除么？',
    	        onOk:function(){
    	        	let param = new URLSearchParams(); 
    	      	    param.append("id",myVue.tableDatas[index].id); 
    	       	    axios.post('<%=ctxPath %>/htgl/functioninfocontroller/delfunctioninfo/api', param)
    	      		  .then(function (response) {
    	      			  if(response.data.flag){
    	      				  myVue.$Message.success({
    	                          content: "删除成功",
    	                          duration: 3,
    	                          closable: true
    	                      });
    	      				  myVue.tableDatas=response.data.data;
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
               content: '您确定要恢复这个功能么？',
               onOk:function(){
               		let param = new URLSearchParams(); 
             	    param.append("id",myVue.tableDatas[index].id); 
             	    axios.post('<%=ctxPath %>/htgl/functioninfocontroller/openfunctioninfo/api', param)
       		         .then(function (response) {
       			  		if(response.data.flag){
       			  			myVue.$Message.success({
                                   content: "恢复成功",
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
//修改功能信息
function toUpdate(data){
	var id = data.getAttribute("update-id");
	window.location.href='<%=ctxPath%>/htgl/functioninfocontroller/toupdate?id='+id;
};
//删除功能信息
function delRecord(data){
	var id = data.getAttribute("del-id");
	myVue.$Modal.confirm({
        title: '提示',
        content: '您确定要删除么？',
        onOk:function(){
        	let param = new URLSearchParams(); 
      	    param.append("id",id); 
       	    axios.post('<%=ctxPath %>/htgl/functioninfocontroller/delfunctioninfo/api', param)
      		  .then(function (response) {
      			  if(response.data.flag){
      				  myVue.$Message.success({
                          content: "删除成功",
                          duration: 3,
                          closable: true
                      });
      				  myVue.tableDatas=response.data.data;
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
//恢复功能信息
function openRecord(data){
	var id = data.getAttribute("open-id");
	myVue.$Modal.confirm({
        title: '提示',
        content: '您确定要恢复么？',
        onOk:function(){
        	let param = new URLSearchParams(); 
      	    param.append("id",id); 
       	    axios.post('<%=ctxPath %>/htgl/functioninfocontroller/openfunctioninfo/api', param)
      		  .then(function (response) {
      			  if(response.data.flag){
      				  myVue.$Message.success({
                          content: "恢复成功",
                          duration: 3,
                          closable: true
                      });
      				  myVue.tableDatas=response.data.data;
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
</script>
