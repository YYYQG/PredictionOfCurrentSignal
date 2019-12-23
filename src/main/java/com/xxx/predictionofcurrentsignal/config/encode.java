package com.xxx.predictionofcurrentsignal.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

public class encode {

    public static void main(String[] args) {
        System.out.print(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456"));
    }

}
