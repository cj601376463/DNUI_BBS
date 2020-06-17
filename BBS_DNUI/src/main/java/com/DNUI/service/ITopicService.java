package com.DNUI.service;

import com.DNUI.domain.ArticleVO;
import com.DNUI.domain.TopicVO;
import com.DNUI.domain.Type;

import java.util.List;

public interface ITopicService {
    //查询所有，并分页
    public List<TopicVO> findAllTopic(int page, int size, String type, String topicName);
    //得到所有的发帖数
    public int findTopicTotal();
    //根据id查找帖子
    public TopicVO findTopicById(Integer topic_id);
    //根据帖子id查找帖子的回复
    public List<ArticleVO> findArticlesByTopicId(Integer topic_id);
    //根据用户id查找用户所有的帖子
    public List<TopicVO> findTopicByUserId(Integer user_id);
    //根据传进来的更新类型，要更新的状态，以及帖子的ids来决定怎么更新
    public void deleteTopic(Integer topic_id,Integer user_id);
    //管理员查找所有帖子
    public List<TopicVO> adminFindAllTopic();
    //根据传进来的参数，选择是根据什么类型的排序，怎么样排序
    public List<TopicVO> findDeletedTopic(String type, String orderBy);
    //查找帖子类型
    public List<Type> findAllType();
    //根据传进来的更新类型，要更新的状态，以及帖子的ids来决定怎么更新
    public void updateTopicsStatus(String type, Integer status, Integer[] ids);
    //模糊查询假删除的帖子
    public List<TopicVO> findDeleteTopicByLikeName(String TopicName);
    //获取帖子的回复数
    public Integer reply_num(Integer topic_id);

}
