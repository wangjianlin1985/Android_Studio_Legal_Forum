package com.chengxusheji.domain;

import java.sql.Timestamp;
public class News {
    /*����id*/
    private int newsId;
    public int getNewsId() {
        return newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    /*����*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*��������*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*�����*/
    private int viewNum;
    public int getViewNum() {
        return viewNum;
    }
    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    /*����ʱ��*/
    private String publishDate;
    public String getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

}