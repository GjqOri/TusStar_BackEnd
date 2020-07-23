package com.mr.tusstar.service;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @author Qi
 * @create 2020-07-14 8:36
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void judgeUserExistTest() {
        userService.judgeUserExist("18797151587");
    }

    @Test
    public void querySaltByPhoneTest() {
        System.out.println(userService.querySaltByPhone("187971515871"));
    }

    @Test
    public void MD5AddSaltHashTest() {
        // 前端传来的密码字符串
        String password = "202cb962ac59075b964b07152d234b70";
        // 第一个参数是要加密的字符串,第二个参数是随机盐,第三个参数是求hash的次数,最后将结果转换为16进制返回(长度为32位)
        String md5Hash = new Md5Hash(password, "942cf727efc25ffe", 2).toHex();
        System.out.println(md5Hash);
    }

    @Test
    public void createSaltTest() {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }

}
