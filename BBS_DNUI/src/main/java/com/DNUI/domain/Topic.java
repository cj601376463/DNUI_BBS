package com.DNUI.domain;

import java.io.Serializable;
import java.sql.Timestamp;


public class Topic implements Serializable {
    private Integer topic_id;
    private String topic;
    private String content;
    // 该条帖子回复数
    private Integer reply_number;
    // 该条帖子是谁的发的，本来是userid，这里直接写成User方便后面使用
    private User user;
    private Integer user_id;
    private Integer isindex;
    // 该条帖子的状态，0表示删除了（假删除），默认为1
    private Integer flag;
    // 该帖帖子所发的时间(时间戳)
    private Timestamp create_time;
    //帖子的类别
    private String type;
    private Integer view;
    private Integer tlike;

    public Integer getTlike() {
        return tlike;
    }

    public void setTlike(Integer tlike) {
        this.tlike = tlike;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Integer topic_id) {
        this.topic_id = topic_id;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReply_number() {
        return reply_number;
    }

    public void setReply_number(Integer reply_number) {
        this.reply_number = reply_number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getIsindex() {
        return isindex;
    }

    public void setIsindex(Integer isindex) {
        this.isindex = isindex;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topic_id=" + topic_id +
                ", topic='" + topic + '\'' +
                ", content='" + content + '\'' +
                ", reply_number=" + reply_number +
                ", user=" + user +
                ", user_id=" + user_id +
                ", isindex=" + isindex +
                ", flag=" + flag +
                ", create_time=" + create_time +
                ", type='" + type + '\'' +
                ", view=" + view +
                ", tlike=" + tlike +
                '}';
    }
}
