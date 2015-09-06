/**
 * ������: SpecialFocus
 * �ļ���: BaseApplication.java
 * ����: com.sepcialfocus.android
 * ����: 2015-9-1����8:47:25
 * Copyright (c) 2015, ����С���ӽ���Ƽ����޹�˾ All Rights Reserved.
 * http://www.xiaoma.com/
 * Mail: leixun@xiaoma.cn
 * QQ: 378640336
 *
*/

package com.sepcialfocus.android;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sepcialfocus.android.config.AppConfig;
import com.sepcialfocus.android.config.ImageLoaderConfig;

import android.app.Application;

/**
 * ����: BaseApplication <br/>
 * ����: TODO ��ӹ�������. <br/>
 * ����: 2015-9-1 ����8:47:25 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class BaseApplication extends Application{
	/** 全局上下文 */
	public static BaseApplication globalContext;
	@Override
	public void onCreate() {
		super.onCreate();
		this.globalContext = this;
		if (!ImageLoader.getInstance().isInited()) {
	         ImageLoaderConfig.initImageLoader(this, AppConfig.getDownloadImgPath());
        }
	}
	
	

}

