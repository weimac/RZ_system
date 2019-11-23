package com.weikun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weikun.dto.TableData;
import com.weikun.entity.SysRole;
import com.weikun.entity.SysUser;
import com.weikun.mapper.SysRoleMapper;
import com.weikun.service.SysRoleService;
import com.weikun.utils.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/15
 * @Time:20:42
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Override
    public TableData findRoleByPage(String search, String order, int limit, int offset) {

        //分页工具
        PageHelper.offsetPage(offset,limit);
        List<SysRole> list=sysRoleMapper.findRoleByPage(search, order);
        PageInfo pageInfo=new PageInfo(list);

        long total=pageInfo.getTotal();
        List<SysRole> menuList=pageInfo.getList();

        TableData data=new TableData();

        data.setTotal(total);

        data.setRows(menuList);

        return data;
    }

    @Override
    public R findRoleById(long roleId) {

        return R.ok().put("role",sysRoleMapper.selectByPrimaryKey(roleId));


    }

    @Override
    public R saveRole(SysRole sysRole) {
        sysRole.setCreateTime(new Date());
        int i=sysRoleMapper.insertSelective(sysRole);
        return i>0?R.ok():R.error("添加失败");
    }


    @Override
    public R updateRole(SysRole sysRole) {
        sysRole.setCreateTime(new Date());
        int i=sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        return i>0?R.ok():R.error("修改失败");
    }

    @Override
    public R deleteBatch(List<Long> roleId) {

        int i=sysRoleMapper.deleteBatch(roleId);

        return i>0?R.ok():R.error("删除失败");
    }

    @Override
    public R findAll(long userId) {

        List<SysRole> list=sysRoleMapper.findRoleByUserId(userId);

        return R.ok().put("roles",list);

    }

    @Override
    public R saveUserRole(long userId, long roleId) {

        int i=sysRoleMapper.insertUserRole(userId, roleId);

        return i>0?R.ok():R.error("添加失败");
    }
}
