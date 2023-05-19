package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Flzs;
import com.mobileclient.service.FlzsService;
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
public class FlzsDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录id控件
	private TextView TV_zsId;
	// 声明知识标题控件
	private TextView TV_title;
	// 声明知识图片图片框
	private ImageView iv_zsPhoto;
	// 声明知识简介控件
	private TextView TV_zsDesc;
	// 声明作者控件
	private TextView TV_author;
	// 声明出版社控件
	private TextView TV_publish;
	// 声明出版日期控件
	private TextView TV_publishDate;
	// 声明阅读量控件
	private TextView TV_viewNum;
	// 声明知识文件控件
	private TextView TV_zsFile;
	private Button btnDownZsFile;
	/* 要保存的法律知识信息 */
	Flzs flzs = new Flzs(); 
	/* 法律知识管理业务逻辑层 */
	private FlzsService flzsService = new FlzsService();
	private int zsId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.flzs_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看法律知识详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_zsId = (TextView) findViewById(R.id.TV_zsId);
		TV_title = (TextView) findViewById(R.id.TV_title);
		iv_zsPhoto = (ImageView) findViewById(R.id.iv_zsPhoto); 
		TV_zsDesc = (TextView) findViewById(R.id.TV_zsDesc);
		TV_author = (TextView) findViewById(R.id.TV_author);
		TV_publish = (TextView) findViewById(R.id.TV_publish);
		TV_publishDate = (TextView) findViewById(R.id.TV_publishDate);
		TV_viewNum = (TextView) findViewById(R.id.TV_viewNum);
		TV_zsFile = (TextView) findViewById(R.id.TV_zsFile);
		Bundle extras = this.getIntent().getExtras();
		zsId = extras.getInt("zsId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FlzsDetailActivity.this.finish();
			}
		}); 
		btnDownZsFile = (Button)findViewById(R.id.btnDownZsFile);
		btnDownZsFile.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FlzsDetailActivity.this.setTitle("正在开始下载知识文件....");
				HttpUtil.downloadFile(flzs.getZsFile()); 
				Toast.makeText(getApplicationContext(), "下载成功，你也可以在mobileclient/upload目录查看！", 1).show();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    flzs = flzsService.GetFlzs(zsId); 
	    flzs.setViewNum(flzs.getViewNum() + 1);
	    flzsService.UpdateFlzs(flzs); 
	    
		this.TV_zsId.setText(flzs.getZsId() + "");
		this.TV_title.setText(flzs.getTitle());
		byte[] zsPhoto_data = null;
		try {
			// 获取图片数据
			zsPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + flzs.getZsPhoto());
			Bitmap zsPhoto = BitmapFactory.decodeByteArray(zsPhoto_data, 0,zsPhoto_data.length);
			this.iv_zsPhoto.setImageBitmap(zsPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_zsDesc.setText(flzs.getZsDesc());
		this.TV_author.setText(flzs.getAuthor());
		this.TV_publish.setText(flzs.getPublish());
		Date publishDate = new Date(flzs.getPublishDate().getTime());
		String publishDateStr = (publishDate.getYear() + 1900) + "-" + (publishDate.getMonth()+1) + "-" + publishDate.getDate();
		this.TV_publishDate.setText(publishDateStr);
		this.TV_viewNum.setText(flzs.getViewNum() + "");
		this.TV_zsFile.setText(flzs.getZsFile());
		if(flzs.getZsFile().equals("")) {
			// 获取知识文件数据
			this.btnDownZsFile.setVisibility(View.GONE);
		}
	} 
}
