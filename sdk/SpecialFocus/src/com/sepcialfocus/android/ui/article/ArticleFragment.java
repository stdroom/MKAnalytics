/**
 * 工程名: SpecialFocus
 * 文件名: ArticleFragment.java
 * 包名: com.sepcialfocus.android.ui
 * 日期: 2015-9-1下午8:47:05
 * Copyright (c) 2015, 北京小马过河教育科技有限公司 All Rights Reserved.
 * http://www.xiaoma.com/
 * Mail: leixun@xiaoma.cn
 * QQ: 378640336
 *
*/

package com.sepcialfocus.android.ui.article;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sepcialfocus.android.BaseFragment;

/**
 * 类名: ArticleFragment <br/>
 * 功能: TODO 添加功能描述. <br/>
 * 日期: 2015-9-1 下午8:47:05 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class ArticleFragment extends BaseFragment{
	private ArrayList<HashMap<String,String>> mArticleList;

	public ArticleFragment(){
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	
}

