package com.DNUI.service;

import com.DNUI.domain.Attention;

import java.util.List;

public interface IAttentionService {
    //查找用户关注的用户
    public List<Attention> findAttentionByUserId(Integer user_id);
    //查找关注信息
    public Attention findOneAttention(Integer user_id, Integer puser_id);
    //关注用户
    public void attentionUser(Integer user_id, Integer puser_id);
    //取消收藏
    public void cancelAttention(Integer user_id, Integer puser_id);
}
