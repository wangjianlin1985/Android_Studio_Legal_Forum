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
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
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
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.flzs_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
	  holder.iv_zsPhoto = (ImageView)convertView.findViewById(R.id.iv_zsPhoto);
	  holder.tv_author = (TextView)convertView.findViewById(R.id.tv_author);
	  holder.tv_publish = (TextView)convertView.findViewById(R.id.tv_publish);
	  holder.tv_publishDate = (TextView)convertView.findViewById(R.id.tv_publishDate);
	  holder.tv_viewNum = (TextView)convertView.findViewById(R.id.tv_viewNum);
	  /*设置各个控件的展示内容*/
	  holder.tv_title.setText("知识标题：" + mData.get(position).get("title").toString());
	  holder.iv_zsPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener zsPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_zsPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("zsPhoto"),zsPhotoLoadListener);  
	  holder.tv_author.setText("作者：" + mData.get(position).get("author").toString());
	  holder.tv_publish.setText("出版社：" + mData.get(position).get("publish").toString());
	  try {holder.tv_publishDate.setText("出版日期：" + mData.get(position).get("publishDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_viewNum.setText("阅读量：" + mData.get(position).get("viewNum").toString());
	  /*返回修改好的view*/
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
