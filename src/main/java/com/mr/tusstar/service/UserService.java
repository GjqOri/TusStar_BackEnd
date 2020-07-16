package com.mr.tusstar.service;

import com.mr.tusstar.entity.User;
import com.mr.tusstar.mapper.UserMapper;
import com.mr.tusstar.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
