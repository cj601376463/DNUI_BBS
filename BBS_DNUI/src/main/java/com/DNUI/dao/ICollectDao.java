package com.DNUI.dao;

import com.DNUI.domain.Collect;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICollectDao {
    //查找一个收藏记录
    @Select("SELECT  * FROM collect WHERE user_id = #{user_id} AND topic_id = #{topic_id}")
    Collect findOneCollect(@Param("user_id") Integer user_id, @Param("topic_id") Integer topic_id);
    //添加一条收藏记录
    @Update("INSERT INTO collect(user_id, topic_id, create_time) VALUES (#{user_id}, #{topic_id}, now())")
    void collectTopic(@Param("user_id") Integer user_id, @Param("topic_id") Integer topic_id);
    //根据ID查询用户有多少个收藏
    @Select("SELECT count(*) FROM collect WHERE  user_id = #{user_id}")
    int countCollect_num(Integer user_id);
    //取消收藏
    @Delete("DELETE FROM collect WHERE user_id = #{user_id} AND topic_id = #{topic_id}")
    void cancelCollect(@Param("user_id") Integer user_id, @Param("topic_id") Integer topic_id);
    //根据用户ID查找收藏记录
    @Select("SELECT * FROM collect WHERE user_id = #{user_id} ORDER BY create_time DESC")
    List<Collect> findCollectsByUserId(Integer user_id);
}
