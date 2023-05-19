package com.mobileclient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("�ֻ��ͻ���-������");
        setContentView(R.layout.main_menu);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        
        AnimationSet set = new AnimationSet(false);
        Animation animation = new AlphaAnimation(0,1);
        animation.setDuration(500);
        set.addAnimation(animation);
        
        animation = new TranslateAnimation(1, 13, 10, 50);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new RotateAnimation(30,10);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new ScaleAnimation(5,0,2,0);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        LayoutAnimationController controller = new LayoutAnimationController(set, 1);
        
        gridview.setLayoutAnimation(controller);
        
        gridview.setAdapter(new ImageAdapter(this));
    }
    
    // �̳�BaseAdapter
    public class ImageAdapter extends BaseAdapter {
    	
    	LayoutInflater inflater;
    	
    	// ������
        private Context mContext;
        
        // ͼƬ��Դ����
        private Integer[] mThumbIds = {
                R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon
        };
        private String[] menuString = {"�û�����","���Ź���","���ӹ���","���ӻظ�����","�滭����","���¹���","�滭�������","���·������"};

        // ���췽��
        public ImageAdapter(Context c) {
            mContext = c;
            inflater = LayoutInflater.from(mContext);
        }
        // �������
        public int getCount() {
            return mThumbIds.length;
        }
        // ��ǰ���
        public Object getItem(int position) {
            return null;
        }
        // ��ǰ���id
        public long getItemId(int position) {
            return 0;
        }
        // ��õ�ǰ��ͼ
        public View getView(int position, View convertView, ViewGroup parent) { 
        	View view = inflater.inflate(R.layout.gv_item, null);
			TextView tv = (TextView) view.findViewById(R.id.gv_item_appname);
			ImageView iv = (ImageView) view.findViewById(R.id.gv_item_icon);  
			tv.setText(menuString[position]); 
			iv.setImageResource(mThumbIds[position]); 
			  switch (position) {
				case 0:
					// �û����������
					view.setOnClickListener(userInfoLinstener);
					break;
				case 1:
					// ���Ź��������
					view.setOnClickListener(newsLinstener);
					break;
				case 2:
					// ���ӹ��������
					view.setOnClickListener(postInfoLinstener);
					break;
				case 3:
					// ���ӻظ����������
					view.setOnClickListener(replyLinstener);
					break;
				case 4:
					// �滭���������
					view.setOnClickListener(paintLinstener);
					break;
				case 5:
					// ���¹��������
					view.setOnClickListener(articleLinstener);
					break;
				case 6:
					// �滭������������
					view.setOnClickListener(paintClassLinstener);
					break;
				case 7:
					// ���·�����������
					view.setOnClickListener(articleClassLinstener);
					break;

			 
				default:
					break;
				} 
			return view; 
        }
       
    }
    
    OnClickListener userInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// �����û�����Activity
			intent.setClass(MainMenuActivity.this, UserInfoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener newsLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// �������Ź���Activity
			intent.setClass(MainMenuActivity.this, NewsListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener postInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// �������ӹ���Activity
			intent.setClass(MainMenuActivity.this, PostInfoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener replyLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// �������ӻظ�����Activity
			intent.setClass(MainMenuActivity.this, ReplyListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener paintLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// �����滭����Activity
			//intent.setClass(MainMenuActivity.this, PaintListActivity.class);
			//startActivity(intent);
		}
	};
    OnClickListener articleLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// �������¹���Activity
			intent.setClass(MainMenuActivity.this, ArticleListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener paintClassLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// �����滭�������Activity
			//intent.setClass(MainMenuActivity.this, PaintClassListActivity.class);
			//startActivity(intent);
		}
	};
    OnClickListener articleClassLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// �������·������Activity
			intent.setClass(MainMenuActivity.this, ArticleClassListActivity.class);
			startActivity(intent);
		}
	};


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "���µ���");  
		menu.add(0, 2, 2, "�˳�"); 
		return super.onCreateOptionsMenu(menu); 
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {//���µ��� 
			Intent intent = new Intent();
			intent.setClass(MainMenuActivity.this,
					LoginActivity.class);
			startActivity(intent);
		} else if (item.getItemId() == 2) {//�˳�
			System.exit(0);  
		} 
		return true; 
	}
}
