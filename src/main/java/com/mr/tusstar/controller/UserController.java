package com.mr.tusstar.controller;

import com.mr.tusstar.common.error.LoginErrors;
import com.mr.tusstar.common.error.RegisterErrors;
import com.mr.tusstar.entity.*;
import com.mr.tusstar.service.CommonService;
import com.mr.tusstar.service.MailService;
import com.mr.tusstar.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * @author 董帅
 * @date 2020/7/16 - 10:27
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private MailService mailService;

    /*
    * 注册功能
    * */
    @PostMapping("/register")
    public Object register(String phone, String name, String email, String password){
        if (userService.judgeUserExist(phone).equals("userExist")){
            return RegisterErrors.USEREXIST_ERROR;
        }else {
            int register = userService.register(phone, name, email, password);
            if (register == 1){
                return "success";
            }else {
                return RegisterErrors.OTHER_ERROR;
            }
        }
    }
    /*
    * 邮箱验证
    * */
    @GetMapping("/emailCode/{mail}")
    public int emailCode(@PathVariable(name = "mail") String mail){
        int code = (int) ((Math.random()*9+1)*100000);
        mailService.sendSimpleEmail(code, mail);
        return code;
    }
    /*
     * 查看岗位列表
     * */
    @GetMapping("/jobList")
    public Job[] jobList(){
        return commonService.mainInfo();
    }
    /*
     * 搜索岗位
     * */
    @PostMapping("/searchJobs")
    public Job[] searchJobs(String jobName, String workLocation, String type){
        return commonService.searchJobs(jobName, workLocation, type);
    }
    /*
     * 得到公司列表
     * */
    @GetMapping("/getAllCompanies")
    public CompanyInfo[] allCompanies(){
        return commonService.allCompanies();
    }
    /*
     * 统计职位分类个数
     * */
    @GetMapping("/getJobTypeNum")
    public int[] selectJobTypeNum(){
        return commonService.selectJobTypeNum();
    }
    /*
     * 查看某个公司详细信息
     * */
    @GetMapping("/companyDetail/{id}")
    public CompanyInfo companyDetail(@PathVariable(value = "id") int id){
        return commonService.comapnyDetail(id);
    }
    /*
     * 查看某个公司曾经发布的岗位
     * */
    @GetMapping("/postedJobs/{name}")
    public Job[] postedJobs(@PathVariable(value = "name") String name){
        return commonService.companyPostedJobs(name);
    }


    /*
    * 登录功能
    * */
    @PostMapping(path = "/login")
    public Object login(@RequestParam("phone") String phone,@RequestParam("password") String password) {
        // 1. 获取subject(实体)
        Subject subject = SecurityUtils.getSubject();
        // 2. 判断用户是否已经登录
        if (subject.isAuthenticated()) {
            // 如果用户已经登录,那么注销当前用户
            logOut();
        }
        // 3. 查询该用户的随机盐
        String salt = userService.querySaltByPhone(phone);
        // 4. 求MD5加盐hash
        password = new Md5Hash(password, salt, 2).toHex();
        // 5. 封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
        // token.setRememberMe(true); 记住我功能
        try {
            subject.login(token);
            return userService.selectIdByPhone(phone);
        }
        catch (AuthenticationException e) {
            return LoginErrors.NOUSER_ERROR;
        }
    }
    /*
    * 返回登录名字l
    * */
    @GetMapping("/getName")
    public String getName(HttpSession session){
        return (String) session.getAttribute("userName");
    }
    /*
    * 查看某个岗位的详细信息
    * */
    @GetMapping("/job/{id}")
    public Job jobDetail(@PathVariable(value = "id") int id){
        return commonService.allInfo(id);
    }
    /*
    * 创建简历import crypto from "crypto";
    * */
    @PostMapping("/createResume")
    public String createResume(String name, String degree, String birth, String sex,
                               String nation, String introduction, String address,
                               String phone, String email, String school, String department,
                               String major, String educationalSystem, String timeOfEnrollment,
                               String overHeadInfo, int salary, String internInfo, HttpSession session){
        return userService.createResume(name, degree, birth, sex, nation, introduction, address, phone, email,
                school, department, major, educationalSystem, timeOfEnrollment, overHeadInfo,
                salary, internInfo, session);
    }
    /*
    * 完善简历前向前端发送已有的数据
    * */
    @GetMapping("/getResume")
    public Resume getResume(HttpSession session){
        return userService.selectAllById(session);
    }
    /*
    * 完善后提交简历
    * */
    @PostMapping("/updateResume")
    public String updateResume(String name, String degree, String birth, String sex,
                               String nation, String introduction, String address,
                               String phone, String email, String school, String department,
                               String major, String educationalSystem, String timeOfEnrollment,
                               String overHeadInfo, int salary, String internInfo, HttpSession session){
        return userService.updateResume(name, degree, birth, sex, nation, introduction, address, phone, email,
                school, department, major, educationalSystem, timeOfEnrollment, overHeadInfo, salary, internInfo, session);
    }
    /*
    * 判断是否有简历
    * */
    @GetMapping("/resumeExist")
    public String resumeExist(HttpSession session){
        return userService.resumeExist(session);
    }
    /*
    * 用户申请岗位
    * */
    @PostMapping("/applyJob/{id}")
    public String applyJob(@PathVariable(value = "id") int id, HttpSession session){
        return userService.applyJob(id, session);
    }
    /*
    * 判断用户是否已经申请了职位
    * */
    @GetMapping("/ifApplyJob/{jobId}")
    public String ifApplyJob(@PathVariable(value = "jobId") int jobId, HttpSession session){
        return userService.ifApplyJob(jobId, session);
    }
    /*
    * 注销
    * */
    @GetMapping("/logOut")
    public String logOut(){
        return commonService.logOut();
    }
    /*
    * 获取个人基本信息
    * */
    @GetMapping("/getUserInfo")
    public User userInfo(HttpSession session){
        return userService.userInfo(session);
    }
    /*
     * 获取用户曾经投递过得岗位
     * */
    @GetMapping("/getUserAppliedJobs")
    public UserApplyJob[] userAppliedJobs(HttpSession session){
        return userService.userAppliedJobs(session);
    }
    /*
    * 上传头像
    * */
    @PostMapping("/uploadHead")
    public String uploadHead(MultipartFile file, HttpSession session){
        return commonService.uploadHead(file, session);
    }
    /*
    * 判断是否有头像，如果有直接返回名字，
    * 没有的话就返回noHave
    * */
    @GetMapping("/headExist")
    public String headExist(HttpSession session){
        return commonService.headExist(session);
    }
    /*
     * 统计主页最下面的相应个数
     * */
    @GetMapping("/indexCount")
    public int[] indexCount(){
        return commonService.indexCount();
    }
}
