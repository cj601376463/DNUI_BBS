package com.DNUI.domain;

public class ArticleVO extends Article {
    //这条回复是几楼的
    private int level;
    //被引用的帖子
    private ArticleVO refArticleVO;
    //这条回复的那条帖子的名字
    private String topic;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArticleVO getRefArticleVO() {
        return refArticleVO;
    }

    public void setRefArticleVO(ArticleVO refArticleVO) {
        this.refArticleVO = refArticleVO;
    }

}
