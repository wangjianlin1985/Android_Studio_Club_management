package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.ClassInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class ClassInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明班级编号输入框
	private EditText ET_classNo;
	// 声明班级名称输入框
	private EditText ET_className;
	// 声明所在学院输入框
	private EditText ET_collegeName;
	// 声明所属专业输入框
	private EditText ET_specialName;
	/*查询过滤条件保存到这个对象中*/
	private ClassInfo queryConditionClassInfo = new ClassInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.classinfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置班级查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_classNo = (EditText) findViewById(R.id.ET_classNo);
		ET_className = (EditText) findViewById(R.id.ET_className);
		ET_collegeName = (EditText) findViewById(R.id.ET_collegeName);
		ET_specialName = (EditText) findViewById(R.id.ET_specialName);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionClassInfo.setClassNo(ET_classNo.getText().toString());
					queryConditionClassInfo.setClassName(ET_className.getText().toString());
					queryConditionClassInfo.setCollegeName(ET_collegeName.getText().toString());
					queryConditionClassInfo.setSpecialName(ET_specialName.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionClassInfo", queryConditionClassInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
