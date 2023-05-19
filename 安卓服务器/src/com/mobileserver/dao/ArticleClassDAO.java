package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.ArticleClass;
import com.mobileserver.util.DB;

public class ArticleClassDAO {

	public List<ArticleClass> QueryArticleClass() {
		List<ArticleClass> articleClassList = new ArrayList<ArticleClass>();
		DB db = new DB();
		String sql = "select * from ArticleClass where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				ArticleClass articleClass = new ArticleClass();
				articleClass.setArticleClassId(rs.getInt("articleClassId"));
				articleClass.setArticleClassName(rs.getString("articleClassName"));
				articleClassList.add(articleClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return articleClassList;
	}
	/* �������·�����󣬽������·�������ҵ�� */
	public String AddArticleClass(ArticleClass articleClass) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в��������·��� */
			String sqlString = "insert into ArticleClass(articleClassName) values (";
			sqlString += "'" + articleClass.getArticleClassName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "���·�����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���·������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�����·��� */
	public String DeleteArticleClass(int articleClassId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ArticleClass where articleClassId=" + articleClassId;
			db.executeUpdate(sqlString);
			result = "���·���ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���·���ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* �������·���id��ȡ�����·��� */
	public ArticleClass GetArticleClass(int articleClassId) {
		ArticleClass articleClass = null;
		DB db = new DB();
		String sql = "select * from ArticleClass where articleClassId=" + articleClassId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				articleClass = new ArticleClass();
				articleClass.setArticleClassId(rs.getInt("articleClassId"));
				articleClass.setArticleClassName(rs.getString("articleClassName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return articleClass;
	}
	/* �������·��� */
	public String UpdateArticleClass(ArticleClass articleClass) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ArticleClass set ";
			sql += "articleClassName='" + articleClass.getArticleClassName() + "'";
			sql += " where articleClassId=" + articleClass.getArticleClassId();
			db.executeUpdate(sql);
			result = "���·�����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���·������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
