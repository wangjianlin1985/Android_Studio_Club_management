package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Huodong;
import com.mobileclient.service.HuodongService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class HuodongEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明活动idTextView
	private TextView TV_huodongId;
	// 声明活动名称输入框
	private EditText ET_huodongName;
	// 声明活动内容输入框
	private EditText ET_huodongDesc;
	// 出版活动时间控件
	private DatePicker dp_huodongTime;
	// 声明活动社团下拉框
	private Spinner spinner_shetuanObj;
	private ArrayAdapter<String> shetuanObj_adapter;
	private static  String[] shetuanObj_ShowText  = null;
	private List<Shetuan> shetuanList = null;
	/*活动社团管理业务逻辑层*/
	private ShetuanService shetuanService = new ShetuanService();
	// 声明活动备注输入框
	private EditText ET_huodongMemo;
	protected String carmera_path;
	/*要保存的社团活动信息*/
	Huodong huodong = new Huodong();
	/*社团活动管理业务逻辑层*/
	private HuodongService huodongService = new HuodongService();

	private int huodongId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.huodong_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑社团活动信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_huodongId = (TextView) findViewById(R.id.TV_huodongId);
		ET_huodongName = (EditText) findViewById(R.id.ET_huodongName);
		ET_huodongDesc = (EditText) findViewById(R.id.ET_huodongDesc);
		dp_huodongTime = (DatePicker)this.findViewById(R.id.dp_huodongTime);
		spinner_shetuanObj = (Spinner) findViewById(R.id.Spinner_shetuanObj);
		// 获取所有的活动社团
		try {
			shetuanList = shetuanService.QueryShetuan(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int shetuanCount = shetuanList.size();
		shetuanObj_ShowText = new String[shetuanCount];
		for(int i=0;i<shetuanCount;i++) { 
			shetuanObj_ShowText[i] = shetuanList.get(i).getShetuanName();
		}
		// 将可选内容与ArrayAdapter连接起来
		shetuanObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, shetuanObj_ShowText);
		// 设置图书类别下拉列表的风格
		shetuanObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_shetuanObj.setAdapter(shetuanObj_adapter);
		// 添加事件Spinner事件监听
		spinner_shetuanObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				huodong.setShetuanObj(shetuanList.get(arg2).getStUserName()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_shetuanObj.setVisibility(View.VISIBLE);
		ET_huodongMemo = (EditText) findViewById(R.id.ET_huodongMemo);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		huodongId = extras.getInt("huodongId");
		/*单击修改社团活动按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取活动名称*/ 
					if(ET_huodongName.getText().toString().equals("")) {
						Toast.makeText(HuodongEditActivity.this, "活动名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_huodongName.setFocusable(true);
						ET_huodongName.requestFocus();
						return;	
					}
					huodong.setHuodongName(ET_huodongName.getText().toString());
					/*验证获取活动内容*/ 
					if(ET_huodongDesc.getText().toString().equals("")) {
						Toast.makeText(HuodongEditActivity.this, "活动内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_huodongDesc.setFocusable(true);
						ET_huodongDesc.requestFocus();
						return;	
					}
					huodong.setHuodongDesc(ET_huodongDesc.getText().toString());
					/*获取出版日期*/
					Date huodongTime = new Date(dp_huodongTime.getYear()-1900,dp_huodongTime.getMonth(),dp_huodongTime.getDayOfMonth());
					huodong.setHuodongTime(new Timestamp(huodongTime.getTime()));
					/*验证获取活动备注*/ 
					if(ET_huodongMemo.getText().toString().equals("")) {
						Toast.makeText(HuodongEditActivity.this, "活动备注输入不能为空!", Toast.LENGTH_LONG).show();
						ET_huodongMemo.setFocusable(true);
						ET_huodongMemo.requestFocus();
						return;	
					}
					huodong.setHuodongMemo(ET_huodongMemo.getText().toString());
					/*调用业务逻辑层上传社团活动信息*/
					HuodongEditActivity.this.setTitle("正在更新社团活动信息，稍等...");
					String result = huodongService.UpdateHuodong(huodong);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    huodong = huodongService.GetHuodong(huodongId);
		this.TV_huodongId.setText(huodongId+"");
		this.ET_huodongName.setText(huodong.getHuodongName());
		this.ET_huodongDesc.setText(huodong.getHuodongDesc());
		Date huodongTime = new Date(huodong.getHuodongTime().getTime());
		this.dp_huodongTime.init(huodongTime.getYear() + 1900,huodongTime.getMonth(), huodongTime.getDate(), null);
		for (int i = 0; i < shetuanList.size(); i++) {
			if (huodong.getShetuanObj().equals(shetuanList.get(i).getStUserName())) {
				this.spinner_shetuanObj.setSelection(i);
				break;
			}
		}
		this.ET_huodongMemo.setText(huodong.getHuodongMemo());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
