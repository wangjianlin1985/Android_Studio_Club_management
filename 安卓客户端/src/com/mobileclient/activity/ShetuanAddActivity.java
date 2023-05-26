package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Shetuan;
import com.mobileclient.service.ShetuanService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class ShetuanAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明负责人账号输入框
	private EditText ET_stUserName;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明社团名称输入框
	private EditText ET_shetuanName;
	// 声明社团logo图片框控件
	private ImageView iv_shetuanPhoto;
	private Button btn_shetuanPhoto;
	protected int REQ_CODE_SELECT_IMAGE_shetuanPhoto = 1;
	private int REQ_CODE_CAMERA_shetuanPhoto = 2;
	// 声明社团简介输入框
	private EditText ET_shetuanDesc;
	// 出版成立日期控件
	private DatePicker dp_bornDate;
	// 声明负责人输入框
	private EditText ET_fuzeren;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明社团备注输入框
	private EditText ET_shetuanMemo;
	protected String carmera_path;
	/*要保存的社团信息*/
	Shetuan shetuan = new Shetuan();
	/*社团管理业务逻辑层*/
	private ShetuanService shetuanService = new ShetuanService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.shetuan_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加社团");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_stUserName = (EditText) findViewById(R.id.ET_stUserName);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_shetuanName = (EditText) findViewById(R.id.ET_shetuanName);
		iv_shetuanPhoto = (ImageView) findViewById(R.id.iv_shetuanPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_shetuanPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ShetuanAddActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_shetuanPhoto);
			}
		});
		btn_shetuanPhoto = (Button) findViewById(R.id.btn_shetuanPhoto);
		btn_shetuanPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_shetuanPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_shetuanPhoto);  
			}
		});
		ET_shetuanDesc = (EditText) findViewById(R.id.ET_shetuanDesc);
		dp_bornDate = (DatePicker)this.findViewById(R.id.dp_bornDate);
		ET_fuzeren = (EditText) findViewById(R.id.ET_fuzeren);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_shetuanMemo = (EditText) findViewById(R.id.ET_shetuanMemo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加社团按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取负责人账号*/
					if(ET_stUserName.getText().toString().equals("")) {
						Toast.makeText(ShetuanAddActivity.this, "负责人账号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_stUserName.setFocusable(true);
						ET_stUserName.requestFocus();
						return;
					}
					shetuan.setStUserName(ET_stUserName.getText().toString());
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(ShetuanAddActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					shetuan.setPassword(ET_password.getText().toString());
					/*验证获取社团名称*/ 
					if(ET_shetuanName.getText().toString().equals("")) {
						Toast.makeText(ShetuanAddActivity.this, "社团名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_shetuanName.setFocusable(true);
						ET_shetuanName.requestFocus();
						return;	
					}
					shetuan.setShetuanName(ET_shetuanName.getText().toString());
					if(shetuan.getShetuanPhoto() != null) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						ShetuanAddActivity.this.setTitle("正在上传图片，稍等...");
						String shetuanPhoto = HttpUtil.uploadFile(shetuan.getShetuanPhoto());
						ShetuanAddActivity.this.setTitle("图片上传完毕！");
						shetuan.setShetuanPhoto(shetuanPhoto);
					} else {
						shetuan.setShetuanPhoto("upload/noimage.jpg");
					}
					/*验证获取社团简介*/ 
					if(ET_shetuanDesc.getText().toString().equals("")) {
						Toast.makeText(ShetuanAddActivity.this, "社团简介输入不能为空!", Toast.LENGTH_LONG).show();
						ET_shetuanDesc.setFocusable(true);
						ET_shetuanDesc.requestFocus();
						return;	
					}
					shetuan.setShetuanDesc(ET_shetuanDesc.getText().toString());
					/*获取成立日期*/
					Date bornDate = new Date(dp_bornDate.getYear()-1900,dp_bornDate.getMonth(),dp_bornDate.getDayOfMonth());
					shetuan.setBornDate(new Timestamp(bornDate.getTime()));
					/*验证获取负责人*/ 
					if(ET_fuzeren.getText().toString().equals("")) {
						Toast.makeText(ShetuanAddActivity.this, "负责人输入不能为空!", Toast.LENGTH_LONG).show();
						ET_fuzeren.setFocusable(true);
						ET_fuzeren.requestFocus();
						return;	
					}
					shetuan.setFuzeren(ET_fuzeren.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(ShetuanAddActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					shetuan.setTelephone(ET_telephone.getText().toString());
					/*验证获取社团备注*/ 
					if(ET_shetuanMemo.getText().toString().equals("")) {
						Toast.makeText(ShetuanAddActivity.this, "社团备注输入不能为空!", Toast.LENGTH_LONG).show();
						ET_shetuanMemo.setFocusable(true);
						ET_shetuanMemo.requestFocus();
						return;	
					}
					shetuan.setShetuanMemo(ET_shetuanMemo.getText().toString());
					/*调用业务逻辑层上传社团信息*/
					ShetuanAddActivity.this.setTitle("正在上传社团信息，稍等...");
					String result = shetuanService.AddShetuan(shetuan);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_shetuanPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_shetuanPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_shetuanPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_shetuanPhoto.setImageBitmap(booImageBm);
				this.iv_shetuanPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.shetuan.setShetuanPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_shetuanPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_shetuanPhoto.setImageBitmap(bm); 
				this.iv_shetuanPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			shetuan.setShetuanPhoto(filename); 
		}
	}
}
