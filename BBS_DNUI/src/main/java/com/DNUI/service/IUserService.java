package com.DNUI.service;

import com.DNUI.domain.User;

import java.util.List;

public interface IUserService {

    //检验用户登陆
//    User checkLogin(String sno,String password);
    //用户发帖
    public void postTopic(Integer user_id, String topic, String content, String typeName);
    //根据ID查找用户
    public User findUserById(Integer user_id);
    //更新一个用户信息
    public void updateUserInfo(User user);
    //用户关注数和收藏数
    public User attentionAndCollectNumber(Integer user_id);
    //用户更新密码
    public void updatePwdByUser_id(String password, Integer user_id);
    //ajax请求用户初始密码是否正确
    public User checkInitPwd(String initPwd, Integer user_id);
    //查找所有用户
    public List<User> findAllUser();
    //批量更新用户状态
    public void updateUsersStatus(Integer status, Integer[] user_ids);
    //查找被禁言的用户
    public List<User> findUsersByShut();
    //模糊查找用户
    public List<User> findUsersByLikeName(String username);
    //模糊查找被禁言的用户
    public List<User> findShutUsersByLikeName(String username);
    //测试登陆
    public User login(String login_name, String password);
    //测试注册
    public boolean register(String login_name, String password, String user_name,String email);
    //查询是否有重复学号
    public Integer findByIfUserName(String login_name);
    //查询是否有重复邮箱
    public Integer findEmailByLgName(String email);
    //根据传进来的参数更新用户是否允许别人访问自己的空间
    public void updateIsLockById(Integer user_id, Integer isLock);
}
