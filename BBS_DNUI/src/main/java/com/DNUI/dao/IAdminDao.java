package com.DNUI.dao;

import com.DNUI.domain.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface IAdminDao {
    //查找一个管理员
    @Select("SELECT * FROM admin where account = #{account} AND password = #{password}")
    Admin findAdmin(Map<String, String> admin);
    //更新管理员密码
    @Select("UPDATE admin SET password = #{newPwd} WHERE admin_id = #{admin_id} AND password = #{initPwd}")
    void updatePwd(Map<String, Object> admin);
    //验证初始密码
    @Select("SELECT * from admin where password = #{initPwd} and admin_id = #{admin_id}")
    Admin findAdminByInitPwd(Map<String, Object> admin);
    //根据帖子id删除帖子
    @Delete("Delete from topic where topic_id = #{topic_id}")
    public void deleteTopic(Integer topic_id);
}
