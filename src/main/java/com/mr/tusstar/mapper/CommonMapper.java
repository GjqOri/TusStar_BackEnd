package com.mr.tusstar.mapper;

import com.mr.tusstar.entity.Job;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
}
