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
import com.chengxusheji.domain.Flzs;

@Service @Transactional
public class FlzsDAO {

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
    public void AddFlzs(Flzs flzs) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(flzs);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Flzs> QueryFlzsInfo(String title,String author,String publish,String publishDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Flzs flzs where 1=1";
    	if(!title.equals("")) hql = hql + " and flzs.title like '%" + title + "%'";
    	if(!author.equals("")) hql = hql + " and flzs.author like '%" + author + "%'";
    	if(!publish.equals("")) hql = hql + " and flzs.publish like '%" + publish + "%'";
    	if(!publishDate.equals("")) hql = hql + " and flzs.publishDate like '%" + publishDate + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List flzsList = q.list();
    	return (ArrayList<Flzs>) flzsList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Flzs> QueryFlzsInfo(String title,String author,String publish,String publishDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Flzs flzs where 1=1";
    	if(!title.equals("")) hql = hql + " and flzs.title like '%" + title + "%'";
    	if(!author.equals("")) hql = hql + " and flzs.author like '%" + author + "%'";
    	if(!publish.equals("")) hql = hql + " and flzs.publish like '%" + publish + "%'";
    	if(!publishDate.equals("")) hql = hql + " and flzs.publishDate like '%" + publishDate + "%'";
    	Query q = s.createQuery(hql);
    	List flzsList = q.list();
    	return (ArrayList<Flzs>) flzsList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Flzs> QueryAllFlzsInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Flzs";
        Query q = s.createQuery(hql);
        List flzsList = q.list();
        return (ArrayList<Flzs>) flzsList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String title,String author,String publish,String publishDate) {
        Session s = factory.getCurrentSession();
        String hql = "From Flzs flzs where 1=1";
        if(!title.equals("")) hql = hql + " and flzs.title like '%" + title + "%'";
        if(!author.equals("")) hql = hql + " and flzs.author like '%" + author + "%'";
        if(!publish.equals("")) hql = hql + " and flzs.publish like '%" + publish + "%'";
        if(!publishDate.equals("")) hql = hql + " and flzs.publishDate like '%" + publishDate + "%'";
        Query q = s.createQuery(hql);
        List flzsList = q.list();
        recordNumber = flzsList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Flzs GetFlzsByZsId(int zsId) {
        Session s = factory.getCurrentSession();
        Flzs flzs = (Flzs)s.get(Flzs.class, zsId);
        return flzs;
    }

    /*����Flzs��Ϣ*/
    public void UpdateFlzs(Flzs flzs) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(flzs);
    }

    /*ɾ��Flzs��Ϣ*/
    public void DeleteFlzs (int zsId) throws Exception {
        Session s = factory.getCurrentSession();
        Object flzs = s.load(Flzs.class, zsId);
        s.delete(flzs);
    }

}
