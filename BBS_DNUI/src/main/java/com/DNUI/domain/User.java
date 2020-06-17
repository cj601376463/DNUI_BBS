package com.DNUI.domain;

import java.io.Serializable;
import java.sql.Date;


public class User implements Serializable {
    private String username;
    private String password;
    private Integer user_id;
    private String sno;
    private String sex;
    private String sdept;
    private String clazz;
    private Integer topic_num;
    private String imgUrl;
    private String sign;
    private String email;
    private String phone;
    // 用户控件是否被锁定
    private Integer isLock;
    // 用户状态，是否可以发言等等
    private Integer status;
    //用户注册时间，用户回帖数
    private Date register_date;
    private Integer reply_num;
    //有多少条关注
    private Integer attention_num;
    //有多少个收藏
    private Integer collect_num;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSdept() {
        return sdept;
    }

    public void setSdept(String sdept) {
        this.sdept = sdept;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Integer getTopic_num() {
        return topic_num;
    }

    public void setTopic_num(Integer topic_num) {
        this.topic_num = topic_num;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

    public Integer getReply_num() {
        return reply_num;
    }

    public void setReply_num(Integer reply_num) {
        this.reply_num = reply_num;
    }

    public Integer getAttention_num() {
        return attention_num;
    }

    public void setAttention_num(Integer attention_num) {
        this.attention_num = attention_num;
    }

    public Integer getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(Integer collect_num) {
        this.collect_num = collect_num;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", user_id=" + user_id +
                ", sno='" + sno + '\'' +
                ", sex='" + sex + '\'' +
                ", sdept='" + sdept + '\'' +
                ", clazz='" + clazz + '\'' +
                ", article_num=" + topic_num +
                ", imgUrl='" + imgUrl + '\'' +
                ", sign='" + sign + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", isLock=" + isLock +
                ", status=" + status +
                ", register_date=" + register_date +
                ", reply_num=" + reply_num +
                ", attention_num=" + attention_num +
                ", collect_num=" + collect_num +
                '}';
    }
}
