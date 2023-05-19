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
	/* 传入文章分类对象，进行文章分类的添加业务 */
	public String AddArticleClass(ArticleClass articleClass) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新文章分类 */
			String sqlString = "insert into ArticleClass(articleClassName) values (";
			sqlString += "'" + articleClass.getArticleClassName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "文章分类添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "文章分类添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除文章分类 */
	public String DeleteArticleClass(int articleClassId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ArticleClass where articleClassId=" + articleClassId;
			db.executeUpdate(sqlString);
			result = "文章分类删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "文章分类删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据文章分类id获取到文章分类 */
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
	/* 更新文章分类 */
	public String UpdateArticleClass(ArticleClass articleClass) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ArticleClass set ";
			sql += "articleClassName='" + articleClass.getArticleClassName() + "'";
			sql += " where articleClassId=" + articleClass.getArticleClassId();
			db.executeUpdate(sql);
			result = "文章分类更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "文章分类更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
