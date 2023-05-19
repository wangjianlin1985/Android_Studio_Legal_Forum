package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.ArticleClass;
import com.mobileclient.util.HttpUtil;

/*���·������ҵ���߼���*/
public class ArticleClassService {
	/* ������·��� */
	public String AddArticleClass(ArticleClass articleClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("articleClassId", articleClass.getArticleClassId() + "");
		params.put("articleClassName", articleClass.getArticleClassName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ArticleClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ���·��� */
	public List<ArticleClass> QueryArticleClass(ArticleClass queryConditionArticleClass) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ArticleClassServlet?action=query";
		if(queryConditionArticleClass != null) {
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ArticleClassListHandler articleClassListHander = new ArticleClassListHandler();
		xr.setContentHandler(articleClassListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<ArticleClass> articleClassList = articleClassListHander.getArticleClassList();
		return articleClassList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<ArticleClass> articleClassList = new ArrayList<ArticleClass>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ArticleClass articleClass = new ArticleClass();
				articleClass.setArticleClassId(object.getInt("articleClassId"));
				articleClass.setArticleClassName(object.getString("articleClassName"));
				articleClassList.add(articleClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articleClassList;
	}

	/* �������·��� */
	public String UpdateArticleClass(ArticleClass articleClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("articleClassId", articleClass.getArticleClassId() + "");
		params.put("articleClassName", articleClass.getArticleClassName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ArticleClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ�����·��� */
	public String DeleteArticleClass(int articleClassId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("articleClassId", articleClassId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ArticleClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "���·�����Ϣɾ��ʧ��!";
		}
	}

	/* �������·���id��ȡ���·������ */
	public ArticleClass GetArticleClass(int articleClassId)  {
		List<ArticleClass> articleClassList = new ArrayList<ArticleClass>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("articleClassId", articleClassId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ArticleClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ArticleClass articleClass = new ArticleClass();
				articleClass.setArticleClassId(object.getInt("articleClassId"));
				articleClass.setArticleClassName(object.getString("articleClassName"));
				articleClassList.add(articleClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = articleClassList.size();
		if(size>0) return articleClassList.get(0); 
		else return null; 
	}
}
