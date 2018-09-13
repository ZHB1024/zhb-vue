package com.zhb.vue.util;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.zhb.forever.framework.util.StringUtil;


public class TestUtil {
    
    @Test
    @Transactional
    @Rollback(true)
    public void saveOrUpdateTest() {
        int num = 43;
        int result = 49/10;
        StringUtil.println(result+"");
    }

}
