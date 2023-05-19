package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.PostInfo;

@Service @Transactional
public class PostInfoDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddPostInfo(PostInfo postInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(postInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<PostInfo> QueryPostInfoInfo(String title,UserInfo userObj,String addTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From PostInfo postInfo where 1=1";
    	if(!title.equals("")) hql = hql + " and postInfo.title like '%" + title + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and postInfo.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) hql = hql + " and postInfo.addTime like '%" + addTime + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List postInfoList = q.list();
    	return (ArrayList<PostInfo>) postInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<PostInfo> QueryPostInfoInfo(String title,UserInfo userObj,String addTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From PostInfo postInfo where 1=1";
    	if(!title.equals("")) hql = hql + " and postInfo.title like '%" + title + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and postInfo.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) hql = hql + " and postInfo.addTime like '%" + addTime + "%'";
    	Query q = s.createQuery(hql);
    	List postInfoList = q.list();
    	return (ArrayList<PostInfo>) postInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<PostInfo> QueryAllPostInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From PostInfo";
        Query q = s.createQuery(hql);
        List postInfoList = q.list();
        return (ArrayList<PostInfo>) postInfoList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String title,UserInfo userObj,String addTime) {
        Session s = factory.getCurrentSession();
        String hql = "From PostInfo postInfo where 1=1";
        if(!title.equals("")) hql = hql + " and postInfo.title like '%" + title + "%'";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and postInfo.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!addTime.equals("")) hql = hql + " and postInfo.addTime like '%" + addTime + "%'";
        Query q = s.createQuery(hql);
        List postInfoList = q.list();
        recordNumber = postInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public PostInfo GetPostInfoByPostInfoId(int postInfoId) {
        Session s = factory.getCurrentSession();
        PostInfo postInfo = (PostInfo)s.get(PostInfo.class, postInfoId);
        return postInfo;
    }

    /*����PostInfo��Ϣ*/
    public void UpdatePostInfo(PostInfo postInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(postInfo);
    }

    /*ɾ��PostInfo��Ϣ*/
    public void DeletePostInfo (int postInfoId) throws Exception {
        Session s = factory.getCurrentSession();
        Object postInfo = s.load(PostInfo.class, postInfoId);
        s.delete(postInfo);
    }

}
