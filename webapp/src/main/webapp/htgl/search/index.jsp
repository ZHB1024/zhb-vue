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
            <breadcrumb-item>搜索管理</breadcrumb-item> 
            <breadcrumb-item>搜索信息</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', minHeight: '428px', background: '#fff'}">
          <i-form inline ref="formInline" method="post" action="" >
                <form-item>
                    	<i-button type="primary" @click="handleSubmit()" > solrSearch </i-button>
                    	<i-button type="primary" @click="handleSubmit2()" > elasticSearch </i-button>
                </form-item>
        	</i-form>
	</i-content>
        
    </Layout>
</div>


<script type="text/javascript">
var myVue = new Vue({
    el: '#app_content',
    methods: {
        handleSubmit:function() {
        	let param = new URLSearchParams(); 
     	  	axios.post('<%=ctxPath %>/htgl/mqcontroller/sendmessage', param)
     		  	.then(function (response) {
     			  	if(response.data.flag){
     			  		myVue.$Message.success({
                            content: response.data.data,
                            duration: 3,
                            closable: true
                        });
                   }else{
                 	  myVue.$Message.error({
                           content: response.data.errorMessages,
                           duration: 3,
                           closable: true
                       });
                   }
     		  })
        },
        handleSubmit2:function() {
        	let param = new URLSearchParams(); 
     	  	axios.post('<%=ctxPath %>/htgl/mqcontroller/receivemessage', param)
     		  	.then(function (response) {
     			  	if(response.data.flag){
     			  		myVue.$Message.success({
                            content: response.data.data,
                            duration: 3,
                            closable: true
                        });
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
</script>