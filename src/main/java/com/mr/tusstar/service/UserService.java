package com.mr.tusstar.service;

import com.mr.tusstar.entity.Job;
import com.mr.tusstar.entity.Resume;
import com.mr.tusstar.entity.User;
import com.mr.tusstar.entity.UserApplyJob;
import com.mr.tusstar.mapper.UserMapper;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * @author 董帅
 * @date 2020/7/16 - 10:16
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommonService commonService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    /*
    * 注册
    * */
    public int register(String phone, String name, String email, String password){
        String registerTime = dateFormat.format(new Date());
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        // 第一个参数是要加密的字符串,第二个参数是随机盐,第三个参数是求hash的次数(可修改为其它整数,这里修改的话对应的controller的login也要修改),最后将结果转换为16进制返回(长度为32位)
        String md5_password = new Md5Hash(password, salt, 2).toHex();
        // String md5_password = MD5.getMD5(password);
        return userMapper.register(phone, name, email, md5_password, registerTime, salt);
    }
    /*
    * 根据phone和password进行登录判断
    * */
    /*public String queryByPhoneAndPassword(String phone, String password){
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
    }*/
    public User queryByPhoneAndPassword(String phone, String password) {
        return userMapper.queryByPhoneAndPassword(phone, password);
    }
    public String querySaltByPhone(String phone) {
        return userMapper.selectSaltByPhone(phone);
    }

    /*
    * 判断注册时用户是否存在
    * */
    public String judgeUserExist(String phone){
        Integer exist = userMapper.verifyUserExist(phone);
        if (exist == null){
            return "userNotExist";
        }
        else {
            return "userExist";
        }
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
    /*
    * 用户申请岗位,此参数id为job 的id
    * */
    public String applyJob(int jobId, HttpSession session){
        Job job = userMapper.selectJobInfoById(jobId);
        String companyName = job.getName();
        String jobName = job.getJobName();
        String workLocation = job.getWorkLocation();
        String nature = job.getNature();
        String phone = (String) session.getAttribute("userPhone");
        String name = (String) session.getAttribute("userName");
        String postTime = dateFormat.format(new Date());
        int i = userMapper.applyJob(phone, name, jobId, jobName, companyName, workLocation, nature, "wait", postTime);
        if (i == 1){
            return "success";
        }else {
            return "fail";
        }
    }
    /*
    * 根据phone查询名字
    * */
    public String selectNameByPhone(String phone){
        return userMapper.selectNameByPhone(phone);
    }
    /*
    * 判断用户是否申请了职位
    * */
    public String ifApplyJob(int JobId, HttpSession session){
        String userPhone = (String) session.getAttribute("userPhone");
        int i = userMapper.ifApplyJob(JobId, userPhone);
        if (i == 1){
            return "applied";
        }else {
            return "firstApply";
        }
    }
    /*
    * 获取个人用户基础信息，如电话、姓名、邮箱
    * */
    public User userInfo(HttpSession session){
        String phone = (String) session.getAttribute("userPhone");
        return userMapper.userInfo(phone);
    }
    /*
    * 获取用户曾经投递过得岗位
    * */
    public UserApplyJob[] userAppliedJobs(HttpSession session){
        String phone = (String) session.getAttribute("userPhone");
        return userMapper.userAppliedJobs(phone);
    }
}
