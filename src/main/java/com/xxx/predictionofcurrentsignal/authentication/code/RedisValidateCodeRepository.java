package com.xxx.predictionofcurrentsignal.authentication.code;

import com.xxx.predictionofcurrentsignal.exception.ValidateCodeException;
import com.xxx.predictionofcurrentsignal.po.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Override
    public void save(ServletWebRequest request, ValidateCode validateCode) {
        redisTemplate.opsForValue().set(buildKey(request),validateCode,30,TimeUnit.MINUTES);
    }

    private String buildKey(ServletWebRequest request) {
        String deviceId = request.getHeader("deviceId");
        if(StringUtils.isBlank(deviceId)){
            throw new ValidateCodeException("请在请求中携带deviceId参数！");
        }
        return "code:"+deviceId;
    }

    @Override
    public ValidateCode get(ServletWebRequest request) {
        return (ValidateCode) redisTemplate.opsForValue().get(buildKey(request));
    }

    @Override
    public void remove(ServletWebRequest request) {
        redisTemplate.delete(buildKey(request));
    }
}
