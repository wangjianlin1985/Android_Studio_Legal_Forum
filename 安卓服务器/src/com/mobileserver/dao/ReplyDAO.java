package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Reply;
import com.mobileserver.util.DB;

public class ReplyDAO {

	public List<Reply> QueryReply(int postInfoObj,String userObj,String replyTime) {
		List<Reply> replyList = new ArrayList<Reply>();
		DB db = new DB();
		String sql = "select * from Reply where 1=1";
		if (postInfoObj != 0)
			sql += " and postInfoObj=" + postInfoObj;
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!replyTime.equals(""))
			sql += " and replyTime like '%" + replyTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Reply reply = new Reply();
				reply.setReplyId(rs.getInt("replyId"));
				reply.setPostInfoObj(rs.getInt("postInfoObj"));
				reply.setContent(rs.getString("content"));
				reply.setUserObj(rs.getString("userObj"));
				reply.setReplyTime(rs.getString("replyTime"));
				replyList.add(reply);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return replyList;
	}
	/* 传入帖子回复对象，进行帖子回复的添加业务 */
	public String AddReply(Reply reply) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新帖子回复 */
			String sqlString = "insert into Reply(postInfoObj,content,userObj,replyTime) values (";
			sqlString += reply.getPostInfoObj() + ",";
			sqlString += "'" + reply.getContent() + "',";
			sqlString += "'" + reply.getUserObj() + "',";
			sqlString += "'" + reply.getReplyTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "帖子回复添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "帖子回复添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除帖子回复 */
	public String DeleteReply(int replyId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Reply where replyId=" + replyId;
			db.executeUpdate(sqlString);
			result = "帖子回复删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "帖子回复删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据回复id获取到帖子回复 */
	public Reply GetReply(int replyId) {
		Reply reply = null;
		DB db = new DB();
		String sql = "select * from Reply where replyId=" + replyId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				reply = new Reply();
				reply.setReplyId(rs.getInt("replyId"));
				reply.setPostInfoObj(rs.getInt("postInfoObj"));
				reply.setContent(rs.getString("content"));
				reply.setUserObj(rs.getString("userObj"));
				reply.setReplyTime(rs.getString("replyTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return reply;
	}
	/* 更新帖子回复 */
	public String UpdateReply(Reply reply) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Reply set ";
			sql += "postInfoObj=" + reply.getPostInfoObj() + ",";
			sql += "content='" + reply.getContent() + "',";
			sql += "userObj='" + reply.getUserObj() + "',";
			sql += "replyTime='" + reply.getReplyTime() + "'";
			sql += " where replyId=" + reply.getReplyId();
			db.executeUpdate(sql);
			result = "帖子回复更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "帖子回复更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
