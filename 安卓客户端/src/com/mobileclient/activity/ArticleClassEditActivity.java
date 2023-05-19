package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.ArticleClass;
import com.mobileclient.service.ArticleClassService;
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

public class ArticleClassEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// �������·���idTextView
	private TextView TV_articleClassId;
	// �������·������������
	private EditText ET_articleClassName;
	protected String carmera_path;
	/*Ҫ��������·�����Ϣ*/
	ArticleClass articleClass = new ArticleClass();
	/*���·������ҵ���߼���*/
	private ArticleClassService articleClassService = new ArticleClassService();

	private int articleClassId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.articleclass_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭���·�����Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_articleClassId = (TextView) findViewById(R.id.TV_articleClassId);
		ET_articleClassName = (EditText) findViewById(R.id.ET_articleClassName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		articleClassId = extras.getInt("articleClassId");
		/*�����޸����·��ఴť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ���·�������*/ 
					if(ET_articleClassName.getText().toString().equals("")) {
						Toast.makeText(ArticleClassEditActivity.this, "���·����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_articleClassName.setFocusable(true);
						ET_articleClassName.requestFocus();
						return;	
					}
					articleClass.setArticleClassName(ET_articleClassName.getText().toString());
					/*����ҵ���߼����ϴ����·�����Ϣ*/
					ArticleClassEditActivity.this.setTitle("���ڸ������·�����Ϣ���Ե�...");
					String result = articleClassService.UpdateArticleClass(articleClass);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    articleClass = articleClassService.GetArticleClass(articleClassId);
		this.TV_articleClassId.setText(articleClassId+"");
		this.ET_articleClassName.setText(articleClass.getArticleClassName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
