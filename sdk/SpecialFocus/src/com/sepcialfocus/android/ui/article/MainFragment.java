/**
 * ������: SpecialFocus
 * �ļ���: MainFragment.java
 * ����: com.sepcialfocus.android.ui.article
 * ����: 2015-9-1����9:42:39
 * Copyright (c) 2015, ����С����ӽ����Ƽ����޹�˾ All Rights Reserved.
 * http://www.xiaoma.com/
 * Mail: leixun@xiaoma.cn
 * QQ: 378640336
 *
*/

package com.sepcialfocus.android.ui.article;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sepcialfocus.android.BaseFragment;
import com.sepcialfocus.android.ui.adapter.MainPagerAdapter;

/**
 * 类名: MainFragment <br/>
 * 功能描述: TODO 添加功能描述. <br/>
 * 日期: 2015年9月2日 上午10:27:39 <br/>
 *
 * @author leixun
 * @version 
 */
public class MainFragment extends BaseFragment{
	private View mView;
	private Context mContext;

	public MainFragment(){
	}
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}

 

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	protected void initView() {
		
	}
	
	
}

