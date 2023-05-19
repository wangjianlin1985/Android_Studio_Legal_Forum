package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Article;
import com.mobileclient.util.HttpUtil;

/*���¹���ҵ���߼���*/
public class ArticleService {
	/* ������� */
	public String AddArticle(Article article) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("articleId", article.getArticleId() + "");
		params.put("title", article.getTitle());
		params.put("articleClassObj", article.getArticleClassObj() + "");
		params.put("content", article.getContent());
		params.put("hitNum", article.getHitNum() + "");
		params.put("userObj", article.getUserObj());
		params.put("addTime", article.getAddTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ArticleServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ���� */
	public List<Article> QueryArticle(Article queryConditionArticle) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ArticleServlet?action=query";
		if(queryConditionArticle != null) {
			urlString += "&title=" + URLEncoder.encode(queryConditionArticle.getTitle(), "UTF-8") + "";
			urlString += "&articleClassObj=" + queryConditionArticle.getArticleClassObj();
			urlString += "&userObj=" + URLEncoder.encode(queryConditionArticle.getUserObj(), "UTF-8") + "";
			urlString += "&addTime=" + URLEncoder.encode(queryConditionArticle.getAddTime(), "UTF-8") + "";
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ArticleListHandler articleListHander = new ArticleListHandler();
		xr.setContentHandler(articleListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Article> articleList = articleListHander.getArticleList();
		return articleList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Article> articleList = new ArrayList<Article>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Article article = new Article();
				article.setArticleId(object.getInt("articleId"));
				article.setTitle(object.getString("title"));
				article.setArticleClassObj(object.getInt("articleClassObj"));
				article.setContent(object.getString("content"));
				article.setHitNum(object.getInt("hitNum"));
				article.setUserObj(object.getString("userObj"));
				article.setAddTime(object.getString("addTime"));
				articleList.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articleList;
	}

	/* �������� */
	public String UpdateArticle(Article article) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("articleId", article.getArticleId() + "");
		params.put("title", article.getTitle());
		params.put("articleClassObj", article.getArticleClassObj() + "");
		params.put("content", article.getContent());
		params.put("hitNum", article.getHitNum() + "");
		params.put("userObj", article.getUserObj());
		params.put("addTime", article.getAddTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ArticleServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ������ */
	public String DeleteArticle(int articleId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("articleId", articleId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ArticleServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "������Ϣɾ��ʧ��!";
		}
	}

	/* ��������id��ȡ���¶��� */
	public Article GetArticle(int articleId)  {
		List<Article> articleList = new ArrayList<Article>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("articleId", articleId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ArticleServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Article article = new Article();
				article.setArticleId(object.getInt("articleId"));
				article.setTitle(object.getString("title"));
				article.setArticleClassObj(object.getInt("articleClassObj"));
				article.setContent(object.getString("content"));
				article.setHitNum(object.getInt("hitNum"));
				article.setUserObj(object.getString("userObj"));
				article.setAddTime(object.getString("addTime"));
				articleList.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = articleList.size();
		if(size>0) return articleList.get(0); 
		else return null; 
	}
}
