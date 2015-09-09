/**
 * 工程名: MainActivity
 * 文件名: ArticleFragmentPagerAdapter.java
 * 包名: com.sepcialfocus.android.ui.adapter
 * 日期: 2015-9-9上午7:27:03
 * Copyright (c) 2015, 北京小马过河教育科技有限公司 All Rights Reserved.
 * http://www.xiaoma.com/
 * Mail: leixun@xiaoma.cn
 * QQ: 378640336
 *
*/

package com.sepcialfocus.android.ui.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

/**
 * 类名: ArticleFragmentPagerAdapter <br/>
 * 功能: TODO 添加功能描述. <br/>
 * 日期: 2015-9-9 上午7:27:03 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class ArticleFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();;
    private final FragmentManager fm;

    public ArticleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    public ArticleFragmentPagerAdapter(FragmentManager fm,
            ArrayList<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
    }

    public void appendList(ArrayList<Fragment> fragment) {
        fragments.clear();
        if (!fragments.containsAll(fragment) && fragment.size() > 0) {
            fragments.addAll(fragment);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        if (this.fragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragments) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 这里Destroy的是Fragment的视图层次，并不是Destroy Fragment对象
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (fragments.size() <= position) {
            position = position % fragments.size();
        }
        Object obj = super.instantiateItem(container, position);
        return obj;
    }
}

