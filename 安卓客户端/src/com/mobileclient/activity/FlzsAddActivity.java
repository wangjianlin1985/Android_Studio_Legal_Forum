package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Flzs;
import com.mobileclient.service.FlzsService;
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
public class FlzsAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明知识标题输入框
	private EditText ET_title;
	// 声明知识图片图片框控件
	private ImageView iv_zsPhoto;
	private Button btn_zsPhoto;
	protected int REQ_CODE_SELECT_IMAGE_zsPhoto = 1;
	private int REQ_CODE_CAMERA_zsPhoto = 2;
	// 声明知识简介输入框
	private EditText ET_zsDesc;
	// 声明作者输入框
	private EditText ET_author;
	// 声明出版社输入框
	private EditText ET_publish;
	// 出版出版日期控件
	private DatePicker dp_publishDate;
	// 声明阅读量输入框
	private EditText ET_viewNum;
	// 声明知识文件相关控件
	private TextView TV_zsFile;
	private Button btn_zsFile;
	private int REQ_CODE_SELECT_FILE_zsFile = 3;
	protected String carmera_path;
	/*要保存的法律知识信息*/
	Flzs flzs = new Flzs();
	/*法律知识管理业务逻辑层*/
	private FlzsService flzsService = new FlzsService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.flzs_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加法律知识");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_title = (EditText) findViewById(R.id.ET_title);
		iv_zsPhoto = (ImageView) findViewById(R.id.iv_zsPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_zsPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(FlzsAddActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_zsPhoto);
			}
		});
		btn_zsPhoto = (Button) findViewById(R.id.btn_zsPhoto);
		btn_zsPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_zsPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_zsPhoto);  
			}
		});
		ET_zsDesc = (EditText) findViewById(R.id.ET_zsDesc);
		ET_author = (EditText) findViewById(R.id.ET_author);
		ET_publish = (EditText) findViewById(R.id.ET_publish);
		dp_publishDate = (DatePicker)this.findViewById(R.id.dp_publishDate);
		ET_viewNum = (EditText) findViewById(R.id.ET_viewNum);
		TV_zsFile = (TextView) findViewById(R.id.TV_zsFile);
		btn_zsFile = (Button) findViewById(R.id.btn_zsFile);
		btn_zsFile.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(FlzsAddActivity.this,fileListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_FILE_zsFile);
			}
		});
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加法律知识按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取知识标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(FlzsAddActivity.this, "知识标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					flzs.setTitle(ET_title.getText().toString());
					if(flzs.getZsPhoto() != null) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						FlzsAddActivity.this.setTitle("正在上传图片，稍等...");
						String zsPhoto = HttpUtil.uploadFile(flzs.getZsPhoto());
						FlzsAddActivity.this.setTitle("图片上传完毕！");
						flzs.setZsPhoto(zsPhoto);
					} else {
						flzs.setZsPhoto("upload/noimage.jpg");
					}
					/*验证获取知识简介*/ 
					if(ET_zsDesc.getText().toString().equals("")) {
						Toast.makeText(FlzsAddActivity.this, "知识简介输入不能为空!", Toast.LENGTH_LONG).show();
						ET_zsDesc.setFocusable(true);
						ET_zsDesc.requestFocus();
						return;	
					}
					flzs.setZsDesc(ET_zsDesc.getText().toString());
					/*验证获取作者*/ 
					if(ET_author.getText().toString().equals("")) {
						Toast.makeText(FlzsAddActivity.this, "作者输入不能为空!", Toast.LENGTH_LONG).show();
						ET_author.setFocusable(true);
						ET_author.requestFocus();
						return;	
					}
					flzs.setAuthor(ET_author.getText().toString());
					/*验证获取出版社*/ 
					if(ET_publish.getText().toString().equals("")) {
						Toast.makeText(FlzsAddActivity.this, "出版社输入不能为空!", Toast.LENGTH_LONG).show();
						ET_publish.setFocusable(true);
						ET_publish.requestFocus();
						return;	
					}
					flzs.setPublish(ET_publish.getText().toString());
					/*获取出版日期*/
					Date publishDate = new Date(dp_publishDate.getYear()-1900,dp_publishDate.getMonth(),dp_publishDate.getDayOfMonth());
					flzs.setPublishDate(new Timestamp(publishDate.getTime()));
					/*验证获取阅读量*/ 
					if(ET_viewNum.getText().toString().equals("")) {
						Toast.makeText(FlzsAddActivity.this, "阅读量输入不能为空!", Toast.LENGTH_LONG).show();
						ET_viewNum.setFocusable(true);
						ET_viewNum.requestFocus();
						return;	
					}
					flzs.setViewNum(Integer.parseInt(ET_viewNum.getText().toString()));
					if(flzs.getZsFile() != null) {
						//如果文件地址不为空，说明用户选择了文件，这时需要连接服务器上传文件
						FlzsAddActivity.this.setTitle("正在上传文件，稍等...");
						String zsFile = HttpUtil.uploadFile(flzs.getZsFile());
						FlzsAddActivity.this.setTitle("文件上传完毕！");
						flzs.setZsFile(zsFile); 
					} else {
						Toast.makeText(getApplicationContext(), "请先选择知识文件", 1).show();
						return;
					}
					/*调用业务逻辑层上传法律知识信息*/
					FlzsAddActivity.this.setTitle("正在上传法律知识信息，稍等...");
					String result = flzsService.AddFlzs(flzs);
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
		if (requestCode == REQ_CODE_CAMERA_zsPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_zsPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_zsPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_zsPhoto.setImageBitmap(booImageBm);
				this.iv_zsPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.flzs.setZsPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_zsPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_zsPhoto.setImageBitmap(bm); 
				this.iv_zsPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			flzs.setZsPhoto(filename); 
		}
		if(requestCode == REQ_CODE_SELECT_FILE_zsFile && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			this.TV_zsFile.setText(filepath); 
			flzs.setZsFile(filename);
		}

	}
}
