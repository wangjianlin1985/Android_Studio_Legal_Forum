package com.mobileclient.domain;

import java.io.Serializable;

public class ArticleClass implements Serializable {
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