package com.xxx.predictionofcurrentsignal.properties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeiXinProperties {
    private String appId;
    private String appSecret;
    private String providerId="weixin";
}
