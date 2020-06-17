package com.DNUI.service.impl;

import com.DNUI.dao.ICollectDao;
import com.DNUI.dao.ITopicDao;
import com.DNUI.dao.IUserDao;
import com.DNUI.domain.Collect;
import com.DNUI.service.ICollectService;
import com.DNUI.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("collectService")
public class CollectServiceImpl implements ICollectService {

    private final ICollectDao collectDao;
    private final IUserDao userDao;
    private final ITopicDao topicDao;
    private final IUserService userService;
    @Autowired
    public CollectServiceImpl(ICollectDao collectDao, IUserDao userDao, ITopicDao topicDao, IUserService userService) {
        this.collectDao = collectDao;
        this.userDao = userDao;
        this.topicDao = topicDao;
        this.userService = userService;
    }

    @Override
    public void cancelCollect(Integer user_id, Integer topic_id) {
        collectDao.cancelCollect(user_id,topic_id);
    }

    @Override
    public List<Collect> findCollectsByUserId(Integer user_id) {
        List<Collect> collects = collectDao.findCollectsByUserId(user_id);
        System.out.println("0:"+collects);
        for (Collect collect : collects){
            collect.setUser(userDao.findUserById(user_id));
//            System.out.println("1:"+userDao.findUserById(user_id));
//            System.out.println("11:"+collect.getUser());
            collect.setTopicVO(topicDao.findTopicVOById(collect.getTopic_id()));
//            System.out.println("2:"+topicDao.findTopicVOById(collect.getTopic_id()));
            collect.getTopicVO().setUser(userService.findUserById(collect.getTopicVO().getUser_id()));
//            System.out.println("3:"+userService.findUserById(collect.getTopicVO().getUser_id()));
        }
        System.out.println(collects);
        return collects;
    }
}
