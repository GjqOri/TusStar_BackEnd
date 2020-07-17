package com.mr.tusstar.mapper;

import com.mr.tusstar.entity.Resume;
import com.mr.tusstar.entity.User;
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
    @Insert("INSERT INTO user(phone, name, email, password, registertime) VALUES(#{phone}, #{name}, #{email}, #{password}, #{registerTime})")
    int register(@Param("phone") String phone, @Param("name") String name, @Param("email") String email, @Param("password") String password, @Param("registerTime") String registerTime);

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
}
