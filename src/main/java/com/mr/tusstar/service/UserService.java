package com.mr.tusstar.service;

import com.mr.tusstar.entity.Resume;
import com.mr.tusstar.entity.User;
import com.mr.tusstar.mapper.UserMapper;
import com.mr.tusstar.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author 董帅
 * @date 2020/7/16 - 10:16
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    /*
    * 注册
    * */
    public int register(String phone, String name, String email, String password){
        String registerTime = dateFormat.format(new Date());
        String md5_password = MD5.getMD5(password);
        return userMapper.register(phone, name, email, md5_password, registerTime);
    }
    /*
    * 根据phone和password进行登录判断
    * */
    public String queryByPhoneAndPassword(String phone, String password){
        String md5_password = MD5.getMD5(password);
        User user = userMapper.selectUserByPhone(phone);
        if (user != null){
            if (user.getPassword().equals(md5_password)){
                return "success";
            }else {
                return "fail_password";
            }
        }
        return "fail_no user";
    }
    /*
    * 判断注册时用户是否存在
    * */
    public String judgeUserExist(String phone){
        User user = userMapper.selectUserByPhone(phone);
        if (user != null){
            return "userExist";
        }
        return "userNotExist";
    }
    /*
    * 创建简历
    * */
    public String createResume(String name, String degree, String birth, String sex,
                               String nation, String introduction, String address,
                               String phone, String email, String school, String department,
                               String major, String educationalSystem, String timeOfEnrollment,
                               String overHeadInfo, int salary, String internInfo, HttpSession session){
        int id = (int) session.getAttribute("userId");
        int resume = userMapper.createResume(id, name, degree, birth, sex, nation, introduction, address,
                phone, email, school, department, major, educationalSystem, timeOfEnrollment,
                overHeadInfo, salary, internInfo);
        if (resume == 1){
            return "success";
        }else {
            return "fail";
        }
    }
    /*
    * 根据phone取出id
    * */
    public int selectIdByPhone(String phone){
        return userMapper.selectIdByPhone(phone);
    }
    /*
    * 根据id获取整个resume
    * */
    public Resume selectAllById(HttpSession session){
        int id = (int) session.getAttribute("userId");
        return userMapper.selectAllById(id);
    }
    /*
    * 完善提交简历
    * */
    public String updateResume(String name, String degree, String birth, String sex,
                               String nation, String introduction, String address,
                               String phone, String email, String school, String department,
                               String major, String educationalSystem, String timeOfEnrollment,
                               String overHeadInfo, int salary, String internInfo, HttpSession session){
        int userId = (int) session.getAttribute("userId");
        int i = userMapper.updateResume(userId, name, degree, birth, sex, nation, introduction, address, phone, email,
                school, department, major, educationalSystem, timeOfEnrollment, overHeadInfo, salary, internInfo);
        if (i == 1){
            return "success";
        }else {
            return "fail";
        }
    }
    /*
    * 判断是否有简历
    * */
    public String resumeExist(HttpSession session){
        int id = (int) session.getAttribute("userId");
        int i = userMapper.selectResumeById(id);
        if (i == 1){
            return "have_resume";
        }else {
            return "no_resume";
        }
    }
}
