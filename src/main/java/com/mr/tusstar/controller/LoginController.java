package com.mr.tusstar.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author Qi
 * @create 2020-07-13 20:50
 */

// 使用@CrossOrigin注解允许跨域请求
@CrossOrigin
@RestController
@RequestMapping("/login")

public class LoginController {

    @GetMapping(path="/{usertype}",produces = "application/json; charset=utf-8")
    public String queryUser(@PathVariable(value="usertype") String usertype, @RequestParam(value="userid") int userid) {
        System.out.println("get type: " + usertype);
        System.out.println("get id: " + userid);
        return "success";
    }

}
