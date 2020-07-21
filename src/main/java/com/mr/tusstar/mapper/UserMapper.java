package com.mr.tusstar.mapper;

import com.mr.tusstar.entity.Job;
import com.mr.tusstar.entity.Resume;
import com.mr.tusstar.entity.User;
import com.mr.tusstar.entity.UserApplyJob;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author 董帅
 * @date 2020/7/16 - 10:04
 */
@Repository
public interface UserMapper {
    @Insert("INSERT INTO user(phone, name, email, password, registertime, salt) VALUES(#{phone}, #{name}, #{email}, #{password}, #{registerTime}, #{salt})")
    int register(@Param("phone") String phone, @Param("name") String name, @Param("email") String email, @Param("password") String password, @Param("registerTime") String registerTime, @Param("salt") String salt);

    @Select("SELECT id, phone, name, email, password FROM user WHERE phone = #{phone}")
    User selectUserByPhone(@Param("phone") String phone);

    @Insert("INSERT INTO resume VALUES(#{id},#{name},#{degree},#{birth},#{sex},#{nation},#{introduction}," +
            "#{address},#{phone},#{email},#{school},#{department},#{major},#{educationalSystem},#{timeOfEnrollment}," +
            "#{overHeadInfo},#{salary},#{internInfo})")
    int createResume(@Param("id") int id, @Param("name") String name, @Param("degree") String degree, @Param("birth") String birth, @Param("sex") String sex, @Param("nation") String nation,
                     @Param("introduction") String introduction, @Param("address") String address, @Param("phone") String phone, @Param("email") String email, @Param("school") String school,
                     @Param("department") String department, @Param("major") String major, @Param("educationalSystem") String educationalSystem, @Param("timeOfEnrollment") String timeOfEnrollment,
                     @Param("overHeadInfo") String overHeadInfo, @Param("salary") int salary, @Param("internInfo") String internInfo);

    @Select("SELECT id FROM user WHERE phone=#{phone} limit 1")
    int selectIdByPhone(@Param("phone") String phone);

    @Select("SELECT * FROM resume WHERE id=#{id}")
    Resume selectAllById(@Param("id") int id);

    @Update("UPDATE resume SET name=#{name}, degree=#{degree}, birth=#{birth}, sex=#{sex}, nation=#{nation}, introduction=#{introduction}, address=#{address}, phone=#{phone}, " +
            "email=#{email}, school=#{school}, department=#{department}, major=#{major}, educationalSystem=#{educationalSystem}, timeOfEnrollment=#{timeOfEnrollment}, overHeadInfo=#{overHeadInfo}, salary=#{salary}, internInfo=#{internInfo} WHERE id=#{id}")
    int updateResume(@Param("id") int id, @Param("name") String name, @Param("degree") String degree, @Param("birth") String birth, @Param("sex") String sex, @Param("nation") String nation,
                     @Param("introduction") String introduction, @Param("address") String address, @Param("phone") String phone, @Param("email") String email, @Param("school") String school,
                     @Param("department") String department, @Param("major") String major, @Param("educationalSystem") String educationalSystem, @Param("timeOfEnrollment") String timeOfEnrollment,
                     @Param("overHeadInfo") String overHeadInfo, @Param("salary") int salary, @Param("internInfo") String internInfo);

    @Select("SELECT EXISTS(SELECT 1 FROM resume WHERE id = #{id})")
    int selectResumeById(@Param("id") int id);

    @Insert("INSERT INTO userapplyjob(phone, name, jobid, jobname, companyname, worklocation, " +
            "nature, status, posttime) VALUES(#{phone}, #{name},#{jobId}, #{jobName}, #{companyName}, #{workLocation}, #{nature}, #{status}, #{postTime})")
    int applyJob(@Param("phone") String phone, @Param("name") String name, @Param("jobId") int jobId, @Param("jobName") String jobName, @Param("companyName") String companyName, @Param("workLocation") String workLocation, @Param("nature") String nature, @Param("status") String status, @Param("postTime") String postTime);

    @Select("SELECT id, name, jobname, worklocation, nature  FROM job WHERE id=#{id}")
    Job selectJobInfoById(@Param("id") int id);

    @Select("SELECT name FROM user WHERE phone=#{phone}")
    String selectNameByPhone(@Param("phone") String phone);

    @Select("SELECT EXISTS(SELECT 1 FROM userapplyjob WHERE jobid = #{jobId} and phone=#{phone})")
    int ifApplyJob(@Param("jobId") int jobId, @Param("phone") String phone);

    @Select("SELECT id, phone, name, email FROM user WHERE phone=#{phone}")
    User userInfo(@Param("phone") String phone);

    @Select("SELECT jobid, jobname, companyname,worklocation, nature, status, posttime FROM userapplyjob WHERE phone=#{phone}")
    UserApplyJob[] userAppliedJobs(@Param("phone") String phone);

    @Select("SELECT 1 FROM user WHERE phone=#{phone} LIMIT 1")
    Integer verifyUserExist(@Param("phone") String phone);

    @Select("select * from user where phone=#{phone} and password=#{password}")
    User queryByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);

    @Select("select salt from user where phone=#{phone}")
    String selectSaltByPhone(@Param("phone") String phone);
}
