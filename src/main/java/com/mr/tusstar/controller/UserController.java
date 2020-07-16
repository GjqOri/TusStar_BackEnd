package com.mr.tusstar.controller;

import com.mr.tusstar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 董帅
 * @date 2020/7/16 - 10:27
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    /*
    * 注册功能
    * */
    @PostMapping("/register")
    public String register(String phone, String name, String email, String password){
        System.out.println(phone);
        System.out.println(name);
        System.out.println(email);
        System.out.println(password);
        if (userService.judgeUserExist(phone).equals("userExist")){
            return "userExist";
        }else {
            int register = userService.register(phone, name, email, password);
            if (register == 1){
                return "success";
            }else {
                return "fail";
            }
        }
    }
    /*
    * 登录功能
    * */
    @PostMapping("/login")
    public String login(String phone, String password){
        String select = userService.queryByPhoneAndPassword(phone, password);
        if (select.equals("success")){
            return "success";
        }else if (select.equals("fail_password")){
            return "error_password";
        }else {
            return "error_no user";
        }
    }
}
