package com.DNUI.dao;

import com.DNUI.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户登陆dao接口
 */
@Repository
public interface IUserDao {
    //根据user_id查找User
    @Select("Select * from user where user_id = #{user_id}")
    User findUserById(Integer user_id);
    //更新一个用户信息
    @Update("<script>"+"UPDATE user set"+
            "<if test=\"username!=null and username.trim()!=''\">"+
            "username = #{username},"+
            "</if>"+
            "<if test=\"sex!=null and sex.trim()!=''\">"+
            "sex = #{sex},"+
            "</if>"+
            "<if test=\"sdept!=null and sdept.trim()!=''\">"+
            "sdept = #{sdept},"+
            "</if>"+
            "<if test=\"clazz!=null and clazz.trim()!=''\">"+
            "clazz = #{clazz},"+
            "</if>"+
            "<if test=\"phone!=null and phone.trim()!=''\">"+
            "phone = #{phone},"+
            "</if>"+
            "<if test=\"sign!=null and sign.trim()!=''\">"+
            "sign = #{sign},"+
            "</if>"+
            "<if test=\"imgUrl!=null and imgUrl.trim()!=''\">"+
            "imgUrl = #{imgUrl}"+
            "</if>"+"WHERE user_id = #{user_id}"+"</script>"
    )
    void updateUserInfo(Map<String, Object> userMap);
    //计算用户关注数
    @Select("Select count(*) from attention where user_id = #{user_id}")
    Integer attentionNumber(Integer user_id);
    //计算用户收藏数
    @Select("Select count(*) from collect where user_id = #{user_id}")
    Integer collectNumber(Integer user_id);
    //用户更新用户密码
    @Update("UPDATE user SET password = #{password} WHERE user_id = #{user_id}")
    void updatePwdByUser_id(Map<String, Object> pwdAndUserId);
    //检查原始密码是否正确
    @Select("SELECT * FROM user WHERE password = #{password} AND user_id = #{user_id}")
    User checkInitPwd(Map<String, Object> pwdAndUserId);
    //查找所有用户
    @Select("Select * from user")
    List<User> findAllUser();
    //更新用户状态
    @Update("<script>"+"UPDATE user set"+
            "<if test=\"status==1\">"+
            "status = 1"+
            "</if>"+
            "<if test=\"status==0\">"+
            "status = 0"+
            "</if>"+
            "WHERE user_id in"+
            "<foreach collection=\"user_ids\" open=\"(\" separator=\",\" item=\"user_id\" close=\")\">"+
            "#{user_id}"+
            "</foreach>"+
            "</script>")
    void updateUsersStatus(Map<String, Object> user_idsAndStatus);
    //查找被禁言的用户
    @Select("Select * from user where status = 0")
    List<User> findUsersByShut();
    //模糊查询用户
    @Select("SELECT * FROM user WHERE username LIKE CONCAT('%',#{username},'%')")
    List<User> findUsersByLikeName(String username);
    //模糊查询被禁言用户
    @Select("SELECT * FROM user WHERE username LIKE CONCAT('%',#{username},'%') And status = 0")
    List<User> findShutUsersByLikeName(String username);
    //获取用户的发帖数
    @Select("SELECT count(*) FROM topic where user_id = #{user_id}")
    public int findCountUserPost(Integer user_id);
    //测试MD5登陆
    @Select("select * from user WHERE sno = #{login_name} AND password= #{password}")
    public User login(Map<String,String> userInfo);
    //测试MD5注册
    @Insert("insert into user (sno,password,username,email,register_date) values (#{login_name}, #{password}, #{user_name}, #{email},now())")
    public boolean register(Map<String,String> userInfo);
    //验证用户是否存在
    @Select("select count(*) from user where sno = #{login_name}")
    public int findUserByLgName(String login_name);
    //验证用户邮箱是否存在
    @Select("select count(*) from user where email like #{email}")
    public int findEmailByLgName(String email);
    //获得对于用户的回帖数
    @Select("Select count(*) from article where user_id = #{user_id}" )
    public int findTopicReplyByUser_id(Integer user_id);
    //根据传进来的参数更新用户是否允许别人访问自己的空间
    @Update("UPDATE user SET islock = #{isLock} WHERE user_id = #{user_id}")
    public void updateIsLockById(Map<String, Integer> isLockAndUserId);
}
