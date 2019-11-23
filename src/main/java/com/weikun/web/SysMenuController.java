package com.weikun.web;

import com.weikun.dto.TableData;
import com.weikun.entity.SysMenu;
import com.weikun.entity.SysUser;
import com.weikun.mapper.SysMenuMapper;
import com.weikun.service.SysMenuService;
import com.weikun.utils.R;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/14
 * @Time:20:52
 */
@RestController
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @RequestMapping("/sys/menu/list")
    //search:aaaa
    //order:asc
    //limit:20
    //offset:0
    public TableData findAllMenu(String search,String order ,int limit,int offset){

        return sysMenuService.findMenuByPage(search,order,limit,offset);


    }

    @RequestMapping("/sys/menu/select")

    public R selectTreeMenu(){

        List<SysMenu> list=sysMenuService.findTreeMenu();

        return R.ok().put("menuList",list);

    }

    @RequestMapping("/sys/menu/save")
    public R saveMenu(@RequestBody SysMenu sysMenu) {

        return sysMenuService.save(sysMenu);

    }
    //RestFul风格请求
    @RequestMapping("/sys/menu/info/{menuId}")
    //怎么得到url中占位符的值???
    //@PathVariable 能得到RequestMapping的value属性
    public R findById(@PathVariable long menuId){

        System.out.println("memnId"+menuId);

        return sysMenuService.findMenuById(menuId);
    }

    @RequestMapping("/sys/menu/update")
    public R update(@RequestBody SysMenu sysMenu){

        return sysMenuService.updateById(sysMenu);

    }


    @RequestMapping("/sys/menu/del")
    public R deleteMenuByMenuId( @RequestBody List<Long> ids){

        return sysMenuService.del(ids);

    }
    //查询用户能访问的真正的菜单
    @RequestMapping("/sys/menu/user")
    public R menuList(HttpSession session){
        SysUser sysUser= (SysUser) session.getAttribute("sysUser");

        System.out.println("sysUser"+sysUser);

        return  sysMenuService.findUserMenu(sysUser.getUserId());

    }
}
