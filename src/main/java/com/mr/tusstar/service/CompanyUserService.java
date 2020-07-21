package com.mr.tusstar.service;

import com.mr.tusstar.entity.CompanyUser;
import com.mr.tusstar.entity.Pending;
import com.mr.tusstar.entity.Resume;
import com.mr.tusstar.mapper.CompanyUserMapper;
import com.mr.tusstar.mapper.UserMapper;
import com.mr.tusstar.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author 董帅
 * @date 2020/7/16 - 12:12
 */
@Service
public class CompanyUserService {
    @Autowired
    private CompanyUserMapper companyUserMapper;
    @Autowired
    private UserMapper userMapper;

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
    /*public String queryByEmailAndPassword(String email, String password){
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
    }*/
    public CompanyUser queryByEmailAndPassword(String email, String password) {
        return companyUserMapper.selectByEmailAndPassword(email, password);
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
    /*
    * 待处理申请和已经处理完的历史
    * */
    public Pending[] pending(HttpSession session){
        String companyName = (String) session.getAttribute("companyName");
        Pending[] pendings = companyUserMapper.pendingApplications(companyName);
        for (Pending pending : pendings) {
            String userPhone = pending.getPhone();
            int id = userMapper.selectIdByPhone(userPhone);
            Resume resume = userMapper.selectAllById(id);
            String school = resume.getSchool();
            String major = resume.getMajor();
            pending.setSchool(school);
            pending.setMajor(major);
        }
        return pendings;
    }
    /*
     * 通知面试
     * */
    public String interview(String phone, String jobName, HttpSession session){
        String companyName = (String) session.getAttribute("companyName");
        int interview = companyUserMapper.updateUserApplyJobStatus("interview", phone, jobName, companyName);
        if (interview == 1){
            return "success";
        }else {
            return "fail";
        }
    }
    /*
     * 查看简历
     * */
    public Resume viewResume(String phone){
        int id = userMapper.selectIdByPhone(phone);
        return userMapper.selectAllById(id);
    }
    /*
     * 通知拒绝
     * */
    public String refuse(String phone, String jobName, HttpSession session){
        String companyName = (String) session.getAttribute("companyName");
        int refuse = companyUserMapper.updateUserApplyJobStatus("refuse", phone, jobName, companyName);
        if (refuse == 1){
            return "success";
        }else {
            return "fail";
        }
    }
    /*
    * 通知报到
    * */
    public String work(String phone, String jobName, HttpSession session){
        String companyName = (String) session.getAttribute("companyName");
        int work = companyUserMapper.updateUserApplyJobStatus("work", phone, jobName, companyName);
        if (work == 1){
            return "success";
        }else {
            return "fail";
        }
    }
    /*
    * 根据email查id
    * */
    public int selectIdByEmail(String email){
        return companyUserMapper.selectIdByEmail(email);
    }
    /*
    * 上传营业执照
    * */
    public String uploadLicense(MultipartFile file, HttpSession session){
        String originalName = file.getOriginalFilename();
        assert originalName != null;
        String suffixName = originalName.substring(originalName.lastIndexOf("."));
        String filePath = "/pic/upload/";
//        String filePath = "F:/static/";
        String newFileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + newFileName);
        int id = (int) session.getAttribute("companyId");
        if (licenseExist(session).equals("noHave")){
            companyUserMapper.uploadLicense(id, newFileName);
        }else {
            String s = companyUserMapper.selectPathById(id);
            File deleteFile = new File(filePath + s);
            deleteFile.delete();
            companyUserMapper.updateLicenseById(id, newFileName);
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
    /*
    * 判断是否有执照
    * */
    public String licenseExist(HttpSession session){
        int id = (int) session.getAttribute("companyId");
        int i = companyUserMapper.licenseExist(id);
        if (i == 1){
            return companyUserMapper.selectPathById(id);
        }else {
            return "noHave";
        }
    }
}
