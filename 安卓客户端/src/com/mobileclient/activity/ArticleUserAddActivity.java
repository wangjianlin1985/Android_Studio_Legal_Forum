package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Article;
import com.mobileclient.service.ArticleService;
import com.mobileclient.domain.ArticleClass;
import com.mobileclient.service.ArticleClassService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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
public class ArticleUserAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �������������
	private EditText ET_title;
	// �������·���������
	private Spinner spinner_articleClassObj;
	private ArrayAdapter<String> articleClassObj_adapter;
	private static  String[] articleClassObj_ShowText  = null;
	private List<ArticleClass> articleClassList = null;
	/*���·������ҵ���߼���*/
	private ArticleClassService articleClassService = new ArticleClassService();
	// �������������
	private EditText ET_content;
	 
	protected String carmera_path;
	/*Ҫ�����������Ϣ*/
	Article article = new Article();
	/*���¹���ҵ���߼���*/
	private ArticleService articleService = new ArticleService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.article_user_add); 
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
		spinner_articleClassObj = (Spinner) findViewById(R.id.Spinner_articleClassObj);
		// ��ȡ���е����·���
		try {
			articleClassList = articleClassService.QueryArticleClass(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int articleClassCount = articleClassList.size();
		articleClassObj_ShowText = new String[articleClassCount];
		for(int i=0;i<articleClassCount;i++) { 
			articleClassObj_ShowText[i] = articleClassList.get(i).getArticleClassName();
		}
		// ����ѡ������ArrayAdapter��������
		articleClassObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, articleClassObj_ShowText);
		// ���������б�ķ��
		articleClassObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_articleClassObj.setAdapter(articleClassObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_articleClassObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				article.setArticleClassObj(articleClassList.get(arg2).getArticleClassId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_articleClassObj.setVisibility(View.VISIBLE);
		ET_content = (EditText) findViewById(R.id.ET_content);
	
		Declare declare = (Declare) ArticleUserAddActivity.this.getApplication();
		article.setUserObj(declare.getUserName()); 
			 
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*����������°�ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ����*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(ArticleUserAddActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					article.setTitle(ET_title.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(ArticleUserAddActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					article.setContent(ET_content.getText().toString());
					/*��ȡ�����*/
					article.setHitNum(0);
					/*����ʱ��*/
					article.setAddTime("--");
					/*����ҵ���߼����ϴ�������Ϣ*/
					ArticleUserAddActivity.this.setTitle("�����ϴ�������Ϣ���Ե�...");
					String result = articleService.AddArticle(article);
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
