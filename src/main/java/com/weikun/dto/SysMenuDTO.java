package com.weikun.dto;

import com.weikun.entity.SysMenu;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/14
 * @Time:21:03
 */
@Data
public class SysMenuDTO extends SysMenu {

    private String parentName;

    private List<SysMenuDTO> list;
}
