package com.xxx.predictionofcurrentsignal.authentication.sms;

public interface SmsCodeSender {

    void send(String mobile,String code);
}
