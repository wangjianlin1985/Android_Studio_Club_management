package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Huodong;
import com.mobileclient.service.HuodongService;
import com.mobileclient.domain.Shetuan;
import com.mobileclient.service.ShetuanService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class HuodongDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明活动id控件
	private TextView TV_huodongId;
	// 声明活动名称控件
	private TextView TV_huodongName;
	// 声明活动内容控件
	private TextView TV_huodongDesc;
	// 声明活动时间控件
	private TextView TV_huodongTime;
	// 声明活动社团控件
	private TextView TV_shetuanObj;
	// 声明活动备注控件
	private TextView TV_huodongMemo;
	/* 要保存的社团活动信息 */
	Huodong huodong = new Huodong(); 
	/* 社团活动管理业务逻辑层 */
	private HuodongService huodongService = new HuodongService();
	private ShetuanService shetuanService = new ShetuanService();
	private int huodongId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.huodong_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看社团活动详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_huodongId = (TextView) findViewById(R.id.TV_huodongId);
		TV_huodongName = (TextView) findViewById(R.id.TV_huodongName);
		TV_huodongDesc = (TextView) findViewById(R.id.TV_huodongDesc);
		TV_huodongTime = (TextView) findViewById(R.id.TV_huodongTime);
		TV_shetuanObj = (TextView) findViewById(R.id.TV_shetuanObj);
		TV_huodongMemo = (TextView) findViewById(R.id.TV_huodongMemo);
		Bundle extras = this.getIntent().getExtras();
		huodongId = extras.getInt("huodongId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				HuodongDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    huodong = huodongService.GetHuodong(huodongId); 
		this.TV_huodongId.setText(huodong.getHuodongId() + "");
		this.TV_huodongName.setText(huodong.getHuodongName());
		this.TV_huodongDesc.setText(huodong.getHuodongDesc());
		Date huodongTime = new Date(huodong.getHuodongTime().getTime());
		String huodongTimeStr = (huodongTime.getYear() + 1900) + "-" + (huodongTime.getMonth()+1) + "-" + huodongTime.getDate();
		this.TV_huodongTime.setText(huodongTimeStr);
		Shetuan shetuanObj = shetuanService.GetShetuan(huodong.getShetuanObj());
		this.TV_shetuanObj.setText(shetuanObj.getShetuanName());
		this.TV_huodongMemo.setText(huodong.getHuodongMemo());
	} 
}
