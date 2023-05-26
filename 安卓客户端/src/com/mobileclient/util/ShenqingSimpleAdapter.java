package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.ShetuanService;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class ShenqingSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public ShenqingSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.shenqing_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_shentuanObj = (TextView)convertView.findViewById(R.id.tv_shentuanObj);
	  holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
	  holder.tv_xuehao = (TextView)convertView.findViewById(R.id.tv_xuehao);
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_sqTime = (TextView)convertView.findViewById(R.id.tv_sqTime);
	  holder.tv_shenHeState = (TextView)convertView.findViewById(R.id.tv_shenHeState);
	  /*设置各个控件的展示内容*/
	  holder.tv_shentuanObj.setText("申请的社团：" + (new ShetuanService()).GetShetuan(mData.get(position).get("shentuanObj").toString()).getShetuanName());
	  holder.tv_name.setText("姓名：" + mData.get(position).get("name").toString());
	  holder.tv_xuehao.setText("学号：" + mData.get(position).get("xuehao").toString());
	  holder.tv_userObj.setText("申请人：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getName());
	  holder.tv_sqTime.setText("申请时间：" + mData.get(position).get("sqTime").toString());
	  holder.tv_shenHeState.setText("审核状态：" + mData.get(position).get("shenHeState").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_shentuanObj;
    	TextView tv_name;
    	TextView tv_xuehao;
    	TextView tv_userObj;
    	TextView tv_sqTime;
    	TextView tv_shenHeState;
    }
} 
