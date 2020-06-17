package com.DNUI.service;

import com.DNUI.domain.Article;

public interface IArticleService {
    //评论
    public void replyArticle(Article article,String content);
    //删除评论
    public void delArticle(Integer topicID);
}
