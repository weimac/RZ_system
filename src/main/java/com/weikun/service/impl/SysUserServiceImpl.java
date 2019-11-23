package com.weikun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weikun.dto.SysMenuDTO;
import com.weikun.dto.SysUserDTO;
import com.weikun.dto.TableData;
import com.weikun.entity.SysMenu;
import com.weikun.entity.SysUser;
import com.weikun.mapper.SysUserMapper;
import com.weikun.service.SysUserService;
import com.weikun.utils.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/14
 * @Time:14:53
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public R login(SysUserDTO sysUserDTO) {

        SysUser sysUser = sysUserMapper.findUserByName(sysUserDTO.getUsername());
        if(sysUser==null){
            return  R.error("账号不存在！");
        }
        if (!sysUser.getPassword().equals(sysUserDTO.getPassword())){
            return  R.error("密码错误！");
        }

        return R.ok().put("sysUser",sysUser);
    }

    @Override
    public TableData findUserByPage(String search, String order, int limit, int offset) {
        //分页工具
        PageHelper.offsetPage(offset,limit);
        List<SysUser> list=sysUserMapper.findUserByPage(search,order);
        PageInfo pageInfo=new PageInfo(list);

        long total=pageInfo.getTotal();
        List<SysUser> menuList=pageInfo.getList();

        TableData data=new TableData();

        data.setTotal(total);

        data.setRows(menuList);

        return data;
    }

    @Override
    public R findUserById(long userId) {

       return R.ok().put("user",sysUserMapper.selectByPrimaryKey(userId));


    }

    @Override
    public R saveUser(SysUser sysUser) {
        sysUser.setCreateTime(new Date());
        int i=sysUserMapper.insertSelective(sysUser);
        return i>0?R.ok():R.error("添加失败");
    }

    @Override
    public R updateUser(SysUser sysUser) {
        sysUser.setCreateTime(new Date());
        int i=sysUserMapper.updateByPrimaryKeySelective(sysUser);
        return i>0?R.ok():R.error("修改失败");
    }

    @Override
    public R deleteBatch(List<Long> userId) {

        int i=sysUserMapper.deleteBatch(userId);

        return i>0?R.ok():R.error("删除失败");
    }

    @Override
    public R findALL() {
        List<SysUser> list=sysUserMapper.findUserByPage(null,"asc");

        return R.ok().put("userList",list);
    }


}
