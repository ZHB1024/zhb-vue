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
        mqClient.sendQueueDestinationMsg(mqDestination, "hello world");
        
        TextMessage textMessage = mqClient.receiveQueueMessage(mqDestination);
        if (null != textMessage) {
            try {
                logger.info(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        
        ajaxData.setFlag(true);
        return ajaxData;
    }

}


