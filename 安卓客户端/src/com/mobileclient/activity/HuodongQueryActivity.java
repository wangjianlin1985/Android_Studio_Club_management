package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Huodong;
import com.mobileclient.domain.Shetuan;
import com.mobileclient.service.ShetuanService;

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
public class HuodongQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明活动名称输入框
	private EditText ET_huodongName;
	// 活动时间控件
	private DatePicker dp_huodongTime;
	private CheckBox cb_huodongTime;
	// 声明活动社团下拉框
	private Spinner spinner_shetuanObj;
	private ArrayAdapter<String> shetuanObj_adapter;
	private static  String[] shetuanObj_ShowText  = null;
	private List<Shetuan> shetuanList = null; 
	/*社团管理业务逻辑层*/
	private ShetuanService shetuanService = new ShetuanService();
	/*查询过滤条件保存到这个对象中*/
	private Huodong queryConditionHuodong = new Huodong();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.huodong_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置社团活动查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_huodongName = (EditText) findViewById(R.id.ET_huodongName);
		dp_huodongTime = (DatePicker) findViewById(R.id.dp_huodongTime);
		cb_huodongTime = (CheckBox) findViewById(R.id.cb_huodongTime);
		spinner_shetuanObj = (Spinner) findViewById(R.id.Spinner_shetuanObj);
		// 获取所有的社团
		try {
			shetuanList = shetuanService.QueryShetuan(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int shetuanCount = shetuanList.size();
		shetuanObj_ShowText = new String[shetuanCount+1];
		shetuanObj_ShowText[0] = "不限制";
		for(int i=1;i<=shetuanCount;i++) { 
			shetuanObj_ShowText[i] = shetuanList.get(i-1).getShetuanName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		shetuanObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, shetuanObj_ShowText);
		// 设置活动社团下拉列表的风格
		shetuanObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_shetuanObj.setAdapter(shetuanObj_adapter);
		// 添加事件Spinner事件监听
		spinner_shetuanObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionHuodong.setShetuanObj(shetuanList.get(arg2-1).getStUserName()); 
				else
					queryConditionHuodong.setShetuanObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_shetuanObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionHuodong.setHuodongName(ET_huodongName.getText().toString());
					if(cb_huodongTime.isChecked()) {
						/*获取活动时间*/
						Date huodongTime = new Date(dp_huodongTime.getYear()-1900,dp_huodongTime.getMonth(),dp_huodongTime.getDayOfMonth());
						queryConditionHuodong.setHuodongTime(new Timestamp(huodongTime.getTime()));
					} else {
						queryConditionHuodong.setHuodongTime(null);
					} 
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionHuodong", queryConditionHuodong);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
