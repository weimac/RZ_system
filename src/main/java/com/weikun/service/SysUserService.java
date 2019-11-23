package com.weikun.service;

import com.weikun.dto.SysUserDTO;
import com.weikun.dto.TableData;
import com.weikun.entity.SysUser;
import com.weikun.utils.R;

import java.util.List;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/14
 * @Time:14:43
 */
public interface SysUserService {

    public R login(SysUserDTO sysUserDTO);

    public TableData findUserByPage(String search, String order, int limit, int offset);

    public R findUserById(long userId);

    public R saveUser(SysUser sysUser);

    public R updateUser(SysUser sysUser);

    public R deleteBatch(List<Long> userId);

    public R findALL();
}
