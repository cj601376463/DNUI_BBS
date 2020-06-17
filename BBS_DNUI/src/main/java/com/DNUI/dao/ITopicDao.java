package com.DNUI.dao;

import com.DNUI.domain.Topic;
import com.DNUI.domain.TopicVO;
import com.DNUI.domain.Type;
import com.DNUI.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ITopicDao {
    //查询所有帖子
    @Select("Select * from topic where flag = 1")
    public List<TopicVO> findAllTopic();
    @Select("<script>"+"SELECT t.*, u.username from topic t, user u where t.user_id = u.user_id and t.flag = 1" +
            "<choose>"+"<when test=\"type != null\">"+"and type = #{type}"+"</when>"+"</choose>"+" ORDER BY t.isindex DESC"+"</script>")
    public List<TopicVO> findAllTopicByType(Map<String, Object> limitAndSort);
    //获得所有发帖数
    @Select("SELECT count(*) FROM topic")
    public int findTopicTotal();
    //根据ID获取帖子详情
    @Select("Select * from topic where topic_id = #{topic_id}")
    public TopicVO findTopicVOById(Integer topic_id);
    //插入一条帖子
    @Update("INSERT INTO topic (user_id, topic, create_time, content, type) VALUES (#{user_id}, #{topic}, now(), #{content}, #{typeName})")
    public void postTopic(Map<String, Object> topicMap);
    //根据用户id查找用户所有的帖子（只显示状态正常的帖子）
    @Select("Select * from topic where user_id = #{user_id} and flag = 1 ORDER BY isindex DESC, create_time DESC")
    public List<TopicVO> findTopicByUserId(Integer user_id);
    //根据帖子id删除帖子
    @Delete("Delete from topic where topic_id = #{topic_id}")
    public void deleteTopic(Integer topic_id);
    //查找被删除的帖子（假删除）
    @Select("<script>"+"Select * from topic where flag = 0"+
            "<choose>"+
            "<when test=\"type=='time'\">"+
            "<if test=\"orderBy=='ASC'\">"+ "ORDER BY create_time ASC"+"</if>"+
            "<if test=\"orderBy=='DESC'\">"+"ORDER BY create_time DESC"+"</if>"+"</when>"+
            "<otherwise>"+" order by reply_number #{orderBy}"+
            "<if test=\"orderBy=='ASC'\">" + "ORDER BY reply_number ASC" + "</if>"+
            "<if test=\"orderBy=='DESC'\">" + "ORDER BY reply_number DESC" + "</if>"+"</otherwise>"+"</choose>"+"</script>")
    List<TopicVO> findDeletedTopic(Map<String, Object> typeAndOrderBy);
    //模糊查找
    @Select("SELECT t.*, u.username from topic t, user u where t.user_id = u.user_id and t.flag = 1 AND t.topic LIKE CONCAT('%',#{topicName},'%') ORDER BY t.isindex DESC")
    List<TopicVO> findTopicByLikeName(String topicName);
    //获取文章种类
    @Select("Select * from type")
    List<Type> findAllType();
    //更新帖子的状态，是否置顶，是否删除
    @Update("<script>"+"UPDATE topic set"+
            "<choose>"+"<when test=\"type=='flag'\">"+"flag = #{status}"+"</when>"+
            "<otherwise>"+"isindex = #{status}"+"</otherwise>"+"</choose>"+
            "<where>"+"topic_id in"+
                    "<foreach collection=\"topic_ids\" item=\"topic_id\" open=\"(\" close=\")\" separator=\",\">"+
                        "#{topic_id}"+
                    "</foreach>"+"</where>"+"</script>")
    void updateTopicsStatus(Map<String, Object> typeAndStatusAndIds);
    //模糊查询被禁言用户
    @Select("SELECT * FROM topic WHERE topic LIKE CONCAT('%',#{topicName},'%') And flag = 0")
    List<TopicVO> findDeleteTopicByLikeName(String topicName);
    //查找某一类型帖子的数量
    @Select("Select count(*) from topic where type like CONCAT('%',#{typeName},'%')")
    int findTopicNumOfType(String typeName);
}
