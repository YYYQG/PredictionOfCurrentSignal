package com.xxx.predictionofcurrentsignal.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.security")
@Data
public class SecurityProperties {

    private QQProperties qq = new QQProperties();
    private WeiXinProperties weixin=new WeiXinProperties();
}
