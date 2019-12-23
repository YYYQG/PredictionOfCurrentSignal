package com.xxx.predictionofcurrentsignal;

import com.xxx.predictionofcurrentsignal.authentication.sms.AliSmsCodeSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PredictionofcurrentsignalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PredictionofcurrentsignalApplication.class, args);
    }

    public static void sender(){
        AliSmsCodeSender smsCodeSender = new AliSmsCodeSender();
        smsCodeSender.send("15984642026","123");
    }

}
