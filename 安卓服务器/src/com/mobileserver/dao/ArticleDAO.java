package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Article;
import com.mobileserver.util.DB;

public class ArticleDAO {

	public List<Article> QueryArticle(String title,int articleClassObj,String userObj,String addTime) {
		List<Article> articleList = new ArrayList<Article>();
		DB db = new DB();
		String sql = "select * from Article where 1=1";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if (articleClassObj != 0)
			sql += " and articleClassObj=" + articleClassObj;
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!addTime.equals(""))
			sql += " and addTime like '%" + addTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Article article = new Article();
				article.setArticleId(rs.getInt("articleId"));
				article.setTitle(rs.getString("title"));
				article.setArticleClassObj(rs.getInt("articleClassObj"));
				article.setContent(rs.getString("content"));
				article.setHitNum(rs.getInt("hitNum"));
				article.setUserObj(rs.getString("userObj"));
				article.setAddTime(rs.getString("addTime"));
				articleList.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return articleList;
	}
	/* �������¶��󣬽������µ����ҵ�� */
	public String AddArticle(Article article) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в��������� */
			String sqlString = "insert into Article(title,articleClassObj,content,hitNum,userObj,addTime) values (";
			sqlString += "'" + article.getTitle() + "',";
			sqlString += article.getArticleClassObj() + ",";
			sqlString += "'" + article.getContent() + "',";
			sqlString += article.getHitNum() + ",";
			sqlString += "'" + article.getUserObj() + "',";
			sqlString += "'" + article.getAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ������ */
	public String DeleteArticle(int articleId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Article where articleId=" + articleId;
			db.executeUpdate(sqlString);
			result = "����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ��������id��ȡ������ */
	public Article GetArticle(int articleId) {
		Article article = null;
		DB db = new DB();
		String sql = "select * from Article where articleId=" + articleId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				article = new Article();
				article.setArticleId(rs.getInt("articleId"));
				article.setTitle(rs.getString("title"));
				article.setArticleClassObj(rs.getInt("articleClassObj"));
				article.setContent(rs.getString("content"));
				article.setHitNum(rs.getInt("hitNum"));
				article.setUserObj(rs.getString("userObj"));
				article.setAddTime(rs.getString("addTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return article;
	}
	/* �������� */
	public String UpdateArticle(Article article) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Article set ";
			sql += "title='" + article.getTitle() + "',";
			sql += "articleClassObj=" + article.getArticleClassObj() + ",";
			sql += "content='" + article.getContent() + "',";
			sql += "hitNum=" + article.getHitNum() + ",";
			sql += "userObj='" + article.getUserObj() + "',";
			sql += "addTime='" + article.getAddTime() + "'";
			sql += " where articleId=" + article.getArticleId();
			db.executeUpdate(sql);
			result = "���¸��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���¸���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
