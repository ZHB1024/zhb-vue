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
var mainChart,main2Chart;
var myVue = new Vue({
    el: '#app_content',
    data:{
    	bars:{
    		titleName:'',
	        names:[],
	        values:[]
	  	},
    	pies:{
    		titleName:'',
    		names:[],
    		values:[],
	        nameValues:[]
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
    	 //已有统计则销毁
    	 disposeChart:function(){
    		 if (mainChart != null && mainChart != "" && mainChart != undefined) {
 		    	mainChart.dispose();
	    	 }
 		     if (main2Chart != null && main2Chart != "" && main2Chart != undefined) {
 		    	main2Chart.dispose();
	    	 }
    	 },
    	 //init----initMain
    	 initMainEchart:function(){
    		    //基于准备好的dom，初始化echarts实例，如果已经初始化，则重新初始化
    		    if (mainChart != null && mainChart != "" && mainChart != undefined) {
    		    	mainChart.dispose();
	    		}
    		    mainChart = echarts.init(document.getElementById('main'));
    	        var app = {};
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
    	                    magicType: {show: true, type: ['bar','line']},
    	                    restore: {show: true},
    	                    saveAsImage: {show: true}
    	                }
    	            },
    	            color: ['#3398DB'],
    	            legend: {
    	                data:myVue.bars.names
    	            },
    	            xAxis: [
    	                {
    	                    type: 'category',
    	                    data: myVue.bars.names,
    	                    axisPointer: {
    	                        type: 'shadow'
    	                    }
    	                }
    	            ],
    	            yAxis: [
    	                {
    	                    type: 'value',
    	                    name: '数量',
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
    	                    name:myVue.bars.names,
    	                    type:'bar',
    	                    data:myVue.bars.values,
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
    	 //init----initMian2
    	 initMain2Echart:function(){
    		   if (main2Chart != null && main2Chart != "" && main2Chart != undefined) {
    			 	main2Chart.dispose();
	    		}
    	        main2Chart = echarts.init(document.getElementById('main2'));
    	        var option2 = {
    	            title : {
    	                text: myVue.pies.titleName,
    	                subtext: myVue.pies.titleName,
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
    	                formatter: "{b} : {c} 个 <br/>   {d}%"
    	            },
    	            legend: {
    	                orient: 'vertical',
    	                left: 'left',
    	                data: myVue.pies.names
    	            },
    	            series : [
    	                {
    	                    name: '加班内容',
    	                    type: 'pie',
    	                    radius : '55%',//饼的半径
    	                    center: ['50%', '60%'],//饼的中心位置
    	                    data:myVue.pies.nameValues,
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
    },
    mounted:function () {
    	 let param = new URLSearchParams(); 
	     axios.all([
   	    			axios.post('<%=ctxPath %>/htgl/statisticController/statisticattachment/api', param)
   	  		]).then(axios.spread(function (statisticAttachmentResp) {
   		  			if(statisticAttachmentResp.data.flag){
   			  			myVue.bars.titleName = statisticAttachmentResp.data.data.titleName;
   			  			myVue.bars.names = statisticAttachmentResp.data.data.names;
   			  			myVue.bars.values = statisticAttachmentResp.data.data.values;
   			  			
   			  			myVue.pies.titleName = statisticAttachmentResp.data.data.titleName;
   			  			myVue.pies.names = statisticAttachmentResp.data.data.names;
			  			myVue.pies.values = statisticAttachmentResp.data.data.values;
			  			myVue.pies.nameValues = statisticAttachmentResp.data.data.nameValues;
   			  			
   			  			
   			  			myVue.$options.methods.initMainEchart();
   			  			myVue.$options.methods.initMain2Echart();
   		  			}
   	  		}));
    }
});
</script>