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
	/* �������ӻظ����󣬽������ӻظ������ҵ�� */
	public String AddReply(Reply reply) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в��������ӻظ� */
			String sqlString = "insert into Reply(postInfoObj,content,userObj,replyTime) values (";
			sqlString += reply.getPostInfoObj() + ",";
			sqlString += "'" + reply.getContent() + "',";
			sqlString += "'" + reply.getUserObj() + "',";
			sqlString += "'" + reply.getReplyTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "���ӻظ���ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���ӻظ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�����ӻظ� */
	public String DeleteReply(int replyId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Reply where replyId=" + replyId;
			db.executeUpdate(sqlString);
			result = "���ӻظ�ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���ӻظ�ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݻظ�id��ȡ�����ӻظ� */
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
	/* �������ӻظ� */
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
			result = "���ӻظ����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���ӻظ�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
