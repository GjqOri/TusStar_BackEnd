package com.mr.tusstar.mapper;

import com.mr.tusstar.entity.CompanyInfo;
import com.mr.tusstar.entity.Job;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author 董帅
 * @date 2020/7/16 - 22:57
 */
@Repository
public interface CommonMapper {

    @Select("SELECT id, jobname, name, worklocation, nature, posttime FROM job")
    Job[] mainInfo();

    @Select("SELECT * FROM job WHERE id=#{id} limit 1")
    Job allInfo(@Param("id") int id);

    @Select("SELECT id, name, area FROM companyinfo")
    CompanyInfo[] allCompanies();

    @Select("SELECT * FROM companyinfo WHERE id=#{id}")
    CompanyInfo companyDetail(@Param("id") int id);

    @Select("SELECT id, jobname, type, jobdesc, posttime FROM job WHERE name=#{name}")
    Job[] selectJobByName(@Param("name") String name);

    @Select("SELECT id, jobname, name, worklocation, nature, posttime FROM job WHERE IF(#{jobName}!='', jobname like "+ "'%"+"${jobName}"+"%'"+", '1=1') AND " +
            "IF(#{workLocation}!='', worklocation like "+"'%"+"${workLocation}"+"%'"+", '1=1') AND IF(#{type}!='', type like "+"'%"+"${type}"+"%'"+", '1=1')")
    Job[] searchJobs(@Param("jobName") String jobName, @Param("workLocation") String workLocation, @Param("type") String type);

    @Select("SELECT id, jobname, name, worklocation, nature FROM job WHERE jobname=#{jobName} AND name=#{companyName}")
    Job mainInfoByJobNameAndCompanyName(@Param("jobName") String jobName, @Param("companyName") String companyName);

    @Insert("INSERT INTO userheadportrait VALUES(#{id}, #{path})")
    int uploadHeadUser(@Param("id") int id, @Param("path") String path);

    @Insert("INSERT INTO companyheadportrait VALUES(#{id}, #{path})")
    int uploadHeadCom(@Param("id") int id, @Param("path") String path);

    @Select("SELECT EXISTS(SELECT 1 FROM userheadportrait WHERE id = #{id} limit 1)")
    int headExistUser(@Param("id") int id);

    @Select("SELECT EXISTS(SELECT 1 FROM companyheadportrait WHERE id = #{id} limit 1)")
    int headExistCom(@Param("id") int id);

    @Select("SELECT path FROM userheadportrait WHERE id=#{id}")
    String selectPathByIdUser(@Param("id") int id);

    @Select("SELECT path FROM companyheadportrait WHERE id=#{id}")
    String selectPathByIdCom(@Param("id") int id);

    @Update("UPDATE userheadportrait SET path=#{path} WHERE id=#{id}")
    int updateHeadUser(@Param("id") int id, @Param("path") String path);

    @Update("UPDATE companyheadportrait SET path=#{path} WHERE id=#{id}")
    int updateHeadCom(@Param("id") int id, @Param("path") String path);

    @Select("SELECT type FROM job")
    String[] selectJobTypeNum();
}
