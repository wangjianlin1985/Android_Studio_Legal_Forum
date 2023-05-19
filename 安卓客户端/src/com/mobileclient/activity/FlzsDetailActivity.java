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
	// �������ذ�ť
	private Button btnReturn;
	// ������¼id�ؼ�
	private TextView TV_zsId;
	// ����֪ʶ����ؼ�
	private TextView TV_title;
	// ����֪ʶͼƬͼƬ��
	private ImageView iv_zsPhoto;
	// ����֪ʶ���ؼ�
	private TextView TV_zsDesc;
	// �������߿ؼ�
	private TextView TV_author;
	// ����������ؼ�
	private TextView TV_publish;
	// �����������ڿؼ�
	private TextView TV_publishDate;
	// �����Ķ����ؼ�
	private TextView TV_viewNum;
	// ����֪ʶ�ļ��ؼ�
	private TextView TV_zsFile;
	private Button btnDownZsFile;
	/* Ҫ����ķ���֪ʶ��Ϣ */
	Flzs flzs = new Flzs(); 
	/* ����֪ʶ����ҵ���߼��� */
	private FlzsService flzsService = new FlzsService();
	private int zsId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.flzs_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴����֪ʶ����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
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
				FlzsDetailActivity.this.setTitle("���ڿ�ʼ����֪ʶ�ļ�....");
				HttpUtil.downloadFile(flzs.getZsFile()); 
				Toast.makeText(getApplicationContext(), "���سɹ�����Ҳ������mobileclient/uploadĿ¼�鿴��", 1).show();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    flzs = flzsService.GetFlzs(zsId); 
	    flzs.setViewNum(flzs.getViewNum() + 1);
	    flzsService.UpdateFlzs(flzs); 
	    
		this.TV_zsId.setText(flzs.getZsId() + "");
		this.TV_title.setText(flzs.getTitle());
		byte[] zsPhoto_data = null;
		try {
			// ��ȡͼƬ����
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
			// ��ȡ֪ʶ�ļ�����
			this.btnDownZsFile.setVisibility(View.GONE);
		}
	} 
}
