package com.weikun.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Description:
 * @Company:qianfeng
 * @Auther:weiMac
 * @Date:2019/10/14
 * @Time:15:39
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public Producer producer(){
        DefaultKaptcha producer=new DefaultKaptcha();
        Properties properties=new Properties();
        //设置验证码长度
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH,"4");
        properties.setProperty(Constants.KAPTCHA_BORDER,"no");

        Config config=new Config(properties);
        producer.setConfig(config);

        return producer;

    }

}
