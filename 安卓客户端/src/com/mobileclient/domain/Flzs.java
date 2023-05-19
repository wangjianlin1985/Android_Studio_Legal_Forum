package com.mobileclient.domain;

import java.io.Serializable;

public class Flzs implements Serializable {
    /*记录id*/
    private int zsId;
    public int getZsId() {
        return zsId;
    }
    public void setZsId(int zsId) {
        this.zsId = zsId;
    }

    /*知识标题*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*知识图片*/
    private String zsPhoto;
    public String getZsPhoto() {
        return zsPhoto;
    }
    public void setZsPhoto(String zsPhoto) {
        this.zsPhoto = zsPhoto;
    }

    /*知识简介*/
    private String zsDesc;
    public String getZsDesc() {
        return zsDesc;
    }
    public void setZsDesc(String zsDesc) {
        this.zsDesc = zsDesc;
    }

    /*作者*/
    private String author;
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    /*出版社*/
    private String publish;
    public String getPublish() {
        return publish;
    }
    public void setPublish(String publish) {
        this.publish = publish;
    }

    /*出版日期*/
    private java.sql.Timestamp publishDate;
    public java.sql.Timestamp getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(java.sql.Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    /*阅读量*/
    private int viewNum;
    public int getViewNum() {
        return viewNum;
    }
    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    /*知识文件*/
    private String zsFile;
    public String getZsFile() {
        return zsFile;
    }
    public void setZsFile(String zsFile) {
        this.zsFile = zsFile;
    }

}