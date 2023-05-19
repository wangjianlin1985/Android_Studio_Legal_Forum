package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Flzs;
import com.mobileclient.service.FlzsService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.FlzsSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class FlzsListActivity extends Activity {
	FlzsSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int zsId;
	/* ����֪ʶ����ҵ���߼������ */
	FlzsService flzsService = new FlzsService();
	/*�����ѯ���������ķ���֪ʶ����*/
	private Flzs queryConditionFlzs;

	private MyProgressDialog dialog; //������	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.flzs_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//�������ؼ�
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(FlzsListActivity.this, FlzsQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//�˴���requestCodeӦ�������������е��õ�requestCodeһ��
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("����֪ʶ��ѯ�б�");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(FlzsListActivity.this, FlzsAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		
		if(declare.getIdentify().equals("user")) {
			add_btn.setVisibility(View.GONE);
		}
		
		setViews();
	}

	//���������������secondActivity�з���ʱ���ô˺���
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionFlzs = (Flzs)extras.getSerializable("queryConditionFlzs");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionFlzs = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//�����߳��н����������ݲ���
				list = getDatas();
				//������ʧ��handler��֪ͨ���߳��������
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new FlzsSimpleAdapter(FlzsListActivity.this, list,
	        					R.layout.flzs_list_item,
	        					new String[] { "title","zsPhoto","author","publish","publishDate","viewNum" },
	        					new int[] { R.id.tv_title,R.id.iv_zsPhoto,R.id.tv_author,R.id.tv_publish,R.id.tv_publishDate,R.id.tv_viewNum,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// ��ӳ������
		lv.setOnCreateContextMenuListener(flzsListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int zsId = Integer.parseInt(list.get(arg2).get("zsId").toString());
            	Intent intent = new Intent();
            	intent.setClass(FlzsListActivity.this, FlzsDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("zsId", zsId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener flzsListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			
			Declare declare = (Declare)FlzsListActivity.this.getApplication();
			if(declare.getIdentify().equals("admin")) {
				menu.add(0, 0, 0, "�༭����֪ʶ��Ϣ"); 
				menu.add(0, 1, 0, "ɾ������֪ʶ��Ϣ");
			}
		
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭����֪ʶ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼id
			zsId = Integer.parseInt(list.get(position).get("zsId").toString());
			Intent intent = new Intent();
			intent.setClass(FlzsListActivity.this, FlzsEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("zsId", zsId);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// ɾ������֪ʶ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼id
			zsId = Integer.parseInt(list.get(position).get("zsId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(FlzsListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = flzsService.DeleteFlzs(zsId);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* ��ѯ����֪ʶ��Ϣ */
			List<Flzs> flzsList = flzsService.QueryFlzs(queryConditionFlzs);
			for (int i = 0; i < flzsList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("zsId",flzsList.get(i).getZsId());
				map.put("title", flzsList.get(i).getTitle());
				/*byte[] zsPhoto_data = ImageService.getImage(HttpUtil.BASE_URL+ flzsList.get(i).getZsPhoto());// ��ȡͼƬ����
				BitmapFactory.Options zsPhoto_opts = new BitmapFactory.Options();  
				zsPhoto_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(zsPhoto_data, 0, zsPhoto_data.length, zsPhoto_opts); 
				zsPhoto_opts.inSampleSize = photoListActivity.computeSampleSize(zsPhoto_opts, -1, 100*100); 
				zsPhoto_opts.inJustDecodeBounds = false; 
				try {
					Bitmap zsPhoto = BitmapFactory.decodeByteArray(zsPhoto_data, 0, zsPhoto_data.length, zsPhoto_opts);
					map.put("zsPhoto", zsPhoto);
				} catch (OutOfMemoryError err) { }*/
				map.put("zsPhoto", HttpUtil.BASE_URL+ flzsList.get(i).getZsPhoto());
				map.put("author", flzsList.get(i).getAuthor());
				map.put("publish", flzsList.get(i).getPublish());
				map.put("publishDate", flzsList.get(i).getPublishDate());
				map.put("viewNum", flzsList.get(i).getViewNum());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
