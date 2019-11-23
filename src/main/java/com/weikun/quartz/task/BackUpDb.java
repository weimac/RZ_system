package com.weikun.quartz.task;

import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/19
 * @Time:10:19
 */
@Component("BackUpDb")
public class BackUpDb {

    public void backup(String msg){

        try {
            System.out.println(msg);

            Runtime runtime = Runtime.getRuntime();
            String fileName = System.currentTimeMillis() + ".sql";

            runtime.exec("mysqldump -uroot -p mydb -r D:\\mywork\\WorkMenu\\RZ_System\\src\\main\\resources/"+fileName);

            System.out.println("备份成功!");
        }catch (Exception e){

            e.printStackTrace();;

        }
    }
}
