package com.weikun.service;

import com.weikun.dto.TableData;
import com.weikun.entity.ScheduleJob;
import com.weikun.utils.R;

import java.util.List;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/18
 * @Time:10:28
 */
public interface ScheduleJobService {

    public TableData findJobPage(String search, String order, int limit, int offset);

    public R saveJob(ScheduleJob scheduleJob);

    public R updateScheduleJob(ScheduleJob scheduleJob);

    public R findScheduleById(long scheduleJobId);

    public R deleteSchedule(long scheduleJobId);

    public R deleteBatch(List<Long> ids);

    public R resumeTask(List<Long> ids);

    public R pauseTask(List<Long> ids);

    public R runOnceTask(List<Long> ids);



}
