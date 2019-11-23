package com.weikun.utils;

import com.alibaba.fastjson.JSON;
import com.weikun.entity.ScheduleJob;
import com.weikun.exception.RZException;
import com.weikun.quartz.MyQuartzJob;
import org.quartz.*;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/18
 * @Time:14:29
 */
public class ScheduleUtils {

    private static  final String JOB_NAME_PREFIX="myJob_";
    private static  final String JOB_GROUP_PREFIX="MyGroup_";
    private static  final String TRIGGER_NAME_PREFIX="myTrigger_";
    private static  final String TRIGGER_GROUP_PREFIX="MyTriggerGroup_";


    /***
     * 创建定时任务
     * @param scheduler  quartz调度器
     * @param scheduleJob 描述定时任务的实体类
     */

    public static void createTask(Scheduler scheduler, ScheduleJob scheduleJob){

        try {
            //1,jobDetail
            JobDetail jobDetail = JobBuilder.newJob(MyQuartzJob.class).
                    withIdentity(JOB_NAME_PREFIX+scheduleJob.getJobId(),
                            JOB_GROUP_PREFIX+scheduleJob.getJobId()).build();
            //System.out.println(jobDetail.getKey());
            String json= JSON.toJSONString(scheduleJob);
            //传递数据
            jobDetail.getJobDataMap().put("scheduleJob",json);

            //2,trigger
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().
                    withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression()))
                    .withIdentity(TRIGGER_NAME_PREFIX+scheduleJob.getJobId(),
                            TRIGGER_GROUP_PREFIX+scheduleJob.getJobId()).build();

            //3,注册job和触发器
            scheduler.scheduleJob(jobDetail, cronTrigger);

            scheduler.start();

        }catch (Exception e){
            throw  new RZException("创建定时任务失败");
        }

    }
    public  static void updateScheduler(Scheduler scheduler,ScheduleJob scheduleJob)  {

        try {
        //修改cron表达式？
        //得到原来的触发器
        TriggerKey triggerKey=TriggerKey.triggerKey(TRIGGER_NAME_PREFIX+scheduleJob.getJobId(),TRIGGER_GROUP_PREFIX+scheduleJob.getJobId());
        CronTrigger trigger= (CronTrigger) scheduler.getTrigger(triggerKey);

        //修改触发器
        CronTrigger newTrigger=trigger.getTriggerBuilder().
                withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())).build();
        //重新指定触发器
        scheduler.rescheduleJob(triggerKey,newTrigger);

        } catch (SchedulerException e) {
            throw  new  RZException("修改定时任务失败！");
        }
    }

    public static  boolean deleteScheduler(Scheduler scheduler,long jobld){
        // boolean deleteJob(JobKey jobKey)
        try {
            return  scheduler.deleteJob(JobKey.jobKey(JOB_NAME_PREFIX+jobld,JOB_GROUP_PREFIX+jobld));
        } catch (SchedulerException e) {
            throw  new  RZException("删除定时任务失败！");
        }

    }
    public  static void    pause(Scheduler scheduler,long jobld){
        try {
            JobKey jobKey = JobKey.jobKey(JOB_NAME_PREFIX+jobld,JOB_GROUP_PREFIX+jobld);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw  new  RZException("暂停定时任务失败！");
        }
    }

    public static void  resume(Scheduler scheduler,long jobld){
        try {
            JobKey jobKey = JobKey.jobKey(JOB_NAME_PREFIX+jobld,JOB_GROUP_PREFIX+jobld);
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw  new  RZException("恢复定时任务失败！");
        }
    }
    public static void  runOnce(Scheduler scheduler,long jobld){

        try {
            JobKey jobKey=JobKey.jobKey(JOB_NAME_PREFIX+jobld,JOB_GROUP_PREFIX+jobld);
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
           throw new RZException("运行定时任务失败!");
        }


    }

}
