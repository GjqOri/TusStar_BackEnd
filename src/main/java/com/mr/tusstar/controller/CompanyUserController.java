package com.mr.tusstar.controller;

import com.mr.tusstar.common.error.CompanyUserErrors;
import com.mr.tusstar.common.error.UserErrors;
import com.mr.tusstar.entity.CompanyInfo;
import com.mr.tusstar.entity.Job;
import com.mr.tusstar.entity.Pending;
import com.mr.tusstar.entity.Resume;
import com.mr.tusstar.mapper.CompanyUserMapper;
import com.mr.tusstar.service.CommonService;
import com.mr.tusstar.service.CompanyUserService;
import com.mr.tusstar.service.MailService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private CommonService commonService;
    @Autowired
    private MailService mailService;
    /*
    * 企业用户注册
    * */
    @PostMapping("/register")
    public String register(String name, String type, String scale, String area,
                           int fund, String industry, String phone, String email,
                           String introduction, String listed, String headQuarters,
                           String website, String password){
        if (companyUserService.judgeCompanyUserExist(name).equals("userExist")){
            return "userExist";
        }
        int register = companyUserService.register(name, type, scale, area, fund, industry, phone, email, introduction, listed, headQuarters, website, password);
        if (register == 1){
            return "success";
        }else {
            return "fail";
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
    * 企业登录
    * */
    /*@PostMapping("/login")
    @SessionScope
    public String login(@RequestParam(value = "phone") String email, String password, HttpSession session){
        String query = companyUserService.queryByEmailAndPassword(email, password);
        if (query.equals("success")){
            String name = companyUserService.selectNameByEmail(email);
            int id = companyUserService.selectIdByEmail(email);
            session.setAttribute("companyEmail", email);
            session.setAttribute("companyName", name);
            session.setAttribute("userType", "company");
            session.setAttribute("companyId", id);
            return String.valueOf(id);
        }else if (query.equals("fail_password")){
            return "error_password";
        }else {
            return "error_ no companyuser";
        }
    }*/
    @PostMapping(path = "/login")
    public Object login(@RequestParam(value = "phone") String email, String password) {
        // 1. 获取subject(实体)
        Subject subject = SecurityUtils.getSubject();
        // 2. 判断用户是否已经登录
        if (!subject.isAuthenticated()) {
            // 2.1 封装用户的登录数据
            UsernamePasswordToken token = new UsernamePasswordToken(email, password);
            // token.setRememberMe(true); 记住我功能
            try {
                subject.login(token);
                return companyUserService.selectIdByEmail(email);
            }
            catch (AuthenticationException e) {
                return CompanyUserErrors.NOUSER_ERROR;
            }
        }
        else {
            // 提示用户您已登录或注销并跳转到登录页面(二选一)
            return CompanyUserErrors.REPEATLOGIN_ERROR;
        }
    }
    // 用于测试角色权限
    /*@GetMapping(path = "/listRoles")
    public String listRoles() {
        return "企业用户拥有companyuser role";
    }*/

    /*
     * 返回登录名字
     * */
    @GetMapping("/getName")
    public String getName(HttpSession session){
        return (String) session.getAttribute("companyName");
    }
    /*
    * 企业发布岗位
    * */
    @PostMapping("/postJob")
    public String postJob(String jobName, String nature, String type, String workLocation, int salary,
                          String degree, String experience, String email, String contactPhone, String contactName,
                          int recruitingNumbers, String jobWelfare, String jobDesc, String jobContent, HttpSession session){
        return companyUserService.postJob(jobName, nature, type, workLocation, salary,
                degree, experience, email, contactPhone, contactName, recruitingNumbers, jobWelfare, jobDesc, jobContent, session);
    }
    /*
     * 查看岗位列表
     * */
    @GetMapping("/jobList")
    public Job[] jobList(){
        return commonService.mainInfo();
    }
    /*
     * 查看某个岗位的详细信息
     * */
    @GetMapping("/job/{id}")
    public Job jobDetail(@PathVariable(value = "id") int id){
        return commonService.allInfo(id);
    }
    /*
    * 公司列表
    * */
    @GetMapping("/getAllCompanies")
    public CompanyInfo[] allCompanies(){
        return commonService.allCompanies();
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
     * 搜索岗位
     * */
    @PostMapping("/searchJobs")
    public Job[] searchJobs(String jobName, String workLocation, String type){
        return commonService.searchJobs(jobName, workLocation, type);
    }
    /*
     * 注销
     * */
    @GetMapping("/logOut")
    public String logOut(HttpSession session){
        return commonService.logOut(session);
    }
    /*
    * 根据email返回id
    * */
    @GetMapping("/getId")
    public int selectIdByEmail(HttpSession session){
        return companyUserService.selectIdByEmail(session);
    }
    /*
    * 待处理的申请
    * */
    @GetMapping("/getPendings")
    public Pending[] pending(HttpSession session){
        return companyUserService.pending(session);
    }
    /*
    * 通知面试
    * */
    @GetMapping("/interview")
    public String interview(String phone, String jobName, HttpSession session){
        return companyUserService.interview(phone, jobName, session);
    }
    /*
    * 查看简历
    * */
    @GetMapping("/viewResume/{phone}")
    public Resume viewResume(@PathVariable(value = "phone") String phone){
        return companyUserService.viewResume(phone);
    }
    /*
    * 通知拒绝
    * */
    @GetMapping("/refuse")
    public String refuse(String phone, String jobName, HttpSession session){
        return companyUserService.refuse(phone, jobName, session);
    }
    /*
    * 通知报到
    * */
    @GetMapping("/work")
    public String work(String phone, String jobName, HttpSession session){
        return companyUserService.work(phone, jobName, session);
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
    * 上传营业执照
    * */
    @PostMapping("/uploadLicense")
    public String uploadLicense(MultipartFile file, HttpSession session){
        return companyUserService.uploadLicense(file, session);
    }
    /*
    * 判断是否有执照
    * */
    @GetMapping("/licenseExist")
    public String licenseExist(HttpSession session){
        return companyUserService.licenseExist(session);
    }
    /*
     * 统计职位分类个数
     * */
    @GetMapping("/getJobTypeNum")
    public int[] selectJobTypeNum(){
        return commonService.selectJobTypeNum();
    }
}
