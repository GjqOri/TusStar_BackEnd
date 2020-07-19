package com.mr.tusstar.service;

import com.mr.tusstar.entity.CompanyUser;
import com.mr.tusstar.mapper.CompanyUserMapper;
import com.mr.tusstar.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 董帅
 * @date 2020/7/16 - 12:12
 */
@Service
public class CompanyUserService {
    @Autowired
    private CompanyUserMapper companyUserMapper;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    /*
    * 注册
    * */
    public int register(String name, String type, String scale, String area,
                        int fund, String industry, String phone, String email,
                        String introduction, String listed, String headquarters,
                        String website, String password){
        String md5_password = MD5.getMD5(password);
        String registertime = dateFormat.format(new Date());
        int register = companyUserMapper.register(name, type, scale, area, fund, industry, phone, email, introduction, listed, headquarters, website, registertime);
        int insertCompanyUser = companyUserMapper.insertCompanyUser(email, md5_password, registertime);
        if (register == 1 && insertCompanyUser == 1){
            return 1;
        }
        return 0;
    }
    /*
    * 查询登录是否正确
    * */
    public String queryByEmailAndPassword(String email, String password){
        String md5_password = MD5.getMD5(password);
        CompanyUser companyUser = companyUserMapper.selectByEmail(email);
        if (companyUser != null){
            if (companyUser.getPassword().equals(md5_password)){
                return "success";
            }else {
                return "fail_password";
            }
        }
        return "fail_no companyuser";
    }
    /*
    * 判断注册时企业用户是否存在
    * */
    public String judgeCompanyUserExist(String name){
        int select = companyUserMapper.selectByName(name);
        if (select == 1){
            return "userExist";
        }
        return "uerNotExist";
    }
    /*
    * 企业发布工作
    * */
    public String postJob(String jobName, String nature, String type, String workLocation, int salary,
                          String degree, String experience, String email, String contactPhone, String contactName,
                          int recruitingNumbers, String jobWelfare, String jobDesc, String jobContent, HttpSession session){
        String companyEmail = String.valueOf(session.getAttribute("companyEmail"));
        String name = companyUserMapper.selectNameByEmail(companyEmail);
        String postTime = dateFormat.format(new Date());
        int i = companyUserMapper.postJob(name, jobName, nature, type, workLocation, salary, degree, experience, email
                , contactPhone, contactName, recruitingNumbers, jobWelfare, jobDesc, jobContent, postTime);
        if (i == 1){
            return "success";
        }else {
            return "fail";
        }
    }
    /*
    * 根据邮箱返回名字
    * */
    public String selectNameByEmail(String email){
        return companyUserMapper.selectNameByEmail(email);
    }
    /*
    * 根据email查询id
    * */
    public int selectIdByEmail(HttpSession session){
        String email = (String) session.getAttribute("companyEmail");
        return companyUserMapper.selectIdByEmail(email);
    }
}
