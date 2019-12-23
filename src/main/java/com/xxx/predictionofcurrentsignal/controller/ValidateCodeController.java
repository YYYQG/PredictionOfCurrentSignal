package com.xxx.predictionofcurrentsignal.controller;

import com.xxx.predictionofcurrentsignal.authentication.sms.SmsCodeSender;
import com.xxx.predictionofcurrentsignal.po.ValidateCode;
import com.xxx.predictionofcurrentsignal.authentication.code.ValidateCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class ValidateCodeController {

    public static final String SESSION_KEY="SESSION_KEY_SMS_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;

    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {

        ValidateCode validateCode = smsCodeGenerator.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,validateCode);
        String mobile = ServletRequestUtils.getRequiredStringParameter(request,"mobile");
        log.info(mobile,validateCode.getCode());
        smsCodeSender.send(mobile,validateCode.getCode() );
    }

}
