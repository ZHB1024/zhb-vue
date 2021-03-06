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
            <breadcrumb-item>附件管理</breadcrumb-item> 
            <breadcrumb-item>附件上传</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', minHeight: '428px', background: '#fff'}">
          <div style="width:100%;">
            <div style="width: 600px;margin-left: auto;margin-right: auto;">
            	<Upload 
            		multiple 
            		ref="upload" 
            		type="drag" 
            		name="upFile" 
            		:max-size="20480" 
            		:show-upload-list="true" 
            		:on-format-error="handleFormatError"
            		:on-exceeded-size="handleSizeError"
            		:on-success="uploadResponse"
            		action="<%=ctxPath%>/htgl/attachmentinfocontroller/uploadattachmentinfo">
            		
        			<div style="padding: 20px 0">
            			<Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
            			<p>请选择待上传的文件</p>
        			</div>
    			</Upload>
    			
    			<!-- <div v-for="(item, index) in file"> 
    				{{ item.name }} 
					<Icon type="md-close" @click="delectFile(item.keyID)" size="24" color="red" style="margin-left:20px;"/>
		  		</div>
		  		<i-button v-if="file.length > 0" type="primary" @click="upload" :loading="loadingStatus" style="margin-top:30px;">
						 上传
				</i-button> -->
				
            </div>
          </div> 
	</i-content>
        
    </Layout>
</div>


<script type="text/javascript">
var myVue =  new Vue({
	  el: '#app_content',
	  data:{
		  /* file: [],//用于显示
		  uploadFile:[],//用于后台上传
          loadingStatus: false */
	  },
	  methods:{
		   // 上传文件前的事件
           /* handleUpload:function (file) {
            	 // 选择文件后 这里判断文件类型 保存文件 自定义一个keyid 值 方便后面删除操作
            	 let keyID = Math.random().toString().substr(2);
            	 file['keyID'] = keyID;
            	 
            	 this.file.push(file);// 保存文件到总展示文件数据里
            	 
            	 this.uploadFile.push(file)// 保存文件到需要上传的文件数组里
            	 
            	 return false;// 返回 falsa 停止自动上传 我们需要手动上传
           }, */
           // 上传文件格式不正确
           handleFormatError:function (file) {
               myVue.$Message.error({
             		content: file.name + " 的文件格式不允许",
             		duration: 4,
             		closable: true
         		});
               return false;
           },
           // 上传文件大小超出限制
           handleSizeError:function (file) {
        	   myVue.$Message.error({
            		content: file.name + " 的文件大小超出20MB限制",
            		duration: 4,
            		closable: true
        		});
              return false;
           },
           /* delectFile:function (keyID) { // 删除文件
        	   this.file = this.file.filter(item => {
                   return item.keyID != keyID
               });
               this.uploadFile = this.uploadFile.filter(item => {
                   return item.keyID != keyID
               });
           },
           upload:function () { // 上传文件
        	   this.loadingStatus = true;
               if(this.uploadFile.length === 0 ) {
                   this.$Message.error('未选择上传文件') 
                   return false
               }
               var size = this.uploadFile.length;
               for (var i = size-1; i >= 0; i--) {
                   let fileItem = this.file[i];
                   this.$refs.upload.post(fileItem);
                   
                   this.file = this.file.filter(item => {
                   		return item.keyID != fileItem.keyID
               	   });
               	   this.uploadFile = this.uploadFile.filter(item => {
                   		return item.keyID != fileItem.keyID
               	   });
               }
               if(this.uploadFile.length == 0){
            	   this.loadingStatus = false;
                   this.$Message.success('Success');
               } 
           }, */
           // 文件上传结果回调 
           uploadResponse:function (response, file, fileList) { 
        	   if(!response.flag){
        		   myVue.$Message.error({
                		content: response.errorMessages,
                		duration: 4,
                		closable: true
            		});
        		   this.$refs.upload.fileList = this.$refs.upload.fileList.filter(item => {
                 		return item.uid != file.uid
             	   });
        	   }
           }
	  }
});
</script>