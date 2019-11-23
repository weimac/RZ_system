package com.weikun.mapper;

import com.weikun.dto.TableData;
import com.weikun.entity.ScheduleJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScheduleJobMapper {
    int deleteByPrimaryKey(Long jobId);

    int insert(ScheduleJob record);

    int insertSelective(ScheduleJob record);

    ScheduleJob selectByPrimaryKey(Long jobId);

    int updateByPrimaryKeySelective(ScheduleJob record);

    int updateByPrimaryKey(ScheduleJob record);

    List<ScheduleJob> findScheduleJob(@Param("search")String search, @Param("order")String order);

    int deleteBylds(List<Long> ids);

    int updateStatus(@Param("ids")List<Long> ids,@Param("status") byte status);


}