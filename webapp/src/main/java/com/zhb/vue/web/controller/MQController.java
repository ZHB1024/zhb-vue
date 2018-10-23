package com.zhb.vue.web.controller;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhb.forever.framework.proto.protobuf.KeyValueProtobuf;
import com.zhb.forever.framework.proto.protobuf.KeyValueProtobuf.KeyValue;
import com.zhb.forever.framework.util.AjaxData;
import com.zhb.forever.mq.client.JmsActiveMQClientFactory;
import com.zhb.forever.mq.client.JmsActiveMQManager;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年10月23日上午11:30:06
*/

@Controller
@RequestMapping("/htgl/mqcontroller")
public class MQController {
    
    private Logger logger = LoggerFactory.getLogger(MQController.class);
    
    private JmsActiveMQManager mqClient = JmsActiveMQClientFactory.getRedisClientBean();
    
    private Destination mqDestination = JmsActiveMQClientFactory.getMQDestinationBean();
    
    @RequestMapping(value = "/toindex",method = RequestMethod.GET)
    @Transactional
    public String toSpider(HttpServletRequest request,HttpServletResponse response){
        return "htgl.mq.index";
    }
    
    //sendMessage
    @RequestMapping(value="/sendmessage",method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxData sendMessage(HttpServletRequest request,HttpServletResponse response){
        AjaxData ajaxData = new AjaxData();
        /*mqClient.sendQueueDestinationMsg(mqDestination, "hello world");
        
        TextMessage textMessage = mqClient.receiveQueueMessage(mqDestination);
        if (null != textMessage) {
            try {
                logger.info(textMessage.getText());
                ajaxData.setData(textMessage.getText());
                ajaxData.setFlag(true);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }*/
        
        KeyValueProtobuf.KeyValue.Builder newsBuilder = KeyValueProtobuf.KeyValue.newBuilder(); 
        newsBuilder.setId("123");
        newsBuilder.setKey("测试");
        newsBuilder.setValue("测试一下不行呀");
        //newsBuilder.setCreateTime(Calendar.getInstance().getTimeInMillis());
        KeyValue news = newsBuilder.build();
        byte[] newsByte = news.toByteArray();
        mqClient.sendQueueRemoteMsg("zhb_vue_object", newsByte);
        
        return ajaxData;
    }
    
    @RequestMapping(value = "/receivemessage", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxData receiveQueueMes(HttpServletRequest request, HttpServletResponse response) {
        AjaxData ajaxData = new AjaxData();
        try {
            com.google.protobuf.Message mes = mqClient.receiveQueueRemoteMsgByDesNamePath("zhb_vue_object", "com.zhb.forever.framework.proto.protobuf.KeyValueProtobuf$KeyValue");
            if (null != mes) {
                KeyValueProtobuf.KeyValue news2 = (KeyValueProtobuf.KeyValue)mes;
                logger.info("从队列 zhb_vue_object 收到了消息：\t" + news2.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxData;
    }

}


