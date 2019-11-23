package com.weikun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weikun.dto.TableData;
import com.weikun.entity.ScheduleJob;
import com.weikun.mapper.ScheduleJobMapper;
import com.weikun.service.ScheduleJobService;
import com.weikun.utils.R;
import com.weikun.utils.ScheduleUtils;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/18
 * @Time:10:28
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

    @Resource
    private ScheduleJobMapper scheduleJobMapper;
    @Resource
    private Scheduler scheduler;//applicationContext-quartz.xml
    @Override
    public TableData findJobPage(String search, String order, int limit, int offset) {

        //分页工具
        PageHelper.offsetPage(offset,limit);
        List<ScheduleJob> list=scheduleJobMapper.findScheduleJob(search, order);

        PageInfo pageInfo=new PageInfo(list);

        long total=pageInfo.getTotal();
        List<ScheduleJob> menuList=pageInfo.getList();

        TableData data=new TableData();

        data.setTotal(total);

        data.setRows(menuList);

        return data;
    }

    @Override
    public R saveJob(ScheduleJob scheduleJob) {

        int i =scheduleJobMapper.insertSelective(scheduleJob);

        System.out.println("--->自增主键id"+scheduleJob.getJobId());
        //创建定时任务
        ScheduleUtils.createTask(scheduler,scheduleJob);//(Scheduler scheduler, ScheduleJob scheduleJob) {

        return i>0?R.ok():R.error("添加失败");
    }



    @Override
    public R updateScheduleJob(ScheduleJob scheduleJob) {

        int i=scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
        //修改定时任务
        ScheduleUtils.updateScheduler(scheduler,scheduleJob);
        return i>0?R.ok():R.error("修改失败");

    }

    @Override
    public R findScheduleById(long scheduleJobId) {

        ScheduleJob scheduleJob=scheduleJobMapper.selectByPrimaryKey(scheduleJobId);

        return R.ok().put("scheduleJob",scheduleJob);

    }

    @Override
    public R deleteSchedule(long scheduleJobId) {

        int i = scheduleJobMapper.deleteByPrimaryKey(scheduleJobId);
        //删除定时任务
        ScheduleUtils.deleteScheduler(scheduler,scheduleJobId);
        return i>0?R.ok():R.error("删除失败");

    }
    @Override
    public R deleteBatch(List<Long> ids) {

        int i =scheduleJobMapper.deleteBylds(ids);
        //删除定时任务
        for (Long id : ids) {
            ScheduleUtils.deleteScheduler(scheduler,id);
        }
        return i>0?R.ok():R.error("删除失败");

    }
    @Override
    public R resumeTask(List<Long> ids) {
        //修改scheduleJob状态
        int i =  scheduleJobMapper.updateStatus(ids,(byte)0);
        //恢复任务
        for (Long id : ids) {
            ScheduleUtils.resume(scheduler,id);
        }

        return i>0?R.ok():R.error();
    }

    @Override
    public R pauseTask(List<Long> ids) {
        //修改scheduleJob状态
       int i=scheduleJobMapper.updateStatus(ids, (byte) 1);
        //恢复任务
       for (Long id:ids){
           ScheduleUtils.pause(scheduler,id);
       }
       return i>0?R.ok():R.error();
    }

    @Override
    public R runOnceTask(List<Long> ids) {
        //恢复任务
        for (Long id:ids) {
            ScheduleUtils.runOnce(scheduler,id);
        }
    return R.ok();
    }
}
