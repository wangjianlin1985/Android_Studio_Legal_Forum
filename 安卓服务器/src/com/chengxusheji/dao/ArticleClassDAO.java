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
import com.chengxusheji.domain.ArticleClass;

@Service @Transactional
public class ArticleClassDAO {

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
    public void AddArticleClass(ArticleClass articleClass) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(articleClass);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ArticleClass> QueryArticleClassInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ArticleClass articleClass where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List articleClassList = q.list();
    	return (ArrayList<ArticleClass>) articleClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ArticleClass> QueryArticleClassInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ArticleClass articleClass where 1=1";
    	Query q = s.createQuery(hql);
    	List articleClassList = q.list();
    	return (ArrayList<ArticleClass>) articleClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ArticleClass> QueryAllArticleClassInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From ArticleClass";
        Query q = s.createQuery(hql);
        List articleClassList = q.list();
        return (ArrayList<ArticleClass>) articleClassList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From ArticleClass articleClass where 1=1";
        Query q = s.createQuery(hql);
        List articleClassList = q.list();
        recordNumber = articleClassList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArticleClass GetArticleClassByArticleClassId(int articleClassId) {
        Session s = factory.getCurrentSession();
        ArticleClass articleClass = (ArticleClass)s.get(ArticleClass.class, articleClassId);
        return articleClass;
    }

    /*����ArticleClass��Ϣ*/
    public void UpdateArticleClass(ArticleClass articleClass) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(articleClass);
    }

    /*ɾ��ArticleClass��Ϣ*/
    public void DeleteArticleClass (int articleClassId) throws Exception {
        Session s = factory.getCurrentSession();
        Object articleClass = s.load(ArticleClass.class, articleClassId);
        s.delete(articleClass);
    }

}
