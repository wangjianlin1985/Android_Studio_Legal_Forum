package com.mobileclient.domain;

import java.io.Serializable;

public class Flzs implements Serializable {
    /*��¼id*/
    private int zsId;
    public int getZsId() {
        return zsId;
    }
    public void setZsId(int zsId) {
        this.zsId = zsId;
    }

    /*֪ʶ����*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*֪ʶͼƬ*/
    private String zsPhoto;
    public String getZsPhoto() {
        return zsPhoto;
    }
    public void setZsPhoto(String zsPhoto) {
        this.zsPhoto = zsPhoto;
    }

    /*֪ʶ���*/
    private String zsDesc;
    public String getZsDesc() {
        return zsDesc;
    }
    public void setZsDesc(String zsDesc) {
        this.zsDesc = zsDesc;
    }

    /*����*/
    private String author;
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    /*������*/
    private String publish;
    public String getPublish() {
        return publish;
    }
    public void setPublish(String publish) {
        this.publish = publish;
    }

    /*��������*/
    private java.sql.Timestamp publishDate;
    public java.sql.Timestamp getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(java.sql.Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    /*�Ķ���*/
    private int viewNum;
    public int getViewNum() {
        return viewNum;
    }
    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    /*֪ʶ�ļ�*/
    private String zsFile;
    public String getZsFile() {
        return zsFile;
    }
    public void setZsFile(String zsFile) {
        this.zsFile = zsFile;
    }

}