package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.ClassInfo;
import com.mobileclient.service.ClassInfoService;
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
public class ClassInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明班级编号输入框
	private EditText ET_classNo;
	// 声明班级名称输入框
	private EditText ET_className;
	// 声明所在学院输入框
	private EditText ET_collegeName;
	// 声明所属专业输入框
	private EditText ET_specialName;
	// 出版成立日期控件
	private DatePicker dp_bornDate;
	// 声明班主任输入框
	private EditText ET_banzhuren;
	protected String carmera_path;
	/*要保存的班级信息*/
	ClassInfo classInfo = new ClassInfo();
	/*班级管理业务逻辑层*/
	private ClassInfoService classInfoService = new ClassInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.classinfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加班级");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_classNo = (EditText) findViewById(R.id.ET_classNo);
		ET_className = (EditText) findViewById(R.id.ET_className);
		ET_collegeName = (EditText) findViewById(R.id.ET_collegeName);
		ET_specialName = (EditText) findViewById(R.id.ET_specialName);
		dp_bornDate = (DatePicker)this.findViewById(R.id.dp_bornDate);
		ET_banzhuren = (EditText) findViewById(R.id.ET_banzhuren);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加班级按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取班级编号*/
					if(ET_classNo.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "班级编号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_classNo.setFocusable(true);
						ET_classNo.requestFocus();
						return;
					}
					classInfo.setClassNo(ET_classNo.getText().toString());
					/*验证获取班级名称*/ 
					if(ET_className.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "班级名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_className.setFocusable(true);
						ET_className.requestFocus();
						return;	
					}
					classInfo.setClassName(ET_className.getText().toString());
					/*验证获取所在学院*/ 
					if(ET_collegeName.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "所在学院输入不能为空!", Toast.LENGTH_LONG).show();
						ET_collegeName.setFocusable(true);
						ET_collegeName.requestFocus();
						return;	
					}
					classInfo.setCollegeName(ET_collegeName.getText().toString());
					/*验证获取所属专业*/ 
					if(ET_specialName.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "所属专业输入不能为空!", Toast.LENGTH_LONG).show();
						ET_specialName.setFocusable(true);
						ET_specialName.requestFocus();
						return;	
					}
					classInfo.setSpecialName(ET_specialName.getText().toString());
					/*获取成立日期*/
					Date bornDate = new Date(dp_bornDate.getYear()-1900,dp_bornDate.getMonth(),dp_bornDate.getDayOfMonth());
					classInfo.setBornDate(new Timestamp(bornDate.getTime()));
					/*验证获取班主任*/ 
					if(ET_banzhuren.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "班主任输入不能为空!", Toast.LENGTH_LONG).show();
						ET_banzhuren.setFocusable(true);
						ET_banzhuren.requestFocus();
						return;	
					}
					classInfo.setBanzhuren(ET_banzhuren.getText().toString());
					/*调用业务逻辑层上传班级信息*/
					ClassInfoAddActivity.this.setTitle("正在上传班级信息，稍等...");
					String result = classInfoService.AddClassInfo(classInfo);
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
	}
}
