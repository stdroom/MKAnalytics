/**
 * 工程名: SpecialFocus
 * 文件名: MainPagerAdapter.java
 * 包名: com.sepcialfocus.android.ui.adapter
 * 日期: 2015-9-1下午9:38:19
 * Copyright (c) 2015, 北京小马过河教育科技有限公司 All Rights Reserved.
 * http://www.xiaoma.com/
 * Mail: leixun@xiaoma.cn
 * QQ: 378640336
 *
*/

package com.sepcialfocus.android.ui.adapter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 类名: MainPagerAdapter <br/>
 * 功能: TODO 添加功能描述. <br/>
 * 日期: 2015-9-1 下午9:38:19 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class MainPagerAdapter extends FragmentPagerAdapter{

	private ArrayList<String> fragmentNameList;
	private ArrayList<Fragment> fragmentList;
	
	public MainPagerAdapter(FragmentManager fm,ArrayList<String> fragmentNameList) {
		super(fm);
		this.fragmentNameList = fragmentNameList;
		fragmentList = new ArrayList<Fragment>();
	}

	@Override
	public Fragment getItem(int position) {
		if(fragmentList.size() <= position){
			if (fragmentList.size() > position && fragmentList.get(position)!=null){
				return fragmentList.get(position);
			}
			String className = fragmentNameList.get(position);
			Class clazz;
			try{
				clazz = Class.forName(className);
				Constructor constructor = clazz.getConstructor();
				Fragment fragment = (Fragment)constructor.newInstance();
				fragmentList.add(position,fragment);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return fragmentList!=null ? fragmentList.get(position):null;
	}

	
	
	@Override
	public int getCount() {
		return fragmentNameList!=null ? fragmentNameList.size():0;
	}

}
