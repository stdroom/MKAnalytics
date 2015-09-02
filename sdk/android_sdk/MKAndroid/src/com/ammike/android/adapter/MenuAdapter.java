/**
 * 工程名: MKAndroid
 * 文件名: HtmlAdapter.java
 * 包名: com.ammike.android
 * 日期: 2015年8月31日下午5:38:52
 * QQ: 378640336
 *
*/

package com.ammike.android.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.ammike.android.R;
import com.ammike.android.R.id;
import com.ammike.android.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 类名: HtmlAdapter <br/>
 * 功能: TODO 添加功能描述. <br/>
 * 日期: 2015年8月31日 下午5:38:52 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class MenuAdapter extends BaseAdapter{
	ArrayList<HashMap<String,String>> mList = null;
	Context mContext = null;
	public MenuAdapter(Context context,ArrayList<HashMap<String,String>> list){
		this.mList = list;
		this.mContext = context;
	}
	
	@Override
	public int getCount() {
		
		// TODO Auto-generated method stub
		return mList!=null? mList.size():0;
	}

	@Override
	public Object getItem(int position) {
		
		// TODO Auto-generated method stub
		return mList!=null? mList.get(position):null;
	}

	@Override
	public long getItemId(int position) {
		
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder =null ;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.ielts_menu_item, null);
			holder.title = (TextView)convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		}
		holder = (ViewHolder)convertView.getTag();
		holder.title.setText(mList.get(position).get("title")+"");
		return convertView;
	}

	static class ViewHolder{
		TextView title;
	}
}

