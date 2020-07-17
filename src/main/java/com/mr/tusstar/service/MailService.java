package com.mr.tusstar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author 董帅
 * @date 2020/7/17 - 15:22
 */
@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendSimpleEmail(int code, String mail){
        try {
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom("dongshuai0331@163.com");
            message.setTo(mail);
            message.setSubject("激活验证码");
            message.setText(String.valueOf(code));
            mailSender.send(message);
        }catch (Exception e){
            System.out.println("发生异常");
        }
    }
}
