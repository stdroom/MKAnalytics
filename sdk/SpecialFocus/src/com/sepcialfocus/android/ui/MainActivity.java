/**
 * 工程名: SpecialFocus
 * 文件名: MainActivity.java
 * 包名: com.sepcialfocus.android.ui
 * 日期: 2015-9-1下午9:40:41
 * Copyright (c) 2015, 北京小马过河教育科技有限公司 All Rights Reserved.
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
 * 类名: MainActivity <br/>
 * 功能: 主页面. <br/>
 * 日期: 2015-9-1 下午9:40:41 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class MainActivity extends BaseFragmentActivity
	implements OnPageChangeListener{
	
	/**
	 * 滑动fragment数据源
	 */
	private ArrayList<String> mFragmentList = null;
	/**
	 * 首页面的滑动控件
	 */
	private ViewPager mFragmentViewPager = null;
	/**
	 * 滑动控件适配器
	 */
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

