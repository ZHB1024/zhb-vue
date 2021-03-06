<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<style>
    .ivu-menu-vertical .ivu-menu-item{ padding: 8px 40px; }
</style>

<div id="app_menu" style="float:left;">
<!-- <Sider hide-trigger :style="{background: '#fff'}"> 
    <i-menu v-bind:active-name="childId" theme="light" width="auto" @on-select="selectMenu"  :open-names="[parentId]" >
        <Submenu v-bind:name="index+1" v-for="parent,index in menu">
            <template slot="title"> 
                <Icon v-bind:type="parent.icon"></Icon> {{ parent.name }}
            </template> 
            <Menu-item v-for="child in parent.children" v-bind:to="child.path" v-bind:name="index+1+ '--' + child.id" >{{ child.name }}</Menu-item> 
            
        </Submenu>
    </i-menu>
</Sider> -->

<i-menu active-name="childId" @on-select="selectMenu">
   <Menu-Group :title="parent.name" v-for="parent,index in menu">
        <Menu-Item v-bind:name="index+1+ '--' + child.id" v-for="child in parent.children" v-bind:to="child.path">
            <Icon v-bind:type="child.icon" /></Icon>
            <span>{{ child.name }}</span>
        </Menu-Item>
   </Menu-Group>
</i-menu>

</div>
<script>
var COOKIE_NAME = "MenuCookie";
var COOKIE_PATH = "/";
new Vue({
      el: '#app_menu',
      data: {
    	  //menu:[{"children":[{"name":"时间管理","id":"jbjl_sjgl","url":"/jb/worktime/getselfworkrecordindex"},{"name":"预览提交","id":"jbjl_ylsb","url":"/jb/worktime/getselfworkdayndex"}],"name":"加班记录","id":"jbjl","url":null}],
    	  menu:[],
    	  parentId:null,
    	  childId:null
      },
      methods: {
    	  
    	  selectMenu:function (name) {
    		  console.log(name)
    		  var values = name.split("--");
    		  var parentId= values[0];
    		  var childId= values[1];
    		  var cookieValue = parentId + "--" + childId;
    		  document.cookie=COOKIE_NAME+ "=" +escape(cookieValue)
    	  }
      } ,
      beforeCreate: function(){
    	  axios({
    		  method:'get',
    		  url:'/htgl/authoritycontroller/getfunctions/api',
    		  responseType:'json'
    		}).then((response) => {
    			this.menu=response.data.data;
    		});
      },
      created: function () {
    	  //获取点击的菜单
    	  if (document.cookie.length>0){
    		  var c_start=document.cookie.indexOf(COOKIE_NAME + "=")
    		  if (c_start!=-1)
    		  { 
    		    c_start=c_start + COOKIE_NAME.length+1 
    		    c_end=document.cookie.indexOf(";",c_start)
    		    if (c_end==-1) c_end=document.cookie.length
    		    var cookieValue = unescape(document.cookie.substring(c_start,c_end))
    		    var values = cookieValue.split("--");
    		    console.log(cookieValue);
    		    this.parentId=values[0];
    		    this.childId=cookieValue;
    		  }else{
    			  console.log("没有找到cookie");
    		  }
    	  }else{
			  console.log("没有找到cookie");
		  } 
          console.log("菜单初始化--加载中...TODO")
      } 
});

</script>
