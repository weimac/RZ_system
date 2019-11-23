package com.weikun.mapper;

import com.weikun.dto.SysMenuDTO;
import com.weikun.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findUserByName(String username);

    List<SysUser> findUserByPage(@Param("search") String search, @Param("order") String order);

    int deleteBatch(List<Long> userId);
}