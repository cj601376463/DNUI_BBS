package com.DNUI.domain;

import java.sql.Timestamp;
import java.util.List;

public class TopicVO extends Topic {
    //帖子细节扩展的list属性，比如说一个主题对应多个回复
    private List<ArticleVO> oneToArticles;

    public List<ArticleVO> getOneToArticles() {
        return oneToArticles;
    }

    public void setOneToArticles(List<ArticleVO> oneToArticles) {
        this.oneToArticles = oneToArticles;
    }

}
