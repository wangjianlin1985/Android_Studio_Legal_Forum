package com.mobileserver.domain;

public class Reply {
    /*�ظ�id*/
    private int replyId;
    public int getReplyId() {
        return replyId;
    }
    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    /*��������*/
    private int postInfoObj;
    public int getPostInfoObj() {
        return postInfoObj;
    }
    public void setPostInfoObj(int postInfoObj) {
        this.postInfoObj = postInfoObj;
    }

    /*�ظ�����*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*�ظ���*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*�ظ�ʱ��*/
    private String replyTime;
    public String getReplyTime() {
        return replyTime;
    }
    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

}