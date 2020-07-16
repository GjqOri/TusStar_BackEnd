package com.mr.tusstar.mapper;

import com.mr.tusstar.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
}
