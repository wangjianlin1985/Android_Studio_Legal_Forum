package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class FlzsSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //ͼƬ�첽���������,���ڴ滺����ļ�����
    private SyncImageLoader syncImageLoader;

    public FlzsSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*��һ��װ�����viewʱ=null,���½�һ������inflate��Ⱦһ��view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.flzs_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
	  holder.iv_zsPhoto = (ImageView)convertView.findViewById(R.id.iv_zsPhoto);
	  holder.tv_author = (TextView)convertView.findViewById(R.id.tv_author);
	  holder.tv_publish = (TextView)convertView.findViewById(R.id.tv_publish);
	  holder.tv_publishDate = (TextView)convertView.findViewById(R.id.tv_publishDate);
	  holder.tv_viewNum = (TextView)convertView.findViewById(R.id.tv_viewNum);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_title.setText("֪ʶ���⣺" + mData.get(position).get("title").toString());
	  holder.iv_zsPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener zsPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_zsPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("zsPhoto"),zsPhotoLoadListener);  
	  holder.tv_author.setText("���ߣ�" + mData.get(position).get("author").toString());
	  holder.tv_publish.setText("�����磺" + mData.get(position).get("publish").toString());
	  try {holder.tv_publishDate.setText("�������ڣ�" + mData.get(position).get("publishDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_viewNum.setText("�Ķ�����" + mData.get(position).get("viewNum").toString());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_title;
    	ImageView iv_zsPhoto;
    	TextView tv_author;
    	TextView tv_publish;
    	TextView tv_publishDate;
    	TextView tv_viewNum;
    }
} 
