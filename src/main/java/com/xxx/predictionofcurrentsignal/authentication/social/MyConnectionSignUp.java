package com.xxx.predictionofcurrentsignal.authentication.social;

import com.xxx.predictionofcurrentsignal.dao.entity.User;
import com.xxx.predictionofcurrentsignal.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

//@Component
public class MyConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String execute(Connection<?> connection) {

        User user = new User(0,connection.getDisplayName(),"","","","ROLE_USER",connection.getImageUrl(),null,null,null,null);
        User user1 = userRepository.saveAndFlush(user);
        return user1.getUserId();

    }
}
