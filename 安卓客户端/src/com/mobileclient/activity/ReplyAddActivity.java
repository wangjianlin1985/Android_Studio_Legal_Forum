package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
public class ReplyAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ������������������
	private Spinner spinner_postInfoObj;
	private ArrayAdapter<String> postInfoObj_adapter;
	private static  String[] postInfoObj_ShowText  = null;
	private List<PostInfo> postInfoList = null;
	/*�������ӹ���ҵ���߼���*/
	private PostInfoService postInfoService = new PostInfoService();
	// �����ظ����������
	private EditText ET_content;
	// �����ظ���������
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*�ظ��˹���ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();
	// �����ظ�ʱ�������
	private EditText ET_replyTime;
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
		setContentView(R.layout.reply_add); 
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
		spinner_postInfoObj = (Spinner) findViewById(R.id.Spinner_postInfoObj);
		// ��ȡ���еı�������
		try {
			postInfoList = postInfoService.QueryPostInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int postInfoCount = postInfoList.size();
		postInfoObj_ShowText = new String[postInfoCount];
		for(int i=0;i<postInfoCount;i++) { 
			postInfoObj_ShowText[i] = postInfoList.get(i).getTitle();
		}
		// ����ѡ������ArrayAdapter��������
		postInfoObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, postInfoObj_ShowText);
		// ���������б�ķ��
		postInfoObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_postInfoObj.setAdapter(postInfoObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_postInfoObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				reply.setPostInfoObj(postInfoList.get(arg2).getPostInfoId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_postInfoObj.setVisibility(View.VISIBLE);
		ET_content = (EditText) findViewById(R.id.ET_content);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// ��ȡ���еĻظ���
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// ����ѡ������ArrayAdapter��������
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// ���������б�ķ��
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_userObj.setAdapter(userObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				reply.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_replyTime = (EditText) findViewById(R.id.ET_replyTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*����������ӻظ���ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�ظ�����*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(ReplyAddActivity.this, "�ظ��������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					reply.setContent(ET_content.getText().toString());
					/*��֤��ȡ�ظ�ʱ��*/ 
					if(ET_replyTime.getText().toString().equals("")) {
						Toast.makeText(ReplyAddActivity.this, "�ظ�ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_replyTime.setFocusable(true);
						ET_replyTime.requestFocus();
						return;	
					}
					reply.setReplyTime(ET_replyTime.getText().toString());
					/*����ҵ���߼����ϴ����ӻظ���Ϣ*/
					ReplyAddActivity.this.setTitle("�����ϴ����ӻظ���Ϣ���Ե�...");
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
