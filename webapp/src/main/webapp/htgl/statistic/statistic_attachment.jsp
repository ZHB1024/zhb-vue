<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<%
    String ctxPath = request.getContextPath();
%>
<div id="app_content" v-cloak>
    <Layout :style="{padding: '0 24px 24px', height: '100%'}">
    	<Breadcrumb :style="{margin: '24px 0'}"> 
            <breadcrumb-item><a href="/">首页</a></breadcrumb-item> 
            <breadcrumb-item>统计管理</breadcrumb-item> 
            <breadcrumb-item>附件统计</breadcrumb-item> 
        </Breadcrumb> 
        <i-content :style="{padding: '24px', minHeight: '428px', background: '#fff'}">
        	<!-- <i-form inline method="post" action="" ref="formValidate">
        	
                <form-item prop="fileName">
                  		<i-input type="text" clearable name="fileName" v-model="formParm.fileName" :maxlength="15" placeholder="请输入附件名称"></i-input>
                </form-item >
                	
        		<form-item prop="type">
        			<i-select name="type" clearable v-model="formParm.type" style="width: 150px;" placeholder="请选择附件类别">
                        	<i-option v-bind:value="item.value" :key="item.value" v-for="item in typeParm">
                        		{{item.key}}
                        	</i-option>
                	</i-select>
                </form-item>
                
                <form-item>
                    	<i-button type="primary" @click="search()" > 查   询 </i-button>
                </form-item>
                	
        	</i-form> -->

            <Row>
                <i-col span="10">
			        <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
			        <div id="main" style="width: 700px;height:600px;"></div>
                </i-col>
                <i-col span="2">
                <div style="width: 20px;height:600px;"></div>
                </i-col>
                <i-col span="10">
			        <div id="main2" style="width: 700px;height:600px;"></div>
                </i-col>
            </Row>
        </i-content>
    </Layout>
</div>
<script type="text/javascript">
var statisticEmpJson = <%=request.getAttribute("statisticEmpJson") %> ;
var statisticContentJson = <%=request.getAttribute("statisticContentJson") %> ;
var workdateJson = <%=request.getAttribute("workdateJson") %> ;
var organizationIdJson = '<%=request.getAttribute("organizationId") %>' ;
var statusJson = '<%=request.getAttribute("status") %>' ;
var mainChart,main2Chart;
var myVue = new Vue({
    el: '#app_content',
    data:{
    	formParm:{
            organizationId:'',
            workdate:workdateJson,
            status:statusJson,
            employeeId:''
        },
        orgsParm:[],
        statusParm:[],
        empParm:[],
    	empworkhours:{
    		orgName:'测试',
	        employees:['张三','李四','王二'],
	        workHours:[12,4,6]
	  	},
    	contentworkhours:{
    		orgName:'测试',
	        contents:['学籍学历平台','学历认证','网上申请','知识库','图像采集'],
	        workHours:[12,4,6,45,67],
	        nameValues:[
                {value:335, name:'学籍学历平台'},
                {value:310, name:'学历认证'},
                {value:234, name:'网上申请'},
                {value:135, name:'知识库'},
                {value:1548, name:'图像采集'}
            ]
	  	}
    },
    created: function () {
    },
    methods:{
    	 search:function (name) {
    		 let param = new URLSearchParams(); 
      	     param.append("organizationId",myVue.formParm.organizationId); 
      	     if(undefined ==  myVue.formParm.employeeId){
      	    	myVue.formParm.employeeId = '';
      	     }
      	     param.append("employeeId",myVue.formParm.employeeId); 
      	     
      	     if(!myVue.formParm.workdate[0]){
       	    	myVue.$Message.warning({
                    content: "请选择日期段",
                    duration: 3,
                    closable: true
                });
       	    	return;
       	     }
      	     param.append("workdate",myVue.formParm.workdate); 
      	     param.append("status",myVue.formParm.status); 
      	     //等待动画
			 myVue.$Spin.show();
     	     axios.all([
        	    axios.post('<%=ctxPath %>/jb/check/statistics/empworkhours/api', param),
        	    axios.post('<%=ctxPath %>/jb/check/statistics/contentworkhours/api', param)
        	  ]).then(axios.spread(function (empworkhoursResp,contentworkhoursResp) {
        		  //结束动画
        		  if(empworkhoursResp.data.flag){
        			  myVue.empworkhours = empworkhoursResp.data.data;
        			  myVue.$options.methods.initMainEchart();
        		  }
        		  if(contentworkhoursResp.data.flag){
        			  myVue.contentworkhours = contentworkhoursResp.data.data;
        			  myVue.$options.methods.initMain2Echart();
        		  }
        		  console.log("结束动画。。。。");
        		  myVue.$Spin.hide();
        	  }));
    	 },
    	 disposeChart:function(){
    		 if (mainChart != null && mainChart != "" && mainChart != undefined) {
 		    	mainChart.dispose();
	    	 }
 		     if (main2Chart != null && main2Chart != "" && main2Chart != undefined) {
 		    	main2Chart.dispose();
	    	 }
    	 },
    	 //initMain
    	 initMainEchart:function(){
    		    //基于准备好的dom，初始化echarts实例，如果已经初始化，则重新初始化
    		    if (mainChart != null && mainChart != "" && mainChart != undefined) {
    		    	mainChart.dispose();
	    		}
    		    mainChart = echarts.init(document.getElementById('main'));
    	        var app = {};
    	        var title = '加班时长统计表';
    	        var option = {
    	            tooltip: {
    	                trigger: 'axis',
    	                axisPointer: {
    	                    type: 'cross',
    	                    crossStyle: {
    	                        color: '#999'
    	                    }
    	                }
    	            },
    	            toolbox: {
    	            	feature: {
    	                    dataView: {show: true, readOnly: false},
    	                    magicType: {show: true, type: ['bar']},
    	                    restore: {show: true},
    	                    saveAsImage: {show: true}
    	                }
    	            },
    	            color: ['#3398DB'],
    	            legend: {
    	                data:['加班时长']
    	            },
    	            xAxis: [
    	                {
    	                    type: 'category',
    	                    data: myVue.empworkhours.employees,
    	                    axisPointer: {
    	                        type: 'shadow'
    	                    }
    	                }
    	            ],
    	            yAxis: [
    	                {
    	                    type: 'value',
    	                    name: '时长（小时）',
    	                    min: 0,
    	                    max: 50,
    	                    interval: 10,
    	                    axisLabel: {
    	                        formatter: '{value}'
    	                    }
    	                }
    	            ],
    	            series: [
    	                {
    	                    name:'加班时长',
    	                    type:'bar',
    	                    data:myVue.empworkhours.workHours,
    	                    itemStyle: {
    	                        normal: {
    	                        	label: {
    	                        		show: true, //开启显示
    	                        		position: 'top', //在上方显示
    	                        		textStyle: { //数值样式
    	                        		    color: 'black',
    	                        		    fontSize: 16
    	                        		}
    	                            }
    	                        }
    	                    },
    	                    markLine : {
    	                        symbol : 'none',
    	                        itemStyle : {
    	                          normal : {
    	                            color:'#1e90ff',
    	                            label : {
    	                              show:true
    	                            }
    	                          }
    	                        },
    	                        data : [{type : 'average', name: '平均值'}]
    	                      }
    	                }
    	            ]
    	        };
    	        if (option && typeof option === "object") {
    	        	// 使用刚指定的配置项和数据显示图表。
    	            mainChart.setOption(option, true);
    	        }
    	 },
    	 
    	 //initMian2
    	 initMain2Echart:function(){
    		   if (main2Chart != null && main2Chart != "" && main2Chart != undefined) {
    			 	main2Chart.dispose();
	    		}
    	        main2Chart = echarts.init(document.getElementById('main2'));
    	        var option2 = {
    	            title : {
    	                text: '加班内容加班时长',
    	                subtext: myVue.contentworkhours.orgName,
    	                x:'center'
    	            },
    	            toolbox: {
    	            	feature: {
    	                    dataView: {show: true, readOnly: false},
    	                    saveAsImage: {show: true}
    	                }
    	            },
    	            tooltip : {
    	                trigger: 'item',
    	                formatter: "{b} : {c} 小时 <br/>   {d}%"
    	            },
    	            legend: {
    	                orient: 'vertical',
    	                left: 'left',
    	                data: myVue.contentworkhours.contents
    	            },
    	            series : [
    	                {
    	                    name: '加班内容',
    	                    type: 'pie',
    	                    radius : '55%',//饼的半径
    	                    center: ['50%', '60%'],//饼的中心位置
    	                    data:myVue.contentworkhours.nameValues,
    	                    itemStyle: {
    	                        emphasis: {
    	                            shadowBlur: 10,
    	                            shadowOffsetX: 0,
    	                            shadowColor: 'rgba(0, 0, 0, 0.5)'
    	                        }
    	                    }
    	                }
    	            ]
    	        };
    	        if (option2 && typeof option2 === "object") {
    	        	main2Chart.setOption(option2, true);
    	        }
    	 },
    	  //组织 员工级联
         getEmployees:function(organizationId){
             let param = new URLSearchParams(); 
             param.append("organizationId",organizationId); 
             axios.post('<%=ctxPath %>/jb/check/statistics/getemps/api', param)
                 .then(function (response) {
                     if(response.data.flag){
                         myVue.empParm=response.data.data;
                         myVue.formParm.employeeId='';
                         myVue.$forceUpdate();
                      }else{
                         myVue.$Message.info({
                              content: response.data.errorMessages,
                              duration: 3,
                              closable: true
                          });
                      }
                 })
         }
    },
    mounted:function () {
    	 //基于准备好的dom，初始化echarts实例，如果已经初始化，则重新初始化
	    if (mainChart != null && mainChart != "" && mainChart != undefined) {
	    	mainChart.dispose();
		}
	    mainChart = echarts.init(document.getElementById('main'));
        var app = {};
        var title = '加班时长统计表';
        var option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    crossStyle: {
                        color: '#999'
                    }
                }
            },
            toolbox: {
                feature: {
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            color: ['#3398DB'],
            legend: {
                data:['加班时长']
            },
            xAxis: [
                {
                    type: 'category',
                    data: this.empworkhours.employees,
                    axisPointer: {
                        type: 'shadow'
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '时长（小时）',
                    min: 0,
                    max: 50,
                    interval: 10,
                    axisLabel: {
                        formatter: '{value}'
                    }
                }
            ],
            series: [
                {
                    name:'加班时长',
                    type:'bar',
                    data:this.empworkhours.workHours,
                    itemStyle: {
                        normal: {
                        	label: {
                        		show: true, //开启显示
                        		position: 'top', //在上方显示
                        		textStyle: { //数值样式
                        		    color: 'black',
                        		    fontSize: 16
                        		}
                            }
                        }
                    },
                    markLine : {
                        symbol : 'none',
                        itemStyle : {
                          normal : {
                            color:'#1e90ff',
                            label : {
                              show:true
                            }
                          }
                        },
                        data : [{type : 'average', name: '平均值'}]
                      }
                }
            ]
        };
        if (option && typeof option === "object") {
        	// 使用刚指定的配置项和数据显示图表。
            mainChart.setOption(option, true);
        }
        
        
        //main2
        if (main2Chart != null && main2Chart != "" && main2Chart != undefined) {
		 	main2Chart.dispose();
		}
        main2Chart = echarts.init(document.getElementById('main2'));
        var option2 = {
            title : {
                text: '加班内容加班时长',
                subtext: this.contentworkhours.orgName,
                x:'center'
            },
            toolbox: {
            	feature: {
                    dataView: {show: true, readOnly: false},
                    saveAsImage: {show: true}
                }
            },
            tooltip : {
                trigger: 'item',
                formatter: "{b} : {c} 小时 <br/>   {d}%"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: this.contentworkhours.contents
            },
            series : [
                {
                    name: '加班内容',
                    type: 'pie',
                    radius : '55%',//饼的半径
                    center: ['50%', '60%'],//饼的中心位置
                    data:this.contentworkhours.nameValues,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        if (option2 && typeof option2 === "object") {
        	main2Chart.setOption(option2, true);
        }
        
    }
});
</script>