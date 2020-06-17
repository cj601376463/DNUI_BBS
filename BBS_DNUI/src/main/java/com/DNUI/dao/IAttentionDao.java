package com.DNUI.dao;

import com.DNUI.domain.Attention;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAttentionDao {
    //查找用户关注的用户
    @Select("SELECT * FROM attention WHERE user_id = #{user_id} ORDER BY create_time DESC")
    public List<Attention> findAttentionByUserId(Integer user_id);
    //查找用户关注的数量
    @Select("SELECT count(*) FROM attention WHERE  user_id = #{user_id}")
    int countAttention_num(Integer user_id);
    //查找相关的关注消息
    @Select("SELECT  * FROM attention WHERE user_id = #{user_id} AND puser_id = #{puser_id}")
    public Attention findOneAttention(@Param("user_id") Integer user_id, @Param("puser_id") Integer puser_id);
    //关注用户
    @Insert("INSERT INTO attention VALUES (#{user_id}, #{puser_id}, now())")
    public void attentionUser(@Param("user_id") Integer user_id, @Param("puser_id") Integer puser_id);
    //取消关注
    @Delete("DELETE FROM attention WHERE user_id = #{user_id} AND puser_id = #{puser_id}")
    public void cancelAttention(@Param("user_id") Integer user_id, @Param("puser_id") Integer puser_id);
}
