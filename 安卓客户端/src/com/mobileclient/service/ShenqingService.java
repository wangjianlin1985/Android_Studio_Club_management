package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Shenqing;
import com.mobileclient.util.HttpUtil;

/*社团申请管理业务逻辑层*/
public class ShenqingService {
	/* 添加社团申请 */
	public String AddShenqing(Shenqing shenqing) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("shenqingId", shenqing.getShenqingId() + "");
		params.put("shentuanObj", shenqing.getShentuanObj());
		params.put("name", shenqing.getName());
		params.put("xuehao", shenqing.getXuehao());
		params.put("zysj", shenqing.getZysj());
		params.put("rhyy", shenqing.getRhyy());
		params.put("userObj", shenqing.getUserObj());
		params.put("sqTime", shenqing.getSqTime());
		params.put("shenHeState", shenqing.getShenHeState());
		params.put("shenHeResult", shenqing.getShenHeResult());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ShenqingServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询社团申请 */
	public List<Shenqing> QueryShenqing(Shenqing queryConditionShenqing) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ShenqingServlet?action=query";
		if(queryConditionShenqing != null) {
			urlString += "&shentuanObj=" + URLEncoder.encode(queryConditionShenqing.getShentuanObj(), "UTF-8") + "";
			urlString += "&name=" + URLEncoder.encode(queryConditionShenqing.getName(), "UTF-8") + "";
			urlString += "&xuehao=" + URLEncoder.encode(queryConditionShenqing.getXuehao(), "UTF-8") + "";
			urlString += "&userObj=" + URLEncoder.encode(queryConditionShenqing.getUserObj(), "UTF-8") + "";
			urlString += "&sqTime=" + URLEncoder.encode(queryConditionShenqing.getSqTime(), "UTF-8") + "";
			urlString += "&shenHeState=" + URLEncoder.encode(queryConditionShenqing.getShenHeState(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ShenqingListHandler shenqingListHander = new ShenqingListHandler();
		xr.setContentHandler(shenqingListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Shenqing> shenqingList = shenqingListHander.getShenqingList();
		return shenqingList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Shenqing> shenqingList = new ArrayList<Shenqing>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Shenqing shenqing = new Shenqing();
				shenqing.setShenqingId(object.getInt("shenqingId"));
				shenqing.setShentuanObj(object.getString("shentuanObj"));
				shenqing.setName(object.getString("name"));
				shenqing.setXuehao(object.getString("xuehao"));
				shenqing.setZysj(object.getString("zysj"));
				shenqing.setRhyy(object.getString("rhyy"));
				shenqing.setUserObj(object.getString("userObj"));
				shenqing.setSqTime(object.getString("sqTime"));
				shenqing.setShenHeState(object.getString("shenHeState"));
				shenqing.setShenHeResult(object.getString("shenHeResult"));
				shenqingList.add(shenqing);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shenqingList;
	}

	/* 更新社团申请 */
	public String UpdateShenqing(Shenqing shenqing) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("shenqingId", shenqing.getShenqingId() + "");
		params.put("shentuanObj", shenqing.getShentuanObj());
		params.put("name", shenqing.getName());
		params.put("xuehao", shenqing.getXuehao());
		params.put("zysj", shenqing.getZysj());
		params.put("rhyy", shenqing.getRhyy());
		params.put("userObj", shenqing.getUserObj());
		params.put("sqTime", shenqing.getSqTime());
		params.put("shenHeState", shenqing.getShenHeState());
		params.put("shenHeResult", shenqing.getShenHeResult());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ShenqingServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除社团申请 */
	public String DeleteShenqing(int shenqingId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("shenqingId", shenqingId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ShenqingServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "社团申请信息删除失败!";
		}
	}

	/* 根据申请id获取社团申请对象 */
	public Shenqing GetShenqing(int shenqingId)  {
		List<Shenqing> shenqingList = new ArrayList<Shenqing>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("shenqingId", shenqingId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ShenqingServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Shenqing shenqing = new Shenqing();
				shenqing.setShenqingId(object.getInt("shenqingId"));
				shenqing.setShentuanObj(object.getString("shentuanObj"));
				shenqing.setName(object.getString("name"));
				shenqing.setXuehao(object.getString("xuehao"));
				shenqing.setZysj(object.getString("zysj"));
				shenqing.setRhyy(object.getString("rhyy"));
				shenqing.setUserObj(object.getString("userObj"));
				shenqing.setSqTime(object.getString("sqTime"));
				shenqing.setShenHeState(object.getString("shenHeState"));
				shenqing.setShenHeResult(object.getString("shenHeResult"));
				shenqingList.add(shenqing);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = shenqingList.size();
		if(size>0) return shenqingList.get(0); 
		else return null; 
	}
}
