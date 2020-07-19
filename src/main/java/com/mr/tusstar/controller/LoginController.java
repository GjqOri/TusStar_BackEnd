package com.mr.tusstar.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author Qi
 * @create 2020-07-13 20:50
 */

@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(path="/toLogin")
    public String toLogin() {
        return "success";
    }

}
