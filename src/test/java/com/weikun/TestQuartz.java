package com.weikun;


import com.weikun.entity.ScheduleJob;
import com.weikun.utils.ScheduleUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Description:
 * @Company: 千锋互联
 * @Author: 李丽婷
 * @Date: 2019/10/18
 * @Time: 上午11:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-quartz.xml")
public class TestQuartz {

    @Resource
    Scheduler scheduler;

    @Test
    public void  test() throws  Exception{

        System.out.println(scheduler);//org.quartz.impl.StdScheduler@68f4865

        ScheduleJob scheduleJob = new ScheduleJob();

        scheduleJob.setCronExpression("* * * * * ?");

        ScheduleUtils.createTask(scheduler,scheduleJob);

        //创建
           // System.in.read();

            Thread.sleep(10000);
        ////修改
        scheduleJob=new ScheduleJob();
        scheduleJob.setCronExpression("*/3 * * * * ?");
        ScheduleUtils.updateScheduler(scheduler,scheduleJob);
        //暂停
        ScheduleUtils.pause(scheduler,1);
        System.out.println("暂停成功！");


        Thread.sleep(5000);
        //恢复运行
        ScheduleUtils.resume(scheduler,1);
        System.out.println("恢复运行成功！");
        Thread.sleep(10000);

        //删除
        System.out.println(ScheduleUtils.deleteScheduler(scheduler,1));

        Thread.sleep(5000);


    }

}
