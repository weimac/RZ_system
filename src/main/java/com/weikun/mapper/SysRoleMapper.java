package com.weikun.mapper;

import com.weikun.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> findRoleByPage(@Param("search") String search, @Param("order") String order);

    int deleteBatch(List<Long> roleId);

    List<SysRole> findRoleByUserId(Long userId);

    int insertUserRole(@Param("userId") long userId, @Param("roleId") long roleId);

}