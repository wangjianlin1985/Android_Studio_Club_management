package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

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

public class ShetuanSimpleAdapter extends SimpleAdapter { 
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

    public ShetuanSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.shetuan_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_stUserName = (TextView)convertView.findViewById(R.id.tv_stUserName);
	  holder.tv_shetuanName = (TextView)convertView.findViewById(R.id.tv_shetuanName);
	  holder.iv_shetuanPhoto = (ImageView)convertView.findViewById(R.id.iv_shetuanPhoto);
	  holder.tv_bornDate = (TextView)convertView.findViewById(R.id.tv_bornDate);
	  holder.tv_fuzeren = (TextView)convertView.findViewById(R.id.tv_fuzeren);
	  /*设置各个控件的展示内容*/
	  holder.tv_stUserName.setText("负责人账号：" + mData.get(position).get("stUserName").toString());
	  holder.tv_shetuanName.setText("社团名称：" + mData.get(position).get("shetuanName").toString());
	  holder.iv_shetuanPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener shetuanPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_shetuanPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("shetuanPhoto"),shetuanPhotoLoadListener);  
	  try {holder.tv_bornDate.setText("成立日期：" + mData.get(position).get("bornDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_fuzeren.setText("负责人：" + mData.get(position).get("fuzeren").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_stUserName;
    	TextView tv_shetuanName;
    	ImageView iv_shetuanPhoto;
    	TextView tv_bornDate;
    	TextView tv_fuzeren;
    }
} 
