<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%
String ctxPath = request.getContextPath();
%>
<style> 
p{
margin-top:20px;
margin-left:30px;
}
</style>

<div id="app_content" style="height: 100%">
    <Layout :style="{padding: '0 24px 24px', height: '100%'}"> 
        <Breadcrumb :style="{margin: '24px 0'}"> 
            <breadcrumb-item>首页</breadcrumb-item> 
            <breadcrumb-item>用户管理</breadcrumb-item> 
            <breadcrumb-item>个人信息</breadcrumb-item> 
        </Breadcrumb> 
        
        <i-content :style="{padding: '24px', minHeight: '428px', background: '#fff'}">
            <Row style="background:#eee;padding:20px">
              <i-col span="20">
                  <Card :bordered="false">
                      <p slot="title">个人信息</p>
                      <a href="<%=ctxPath%>/htgl/userinfocontroller/toupdate" slot="extra" >
			            <Icon type="ios-loop-strong"></Icon>
			            修改
			          </a>
			          
                      <Row>
                        <i-col span="15">
                          <p>用户名： {{userInfo.userName}}</p>
                          <p>姓名： {{userInfo.realName}}</p>
                          <p>身份证号： {{userInfo.identityCard}}</p>
                          <p>性别： {{userInfo.sex}}</p>
                          <p>出生日期： {{userInfo.birthday}}</p>
                          <p>国家/地区： {{userInfo.country}}</p>
                          <p>民族： {{userInfo.nation}}</p>
                          <p>毕业院校： {{userInfo.byyx}}</p>
                          <p>电话： {{userInfo.mobilePhone}}</p>
                          <p>邮箱： {{userInfo.email}}</p>
                          <p>密码：*********
                          	<a  href="<%=ctxPath%>/htgl/userinfocontroller/tomodifypassword">
                          		<Icon type="ios-create" />
                          	</a>
                          </p>
                        </i-col>
                        
                        <i-col span="5">
                          <p style="margin-top:50px;margin-left: 100px;"><img :src="userInfo.lobId"/></p>
                          <p style="margin-top:50px">
                          	<Upload 
            					ref="upload" 
            					type="drag" 
            					name="upFile" 
            					:max-size="20480" 
            					:show-upload-list="false" 
            					:on-format-error="handleFormatError"
            					:on-exceeded-size="handleSizeError"
            					:on-success="uploadResponse"
            					:userId="userInfo.id"
            					action="<%=ctxPath%>/htgl/attachmentinfocontroller/uploadattachmentinfo">
        						<i-button type="primary" v-bind:userId="userInfo.id" id="updateHead">
                          			上传新头像
                          		</i-button>
    						</Upload>
                          </p>
                        </i-col>
                        
                      </Row>
                  </Card>
              </i-col>
          </Row>
        </i-content>
        
    </Layout>
</div>

<script>
var myVue =  new Vue({
	  el: '#app_content',
	  data:{
		  userInfo:{
			  userName:'',
			  realName:'',
			  identityCard:'',
			  sex:'',
			  birthday:'',
			  country:'',
			  nation:'',
			  byyx:'',
			  mobilePhone:'',
			  email:'',
			  lobId:''
		  },
	  },
	  created: function () {
		  axios.all([
	    	    axios.get('<%=ctxPath %>/htgl/userinfocontroller/getselfinfo/api')
	    	  ]).then(axios.spread(function (userInfoResp) {
	    		  myVue.userInfo = userInfoResp.data.data;
	    		  myVue.userInfo.lobId = '/images/loading.gif';
	    	  }));
	 },
	  methods:{
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

/* layui.use('upload', function(){
    var $ = layui.jquery;
    var upload = layui.upload;

    //修改头像
    var uploadInst = upload.render({
        elem: '#updateHead',
        auto: false,
        size: 500 ,//限制文件大小，单位 KB
        choose: function(obj){
            //读取本地文件
            obj.preview(function(index, file, result){
                console.log(result);
            });
        },
        error: function(){
        }
    });
}); */
/*0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）*/
/*弹出层*/
function updateHead(data){
	var id = data.getAttribute("id");
	var showContent = '<table align="center" style="border-collapse:separate; border-spacing:10px;">';

	showContent += generatorValue("id",id);
	   
	showContent += '</table>';
	layer.open({
        title: '修改头像',
        type: 1,
        //skin: 'layui-layer-rim', //加上边框
        area: ['600px', '600px'], //宽高
        content: showContent, 
        btn: ['确定'],
        success: function(layero, index){
        },
        yes: function(index, layero){
        	debugger;
            layer.closeAll();
        }
    });
}

function generatorValue(name,value){
    return '<tr><th>' + name + '：</th><td>' + value + '</td></tr>'; 
}
</script>