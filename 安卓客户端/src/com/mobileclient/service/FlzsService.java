package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Flzs;
import com.mobileclient.util.HttpUtil;

/*法律知识管理业务逻辑层*/
public class FlzsService {
	/* 添加法律知识 */
	public String AddFlzs(Flzs flzs) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("zsId", flzs.getZsId() + "");
		params.put("title", flzs.getTitle());
		params.put("zsPhoto", flzs.getZsPhoto());
		params.put("zsDesc", flzs.getZsDesc());
		params.put("author", flzs.getAuthor());
		params.put("publish", flzs.getPublish());
		params.put("publishDate", flzs.getPublishDate().toString());
		params.put("viewNum", flzs.getViewNum() + "");
		params.put("zsFile", flzs.getZsFile());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FlzsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询法律知识 */
	public List<Flzs> QueryFlzs(Flzs queryConditionFlzs) throws Exception {
		String urlString = HttpUtil.BASE_URL + "FlzsServlet?action=query";
		if(queryConditionFlzs != null) {
			urlString += "&title=" + URLEncoder.encode(queryConditionFlzs.getTitle(), "UTF-8") + "";
			urlString += "&author=" + URLEncoder.encode(queryConditionFlzs.getAuthor(), "UTF-8") + "";
			urlString += "&publish=" + URLEncoder.encode(queryConditionFlzs.getPublish(), "UTF-8") + "";
			if(queryConditionFlzs.getPublishDate() != null) {
				urlString += "&publishDate=" + URLEncoder.encode(queryConditionFlzs.getPublishDate().toString(), "UTF-8");
			}
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		FlzsListHandler flzsListHander = new FlzsListHandler();
		xr.setContentHandler(flzsListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Flzs> flzsList = flzsListHander.getFlzsList();
		return flzsList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Flzs> flzsList = new ArrayList<Flzs>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Flzs flzs = new Flzs();
				flzs.setZsId(object.getInt("zsId"));
				flzs.setTitle(object.getString("title"));
				flzs.setZsPhoto(object.getString("zsPhoto"));
				flzs.setZsDesc(object.getString("zsDesc"));
				flzs.setAuthor(object.getString("author"));
				flzs.setPublish(object.getString("publish"));
				flzs.setPublishDate(Timestamp.valueOf(object.getString("publishDate")));
				flzs.setViewNum(object.getInt("viewNum"));
				flzs.setZsFile(object.getString("zsFile"));
				flzsList.add(flzs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flzsList;
	}

	/* 更新法律知识 */
	public String UpdateFlzs(Flzs flzs) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("zsId", flzs.getZsId() + "");
		params.put("title", flzs.getTitle());
		params.put("zsPhoto", flzs.getZsPhoto());
		params.put("zsDesc", flzs.getZsDesc());
		params.put("author", flzs.getAuthor());
		params.put("publish", flzs.getPublish());
		params.put("publishDate", flzs.getPublishDate().toString());
		params.put("viewNum", flzs.getViewNum() + "");
		params.put("zsFile", flzs.getZsFile());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FlzsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除法律知识 */
	public String DeleteFlzs(int zsId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("zsId", zsId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FlzsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "法律知识信息删除失败!";
		}
	}

	/* 根据记录id获取法律知识对象 */
	public Flzs GetFlzs(int zsId)  {
		List<Flzs> flzsList = new ArrayList<Flzs>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("zsId", zsId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FlzsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Flzs flzs = new Flzs();
				flzs.setZsId(object.getInt("zsId"));
				flzs.setTitle(object.getString("title"));
				flzs.setZsPhoto(object.getString("zsPhoto"));
				flzs.setZsDesc(object.getString("zsDesc"));
				flzs.setAuthor(object.getString("author"));
				flzs.setPublish(object.getString("publish"));
				flzs.setPublishDate(Timestamp.valueOf(object.getString("publishDate")));
				flzs.setViewNum(object.getInt("viewNum"));
				flzs.setZsFile(object.getString("zsFile"));
				flzsList.add(flzs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = flzsList.size();
		if(size>0) return flzsList.get(0); 
		else return null; 
	}
}
