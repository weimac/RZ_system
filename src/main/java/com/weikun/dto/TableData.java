package com.weikun.dto;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/14
 * @Time:20:54
 */
@Data
public class TableData {

    private long total;
    private List<?> rows;
}
