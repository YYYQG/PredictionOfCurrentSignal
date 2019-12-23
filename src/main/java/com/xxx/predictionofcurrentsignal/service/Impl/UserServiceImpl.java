package com.xxx.predictionofcurrentsignal.service.Impl;

import com.xxx.predictionofcurrentsignal.controller.ValidateCodeController;
import com.xxx.predictionofcurrentsignal.dao.entity.User;
import com.xxx.predictionofcurrentsignal.dao.repository.UserRepository;
import com.xxx.predictionofcurrentsignal.exception.UserIsExistException;
import com.xxx.predictionofcurrentsignal.exception.ValidateCodeException;
import com.xxx.predictionofcurrentsignal.po.ValidateCode;
import com.xxx.predictionofcurrentsignal.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User getUserByName(String name) {
        User user = userRepository.findUserByName(name);
        return user;
    }
    @Override
    public Map<String, Object> getAllUser(Integer page, Integer pageSize) {
        Map<String,Object> result = new HashMap();
        Pageable pageable = PageRequest.of(page-1,pageSize);
        Page<User> users = userRepository.findAll(pageable);
        result.put("total",users.getTotalElements());
        result.put("users",users.getContent());
        return result;
    }
    @Override
    public void updateUser(User user) {
        User db = userRepository.findUserByName(user.getName());
        db.setEmail(user.getEmail());
        db.setUserDescribe(user.getUserDescribe());
        db.setAddress(user.getAddress());
        userRepository.save(db);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void addUser(User user) {

        User db = userRepository.findUserByAccount(user.getAccount());
        if(db!=null){
            throw new UserIsExistException("账号已经存在！");
        }
        user.setRole("ROLE_USER");
        user.setHeaderImg("https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png");
        user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void bindTel(String tel, String smsCode, Integer userId, ServletWebRequest request) {

        SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
        ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);
        String codeInRequest=smsCode;
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        User db = userRepository.findUserByTel(tel);
        if(db!=null){
            throw new ValidateCodeException("手机号已存在");
        }
        userRepository.bindTel(tel,userId);
        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);

    }
}
