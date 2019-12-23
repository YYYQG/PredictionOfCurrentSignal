package com.xxx.predictionofcurrentsignal.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.xxx.predictionofcurrentsignal.dao.entity.User;
import com.xxx.predictionofcurrentsignal.exception.ValidateCodeException;
import com.xxx.predictionofcurrentsignal.po.PageDTO;
import com.xxx.predictionofcurrentsignal.po.UserDTO;
import com.xxx.predictionofcurrentsignal.po.ValidateCode;
import com.xxx.predictionofcurrentsignal.service.UserService;
import com.xxx.predictionofcurrentsignal.util.ResponseEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@Api(value = "User-API", description = "这是用户接口详细信息的描述")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @GetMapping("/user")
    public ResponseEntity<User> getUser(HttpServletRequest request) throws UnsupportedEncodingException {

        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer ");
        Claims claims = Jwts.parser().setSigningKey("predict".getBytes("UTF-8")).parseClaimsJws(token).getBody();
        String userName = (String) claims.get("user_name");
        User user = userService.getUserByName(userName);
        return ResponseEntity.success(user);
    }

    @PostMapping("/users")
    public ResponseEntity getAllUser(@RequestBody PageDTO pageDTO) {
        Map<String, Object> result = userService.getAllUser(pageDTO.getPage(), pageDTO.getPageSize());
        return ResponseEntity.success(result);
    }

    @PostMapping("/user")
    public ResponseEntity updateUser(@RequestBody User user, Authentication authentication) {
        user.setName((String) authentication.getPrincipal());
        userService.updateUser(user);
        return ResponseEntity.success();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.success();
    }

    @PostMapping("/newUser")
    public ResponseEntity addUser(@RequestBody UserDTO userDTO) {

        User user = new User();
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setAccount(userDTO.getAccount());
        user.setTel(userDTO.getTel());
        userService.addUser(user);
        return ResponseEntity.success();
    }

    @PostMapping("/bindTel")
    public ResponseEntity bindTel(String tel,String smsCode,Integer userId,ServletWebRequest request){
         userService.bindTel(tel,smsCode,userId,request);
        return ResponseEntity.success();
    }
    @GetMapping("/weixin/signup")
    public ResponseEntity signup(){
        return ResponseEntity.fail();
    }

}
