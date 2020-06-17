package com.DNUI.service.impl;

import com.DNUI.dao.IAttentionDao;
import com.DNUI.dao.ICollectDao;
import com.DNUI.dao.ITopicDao;
import com.DNUI.dao.IUserDao;
import com.DNUI.domain.User;
import com.DNUI.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userService")
public class UserServiceImpl implements IUserService {

    private final IUserDao userDao;
    private final ITopicDao topicDao;
    private final ICollectDao collectDao;
    private final IAttentionDao attentionDao;

    public UserServiceImpl(IUserDao loginDao, ITopicDao topicDao, ICollectDao collectDao, IAttentionDao attentionDao) {
        this.userDao = loginDao;
        this.topicDao = topicDao;
        this.collectDao = collectDao;
        this.attentionDao = attentionDao;
    }

    //根据传进来的信息，插入一条帖子
    @Override
    public void postTopic(Integer user_id, String topic, String content, String typeName) {
        System.out.println("这是UserService中的user_id:" + user_id);
        //用户插入帖子的map
        Map<String, Object> topicMap = new HashMap<>();
        topicMap.put("user_id", user_id);
        topicMap.put("topic", topic);
        topicMap.put("content", content);
        topicMap.put("typeName",typeName);
        //插入一条帖子
        topicDao.postTopic(topicMap);
    }

    @Override
    public User findUserById(Integer user_id) {
        User user = userDao.findUserById(user_id);
        int collect_num = collectDao.countCollect_num(user_id);
        int attention_num = attentionDao.countAttention_num(user_id);
        int topic_num = userDao.findCountUserPost(user_id);
        user.setCollect_num(collect_num);
        user.setAttention_num(attention_num);
        user.setTopic_num(topic_num);
        user.setReply_num(userDao.findTopicReplyByUser_id(user_id));
        return user;
    }
    //更新一个用户信息
    @Override
    public void updateUserInfo(User user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("user_id", user.getUser_id());
        userMap.put("username", user.getUsername());
        userMap.put("sex", user.getSex());
        userMap.put("sdept", user.getSdept());
        userMap.put("clazz", user.getClazz());
        userMap.put("phone", user.getPhone());
        userMap.put("sign", user.getSign());
        userMap.put("imgUrl", user.getImgUrl());
        System.out.println(userMap);
        userDao.updateUserInfo(userMap);
    }

    @Override
    public User attentionAndCollectNumber(Integer user_id) {
        User user = userDao.findUserById(user_id);
        user.setTopic_num(userDao.findCountUserPost(user_id));
        user.setCollect_num(userDao.collectNumber(user_id));
        user.setAttention_num(userDao.attentionNumber(user_id));
        user.setReply_num(userDao.findTopicReplyByUser_id(user_id));
        return user;
    }

    @Override
    public void updatePwdByUser_id(String password, Integer user_id) {
        Map<String,Object> pwdAndUserId = new HashMap<>();
        pwdAndUserId.put("password",password);
        pwdAndUserId.put("user_id",user_id);
        userDao.updatePwdByUser_id(pwdAndUserId);
    }

    @Override
    public User checkInitPwd(String initPwd, Integer user_id) {
        Map<String, Object> pwdAndUserId = new HashMap<>();
        pwdAndUserId.put("password", initPwd);
        pwdAndUserId.put("user_id", user_id);
        return userDao.checkInitPwd(pwdAndUserId);
    }

    @Override
    public List<User> findAllUser() {
        List<User> users = userDao.findAllUser();
        for (User user : users){
            user.setAttention_num(userDao.attentionNumber(user.getUser_id()));
            user.setCollect_num(userDao.collectNumber(user.getUser_id()));
            user.setTopic_num(userDao.findCountUserPost(user.getUser_id()));
        }
        return users;
    }

    @Override
    public void updateUsersStatus(Integer status, Integer[] user_ids) {
        Map<String, Object> user_idsAndStatus = new HashMap<>();
        if (status == 1 || status == 0) {
            user_idsAndStatus.put("status", status);
        }
        List<Integer> userIds = new ArrayList<>();
        Collections.addAll(userIds,user_ids);
        user_idsAndStatus.put("user_ids", userIds);
        userDao.updateUsersStatus(user_idsAndStatus);
    }

    @Override
    public List<User> findUsersByShut() {
        List<User> users = userDao.findUsersByShut();
        for (User user : users){
            user.setAttention_num(userDao.attentionNumber(user.getUser_id()));
            user.setCollect_num(userDao.collectNumber(user.getUser_id()));
            user.setTopic_num(userDao.findCountUserPost(user.getUser_id()));
        }
        return users;
    }

    @Override
    public List<User> findUsersByLikeName(String username) {
        if (username != null && !username.isEmpty()){
            return userDao.findUsersByLikeName(username);
        }
        return null;
    }

    @Override
    public List<User> findShutUsersByLikeName(String username) {
        if (username != null && !username.isEmpty()){
            return userDao.findShutUsersByLikeName(username);
        }
        return null;
    }

    @Override
    public User login(String login_name, String password) {
        Map<String,String> userInfo = new HashMap<>();
        userInfo.put("login_name",login_name);
        userInfo.put("password",password);
        System.out.println("User:"+ userDao.login(userInfo));
        return userDao.login(userInfo);
    }

    @Override
    public boolean register(String login_name, String password, String user_name,String email) {
        Map<String,String> userInfo = new HashMap<>();
        userInfo.put("login_name",login_name);
        userInfo.put("password",password);
        userInfo.put("user_name",user_name);
        userInfo.put("email",email);
        return userDao.register(userInfo);
    }

    @Override
    public Integer findByIfUserName(String login_name) {
        return userDao.findUserByLgName(login_name);
    }

    @Override
    public Integer findEmailByLgName(String email) {
        return userDao.findEmailByLgName(email);
    }
    //根据传进来的参数更新用户是否允许别人访问自己的空间
    @Override
    public void updateIsLockById(Integer user_id, Integer isLock) {
        Map<String, Integer> isLockAndUserId = new HashMap<>();
        isLockAndUserId.put("user_id", user_id);
        isLockAndUserId.put("isLock", isLock);
        userDao.updateIsLockById(isLockAndUserId);
    }
}
