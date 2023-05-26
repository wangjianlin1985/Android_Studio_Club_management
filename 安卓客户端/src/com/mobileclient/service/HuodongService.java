package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Huodong;
import com.mobileclient.util.HttpUtil;

/*社团活动管理业务逻辑层*/
public class HuodongService {
	/* 添加社团活动 */
	public String AddHuodong(Huodong huodong) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("huodongId", huodong.getHuodongId() + "");
		params.put("huodongName", huodong.getHuodongName());
		params.put("huodongDesc", huodong.getHuodongDesc());
		params.put("huodongTime", huodong.getHuodongTime().toString());
		params.put("shetuanObj", huodong.getShetuanObj());
		params.put("huodongMemo", huodong.getHuodongMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HuodongServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询社团活动 */
	public List<Huodong> QueryHuodong(Huodong queryConditionHuodong) throws Exception {
		String urlString = HttpUtil.BASE_URL + "HuodongServlet?action=query";
		if(queryConditionHuodong != null) {
			urlString += "&huodongName=" + URLEncoder.encode(queryConditionHuodong.getHuodongName(), "UTF-8") + "";
			if(queryConditionHuodong.getHuodongTime() != null) {
				urlString += "&huodongTime=" + URLEncoder.encode(queryConditionHuodong.getHuodongTime().toString(), "UTF-8");
			}
			urlString += "&shetuanObj=" + URLEncoder.encode(queryConditionHuodong.getShetuanObj(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		HuodongListHandler huodongListHander = new HuodongListHandler();
		xr.setContentHandler(huodongListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Huodong> huodongList = huodongListHander.getHuodongList();
		return huodongList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Huodong> huodongList = new ArrayList<Huodong>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Huodong huodong = new Huodong();
				huodong.setHuodongId(object.getInt("huodongId"));
				huodong.setHuodongName(object.getString("huodongName"));
				huodong.setHuodongDesc(object.getString("huodongDesc"));
				huodong.setHuodongTime(Timestamp.valueOf(object.getString("huodongTime")));
				huodong.setShetuanObj(object.getString("shetuanObj"));
				huodong.setHuodongMemo(object.getString("huodongMemo"));
				huodongList.add(huodong);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return huodongList;
	}

	/* 更新社团活动 */
	public String UpdateHuodong(Huodong huodong) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("huodongId", huodong.getHuodongId() + "");
		params.put("huodongName", huodong.getHuodongName());
		params.put("huodongDesc", huodong.getHuodongDesc());
		params.put("huodongTime", huodong.getHuodongTime().toString());
		params.put("shetuanObj", huodong.getShetuanObj());
		params.put("huodongMemo", huodong.getHuodongMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HuodongServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除社团活动 */
	public String DeleteHuodong(int huodongId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("huodongId", huodongId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HuodongServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "社团活动信息删除失败!";
		}
	}

	/* 根据活动id获取社团活动对象 */
	public Huodong GetHuodong(int huodongId)  {
		List<Huodong> huodongList = new ArrayList<Huodong>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("huodongId", huodongId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HuodongServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Huodong huodong = new Huodong();
				huodong.setHuodongId(object.getInt("huodongId"));
				huodong.setHuodongName(object.getString("huodongName"));
				huodong.setHuodongDesc(object.getString("huodongDesc"));
				huodong.setHuodongTime(Timestamp.valueOf(object.getString("huodongTime")));
				huodong.setShetuanObj(object.getString("shetuanObj"));
				huodong.setHuodongMemo(object.getString("huodongMemo"));
				huodongList.add(huodong);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = huodongList.size();
		if(size>0) return huodongList.get(0); 
		else return null; 
	}
}
