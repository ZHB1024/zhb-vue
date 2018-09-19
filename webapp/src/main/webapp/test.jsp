<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String ctxPath = request.getContextPath();
%>
<html>
<head>
<!-- 	<link rel="stylesheet" href="/css/iview.css">
    <link rel="stylesheet" href="/css/htgl.css">
    <script src="https://t1.chei.com.cn/common/js/vue/2.5.6/vue.js"></script>
    <script src="/js/iview-3.0.0.js"></script>
    <script src="/js/axios.min.js"></script> -->
    <script src="/js/jquery-3.3.1.js"></script>
    <script src="/js/layui/layer.js"></script>
</head>

<body>
      <div align="center">
      	<input type="button" onclick="showInfo();" value="弹出层"/>
      </div>
</body>

<script>
function showInfo(){
	layer.open({
        title: '详细信息',
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        area: ['420px', '340px'], //宽高
        content: "this a test", //这里content是一个普通的String
        btn: ['确定'],
        success: function(layero, index){

        },
        yes: function(index, layero){
            layer.closeAll();
        }
    });
}
</script>

<style lang="scss">
.media-wrapper {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.52);
    z-index: 1010;
    i {
        color: #fff
    }
    .media-controller {
        position: absolute;
        left: 50%;
        bottom: 30px;
        transform: translateX(-50%)
    }
    .media-close {
        position: absolute;
        right: 5px;
        top: 5px;
        i {
            font-size: 30px;
        }
    }
    .media-content {
        div {
            position: absolute;
            top: 50%; // background: green;
            color: #fff;
            text-align: center;
            font-size: 30px;
            transition: all .56s ease;
            img {
                max-width: 100%;
                max-height: 100%
            }
            video {
                width: 100%;
            }
        }
        .media-center {
            height: 50%;
            width: 40%;
            left: 50%;
            transform: translate(-50%, -50%);
            z-index: 1011;
        }
        .media-left,
        .media-right {
            width: 25%;
            height: 35%;
            filter: grayscale(90%);
        }
        .media-right {
            left: 100%;
            transform: translate(-105%, -50%);
        }
        .media-left {
            left: 0;
            transform: translate(5%, -50%);
        }
        .media-hide {
            height: 0;
            width: 0;
            left: 50%;
            z-index: 1010;
            opacity: 0;
        }
    }
}
</style>
</body>

</html>