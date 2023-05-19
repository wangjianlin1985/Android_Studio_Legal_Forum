package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.PostInfo;
import com.mobileserver.util.DB;

public class PostInfoDAO {

	public List<PostInfo> QueryPostInfo(String title,String userObj,String addTime) {
		List<PostInfo> postInfoList = new ArrayList<PostInfo>();
		DB db = new DB();
		String sql = "select * from PostInfo where 1=1";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!addTime.equals(""))
			sql += " and addTime like '%" + addTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				PostInfo postInfo = new PostInfo();
				postInfo.setPostInfoId(rs.getInt("postInfoId"));
				postInfo.setTitle(rs.getString("title"));
				postInfo.setContent(rs.getString("content"));
				postInfo.setHitNum(rs.getInt("hitNum"));
				postInfo.setUserObj(rs.getString("userObj"));
				postInfo.setAddTime(rs.getString("addTime"));
				postInfoList.add(postInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return postInfoList;
	}
	/* 传入帖子对象，进行帖子的添加业务 */
	public String AddPostInfo(PostInfo postInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新帖子 */
			String sqlString = "insert into PostInfo(title,content,hitNum,userObj,addTime) values (";
			sqlString += "'" + postInfo.getTitle() + "',";
			sqlString += "'" + postInfo.getContent() + "',";
			sqlString += postInfo.getHitNum() + ",";
			sqlString += "'" + postInfo.getUserObj() + "',";
			sqlString += "'" + postInfo.getAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "帖子添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "帖子添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除帖子 */
	public String DeletePostInfo(int postInfoId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from PostInfo where postInfoId=" + postInfoId;
			db.executeUpdate(sqlString);
			result = "帖子删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "帖子删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据帖子id获取到帖子 */
	public PostInfo GetPostInfo(int postInfoId) {
		PostInfo postInfo = null;
		DB db = new DB();
		String sql = "select * from PostInfo where postInfoId=" + postInfoId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				postInfo = new PostInfo();
				postInfo.setPostInfoId(rs.getInt("postInfoId"));
				postInfo.setTitle(rs.getString("title"));
				postInfo.setContent(rs.getString("content"));
				postInfo.setHitNum(rs.getInt("hitNum"));
				postInfo.setUserObj(rs.getString("userObj"));
				postInfo.setAddTime(rs.getString("addTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return postInfo;
	}
	/* 更新帖子 */
	public String UpdatePostInfo(PostInfo postInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update PostInfo set ";
			sql += "title='" + postInfo.getTitle() + "',";
			sql += "content='" + postInfo.getContent() + "',";
			sql += "hitNum=" + postInfo.getHitNum() + ",";
			sql += "userObj='" + postInfo.getUserObj() + "',";
			sql += "addTime='" + postInfo.getAddTime() + "'";
			sql += " where postInfoId=" + postInfo.getPostInfoId();
			db.executeUpdate(sql);
			result = "帖子更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "帖子更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
