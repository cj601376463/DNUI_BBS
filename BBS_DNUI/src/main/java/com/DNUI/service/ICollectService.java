package com.DNUI.service;

import com.DNUI.domain.Collect;

import java.util.List;

public interface ICollectService {
    //取消收藏
    void cancelCollect(Integer user_id, Integer topic_id);
    //收藏页面
    List<Collect> findCollectsByUserId(Integer user_id);
}
