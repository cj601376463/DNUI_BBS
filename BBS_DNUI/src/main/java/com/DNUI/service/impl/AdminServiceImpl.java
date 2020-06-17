package com.DNUI.service.impl;

import com.DNUI.dao.IAdminDao;
import com.DNUI.dao.IArticleDao;
import com.DNUI.dao.ITopicDao;
import com.DNUI.dao.IUserDao;
import com.DNUI.domain.Admin;
import com.DNUI.domain.TopicVO;
import com.DNUI.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("adminService")
public class AdminServiceImpl implements IAdminService {
    private final IAdminDao adminDao;
    private final ITopicDao topicDao;
    private final IUserDao userDao;
    private final IArticleDao articleDao;
    @Autowired
    public AdminServiceImpl(IAdminDao adminDao, ITopicDao topicDao, IUserDao userDao, IArticleDao articleDao){
        this.adminDao = adminDao;
        this.topicDao = topicDao;
        this.userDao = userDao;
        this.articleDao = articleDao;
    }
    @Override
    public Admin findAdmin(String account, String password) {
        Map<String ,String> admin = new HashMap<>();
        if(account!=null && password!=null) {
            admin.put("account", account);
            admin.put("password", password);
        }
        return adminDao.findAdmin(admin);
    }

    @Override
    public void updatePwd(Integer admin_id, String newPwd, String initPwd) {
        Map<String ,Object> admin = new HashMap<>();
        admin.put("admin_id", admin_id);
        admin.put("newPwd", newPwd);
        admin.put("initPwd", initPwd);
        adminDao.updatePwd(admin);
    }

    @Override
    public Admin findAdminByInitPwd(Integer admin_id, String initPwd) {
        Map<String ,Object> admin = new HashMap<>();
        admin.put("admin_id", admin_id);
        admin.put("initPwd", initPwd);
        return adminDao.findAdminByInitPwd(admin);
    }

    @Override
    public List<TopicVO> adminFindAllTopic(String type, String topicName) {
        List<TopicVO> topicVOS;
        Map<String, Object> limitAndSort = new HashMap<>();
        if (topicName != null && !topicName.equals("")){
            topicVOS = topicDao.findTopicByLikeName(topicName);
        }else {
            if (type != null) {
                limitAndSort.put("type", type);
                topicVOS = topicDao.findAllTopicByType(limitAndSort);
            } else {
                limitAndSort.put("type", null);
                topicVOS = topicDao.findAllTopicByType(limitAndSort);
            }
        }
        for (TopicVO topicVO : topicVOS){
            topicVO.setUser(userDao.findUserById(topicVO.getUser_id()));
            topicVO.setReply_number(articleDao.findTopicReply(topicVO.getTopic_id()));
            System.out.println(topicVO);
        }
        return topicVOS;
    }

    @Override
    public void realDeleteTopic(Integer[] ids) {
        List<Integer> topic_ids = new ArrayList<>();
        Collections.addAll(topic_ids, ids);
    }

}
