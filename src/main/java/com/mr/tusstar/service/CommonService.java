package com.mr.tusstar.service;

import com.mr.tusstar.entity.CompanyInfo;
import com.mr.tusstar.entity.Job;
import com.mr.tusstar.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    * 邮箱验证码
    * */
}
