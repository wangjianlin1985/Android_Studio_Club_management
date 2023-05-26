package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.ClassInfoService;
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

public class UserInfoSimpleAdapter extends SimpleAdapter { 
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

    public UserInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.userinfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_user_name = (TextView)convertView.findViewById(R.id.tv_user_name);
	  holder.tv_classObj = (TextView)convertView.findViewById(R.id.tv_classObj);
	  holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
	  holder.tv_gender = (TextView)convertView.findViewById(R.id.tv_gender);
	  holder.tv_birthDate = (TextView)convertView.findViewById(R.id.tv_birthDate);
	  holder.iv_userPhoto = (ImageView)convertView.findViewById(R.id.iv_userPhoto);
	  holder.tv_telephone = (TextView)convertView.findViewById(R.id.tv_telephone);
	  /*设置各个控件的展示内容*/
	  holder.tv_user_name.setText("学号：" + mData.get(position).get("user_name").toString());
	  holder.tv_classObj.setText("所在班级：" + (new ClassInfoService()).GetClassInfo(mData.get(position).get("classObj").toString()).getClassName());
	  holder.tv_name.setText("姓名：" + mData.get(position).get("name").toString());
	  holder.tv_gender.setText("性别：" + mData.get(position).get("gender").toString());
	  try {holder.tv_birthDate.setText("出生日期：" + mData.get(position).get("birthDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.iv_userPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener userPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_userPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("userPhoto"),userPhotoLoadListener);  
	  holder.tv_telephone.setText("联系电话：" + mData.get(position).get("telephone").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_user_name;
    	TextView tv_classObj;
    	TextView tv_name;
    	TextView tv_gender;
    	TextView tv_birthDate;
    	ImageView iv_userPhoto;
    	TextView tv_telephone;
    }
} 
