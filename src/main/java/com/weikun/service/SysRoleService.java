package com.weikun.service;

import com.weikun.dto.TableData;
import com.weikun.entity.SysRole;
import com.weikun.entity.SysUser;
import com.weikun.utils.R;

import java.util.List;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/15
 * @Time:20:37
 */
public interface SysRoleService {

    public TableData findRoleByPage(String search, String order, int limit, int offset);

    public R findRoleById(long roleId);

    public R saveRole(SysRole sysRole);

    public R updateRole(SysRole sysRole);

    public R deleteBatch(List<Long> roleId);

    public R findAll(long userId);

    public R saveUserRole(long userId, long roleId);
}
