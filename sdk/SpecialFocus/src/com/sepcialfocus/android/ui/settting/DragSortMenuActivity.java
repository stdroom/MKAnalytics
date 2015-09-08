/**
 * 工程名: SpecialFocus
 * 文件名: DragSortMenuActivity.java
 * 包名: com.sepcialfocus.android.ui.settting
 * 日期: 2015年9月8日上午11:15:51
 * QQ: 378640336
 *
*/

package com.sepcialfocus.android.ui.settting;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sepcialfocus.android.BaseApplication;
import com.sepcialfocus.android.BaseFragmentActivity;
import com.sepcialfocus.android.R;
import com.sepcialfocus.android.bean.NavBean;
import com.sepcialfocus.android.configs.AppConstant;
import com.sepcialfocus.android.ui.adapter.DragNavAdapter;
import com.sepcialfocus.android.ui.widget.DragGridView;
import com.sepcialfocus.android.ui.widget.DragGridView.OnChanageListener;

/**
 * 类名: DragSortMenuActivity <br/>
 * 功能: TODO 添加功能描述. <br/>
 * 日期: 2015年9月8日 上午11:15:51 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class DragSortMenuActivity extends BaseFragmentActivity implements View.OnClickListener{
	ArrayList<NavBean> list = null;
	DragGridView mDragGridView;
	DragNavAdapter mDragAdapter = null;
	
	TextView mTitleTv;
	ImageView mBackImg;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_dragmenusoft);
		if(getIntent().getExtras()!=null 
				&& getIntent().getExtras().getSerializable("key")!=null){
			list = (ArrayList<NavBean>)getIntent().getSerializableExtra("key");
		}
		
		initView();
	}
	
	private void initView(){
		mTitleTv = (TextView)findViewById(R.id.tv_title);
		mBackImg = (ImageView)findViewById(R.id.img_title_back);
		mBackImg.setOnClickListener(this);
		
		 mDragGridView = (DragGridView) findViewById(R.id.dragGridView);
		 mDragAdapter = new DragNavAdapter(this, list);
		 mDragGridView.setAdapter(mDragAdapter);
		 mDragGridView.setOnChangeListener(new OnChanageListener() {
			
			@Override
			public void onChange(int from, int to) {
				NavBean temp = list.get(from);
				if(from < to){
					for(int i=from; i<to; i++){
						Collections.swap(list, i, i+1);
					}
				}else if(from > to){
					for(int i=from; i>to; i--){
						Collections.swap(list, i, i-1);
					}
				}
				list.set(to, temp);
				BaseApplication.globalContext.saveObject(list, AppConstant.MENU_FILE);
				mDragAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.img_title_back:
			setResult(RESULT_OK, new Intent());
			finish();
			break;
		}
	}
	
	

}

