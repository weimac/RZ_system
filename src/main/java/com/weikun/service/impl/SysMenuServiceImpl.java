package com.weikun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weikun.dto.SysMenuDTO;
import com.weikun.dto.TableData;
import com.weikun.entity.SysMenu;
import com.weikun.mapper.SysMenuMapper;
import com.weikun.service.SysMenuService;
import com.weikun.utils.R;
import net.sf.jsqlparser.schema.Table;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/14
 * @Time:21:01
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private Scheduler scheduler;//applicationC

    @Override
    public TableData findMenuByPage(String search, String order, int limit, int offset) {

        //分页工具
        PageHelper.offsetPage(offset,limit);
        List<SysMenuDTO> list=sysMenuMapper.findMenu(search,order);
        PageInfo pageInfo=new PageInfo(list);

        long total=pageInfo.getTotal();
        List<SysMenu> menuList=pageInfo.getList();

        TableData data=new TableData();

        data.setTotal(total);

        data.setRows(menuList);

        return data;
    }

    @Override
    public List<SysMenu> findTreeMenu() {

        List<SysMenu> list=sysMenuMapper.findTreeMenu();

        SysMenu sysMenu=new SysMenu();
        sysMenu.setMenuId(0l);
        sysMenu.setParentId(-1l);
        sysMenu.setName("一级目录");
        sysMenu.setType(0);

        list.add(sysMenu);
        return list;
    }

    @Override
    public R save(SysMenu sysMenu) {

        int i=sysMenuMapper.insertSelective(sysMenu);

        return i>0?R.ok():R.error("添加失败");



    }

    @Override
    public R delete(long menuId) {

        int i=sysMenuMapper.deleteByPrimaryKey(menuId);

        return i>0?R.ok():R.error("删除失败");
    }

    @Override
    public R findMenuById(long menuId) {
        SysMenu sysMenu=sysMenuMapper.selectByPrimaryKey(menuId);

        return R.ok().put("menu",sysMenu);
    }

    @Override
    public R updateById(SysMenu sysMenu) {

        int i=sysMenuMapper.updateByPrimaryKey(sysMenu);

        return i>0?R.ok():R.error("修改失败");

    }

    @Override
    public R del(List<Long> ids) {
        for (Long id:ids){
            if (id<=29){
                return  R.error("系统内置菜单,不能删除");
            }
        }

        int i=sysMenuMapper.del(ids);

        return i>0?R.ok():R.error("删除失败");
    }

    @Override
    public R findUserMenu(long userId) {
        //查询出type=0的目录
        List<SysMenuDTO> dir=sysMenuMapper.findDirByUserId(userId);
        //查询目录下的子菜单
        for (SysMenuDTO sysMenuDTO:dir){
            List<SysMenuDTO> list=sysMenuMapper.findMenuByUserIdAndParentId(userId,sysMenuDTO.getMenuId());

            sysMenuDTO.setList(list);
        }

        return R.ok().put("menuList",dir).put("permissions","[]");
    }

}
