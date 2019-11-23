package com.weikun.web;

import com.google.code.kaptcha.Producer;
import com.weikun.dto.SysUserDTO;
import com.weikun.dto.TableData;
import com.weikun.entity.SysUser;
import com.weikun.service.SysUserService;
import com.weikun.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
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
public class SysUserController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private Producer producer;

    @RequestMapping("sys/login")
// {username: "aaaa", password: "aaaa", captcha: "aaaa"}
//    @RequestBody
//    @ResponseBody
    public R login(@RequestBody SysUserDTO userDTO,HttpSession session){
//        Map map = new HashMap();
//
//        map.put("code",0);
//
//        return  map;
        if (userDTO.getCaptcha()==null){
            return R.error("验证码不能为空");
        }
        String c= (String) session.getAttribute("code");

        if (!userDTO.getCaptcha().equalsIgnoreCase(c)){
            return R.error("验证码错误");
        }

        R r=sysUserService.login(userDTO);

        SysUser sysUser= (SysUser) r.get("sysUser");

        session.setAttribute("sysUser",sysUser);

        return r;

    }
    @RequestMapping("captcha.jpg")
    public void createKaptcha(HttpServletResponse httpServletResponse, HttpSession session){
        try {
            String code = producer.createText();
            System.out.println("code" + code);
            session.setAttribute("code", code);
            BufferedImage bufferedImage = producer.createImage(code);

            ImageIO.write(bufferedImage, "jpg", httpServletResponse.getOutputStream());
        }catch (Exception e){

        }
    }

    @RequestMapping("/sys/user/list")
    //search:aaaa
    //order:asc
    //limit:20
    //offset:0
    public TableData userList(String search, String order , int limit, int offset){

        return sysUserService.findUserByPage(search,order,limit,offset);


    }
    @RequestMapping("/sys/user/save")
    public R saveUser(@RequestBody SysUser sysUser){

       return sysUserService.saveUser(sysUser);
    }
    @RequestMapping("/sys/user/update")
    public R updateUser(@RequestBody SysUser sysUser){

        return sysUserService.updateUser(sysUser);
    }
    @RequestMapping("/sys/user/info/{userId}")
    public R userInfo(@PathVariable long userId){

        return sysUserService.findUserById(userId);
    }
    @RequestMapping("/sys/user/del")
    public R del(@RequestBody List<Long> ids){

        return  sysUserService.deleteBatch(ids);
    }
    @RequestMapping("/sys/user/info")
    public  R info(HttpSession session){
        return R.ok().put("user",(SysUser)session.getAttribute("sysUser"));
    }

    @RequestMapping("/sys/user/logout")
    public  R logout(HttpSession session){
        session.invalidate();//session失效

        return R.ok();
    }

    @RequestMapping("/sys/user/userList")
    public  R allUsers(){
        return  sysUserService.findALL();
    }
}

