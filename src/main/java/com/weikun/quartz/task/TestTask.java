package com.weikun.quartz.task;

import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/19
 * @Time:9:50
 */
//跟schedule_job对象的BeanName字段对应
@Component("TestTask")
public class TestTask {
    //方法名与Schedule_job对象的methodName字段对象
    public void test(){
        System.out.println("TestTask---test---运行了");
    }
}
