/**
 * 工程名: MainActivity
 * 文件名: NavBean.java
 * 包名: com.sepcialfocus.android.bean
 * 日期: 2015-9-4下午9:58:03
 * Copyright (c) 2015, 北京小马过河教育科技有限公司 All Rights Reserved.
 * http://www.xiaoma.com/
 * Mail: leixun@xiaoma.cn
 * QQ: 378640336
 *
*/

package com.sepcialfocus.android.bean;

import java.io.Serializable;

/**
 * 类名: NavBean <br/>
 * 功能: 顶部菜单. <br/>
 * 日期: 2015-9-4 下午9:58:03 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class NavBean implements Serializable{
	String menu = "";
	String menuUrl = "";
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	

}

