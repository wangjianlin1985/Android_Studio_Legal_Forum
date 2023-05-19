package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Reply;
import com.mobileclient.util.HttpUtil;

/*���ӻظ�����ҵ���߼���*/
public class ReplyService {
	/* ������ӻظ� */
	public String AddReply(Reply reply) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("replyId", reply.getReplyId() + "");
		params.put("postInfoObj", reply.getPostInfoObj() + "");
		params.put("content", reply.getContent());
		params.put("userObj", reply.getUserObj());
		params.put("replyTime", reply.getReplyTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ReplyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ���ӻظ� */
	public List<Reply> QueryReply(Reply queryConditionReply) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ReplyServlet?action=query";
		if(queryConditionReply != null) {
			urlString += "&postInfoObj=" + queryConditionReply.getPostInfoObj();
			urlString += "&userObj=" + URLEncoder.encode(queryConditionReply.getUserObj(), "UTF-8") + "";
			urlString += "&replyTime=" + URLEncoder.encode(queryConditionReply.getReplyTime(), "UTF-8") + "";
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ReplyListHandler replyListHander = new ReplyListHandler();
		xr.setContentHandler(replyListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Reply> replyList = replyListHander.getReplyList();
		return replyList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Reply> replyList = new ArrayList<Reply>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Reply reply = new Reply();
				reply.setReplyId(object.getInt("replyId"));
				reply.setPostInfoObj(object.getInt("postInfoObj"));
				reply.setContent(object.getString("content"));
				reply.setUserObj(object.getString("userObj"));
				reply.setReplyTime(object.getString("replyTime"));
				replyList.add(reply);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return replyList;
	}

	/* �������ӻظ� */
	public String UpdateReply(Reply reply) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("replyId", reply.getReplyId() + "");
		params.put("postInfoObj", reply.getPostInfoObj() + "");
		params.put("content", reply.getContent());
		params.put("userObj", reply.getUserObj());
		params.put("replyTime", reply.getReplyTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ReplyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ�����ӻظ� */
	public String DeleteReply(int replyId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("replyId", replyId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ReplyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "���ӻظ���Ϣɾ��ʧ��!";
		}
	}

	/* ���ݻظ�id��ȡ���ӻظ����� */
	public Reply GetReply(int replyId)  {
		List<Reply> replyList = new ArrayList<Reply>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("replyId", replyId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ReplyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Reply reply = new Reply();
				reply.setReplyId(object.getInt("replyId"));
				reply.setPostInfoObj(object.getInt("postInfoObj"));
				reply.setContent(object.getString("content"));
				reply.setUserObj(object.getString("userObj"));
				reply.setReplyTime(object.getString("replyTime"));
				replyList.add(reply);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = replyList.size();
		if(size>0) return replyList.get(0); 
		else return null; 
	}
}
