package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.News;
import com.mobileclient.service.NewsService;
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
public class NewsAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �������������
	private EditText ET_title;
	// �����������������
	private EditText ET_content;
	// ��������������
	private EditText ET_viewNum;
	// ��������ʱ�������
	private EditText ET_publishDate;
	protected String carmera_path;
	/*Ҫ�����������Ϣ*/
	News news = new News();
	/*���Ź���ҵ���߼���*/
	private NewsService newsService = new NewsService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.news_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		ET_viewNum = (EditText) findViewById(R.id.ET_viewNum);
		ET_publishDate = (EditText) findViewById(R.id.ET_publishDate);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*����������Ű�ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ����*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(NewsAddActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					news.setTitle(ET_title.getText().toString());
					/*��֤��ȡ��������*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(NewsAddActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					news.setContent(ET_content.getText().toString());
					/*��֤��ȡ�����*/ 
					if(ET_viewNum.getText().toString().equals("")) {
						Toast.makeText(NewsAddActivity.this, "��������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_viewNum.setFocusable(true);
						ET_viewNum.requestFocus();
						return;	
					}
					news.setViewNum(Integer.parseInt(ET_viewNum.getText().toString()));
					/*��֤��ȡ����ʱ��*/ 
					if(ET_publishDate.getText().toString().equals("")) {
						Toast.makeText(NewsAddActivity.this, "����ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_publishDate.setFocusable(true);
						ET_publishDate.requestFocus();
						return;	
					}
					news.setPublishDate(ET_publishDate.getText().toString());
					/*����ҵ���߼����ϴ�������Ϣ*/
					NewsAddActivity.this.setTitle("�����ϴ�������Ϣ���Ե�...");
					String result = newsService.AddNews(news);
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
