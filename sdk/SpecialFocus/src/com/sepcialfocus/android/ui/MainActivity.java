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

import net.youmi.android.spot.SpotManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.sepcialfocus.android.BaseApplication;
import com.sepcialfocus.android.BaseFragmentActivity;
import com.sepcialfocus.android.R;
import com.sepcialfocus.android.bean.NavBean;
import com.sepcialfocus.android.configs.AppConstant;
import com.sepcialfocus.android.configs.URLs;
import com.sepcialfocus.android.ui.adapter.ArticleFragmentPagerAdapter;
import com.sepcialfocus.android.ui.adapter.MainPagerAdapter;
import com.sepcialfocus.android.ui.article.ArticleFragment;
import com.sepcialfocus.android.ui.article.MainFragment;
import com.sepcialfocus.android.ui.settting.DragSortMenuActivity;

/**
 * ����: MainActivity <br/>
 * ����: 程序入口. <br/>
 * ����: 2015-9-1 ����9:40:41 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class MainActivity extends BaseFragmentActivity
	implements OnPageChangeListener,View.OnClickListener{
	
	private HorizontalScrollView mHorizontalScrollView ;
	private LinearLayout mLinearLayout;
	private ImageView mImageView;
	private int mScreenWidth;
	private int item_width;
	
	private int endPosition;
	private int beginPosition;
	private int currentFragmentIndex;
	private boolean isEnd;
	
	private ArrayList<NavBean> mUrlsList = null;
	private ViewPager mFragmentViewPager = null;
	private ArticleFragmentPagerAdapter mFragmentPagerAdapter = null;
	private ArrayList<Fragment> mFragmentList;
	
	private ImageView mDragSoftImg;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		SpotManager.getInstance(this).loadSpotAds();
		// 插屏出现动画效果，0:ANIM_NONE为无动画，1:ANIM_SIMPLE为简单动画效果，2:ANIM_ADVANCE为高级动画效果
		SpotManager.getInstance(this).setAnimationType(
				SpotManager.ANIM_ADVANCE);
		// 设置插屏动画的横竖屏展示方式，如果设置了横屏，则在有广告资源的情况下会是优先使用横屏图。
		SpotManager.getInstance(this).setSpotOrientation(
				SpotManager.ORIENTATION_PORTRAIT);
		mFragmentList = new ArrayList<Fragment>();
		initMenu();
		initView();
	}
	
	private void initView(){
		mDragSoftImg = (ImageView)findViewById(R.id.drag_soft_img);
		mDragSoftImg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,DragSortMenuActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("key", mUrlsList);
				intent.putExtras(bundle);
				startActivityForResult(intent,AppConstant.JUMP_CODE);
			}
		});
		mFragmentViewPager = (ViewPager)findViewById(R.id.fragment_viewpager);
		mFragmentPagerAdapter = new ArticleFragmentPagerAdapter(
				getSupportFragmentManager());
		mFragmentViewPager.setOffscreenPageLimit(1);
		mFragmentViewPager.setAdapter(mFragmentPagerAdapter);
		mFragmentViewPager.setOnPageChangeListener(this);
		initFragment();
	}
	
	private void initFragment(){
		mFragmentList.clear();
		int length = mUrlsList.size();
		for(int i = 1 ; i <= length ; i++){
			Bundle bundle = new Bundle();
			bundle.putString("key", URLs.HOST+mUrlsList.get(i-1).getMenuUrl());
			if(i == 1){
				Fragment fragment = new MainFragment();
				fragment.setArguments(bundle);
				mFragmentList.add(fragment);
			}else{
				Fragment fragment = new ArticleFragment();
				fragment.setArguments(bundle);
				mFragmentList.add(fragment);
			}
		}
		mFragmentPagerAdapter.setFragments(mFragmentList);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		if (state == ViewPager.SCROLL_STATE_DRAGGING) {
			isEnd = false;
		} else if (state == ViewPager.SCROLL_STATE_SETTLING) {
			isEnd = true;
			beginPosition = currentFragmentIndex * item_width;
			if (mFragmentViewPager.getCurrentItem() == currentFragmentIndex) {
				// 未跳入下一个页面
				mImageView.clearAnimation();
				Animation animation = null;
				// 恢复位置
				animation = new TranslateAnimation(endPosition, currentFragmentIndex * item_width, 0, 0);
				animation.setFillAfter(true);
				animation.setDuration(1);
				mImageView.startAnimation(animation);
				mHorizontalScrollView.invalidate();
				endPosition = currentFragmentIndex * item_width;
			}
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int arg2) {
		if(!isEnd){
			if(currentFragmentIndex == position){
				endPosition = item_width * currentFragmentIndex + 
						(int)(item_width * positionOffset);
			}
			if(currentFragmentIndex == position+1){
				endPosition = item_width * currentFragmentIndex - 
						(int)(item_width * (1-positionOffset));
			}
			
			Animation mAnimation = new TranslateAnimation(beginPosition, endPosition, 0, 0);
			mAnimation.setFillAfter(true);
			mAnimation.setDuration(0);
			mImageView.startAnimation(mAnimation);
			mHorizontalScrollView.invalidate();
			beginPosition = endPosition;
		}
	}

	@Override
	public void onPageSelected(int position) {
		Animation animation = new TranslateAnimation(endPosition, position* item_width, 0, 0);
		
		beginPosition = position * item_width;
		
		currentFragmentIndex = position;
		if (animation != null) {
			animation.setFillAfter(true);
			animation.setDuration(0);
			mImageView.startAnimation(animation);
			mHorizontalScrollView.smoothScrollTo((currentFragmentIndex - 1) * item_width , 0);
		}
	}
	

	@SuppressWarnings("unchecked")
	private ArrayList<NavBean> getMenuList(){
		ArrayList<NavBean> list = new ArrayList<NavBean>();
		if(BaseApplication.globalContext.readObject(AppConstant.MENU_FILE)!=null){
			list = (ArrayList<NavBean>)BaseApplication.globalContext.readObject(AppConstant.MENU_FILE);
		}else{
			String[] menuName = getResources().getStringArray(R.array.menu_str);
			String[] menuUrl = getResources().getStringArray(R.array.menu_url);
			for(int i = 0 ; i<menuName.length ; i++){
				NavBean bean = new NavBean();
				bean.setMenu(menuName[i]);
				bean.setMenuUrl(menuUrl[i]);
				list.add(bean);
			}
		}
		return list;
	}
	
	private void initMenu(){
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;
		mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.hsv_view);
		mLinearLayout = (LinearLayout) findViewById(R.id.hsv_content);
		mLinearLayout.removeAllViews();
		mImageView = (ImageView) findViewById(R.id.img1);
		item_width = (int) ((mScreenWidth / 4.0 + 0.5f));
		mImageView.getLayoutParams().width = item_width;
		mUrlsList = getMenuList();
		int length = mUrlsList.size();
		for (int i = 0 ; i < length ; i++) {
			RelativeLayout layout = new RelativeLayout(this);
			TextView view = new TextView(this);
			view.setText(mUrlsList.get(i).getMenu());
			RelativeLayout.LayoutParams params =  new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_IN_PARENT);
			layout.addView(view, params);
			mLinearLayout.addView(layout, (int)(mScreenWidth/4 + 0.5f), 50);
			layout.setOnClickListener(this);
			layout.setTag(i);
		}
	}
	
	

	@Override
	public void finish() {
		BaseApplication.globalContext.saveObject(mUrlsList, AppConstant.MENU_FILE);
		super.finish();
	}

	@Override
	public void onClick(View v) {
		mFragmentViewPager.setCurrentItem((Integer)v.getTag());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode,resultCode,data);
		if(requestCode == AppConstant.JUMP_CODE){
			initMenu();
			initFragment();
		}
	}
	
	
}

