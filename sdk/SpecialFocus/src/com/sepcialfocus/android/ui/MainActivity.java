/**
 * ������: SpecialFocus
 * �ļ���: MainActivity.java
 * ����: com.sepcialfocus.android.ui
 * ����: 2015-9-1����9:40:41
 * Copyright (c) 2015, ����С����ӽ����Ƽ����޹�˾ All Rights Reserved.
 * http://www.xiaoma.com/
 * Mail: leixun@xiaoma.cn
 * QQ: 378640336
 *
*/

package com.sepcialfocus.android.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.sepcialfocus.android.BaseFragmentActivity;
import com.sepcialfocus.android.ui.adapter.MainPagerAdapter;

/**
 * ����: MainActivity <br/>
 * ����: ��ҳ��. <br/>
 * ����: 2015-9-1 ����9:40:41 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class MainActivity extends BaseFragmentActivity
	implements OnPageChangeListener{
	
	private ArrayList<String> mFragmentList = null;
	private ViewPager mFragmentViewPager = null;
	private MainPagerAdapter mFragmentPagerAdapter = null;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		
	}
	

}

