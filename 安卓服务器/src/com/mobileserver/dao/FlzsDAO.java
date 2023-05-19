package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Flzs;
import com.mobileserver.util.DB;

public class FlzsDAO {

	public List<Flzs> QueryFlzs(String title,String author,String publish,Timestamp publishDate) {
		List<Flzs> flzsList = new ArrayList<Flzs>();
		DB db = new DB();
		String sql = "select * from Flzs where 1=1";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if (!author.equals(""))
			sql += " and author like '%" + author + "%'";
		if (!publish.equals(""))
			sql += " and publish like '%" + publish + "%'";
		if(publishDate!=null)
			sql += " and publishDate='" + publishDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Flzs flzs = new Flzs();
				flzs.setZsId(rs.getInt("zsId"));
				flzs.setTitle(rs.getString("title"));
				flzs.setZsPhoto(rs.getString("zsPhoto"));
				flzs.setZsDesc(rs.getString("zsDesc"));
				flzs.setAuthor(rs.getString("author"));
				flzs.setPublish(rs.getString("publish"));
				flzs.setPublishDate(rs.getTimestamp("publishDate"));
				flzs.setViewNum(rs.getInt("viewNum"));
				flzs.setZsFile(rs.getString("zsFile"));
				flzsList.add(flzs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return flzsList;
	}
	/* 传入法律知识对象，进行法律知识的添加业务 */
	public String AddFlzs(Flzs flzs) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新法律知识 */
			String sqlString = "insert into Flzs(title,zsPhoto,zsDesc,author,publish,publishDate,viewNum,zsFile) values (";
			sqlString += "'" + flzs.getTitle() + "',";
			sqlString += "'" + flzs.getZsPhoto() + "',";
			sqlString += "'" + flzs.getZsDesc() + "',";
			sqlString += "'" + flzs.getAuthor() + "',";
			sqlString += "'" + flzs.getPublish() + "',";
			sqlString += "'" + flzs.getPublishDate() + "',";
			sqlString += flzs.getViewNum() + ",";
			sqlString += "'" + flzs.getZsFile() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "法律知识添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "法律知识添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除法律知识 */
	public String DeleteFlzs(int zsId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Flzs where zsId=" + zsId;
			db.executeUpdate(sqlString);
			result = "法律知识删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "法律知识删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录id获取到法律知识 */
	public Flzs GetFlzs(int zsId) {
		Flzs flzs = null;
		DB db = new DB();
		String sql = "select * from Flzs where zsId=" + zsId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				flzs = new Flzs();
				flzs.setZsId(rs.getInt("zsId"));
				flzs.setTitle(rs.getString("title"));
				flzs.setZsPhoto(rs.getString("zsPhoto"));
				flzs.setZsDesc(rs.getString("zsDesc"));
				flzs.setAuthor(rs.getString("author"));
				flzs.setPublish(rs.getString("publish"));
				flzs.setPublishDate(rs.getTimestamp("publishDate"));
				flzs.setViewNum(rs.getInt("viewNum"));
				flzs.setZsFile(rs.getString("zsFile"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return flzs;
	}
	/* 更新法律知识 */
	public String UpdateFlzs(Flzs flzs) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Flzs set ";
			sql += "title='" + flzs.getTitle() + "',";
			sql += "zsPhoto='" + flzs.getZsPhoto() + "',";
			sql += "zsDesc='" + flzs.getZsDesc() + "',";
			sql += "author='" + flzs.getAuthor() + "',";
			sql += "publish='" + flzs.getPublish() + "',";
			sql += "publishDate='" + flzs.getPublishDate() + "',";
			sql += "viewNum=" + flzs.getViewNum() + ",";
			sql += "zsFile='" + flzs.getZsFile() + "'";
			sql += " where zsId=" + flzs.getZsId();
			db.executeUpdate(sql);
			result = "法律知识更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "法律知识更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
