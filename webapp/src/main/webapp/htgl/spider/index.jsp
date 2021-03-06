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
            <breadcrumb-item>爬虫管理</breadcrumb-item> 
            <breadcrumb-item>爬取照片</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', minHeight: '428px', background: '#fff'}">
          <i-form inline ref="formInline" method="post" action="" >
                <form-item>
                    	<i-button type="primary" @click="handleSubmit('formInline')" > 摩天轮社区 </i-button>
                    	<i-button type="primary" @click="handleSubmit2('formInline')" > 千百撸 </i-button>
                    	<i-button type="primary" @click="handleSubmit3('formInline')" > 院校 </i-button>
                </form-item>
        	</i-form>
	</i-content>
        
    </Layout>
</div>


<script type="text/javascript">
var myVue = new Vue({
    el: '#app_content',
    methods: {
        handleSubmit:function(name) {
        	let param = new URLSearchParams(); 
     	  	axios.post('<%=ctxPath %>/htgl/jsoupspidercontroller/spideryellow', param)
     		  	.then(function (response) {
     			  	if(response.data.flag){
     			  		myVue.$Message.success({
                            content: "爬取成功，稍后查看结果......",
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
        handleSubmit2:function(name) {
        	let param = new URLSearchParams(); 
     	  	axios.post('<%=ctxPath %>/htgl/jsoupspidercontroller/spideryellow2', param)
     		  	.then(function (response) {
     			  	if(response.data.flag){
     			  		myVue.$Message.success({
                            content: "爬取成功，稍后查看结果......",
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
        handleSubmit3:function(name) {
        	let param = new URLSearchParams(); 
     	  	axios.post('<%=ctxPath %>/htgl/jsoupspidercontroller/spideryx', param)
     		  	.then(function (response) {
     			  	if(response.data.flag){
     			  		myVue.$Message.success({
                            content: "爬取成功，稍后查看结果......",
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