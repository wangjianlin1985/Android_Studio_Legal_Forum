package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Reply;
import com.mobileclient.service.ReplyService;
import com.mobileclient.domain.PostInfo;
import com.mobileclient.service.PostInfoService;
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
public class ReplyUserAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	 
	/*�������ӹ���ҵ���߼���*/
	private PostInfoService postInfoService = new PostInfoService();
	// �����ظ����������
	private EditText ET_content;
	 
	protected String carmera_path;
	/*Ҫ��������ӻظ���Ϣ*/
	Reply reply = new Reply();
	/*���ӻظ�����ҵ���߼���*/
	private ReplyService replyService = new ReplyService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.reply_user_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("������ӻظ�");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		}); 
		Bundle bundle = this.getIntent().getExtras();
		int postInfoId = bundle.getInt("postInfoId"); 
		reply.setPostInfoObj(postInfoId); 
		TextView TV_postInfo = (TextView)this.findViewById(R.id.TV_postInfo);
		TV_postInfo.setText(postInfoService.GetPostInfo(postInfoId).getTitle());
		 
		 
		ET_content = (EditText) findViewById(R.id.ET_content);
		
		Declare declare = (Declare)ReplyUserAddActivity.this.getApplication();
		
		reply.setUserObj(declare.getUserName()); 
		 
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*����������ӻظ���ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�ظ�����*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(ReplyUserAddActivity.this, "�ظ��������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					reply.setContent(ET_content.getText().toString());
					/*�ظ�ʱ��*/ 
					reply.setReplyTime("--");
					/*����ҵ���߼����ϴ����ӻظ���Ϣ*/
					ReplyUserAddActivity.this.setTitle("�����ϴ����ӻظ���Ϣ���Ե�...");
					String result = replyService.AddReply(reply);
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
