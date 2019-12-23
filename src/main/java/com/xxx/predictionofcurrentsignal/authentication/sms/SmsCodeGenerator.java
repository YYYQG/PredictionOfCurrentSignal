package com.xxx.predictionofcurrentsignal.authentication.sms;

import com.xxx.predictionofcurrentsignal.authentication.code.ValidateCodeGenerator;
import com.xxx.predictionofcurrentsignal.po.ValidateCode;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class SmsCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(4);
        return new ValidateCode(code,LocalDateTime.now().plusSeconds(1000));
    }
}
