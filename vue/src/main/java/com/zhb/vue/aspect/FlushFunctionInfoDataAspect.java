package com.zhb.vue.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zhb.forever.redis.util.RedisImplUtil;
import com.zhb.vue.Constant;
import com.zhb.vue.pojo.FunctionInfoData;
import com.zhb.vue.pojo.UserInfoData;

@Aspect
@Component
public class FlushFunctionInfoDataAspect {

    private Logger logger = LoggerFactory.getLogger(FlushFunctionInfoDataAspect.class);

    @Pointcut("execution(public * com.zhb.vue.service.impl.FunctionInfoServiceImpl.saveOrUpdateFunctionInfoData(..))")
    public void flush(){
    }
    
    @Before("flush()")
    public void doBefore(JoinPoint joinPoint){
    }
    
    @After("flush()")
    public void doAfter(){
    }
    
    @AfterReturning(returning="object",pointcut="flush()")
    public void doAfterReturning(Object object){
        if (null != object) {
            RedisImplUtil.del(Constant.FUNCTION_INFO_DATAS.getBytes());
            FunctionInfoData data = (FunctionInfoData)object;
            RedisImplUtil.del(data.getId().getBytes());
            logger.info("delete functionIndoDatas and " + data.getName() + " redis cache...");
        }
    }

}
