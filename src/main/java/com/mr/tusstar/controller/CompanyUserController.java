package com.mr.tusstar.controller;

import com.mr.tusstar.service.CompanyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpSession;

/**
 * @author 董帅
 * @date 2020/7/16 - 11:53
 */
@RestController
@RequestMapping("/company")
public class CompanyUserController {
    @Autowired
    private CompanyUserService companyUserService;
    /*
    * 企业用户注册
    * */
    @PostMapping("/register")
    public String register(String name, String type, String scale, String area,
                           int fund, String industry, String phone, String email,
                           String introduction, String listed, String headquarters,
                           String website, String password){
        if (companyUserService.judgeCompanyUserExist(name).equals("userExist")){
            return "userExist";
        }
        int register = companyUserService.register(name, type, scale, area, fund, industry, phone, email, introduction, listed, headquarters, website, password);
        if (register == 1){
            return "success";
        }else {
            return "fail";
        }
    }
    /*
    * 企业登录
    * */
    @PostMapping("/login")
    @SessionScope
    public String login(String email, String password, HttpSession session){
        String query = companyUserService.queryByEmailAndPassword(email, password);
        if (query.equals("success")){
            session.setAttribute("companyEmail", email);
            return "success";
        }else if (query.equals("fail_password")){
            return "error_password";
        }else {
            return "error_ no companyuser";
        }
    }
    /*
    * 企业发布岗位
    * */
    @PostMapping("/postJob")
    public String postJob(String jobName, String nature, String type, String workLocation, int salary,
                          String degree, String experience, String email, String contactPhone, String contactName,
                          int recruitingNumbers, String jobWelfare, String jobDesc, String jobContent, HttpSession session){
        String s = companyUserService.postJob(jobName, nature, type, workLocation, salary,
                degree, experience, email, contactPhone, contactName, recruitingNumbers, jobWelfare, jobDesc, jobContent, session);
        return s;
    }
}
