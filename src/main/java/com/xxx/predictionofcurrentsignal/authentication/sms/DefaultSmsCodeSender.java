package com.xxx.predictionofcurrentsignal.authentication.sms;

public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.print(mobile+"         "+code);
    }
}
