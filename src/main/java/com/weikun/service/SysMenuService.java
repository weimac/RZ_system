package com.weikun.service;

import com.weikun.dto.SysMenuDTO;
import com.weikun.dto.TableData;
import com.weikun.entity.SysMenu;
import com.weikun.utils.R;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/14
 * @Time:20:59
 */
public interface SysMenuService {

    public TableData findMenuByPage(String search, String order, int limit, int offset);

    public List<SysMenu> findTreeMenu();

    public R save(SysMenu sysMenu);

    public R delete(long menuId);

    public  R findMenuById(long menuId);

    public  R updateById(SysMenu sysMenu);

    public  R del(List<Long> ids);


    R findUserMenu(long userId);

}
