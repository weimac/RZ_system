package com.weikun.mapper;

import com.weikun.dto.SysMenuDTO;
import com.weikun.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.awt.*;
import java.util.List;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    List<SysMenuDTO> findMenu(@Param("search") String search, @Param("order") String order);

    List <SysMenu>  findTreeMenu();

    int  del(List<Long> ids);

    //查询用户 能访问的目录
    List<SysMenuDTO> findDirByUserId(long userId);

    //查询某个目录下的子菜单
    List<SysMenuDTO> findMenuByUserIdAndParentId(@Param("userId") long userId, @Param("parentId") long menuId);



}