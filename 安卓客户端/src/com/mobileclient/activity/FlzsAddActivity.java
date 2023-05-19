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
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ����֪ʶ���������
	private EditText ET_title;
	// ����֪ʶͼƬͼƬ��ؼ�
	private ImageView iv_zsPhoto;
	private Button btn_zsPhoto;
	protected int REQ_CODE_SELECT_IMAGE_zsPhoto = 1;
	private int REQ_CODE_CAMERA_zsPhoto = 2;
	// ����֪ʶ��������
	private EditText ET_zsDesc;
	// �������������
	private EditText ET_author;
	// ���������������
	private EditText ET_publish;
	// ����������ڿؼ�
	private DatePicker dp_publishDate;
	// �����Ķ��������
	private EditText ET_viewNum;
	// ����֪ʶ�ļ���ؿؼ�
	private TextView TV_zsFile;
	private Button btn_zsFile;
	private int REQ_CODE_SELECT_FILE_zsFile = 3;
	protected String carmera_path;
	/*Ҫ����ķ���֪ʶ��Ϣ*/
	Flzs flzs = new Flzs();
	/*����֪ʶ����ҵ���߼���*/
	private FlzsService flzsService = new FlzsService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.flzs_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("��ӷ���֪ʶ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_title = (EditText) findViewById(R.id.ET_title);
		iv_zsPhoto = (ImageView) findViewById(R.id.iv_zsPhoto);
		/*����ͼƬ��ʾ�ؼ�ʱ����ͼƬ��ѡ��*/
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
		/*������ӷ���֪ʶ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ֪ʶ����*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(FlzsAddActivity.this, "֪ʶ�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					flzs.setTitle(ET_title.getText().toString());
					if(flzs.getZsPhoto() != null) {
						//���ͼƬ��ַ��Ϊ�գ�˵���û�ѡ����ͼƬ����ʱ��Ҫ���ӷ������ϴ�ͼƬ
						FlzsAddActivity.this.setTitle("�����ϴ�ͼƬ���Ե�...");
						String zsPhoto = HttpUtil.uploadFile(flzs.getZsPhoto());
						FlzsAddActivity.this.setTitle("ͼƬ�ϴ���ϣ�");
						flzs.setZsPhoto(zsPhoto);
					} else {
						flzs.setZsPhoto("upload/noimage.jpg");
					}
					/*��֤��ȡ֪ʶ���*/ 
					if(ET_zsDesc.getText().toString().equals("")) {
						Toast.makeText(FlzsAddActivity.this, "֪ʶ������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_zsDesc.setFocusable(true);
						ET_zsDesc.requestFocus();
						return;	
					}
					flzs.setZsDesc(ET_zsDesc.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_author.getText().toString().equals("")) {
						Toast.makeText(FlzsAddActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_author.setFocusable(true);
						ET_author.requestFocus();
						return;	
					}
					flzs.setAuthor(ET_author.getText().toString());
					/*��֤��ȡ������*/ 
					if(ET_publish.getText().toString().equals("")) {
						Toast.makeText(FlzsAddActivity.this, "���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_publish.setFocusable(true);
						ET_publish.requestFocus();
						return;	
					}
					flzs.setPublish(ET_publish.getText().toString());
					/*��ȡ��������*/
					Date publishDate = new Date(dp_publishDate.getYear()-1900,dp_publishDate.getMonth(),dp_publishDate.getDayOfMonth());
					flzs.setPublishDate(new Timestamp(publishDate.getTime()));
					/*��֤��ȡ�Ķ���*/ 
					if(ET_viewNum.getText().toString().equals("")) {
						Toast.makeText(FlzsAddActivity.this, "�Ķ������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_viewNum.setFocusable(true);
						ET_viewNum.requestFocus();
						return;	
					}
					flzs.setViewNum(Integer.parseInt(ET_viewNum.getText().toString()));
					if(flzs.getZsFile() != null) {
						//����ļ���ַ��Ϊ�գ�˵���û�ѡ�����ļ�����ʱ��Ҫ���ӷ������ϴ��ļ�
						FlzsAddActivity.this.setTitle("�����ϴ��ļ����Ե�...");
						String zsFile = HttpUtil.uploadFile(flzs.getZsFile());
						FlzsAddActivity.this.setTitle("�ļ��ϴ���ϣ�");
						flzs.setZsFile(zsFile); 
					} else {
						Toast.makeText(getApplicationContext(), "����ѡ��֪ʶ�ļ�", 1).show();
						return;
					}
					/*����ҵ���߼����ϴ�����֪ʶ��Ϣ*/
					FlzsAddActivity.this.setTitle("�����ϴ�����֪ʶ��Ϣ���Ե�...");
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
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// ������д���ļ� 
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
