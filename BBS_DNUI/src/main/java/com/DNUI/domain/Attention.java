package com.DNUI.domain;

import java.sql.Timestamp;

public class Attention {
    // 关注的人的user对象
    private User user;
    private Integer user_id;
    // 被关注的人的User对象
    private User PUser;
    private Timestamp create_time;
    private Integer puser_id;

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
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

    public User getPUser() {
        return PUser;
    }

    public void setPUser(User PUser) {
        this.PUser = PUser;
    }

    public Integer getPuser_id() {
        return puser_id;
    }

    public void setPuser_id(Integer puser_id) {
        this.puser_id = puser_id;
    }

    @Override
    public String toString() {
        return "Attention{" +
                "user=" + user +
                ", user_id=" + user_id +
                ", PUser=" + PUser +
                ", create_time=" + create_time +
                ", puser_id=" + puser_id +
                '}';
    }
}
