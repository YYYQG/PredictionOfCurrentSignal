package com.xxx.predictionofcurrentsignal.service;


import com.xxx.predictionofcurrentsignal.dao.entity.User;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

public interface UserService {
    User getUserById(Integer id);
    User getUserByName(String name);
    Map<String, Object> getAllUser(Integer page, Integer pageSize);
    void updateUser(User user);
    void deleteUser(Integer  id);
    void addUser(User user);
    void bindTel(String tel, String smsCode, Integer userId, ServletWebRequest request);
}
