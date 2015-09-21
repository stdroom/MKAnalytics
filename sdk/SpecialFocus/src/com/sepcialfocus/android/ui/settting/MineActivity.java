/**
 * 工程名: MainActivity
 * 文件名: MineActivity.java
 * 包名: com.sepcialfocus.android.ui.settting
 * 日期: 2015-9-20上午10:55:33
 * Copyright (c) 2015, 北京小马过河教育科技有限公司 All Rights Reserved.
 * http://www.xiaoma.com/
 * Mail: leixun@xiaoma.cn
 * QQ: 378640336
 *
*/

package com.sepcialfocus.android.ui.settting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sepcialfocus.android.BaseFragmentActivity;
import com.sepcialfocus.android.R;

/**
 * 类名: MineActivity <br/>
 * 功能: TODO 添加功能描述. <br/>
 * 日期: 2015-9-20 上午10:55:33 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class MineActivity extends BaseFragmentActivity implements View.OnClickListener{

	ImageView mBackImg;
	TextView mTitleTv;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.fragment_mine);
		initView();
	}
	
	

	@Override
	protected void initView() {
		mBackImg = (ImageView)findViewById(R.id.img_title_back);
		mBackImg.setOnClickListener(this);
		mTitleTv = (TextView)findViewById(R.id.tv_title);
		mTitleTv.setText("我的");
	}



	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.img_title_back:
			finish();
			break;
		}
		
	}
	
	

}

