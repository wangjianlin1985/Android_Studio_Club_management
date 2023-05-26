package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Shetuan;
import com.mobileclient.util.HttpUtil;

/*社团管理业务逻辑层*/
public class ShetuanService {
	/* 添加社团 */
	public String AddShetuan(Shetuan shetuan) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stUserName", shetuan.getStUserName());
		params.put("password", shetuan.getPassword());
		params.put("shetuanName", shetuan.getShetuanName());
		params.put("shetuanPhoto", shetuan.getShetuanPhoto());
		params.put("shetuanDesc", shetuan.getShetuanDesc());
		params.put("bornDate", shetuan.getBornDate().toString());
		params.put("fuzeren", shetuan.getFuzeren());
		params.put("telephone", shetuan.getTelephone());
		params.put("shetuanMemo", shetuan.getShetuanMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ShetuanServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询社团 */
	public List<Shetuan> QueryShetuan(Shetuan queryConditionShetuan) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ShetuanServlet?action=query";
		if(queryConditionShetuan != null) {
			urlString += "&stUserName=" + URLEncoder.encode(queryConditionShetuan.getStUserName(), "UTF-8") + "";
			urlString += "&shetuanName=" + URLEncoder.encode(queryConditionShetuan.getShetuanName(), "UTF-8") + "";
			if(queryConditionShetuan.getBornDate() != null) {
				urlString += "&bornDate=" + URLEncoder.encode(queryConditionShetuan.getBornDate().toString(), "UTF-8");
			}
			urlString += "&fuzeren=" + URLEncoder.encode(queryConditionShetuan.getFuzeren(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ShetuanListHandler shetuanListHander = new ShetuanListHandler();
		xr.setContentHandler(shetuanListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Shetuan> shetuanList = shetuanListHander.getShetuanList();
		return shetuanList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Shetuan> shetuanList = new ArrayList<Shetuan>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Shetuan shetuan = new Shetuan();
				shetuan.setStUserName(object.getString("stUserName"));
				shetuan.setPassword(object.getString("password"));
				shetuan.setShetuanName(object.getString("shetuanName"));
				shetuan.setShetuanPhoto(object.getString("shetuanPhoto"));
				shetuan.setShetuanDesc(object.getString("shetuanDesc"));
				shetuan.setBornDate(Timestamp.valueOf(object.getString("bornDate")));
				shetuan.setFuzeren(object.getString("fuzeren"));
				shetuan.setTelephone(object.getString("telephone"));
				shetuan.setShetuanMemo(object.getString("shetuanMemo"));
				shetuanList.add(shetuan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shetuanList;
	}

	/* 更新社团 */
	public String UpdateShetuan(Shetuan shetuan) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stUserName", shetuan.getStUserName());
		params.put("password", shetuan.getPassword());
		params.put("shetuanName", shetuan.getShetuanName());
		params.put("shetuanPhoto", shetuan.getShetuanPhoto());
		params.put("shetuanDesc", shetuan.getShetuanDesc());
		params.put("bornDate", shetuan.getBornDate().toString());
		params.put("fuzeren", shetuan.getFuzeren());
		params.put("telephone", shetuan.getTelephone());
		params.put("shetuanMemo", shetuan.getShetuanMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ShetuanServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除社团 */
	public String DeleteShetuan(String stUserName) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stUserName", stUserName);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ShetuanServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "社团信息删除失败!";
		}
	}

	/* 根据负责人账号获取社团对象 */
	public Shetuan GetShetuan(String stUserName)  {
		List<Shetuan> shetuanList = new ArrayList<Shetuan>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stUserName", stUserName);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ShetuanServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Shetuan shetuan = new Shetuan();
				shetuan.setStUserName(object.getString("stUserName"));
				shetuan.setPassword(object.getString("password"));
				shetuan.setShetuanName(object.getString("shetuanName"));
				shetuan.setShetuanPhoto(object.getString("shetuanPhoto"));
				shetuan.setShetuanDesc(object.getString("shetuanDesc"));
				shetuan.setBornDate(Timestamp.valueOf(object.getString("bornDate")));
				shetuan.setFuzeren(object.getString("fuzeren"));
				shetuan.setTelephone(object.getString("telephone"));
				shetuan.setShetuanMemo(object.getString("shetuanMemo"));
				shetuanList.add(shetuan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = shetuanList.size();
		if(size>0) return shetuanList.get(0); 
		else return null; 
	}
}
