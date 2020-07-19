package com.mr.tusstar.mapper;

import com.mr.tusstar.entity.CompanyUser;
import com.mr.tusstar.entity.Pending;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author 董帅
 * @date 2020/7/16 - 11:56
 */
@Repository
public interface CompanyUserMapper {

    @Insert("INSERT INTO companyinfo(name, type, scale, area, fund, industry, phone, email, introduction, listed, headquarters, website, registertime)" +
            "VALUES(#{name}, #{type}, #{scale}, #{area}, #{fund}, #{industry}, #{phone}, #{email}, #{introduction}, #{listed}, #{headquarters}, #{website}, #{registertime})")
    int register(@Param("name") String name, @Param("type") String type, @Param("scale") String scale, @Param("area") String area,
                 @Param("fund") int fund, @Param("industry") String industry, @Param("phone") String phone, @Param("email") String email,
                 @Param("introduction") String introduction, @Param("listed") String listed, @Param("headquarters") String headquarters,
                 @Param("website") String website, @Param("registertime") String registertime);

    @Insert("INSERT INTO companyuser(email, password, registertime) VALUES(#{email}, #{password}, #{registertime})")
    int insertCompanyUser(@Param("email") String email, @Param("password") String password, @Param("registertime") String registertime);

    @Select("SELECT id, email, password FROM companyuser WHERE email = #{email}")
    CompanyUser selectByEmail(@Param("email") String email);

    @Select("SELECT EXISTS(SELECT 1 FROM companyinfo WHERE name = #{name} limit 1)")
    int selectByName(@Param("name") String name);

    @Insert("INSERT INTO job(name, jobname, nature, type, worklocation, salary, degree, experience, email, contactphone, contactname, " +
            "recruitingnumbers, jobwelfare, jobdesc, jobcontent, posttime) VALUES(#{name}, #{jobName},#{nature}, #{type},#{workLocation}, #{salary}," +
            "#{degree}, #{experience},#{email}, #{contactPhone},#{contactName}, #{recruitingNumbers},#{jobWelfare}, #{jobDesc},#{jobContent}, #{postTime})")
    int postJob(@Param("name") String name, @Param("jobName") String jobName, @Param("nature") String nature, @Param("type") String type, @Param("workLocation") String workLocation, @Param("salary") int salary,
                @Param("degree") String degree, @Param("experience") String experience, @Param("email") String email, @Param("contactPhone") String contactPhone, @Param("contactName") String contactName,
                @Param("recruitingNumbers") int recruitingNumbers, @Param("jobWelfare") String jobWelfare, @Param("jobDesc") String jobDesc, @Param("jobContent") String jobContent, @Param("postTime") String postTime);

    @Select("SELECT name from companyinfo WHERE email=#{email} limit 1")
    String selectNameByEmail(@Param("email") String email);

    @Select("SELECT id FROM companyuser WHERE email=#{email}")
    int selectIdByEmail(@Param("email") String email);

    @Select("SELECT phone, name, jobname, status FROM userapplyjob WHERE companyname=#{companyName}")
    Pending[] pendingApplications(@Param("companyName") String companyName);

    @Update("UPDATE userapplyjob SET status=#{status} WHERE phone=#{phone} AND jobname=#{jobName} AND companyname=#{companyName}")
    int updateUserApplyJobStatus(@Param("status") String status, @Param("phone") String phone, @Param("jobName") String jobName, @Param("companyName") String companyName);
}
