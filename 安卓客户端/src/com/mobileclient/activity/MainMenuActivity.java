package com.mobileclient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("手机客户端-主界面");
        setContentView(R.layout.main_menu);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        
        AnimationSet set = new AnimationSet(false);
        Animation animation = new AlphaAnimation(0,1);
        animation.setDuration(500);
        set.addAnimation(animation);
        
        animation = new TranslateAnimation(1, 13, 10, 50);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new RotateAnimation(30,10);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new ScaleAnimation(5,0,2,0);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        LayoutAnimationController controller = new LayoutAnimationController(set, 1);
        
        gridview.setLayoutAnimation(controller);
        
        gridview.setAdapter(new ImageAdapter(this));
    }
    
    // 继承BaseAdapter
    public class ImageAdapter extends BaseAdapter {
    	
    	LayoutInflater inflater;
    	
    	// 上下文
        private Context mContext;
        
        // 图片资源数组
        private Integer[] mThumbIds = {
                R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon
        };
        private String[] menuString = {"用户管理","班级管理","社团管理","社团活动管理","社团申请管理","新闻公告管理"};

        // 构造方法
        public ImageAdapter(Context c) {
            mContext = c;
            inflater = LayoutInflater.from(mContext);
        }
        // 组件个数
        public int getCount() {
            return mThumbIds.length;
        }
        // 当前组件
        public Object getItem(int position) {
            return null;
        }
        // 当前组件id
        public long getItemId(int position) {
            return 0;
        }
        // 获得当前视图
        public View getView(int position, View convertView, ViewGroup parent) { 
        	View view = inflater.inflate(R.layout.gv_item, null);
			TextView tv = (TextView) view.findViewById(R.id.gv_item_appname);
			ImageView iv = (ImageView) view.findViewById(R.id.gv_item_icon);  
			tv.setText(menuString[position]); 
			iv.setImageResource(mThumbIds[position]); 
			  switch (position) {
				case 0:
					// 用户管理监听器
					view.setOnClickListener(userInfoLinstener);
					break;
				case 1:
					// 班级管理监听器
					view.setOnClickListener(classInfoLinstener);
					break;
				case 2:
					// 社团管理监听器
					view.setOnClickListener(shetuanLinstener);
					break;
				case 3:
					// 社团活动管理监听器
					view.setOnClickListener(huodongLinstener);
					break;
				case 4:
					// 社团申请管理监听器
					view.setOnClickListener(shenqingLinstener);
					break;
				case 5:
					// 新闻公告管理监听器
					view.setOnClickListener(noticeLinstener);
					break;

			 
				default:
					break;
				} 
			return view; 
        }
       
    }
    
    OnClickListener userInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动用户管理Activity
			intent.setClass(MainMenuActivity.this, UserInfoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener classInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动班级管理Activity
			intent.setClass(MainMenuActivity.this, ClassInfoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener shetuanLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动社团管理Activity
			intent.setClass(MainMenuActivity.this, ShetuanListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener huodongLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动社团活动管理Activity
			intent.setClass(MainMenuActivity.this, HuodongListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener shenqingLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动社团申请管理Activity
			intent.setClass(MainMenuActivity.this, ShenqingListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener noticeLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动新闻公告管理Activity
			intent.setClass(MainMenuActivity.this, NoticeListActivity.class);
			startActivity(intent);
		}
	};


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "重新登入");  
		menu.add(0, 2, 2, "退出"); 
		return super.onCreateOptionsMenu(menu); 
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {//重新登入 
			Intent intent = new Intent();
			intent.setClass(MainMenuActivity.this,
					LoginActivity.class);
			startActivity(intent);
		} else if (item.getItemId() == 2) {//退出
			System.exit(0);  
		} 
		return true; 
	}
}
