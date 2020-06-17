package com.DNUI.dao;

import com.DNUI.domain.Article;
import com.DNUI.domain.ArticleVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IArticleDao {
//    根据传进来一个主题id号，得到所有的回复，并且按时间升序排列
    @Select("SELECT *  FROM article WHERE topic_id = #{topic_id} ORDER BY reply_time ASC")
    public List<ArticleVO> findArticlesByTopicId(Integer topic_id);
    //根据回复的article_id搜索回复
    @Select("Select * from article where article_id = #{article_id}")
    public ArticleVO findArticlesByArticleId(Integer article_id);
    //插入一条回帖
    @Update("INSERT INTO article (topic_id, content, reply_time, user_id, puser_id, arefid) " +
            "VALUES (#{topic_id}, #{content}, now(), #{user_id}, #{puser_id}, #{arefid})")
    public void replyArticle(Map<String, Object> articleMap);
    //根据帖子id删除回复
    @Delete("Delete from article where topic_id = #{topic_id}")
    public void deleteArticleByTopicId(Integer topic_id);
    //获得相应帖子的回复数
    @Select("SELECT count(*) FROM article where topic_id = #{topic_id}")
    public int findTopicReply(Integer topic_id);
}
