package com.mobileclient.activity;

import java.util.Date;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Shetuan;
import com.mobileclient.service.ShetuanService;
import com.mobileclient.util.ActivityUtils;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.content.Intent;
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
public class ShetuanDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn,btnShenqing;
	// 声明负责人账号控件
	private TextView TV_stUserName;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明社团名称控件
	private TextView TV_shetuanName;
	// 声明社团logo图片框
	private ImageView iv_shetuanPhoto;
	// 声明社团简介控件
	private TextView TV_shetuanDesc;
	// 声明成立日期控件
	private TextView TV_bornDate;
	// 声明负责人控件
	private TextView TV_fuzeren;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明社团备注控件
	private TextView TV_shetuanMemo;
	/* 要保存的社团信息 */
	Shetuan shetuan = new Shetuan(); 
	/* 社团管理业务逻辑层 */
	private ShetuanService shetuanService = new ShetuanService();
	private String stUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.shetuan_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看社团详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		btnShenqing = (Button) findViewById(R.id.BtnShenqing);
		TV_stUserName = (TextView) findViewById(R.id.TV_stUserName);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_shetuanName = (TextView) findViewById(R.id.TV_shetuanName);
		iv_shetuanPhoto = (ImageView) findViewById(R.id.iv_shetuanPhoto); 
		TV_shetuanDesc = (TextView) findViewById(R.id.TV_shetuanDesc);
		TV_bornDate = (TextView) findViewById(R.id.TV_bornDate);
		TV_fuzeren = (TextView) findViewById(R.id.TV_fuzeren);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_shetuanMemo = (TextView) findViewById(R.id.TV_shetuanMemo);
		Bundle extras = this.getIntent().getExtras();
		stUserName = extras.getString("stUserName");
		
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ShetuanDetailActivity.this.finish();
			}
		}); 
		
		Declare declare = (Declare)ShetuanDetailActivity.this.getApplication();
		if(declare.getIdentify().equals("user")) btnShenqing.setVisibility(View.VISIBLE);
		btnShenqing.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//ShetuanDetailActivity.this.finish();
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("stUserName", stUserName);
				intent.putExtras(bundle);
				intent.setClass(ShetuanDetailActivity.this, ShenqingUserAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		}); 
		
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    shetuan = shetuanService.GetShetuan(stUserName); 
		this.TV_stUserName.setText(shetuan.getStUserName());
		this.TV_password.setText(shetuan.getPassword());
		this.TV_shetuanName.setText(shetuan.getShetuanName());
		byte[] shetuanPhoto_data = null;
		try {
			// 获取图片数据
			shetuanPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + shetuan.getShetuanPhoto());
			Bitmap shetuanPhoto = BitmapFactory.decodeByteArray(shetuanPhoto_data, 0,shetuanPhoto_data.length);
			this.iv_shetuanPhoto.setImageBitmap(shetuanPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_shetuanDesc.setText(shetuan.getShetuanDesc());
		Date bornDate = new Date(shetuan.getBornDate().getTime());
		String bornDateStr = (bornDate.getYear() + 1900) + "-" + (bornDate.getMonth()+1) + "-" + bornDate.getDate();
		this.TV_bornDate.setText(bornDateStr);
		this.TV_fuzeren.setText(shetuan.getFuzeren());
		this.TV_telephone.setText(shetuan.getTelephone());
		this.TV_shetuanMemo.setText(shetuan.getShetuanMemo());
	} 
}
