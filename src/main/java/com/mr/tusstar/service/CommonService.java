package com.mr.tusstar.service;

import com.mr.tusstar.entity.CompanyInfo;
import com.mr.tusstar.entity.Job;
import com.mr.tusstar.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 董帅
 * @date 2020/7/16 - 22:56
 */
@Service
public class CommonService {

    @Autowired
    private CommonMapper commonMapper;
    /*
    * 岗位列表的主要信息
    * */
    public Job[] mainInfo(){
        return commonMapper.mainInfo();
    }
    /*
    * 岗位的详细信息
    * */
    public Job allInfo(int id){
        return commonMapper.allInfo(id);
    }
    /*
    * 得到所有公司
    * */
    public CompanyInfo[] allCompanies(){
        return commonMapper.allCompanies();
    }
    /*
    * 得到某一个公司的详细信息
    * */
    public CompanyInfo comapnyDetail(int id){
        return commonMapper.companyDetail(id);
    }
    /*
     * 某个企业发布的工作
     * */
    public Job[] companyPostedJobs(String name){
        return commonMapper.selectJobByName(name);
    }
    /*
    * 搜索岗位名称、地点、分类
    * */
    public Job[] searchJobs(String jobName, String workLocation, String type){
        return commonMapper.searchJobs(jobName, workLocation, type);
    }
    /*
    * 注销
    * */
    public String logOut(HttpSession session){
        session.invalidate();
        return "success";
    }
    /*
    * 上传头像
    * */
    public String uploadHead(MultipartFile file, HttpSession session){
        String originalName = file.getOriginalFilename();
        assert originalName != null;
        String suffixName = originalName.substring(originalName.lastIndexOf("."));
        String filePath = "/pic/upload/";
//        String filePath = "F:/static/";
        String newFileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + newFileName);
        String userType = (String) session.getAttribute("userType");
        if (userType.equals("user")){
            int id = (int) session.getAttribute("userId");
            if (headExist(session).equals("noHave")){
                commonMapper.uploadHeadUser(id, newFileName);
            }else {
                String s = commonMapper.selectPathByIdUser(id);
                File deleteFile = new File(filePath + s);
                deleteFile.delete();
                commonMapper.updateHeadUser(id, newFileName);
            }
        }else if(userType.equals("company")){
            int id = (int) session.getAttribute("companyId");
            if (headExist(session).equals("noHave")){
                commonMapper.uploadHeadCom(id, newFileName);
            }else {
                String s = commonMapper.selectPathByIdCom(id);
                File deleteFile = new File(filePath + s);
                deleteFile.delete();
                commonMapper.updateHeadCom(id, newFileName);
            }
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
    /*
    * 判断是否有头像
    * */
    public String headExist(HttpSession session){
        String userType = (String) session.getAttribute("userType");
        if (userType.equals("user")){
            int id = (int) session.getAttribute("userId");
            int i = commonMapper.headExistUser(id);
            if (i == 1){
                return commonMapper.selectPathByIdUser(id);
            }else {
                return "noHave";
            }
        }else if(userType.equals("company")){
            int id = (int) session.getAttribute("companyId");
            int i = commonMapper.headExistCom(id);
            if (i == 1){
                return commonMapper.selectPathByIdCom(id);
            }else {
                return "noHave";
            }
        }
        return "noHave";
    }
    /*
    * 统计job类型个数
    * */
    public int[] selectJobTypeNum(){
        String[] strings = commonMapper.selectJobTypeNum();
        int[] jobNum = new int[9];
        for (String string : strings) {
            switch (string) {
                case "技术":
                    jobNum[0]++;
                    break;
                case "产品":
                    jobNum[1]++;
                    break;
                case "设计":
                    jobNum[2]++;
                    break;
                case "运营":
                    jobNum[3]++;
                    break;
                case "销售":
                    jobNum[4]++;
                    break;
                case "媒体":
                    jobNum[5]++;
                    break;
                case "金融":
                    jobNum[6]++;
                    break;
                case "教育":
                    jobNum[7]++;
                    break;
                case "服务":
                    jobNum[8]++;
                    break;
            }
        }
        return jobNum;
    }
}
