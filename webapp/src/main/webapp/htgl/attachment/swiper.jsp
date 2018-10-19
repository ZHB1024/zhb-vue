<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%
String ctxPath = request.getContextPath();
%>
<style scoped>
.time{
    font-size: 14px;
    font-weight: bold;
}
.content{
    padding-left: 5px;
}
.swiper-container {
    width: 600px;
    height: 300px;
}  
</style>
<div id="app_content" >
    <Layout :style="{padding: '0 20px 20px'}"> 
    	<Breadcrumb :style="{margin: '24px 0'}"> 
            <breadcrumb-item><a href="<%=ctxPath%>/"><Icon type="md-home"></Icon></a></breadcrumb-item>
            <breadcrumb-item>字典管理</breadcrumb-item> 
            <breadcrumb-item>字典信息</breadcrumb-item> 
        </Breadcrumb> 
    	<i-content :style="{padding: '24px', background: '#fff'}">
    		<div class="swiper-container">
        
    		<div class="swiper-wrapper">
    			<!-- <div class="swiper-slide" v-for="item in imgList" :style="{backgroundImage: 'url(' + item + ')'}">
    			</div> -->
        		<div class="swiper-slide">
        			<img src="/images/loading.gif" alt="">
        		</div>
        		<div class="swiper-slide">
        			<img src="/images/12.jpg" alt="">
        		</div>
        		<div class="swiper-slide">
        			<img src="/images/loading.gif" alt="">
        		</div>
        		<div class="swiper-slide">
        			<img src="/images/12.jpg" alt="">
        		</div>
    		</div>
    		
    		<!-- 如果需要分页器 -->
    		<!-- <div class="swiper-pagination"></div> -->
    
    		<!-- 如果需要导航按钮 -->
    		<div class="swiper-button-prev"></div>
    		<div class="swiper-button-next"></div>
    
    		<!-- 如果需要滚动条 -->
    		<!-- <div class="swiper-scrollbar"></div> -->
			</div>
    	</i-content>
    </Layout>
</div>


<script>
var myVue = new Vue({
    el: '#app_content',
    data:{
    	homeIcon:{
    		name:'',
    		value:''
    	},
    },
    created: function () {
    },
    methods: {
    	
    },
    mounted: function (){
    	var mySwiper = new Swiper ('.swiper-container', {
    	    direction: 'vertical',
    	    loop: true,
    	    
    	    // 如果需要分页器
    	    //pagination: '.swiper-pagination',
    	    
    	    // 如果需要前进后退按钮
    	    nextButton: '.swiper-button-next',
    	    prevButton: '.swiper-button-prev',
    	    
    	    // 如果需要滚动条
    	    //scrollbar: '.swiper-scrollbar',
    	  })       
    }
});
</script>
