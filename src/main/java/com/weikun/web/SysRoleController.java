package com.weikun.web;

import com.google.code.kaptcha.Producer;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.weikun.dto.TableData;
import com.weikun.entity.SysRole;
import com.weikun.service.SysRoleService;
import com.weikun.service.SysRoleService;
import com.weikun.utils.R;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/14
 * @Time:15:31
 */
@RestController //@Controller +@ResponseBody
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;


    @RequestMapping("/sys/role/list")
    //search:aaaa
    //order:asc
    //limit:20
    //offset:0
    public TableData roleList(String search, String order , int limit, int offset){

        return sysRoleService.findRoleByPage(search,order,limit,offset);


    }
    @RequestMapping("/sys/role/save")
    public R saveRole(@RequestBody SysRole sysRole){

       return sysRoleService.saveRole(sysRole);
    }

    @RequestMapping("/sys/role/update")
    public R updateRole(@RequestBody SysRole sysRole){

        return sysRoleService.updateRole(sysRole);
    }


    @RequestMapping("/sys/role/del")
    public R del(@RequestBody List<Long> ids){

        return  sysRoleService.deleteBatch(ids);
    }

    @RequestMapping("/sys/user/notHasRole/{userId}")

    public  R allRoles(@PathVariable long userId){

    return sysRoleService.findAll(userId);

    }

    @RequestMapping("/sys/user/addUserRole/{userId}/{roleId}")

    public R saveUserRole(@PathVariable long userId,@PathVariable long roleId){

        return sysRoleService.saveUserRole(userId,roleId);
    }


}

