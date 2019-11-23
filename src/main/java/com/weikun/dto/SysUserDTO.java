package com.weikun.dto;

import com.weikun.entity.SysUser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/14
 * @Time:14:36
 */
//@Getter
//@Setter
//@ToString
@Data
public class SysUserDTO extends SysUser {
    private String  captcha;
    private  boolean rememberMe;


}
