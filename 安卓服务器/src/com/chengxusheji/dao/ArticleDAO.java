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
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.Article;

@Service @Transactional
public class ArticleDAO {

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
    public void AddArticle(Article article) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(article);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Article> QueryArticleInfo(String title,ArticleClass articleClassObj,UserInfo userObj,String addTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Article article where 1=1";
    	if(!title.equals("")) hql = hql + " and article.title like '%" + title + "%'";
    	if(null != articleClassObj && articleClassObj.getArticleClassId()!=0) hql += " and article.articleClassObj.articleClassId=" + articleClassObj.getArticleClassId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and article.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) hql = hql + " and article.addTime like '%" + addTime + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List articleList = q.list();
    	return (ArrayList<Article>) articleList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Article> QueryArticleInfo(String title,ArticleClass articleClassObj,UserInfo userObj,String addTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Article article where 1=1";
    	if(!title.equals("")) hql = hql + " and article.title like '%" + title + "%'";
    	if(null != articleClassObj && articleClassObj.getArticleClassId()!=0) hql += " and article.articleClassObj.articleClassId=" + articleClassObj.getArticleClassId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and article.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) hql = hql + " and article.addTime like '%" + addTime + "%'";
    	Query q = s.createQuery(hql);
    	List articleList = q.list();
    	return (ArrayList<Article>) articleList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Article> QueryAllArticleInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Article";
        Query q = s.createQuery(hql);
        List articleList = q.list();
        return (ArrayList<Article>) articleList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String title,ArticleClass articleClassObj,UserInfo userObj,String addTime) {
        Session s = factory.getCurrentSession();
        String hql = "From Article article where 1=1";
        if(!title.equals("")) hql = hql + " and article.title like '%" + title + "%'";
        if(null != articleClassObj && articleClassObj.getArticleClassId()!=0) hql += " and article.articleClassObj.articleClassId=" + articleClassObj.getArticleClassId();
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and article.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!addTime.equals("")) hql = hql + " and article.addTime like '%" + addTime + "%'";
        Query q = s.createQuery(hql);
        List articleList = q.list();
        recordNumber = articleList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Article GetArticleByArticleId(int articleId) {
        Session s = factory.getCurrentSession();
        Article article = (Article)s.get(Article.class, articleId);
        return article;
    }

    /*����Article��Ϣ*/
    public void UpdateArticle(Article article) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(article);
    }

    /*ɾ��Article��Ϣ*/
    public void DeleteArticle (int articleId) throws Exception {
        Session s = factory.getCurrentSession();
        Object article = s.load(Article.class, articleId);
        s.delete(article);
    }

}
