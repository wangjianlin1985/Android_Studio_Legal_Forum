package com.chengxusheji.domain;

import java.sql.Timestamp;
public class ArticleClass {
    /*���·���id*/
    private int articleClassId;
    public int getArticleClassId() {
        return articleClassId;
    }
    public void setArticleClassId(int articleClassId) {
        this.articleClassId = articleClassId;
    }

    /*���·�������*/
    private String articleClassName;
    public String getArticleClassName() {
        return articleClassName;
    }
    public void setArticleClassName(String articleClassName) {
        this.articleClassName = articleClassName;
    }

}