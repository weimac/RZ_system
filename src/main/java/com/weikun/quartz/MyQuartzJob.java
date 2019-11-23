package com.weikun.quartz;

import com.alibaba.fastjson.JSON;
import com.weikun.entity.ScheduleJob;
import com.weikun.entity.ScheduleJobLog;
import com.weikun.service.ScheduleJobLogService;
import com.weikun.service.ScheduleJobService;
import com.weikun.utils.SpringContextUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Company: 千锋互联
 * @Author: 李丽婷
 * @Date: 2019/10/18
 * @Time: 上午11:55
 */
public class MyQuartzJob implements Job {

    //    @Resource
    //    private ScheduleJobLogService scheduleJobLogService;//无法注入，因为当前对象不是spring容器中的？
    ScheduleJobLog log=new ScheduleJobLog();
    long  start = System.currentTimeMillis();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

       // System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"hello world!!!!!");
        //业务逻辑
        //备份数据库

        try {

            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            //{"beanName":"TestTest","createTime":1571450789063,"cronExpression":"*/2 * * * * ?","jobId":3,"methodName":"test","remark":"测试","status":0}
            //System.out.println(jobDataMap.get("scheduleJob"));

            String json = (String) jobDataMap.get("scheduleJob");

            ScheduleJob scheduleJob = JSON.parseObject(json, ScheduleJob.class);

            String beanName = scheduleJob.getBeanName();//spring容器中某个bean的name   testTask
            String methodName = scheduleJob.getMethodName();
            String params = scheduleJob.getParams();
            //如何根据bean的name  得到这个bean的对象？？？？
            ApplicationContext a = new ClassPathXmlApplicationContext("applicationContext.xml");
            Object object = a.getBean(beanName);
            //已知方法名字  对象的Object表示方式，如何调用该方法呢？？？？
            //反射

            //记录定时任务的日志
            log.setBeanName(beanName);
            log.setMethodName(methodName);
            log.setParams(params);
            log.setStatus(scheduleJob.getStatus());
            log.setCreateTime(new Date());
            log.setJobId(scheduleJob.getJobId());
            Method method = null;
            if (params == null || params.equals("")) {
                //无参方法
                method = object.getClass().getDeclaredMethod(methodName);
                //调用方法
                method.invoke(object);
            }else {
                //带String类型参数
                method=object.getClass().getDeclaredMethod(methodName,String.class);
                //调用方法
                method.invoke(object,params);
            }

            long end=System.currentTimeMillis();
            long time=end-start;
            log.setTimes(new Integer(time+""));

            //从spring容器中 得到ScheduleJobLogService对象
            //方法一 ApplicationContext a=new ClassPathXmlApplicationContext("xxx.xml");
            //方法二
            ScheduleJobLogService scheduleJobLogService= (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogServiceImpl");
            scheduleJobLogService.saveScheduleLog(log);

            System.out.println("日志记录成功");

        }catch (Exception e) {
                e.printStackTrace();
                System.out.println("--------->错误"+e.getMessage());
                log.setError(e.getMessage());
            }

        }




    }

