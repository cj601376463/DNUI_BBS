package com.DNUI.service.impl;

import com.DNUI.dao.IArticleDao;
import com.DNUI.dao.ICollectDao;
import com.DNUI.dao.ITopicDao;
import com.DNUI.dao.IUserDao;
import com.DNUI.domain.ArticleVO;
import com.DNUI.domain.TopicVO;
import com.DNUI.domain.Type;
import com.DNUI.service.ITopicService;
import com.DNUI.service.IUserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("topicService")
public class TopicServiceImpl implements ITopicService  {
    private final ITopicDao topicDao;
    private final IUserDao userDao;
    private final IArticleDao articleDao;
    private final IUserService userService;
    private final ICollectDao collectDao;
    @Autowired
    public TopicServiceImpl(ITopicDao topicDao, IUserDao userDao, IArticleDao articleDao, IUserService userService, ICollectDao collectDao){
        this.topicDao = topicDao;
        this.userDao = userDao;
        this.articleDao = articleDao;
        this.userService = userService;
        this.collectDao = collectDao;
    }

    @Override
    public List<TopicVO> findAllTopic(int page, int size, String type, String topicName) {
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
            topicVO.setReply_number(this.reply_num(topicVO.getTopic_id()));
//            System.out.println(topicVO);
        }
        PageHelper.startPage(page,size);//提醒要显示哪一页的数据和要显示多少条数据
        return topicVOS;
    }

    //获得发帖数
    @Override
    public int findTopicTotal() {
//        System.out.println(topicDao.findTopicTotal());
        return topicDao.findTopicTotal();
    }

    @Override
    public TopicVO findTopicById(Integer topic_id) {
        TopicVO topicVO = topicDao.findTopicVOById(topic_id);
        topicVO.setUser(userService.findUserById(topicVO.getUser_id()));
//        System.out.println(topicVO);
        return topicVO;
    }

    @Override
    public List<ArticleVO> findArticlesByTopicId(Integer topic_id) {
        List<ArticleVO> articleVOS = articleDao.findArticlesByTopicId(topic_id);
        for (int i = 1; i <= articleVOS.size(); i++){
            //获得一条回复
            ArticleVO articleVO =articleVOS.get(i-1);
            //设置楼层
            articleVO.setLevel(i);
            articleVO.setUser(userService.findUserById(articleVO.getUser_id()));
            articleVO.setPuser(userService.findUserById(articleVO.getPuser_id()));
            articleVO.setTopic(topicDao.findTopicVOById(articleVO.getTopic_id()));
            //该条回复是否引用了别人的回复，如果是，就将那条回复注入该回复
            if(articleVO.getArefid()!= 0){
                //得到被引用回复的回复id
                int areFid = articleVO.getArefid();
                //搜出该条回复的内容
                articleVO.setRefArticleVO(articleDao.findArticlesByArticleId(areFid));
                articleVO.getRefArticleVO().setUser(userDao.findUserById(articleVO.getRefArticleVO().getUser_id()));
//                    }
//                }
            }
        }
        return articleVOS;
    }

    @Override
    public List<TopicVO> findTopicByUserId(Integer user_id) {
        List<TopicVO> topicVOS = topicDao.findTopicByUserId(user_id);
        for (TopicVO topicVO : topicVOS){
            topicVO.setUser(userService.findUserById(user_id));
            topicVO.setReply_number(articleDao.findTopicReply(topicVO.getTopic_id()));
        }
        return topicVOS;
    }

    @Override
    public void deleteTopic(Integer topic_id,Integer user_id) {
        topicDao.deleteTopic(topic_id);
        articleDao.deleteArticleByTopicId(topic_id);
        collectDao.cancelCollect(user_id,topic_id);
    }

    @Override
    public List<TopicVO> adminFindAllTopic() {
        List<TopicVO> topicVOS = topicDao.findAllTopic();
        for (TopicVO topicVO : topicVOS){
            topicVO.setUser(userService.findUserById(topicVO.getUser_id()));
            topicVO.setReply_number(articleDao.findTopicReply(topicVO.getTopic_id()));
        }
        return topicVOS;
    }

    @Override
    public List<TopicVO> findDeletedTopic(String type, String orderBy) {
        Map<String, Object> typeAndOrderBy = new HashMap<>();
        //如果参数为空，直接返回空值
        if (type == null || type.isEmpty() || orderBy == null || orderBy.isEmpty()) {
            type = "time";
            orderBy = "DESC";
        }
        typeAndOrderBy.put("type", type);
        typeAndOrderBy.put("orderBy", orderBy);
        List<TopicVO> topicVOS = topicDao.findDeletedTopic(typeAndOrderBy);
        for (TopicVO topicVO : topicVOS){
            topicVO.setUser(userService.findUserById(topicVO.getUser_id()));
            topicVO.setReply_number(articleDao.findTopicReply(topicVO.getTopic_id()));
        }
        return topicVOS;
    }

    @Override
    public List<Type> findAllType() {
        return topicDao.findAllType();
    }

    @Override
    public void updateTopicsStatus(String type, Integer status, Integer[] ids) {
        Map<String, Object> typeAndStatusAndIds = new HashMap<>();
        typeAndStatusAndIds.put("status", status);
        typeAndStatusAndIds.put("type", type);
        List<Integer> topic_ids = new ArrayList<>();
        Collections.addAll(topic_ids, ids);
        System.out.println("Type , status , ids " + type + "  , " + status + " " + ids[0]);
        typeAndStatusAndIds.put("topic_ids", topic_ids);
        topicDao.updateTopicsStatus(typeAndStatusAndIds);
    }

    @Override
    public List<TopicVO> findDeleteTopicByLikeName(String TopicName) {
        if (TopicName != null && !TopicName.isEmpty()){
            return topicDao.findDeleteTopicByLikeName(TopicName);
        }
        return null;
    }

    @Override
    public Integer reply_num(Integer topic_id) {
        return articleDao.findTopicReply(topic_id);
    }

}
