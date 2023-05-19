package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Article;
import com.mobileclient.service.ArticleService;
import com.mobileclient.domain.ArticleClass;
import com.mobileclient.service.ArticleClassService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
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
public class ArticleDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ��������id�ؼ�
	private TextView TV_articleId;
	// ��������ؼ�
	private TextView TV_title;
	// �������·���ؼ�
	private TextView TV_articleClassObj;
	// �������ݿؼ�
	private TextView TV_content;
	// ��������ʿؼ�
	private TextView TV_hitNum;
	// ���������û��ؼ�
	private TextView TV_userObj;
	// ��������ʱ��ؼ�
	private TextView TV_addTime;
	/* Ҫ�����������Ϣ */
	Article article = new Article(); 
	/* ���¹���ҵ���߼��� */
	private ArticleService articleService = new ArticleService();
	private ArticleClassService articleClassService = new ArticleClassService();
	private UserInfoService userInfoService = new UserInfoService();
	private int articleId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.article_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴��������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_articleId = (TextView) findViewById(R.id.TV_articleId);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_articleClassObj = (TextView) findViewById(R.id.TV_articleClassObj);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_hitNum = (TextView) findViewById(R.id.TV_hitNum);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_addTime = (TextView) findViewById(R.id.TV_addTime);
		Bundle extras = this.getIntent().getExtras();
		articleId = extras.getInt("articleId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ArticleDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    article = articleService.GetArticle(articleId); 
	    article.setHitNum(article.getHitNum() + 1);
	    articleService.UpdateArticle(article);
		this.TV_articleId.setText(article.getArticleId() + "");
		this.TV_title.setText(article.getTitle());
		ArticleClass articleClassObj = articleClassService.GetArticleClass(article.getArticleClassObj());
		this.TV_articleClassObj.setText(articleClassObj.getArticleClassName());
		this.TV_content.setText(article.getContent());
		this.TV_hitNum.setText(article.getHitNum() + "");
		UserInfo userObj = userInfoService.GetUserInfo(article.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_addTime.setText(article.getAddTime());
	} 
}
