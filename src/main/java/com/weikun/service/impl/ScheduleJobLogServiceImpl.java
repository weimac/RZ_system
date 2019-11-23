package com.weikun.service.impl;

import com.weikun.entity.ScheduleJobLog;
import com.weikun.mapper.ScheduleJobLogMapper;
import com.weikun.service.ScheduleJobLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/19
 * @Time:14:26
 */
@Service("scheduleJobLogServiceImpl")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {

    @Resource
    private ScheduleJobLogMapper scheduleJobLogMapper;

    @Override
    public void saveScheduleLog(ScheduleJobLog log) {

        scheduleJobLogMapper.insertSelective(log);

    }
}
