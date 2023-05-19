package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Flzs;

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
public class FlzsQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// ����֪ʶ���������
	private EditText ET_title;
	// �������������
	private EditText ET_author;
	// ���������������
	private EditText ET_publish;
	// �������ڿؼ�
	private DatePicker dp_publishDate;
	private CheckBox cb_publishDate;
	/*��ѯ�����������浽���������*/
	private Flzs queryConditionFlzs = new Flzs();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.flzs_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("���÷���֪ʶ��ѯ����");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_author = (EditText) findViewById(R.id.ET_author);
		ET_publish = (EditText) findViewById(R.id.ET_publish);
		dp_publishDate = (DatePicker) findViewById(R.id.dp_publishDate);
		cb_publishDate = (CheckBox) findViewById(R.id.cb_publishDate);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionFlzs.setTitle(ET_title.getText().toString());
					queryConditionFlzs.setAuthor(ET_author.getText().toString());
					queryConditionFlzs.setPublish(ET_publish.getText().toString());
					if(cb_publishDate.isChecked()) {
						/*��ȡ��������*/
						Date publishDate = new Date(dp_publishDate.getYear()-1900,dp_publishDate.getMonth(),dp_publishDate.getDayOfMonth());
						queryConditionFlzs.setPublishDate(new Timestamp(publishDate.getTime()));
					} else {
						queryConditionFlzs.setPublishDate(null);
					} 
					Intent intent = getIntent();
					//����ʹ��bundle��������������
					Bundle bundle =new Bundle();
					//�����������Ȼ�Ǽ�ֵ�Ե���ʽ
					bundle.putSerializable("queryConditionFlzs", queryConditionFlzs);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
