package com.weikun.web;

import com.weikun.dto.TableData;
import com.weikun.entity.ScheduleJob;
import com.weikun.entity.SysUser;
import com.weikun.service.ScheduleJobService;
import com.weikun.utils.R;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/18
 * @Time:10:58
 */
@RestController
public class ScheduleJobController {

    @Resource
    private ScheduleJobService scheduleJobService;

    @RequestMapping("/schedule/job/list")
    public TableData scheduleJobList(String search,String order,int limit ,int offset){

        return scheduleJobService.findJobPage(search, order, limit, offset);
    }
    @RequestMapping("/schedule/job/info/{scheduleJobId}")
    public R scheduleJobInfo(@PathVariable long scheduleJobId){

        return scheduleJobService.findScheduleById(scheduleJobId);
    }
    @RequestMapping("/schedule/job/save")
    public R saveUser(@RequestBody ScheduleJob scheduleJob){
        scheduleJob.setCreateTime(new Date());
        scheduleJob.setStatus((byte)0);
        return scheduleJobService.saveJob(scheduleJob);
    }
    @RequestMapping("/schedule/job/update")
    public R updateUser(@RequestBody ScheduleJob scheduleJob){
        scheduleJob.setCreateTime(new Date());
        return scheduleJobService.updateScheduleJob(scheduleJob);
    }
    @RequestMapping("/schedule/job/del")
    public  R  delete(@RequestBody List<Long> ids) {

        return scheduleJobService.deleteBatch(ids);
    }
    @RequestMapping("/schedule/job/resume")
    public   R  resumeTask(@RequestBody List<Long> ids){
        return  scheduleJobService.resumeTask(ids);
    }

    @RequestMapping("/schedule/job/pause")
    public   R  pauseTask(@RequestBody List<Long> ids){
        return  scheduleJobService.pauseTask(ids);
    }

    @RequestMapping("/schedule/job/run")
    public   R  runOnce(@RequestBody List<Long> ids){
        return  scheduleJobService.runOnceTask(ids);
    }
}
