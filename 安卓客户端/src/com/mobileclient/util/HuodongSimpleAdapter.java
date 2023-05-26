package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.ShetuanService;
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

public class HuodongSimpleAdapter extends SimpleAdapter { 
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

    public HuodongSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.huodong_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_huodongName = (TextView)convertView.findViewById(R.id.tv_huodongName);
	  holder.tv_huodongTime = (TextView)convertView.findViewById(R.id.tv_huodongTime);
	  holder.tv_shetuanObj = (TextView)convertView.findViewById(R.id.tv_shetuanObj);
	  /*设置各个控件的展示内容*/
	  holder.tv_huodongName.setText("活动名称：" + mData.get(position).get("huodongName").toString());
	  try {holder.tv_huodongTime.setText("活动时间：" + mData.get(position).get("huodongTime").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_shetuanObj.setText("活动社团：" + (new ShetuanService()).GetShetuan(mData.get(position).get("shetuanObj").toString()).getShetuanName());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_huodongName;
    	TextView tv_huodongTime;
    	TextView tv_shetuanObj;
    }
} 
