/**
 * ������: SpecialFocus
 * �ļ���: ArticleListAdapter.java
 * ����: com.sepcialfocus.android.ui.adapter
 * ����: 2015-9-1����9:00:03
 * Copyright (c) 2015, ����С����ӽ����Ƽ����޹�˾ All Rights Reserved.
 * http://www.xiaoma.com/
 * Mail: leixun@xiaoma.cn
 * QQ: 378640336
 *
*/

package com.sepcialfocus.android.ui.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.sepcialfocus.android.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ����: ArticleListAdapter <br/>
 * ����: �����б�������. <br/>
 * ����: 2015-9-1 ����9:00:03 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class ArticleListAdapter extends BaseAdapter{
	ArrayList<HashMap<String,String>> mList;
	Context mContext;
	
	public ArticleListAdapter(Context context, ArrayList<HashMap<String,String>> list){
		this.mContext = context;
		this.mList = list;
	}

	@Override
	public int getCount() {
		return mList!=null ? mList.size():0;
	}

	@Override
	public Object getItem(int position) {
		return mList!=null ? mList.get(position):null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_article, null);
			holder = new ViewHolder();
			holder.mArticleImg = (ImageView)convertView.findViewById(R.id.article_img);
			holder.mArticleTitleTv = (TextView)convertView.findViewById(R.id.article_title);
			holder.mArticleContentTv = (TextView)convertView.findViewById(R.id.article_content);
			holder.mArticleFromTv = (TextView)convertView.findViewById(R.id.article_from);
			holder.mArticleDateTv = (TextView)convertView.findViewById(R.id.article_date);
			convertView.setTag(holder);
		}
		holder = (ViewHolder)convertView.getTag();
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView mArticleImg;
		TextView mArticleTitleTv;
		TextView mArticleContentTv;
		TextView mArticleDateTv;
		TextView mArticleFromTv;
		TextView mArticleLabelTv;
	}

}

