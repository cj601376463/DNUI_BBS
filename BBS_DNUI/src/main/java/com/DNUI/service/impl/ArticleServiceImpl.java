package com.DNUI.service.impl;

import com.DNUI.dao.IArticleDao;
import com.DNUI.dao.ITopicDao;
import com.DNUI.dao.IUserDao;
import com.DNUI.domain.Article;
import com.DNUI.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service(value = "articleService")
public class ArticleServiceImpl implements IArticleService {
    private final IUserDao userDao;
    private final ITopicDao topicDao;
    private final IArticleDao articleDao;
    @Autowired
    public ArticleServiceImpl(IUserDao userDao, ITopicDao topicDao, IArticleDao articleDao){
        this.userDao = userDao;
        this.topicDao = topicDao;
        this.articleDao = articleDao;
    }
    @Override
    @Transactional(rollbackFor = Exception.class) //有异常发生时回滚
    public void replyArticle(Article article,String content) {
        //更新回复数和插入一条回复
        //更新用户回帖数
        Map<String,Object> articleMap = new HashMap<>();
        articleMap.put("content",content);
        articleMap.put("topic_id",article.getTopic_id());
        articleMap.put("user_id",article.getUser_id());
        articleMap.put("puser_id",article.getPuser_id());
        articleMap.put("arefid",article.getArefid());
        System.out.println("ServiceArticle:" + article);
        //插入一条回复
        articleDao.replyArticle(articleMap);
    }

    @Override
    public void delArticle(Integer topicID) {
        articleDao.deleteArticleByTopicId(topicID);
    }
}
