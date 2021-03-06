/**
 * 工程名: MainActivity
 * 文件名: ArticleItemBean.java
 * 包名: com.sepcialfocus.android.bean
 * 日期: 2015-9-4下午8:30:36
 * Copyright (c) 2015, 北京小马过河教育科技有限公司 All Rights Reserved.
 * http://www.xiaoma.com/
 * Mail: leixun@xiaoma.cn
 * QQ: 378640336
 *
*/

package com.sepcialfocus.android.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.mike.aframe.database.annotate.Id;

/**
 * 类名: ArticleItemBean <br/>
 * 功能: TODO 添加功能描述. <br/>
 * 日期: 2015-9-4 下午8:30:36 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class ArticleItemBean implements Serializable{
	
	@Id
	private String md5 = "";
	String title = "";
	String date = "";
	String imgUrl = "";
	String url = "";
	String tagUrl = "";
	ArrayList<String> tags = null;
	String summary = "";
	
	/** 该条目类型  
	 * 1：正常文章
	 * */
	int category = 1;
	
	boolean hasReadFlag = false;
	
	
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTagUrl() {
		return tagUrl;
	}
	public void setTagUrl(String tagUrl) {
		this.tagUrl = tagUrl;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public boolean isHasReadFlag() {
		return hasReadFlag;
	}
	public void setHasReadFlag(boolean hasReadFlag) {
		this.hasReadFlag = hasReadFlag;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
}

