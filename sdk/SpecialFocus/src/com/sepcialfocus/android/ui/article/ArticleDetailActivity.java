/**
 * 工程名: MainActivity
 * 文件名: ArticleDetailActivity.java
 * 包名: com.sepcialfocus.android.ui.article
 * 日期: 2015-9-4下午8:10:44
 * Copyright (c) 2015, 北京小马过河教育科技有限公司 All Rights Reserved.
 * http://www.xiaoma.com/
 * Mail: leixun@xiaoma.cn
 * QQ: 378640336
 *
*/

package com.sepcialfocus.android.ui.article;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.sepcialfocus.android.BaseFragmentActivity;
import com.sepcialfocus.android.R;
import com.sepcialfocus.android.bean.ArticleItemBean;
import com.sepcialfocus.android.config.URLs;

/**
 * 类名: ArticleDetailActivity <br/>
 * 功能: TODO 添加功能描述. <br/>
 * 日期: 2015-9-4 下午8:10:44 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class ArticleDetailActivity extends BaseFragmentActivity{
	
	
	private View mView;
	private String urls = "";
	private ArticleItemBean mArticleBean;
	private TextView mArticleTitleTv;
	private TextView mArticlePostmetaTv;
	private TextView mArticleContentTv;
	private String mArticleContentStr = "";
	private String mArticlePostmetaStr = "";

	private WebView mWebView;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_article_detail);
		mArticleBean = (ArticleItemBean)getIntent().getSerializableExtra("key");
		initView();
		mArticleTitleTv.setText(mArticleBean.getTitle());
		urls = URLs.HOST+mArticleBean.getUrl();
		new Loadhtml(urls).execute("","","");
	}
	
	private void initView(){
		mArticleTitleTv = (TextView)findViewById(R.id.article_title);
		mArticlePostmetaTv = (TextView)findViewById(R.id.article_postmeta);
		mArticleContentTv = (TextView)findViewById(R.id.article_content);
		mWebView = (WebView)findViewById(R.id.article_web);
	}

	class Loadhtml extends AsyncTask<String, String, String>
    {
        Document doc;
        String urls = "";
        public Loadhtml(String urls){
        	this.urls = urls;
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                if("".equals(urls)){
                	return null;
                }
                CharSequence charSequence = null;
            	doc = Jsoup.connect(urls).timeout(5000).get();
                 Document content = Jsoup.parse(doc.toString());
                 content.getElementById("hr336").remove();
                 Element article = content.getElementById("text");
                 if(!content.select("p>img").attr("src").contains("http://")){
                	 String urlLast = content.select("p>img").attr("src");
                	 content.select("p>img").removeAttr("src");
                	 content.select("p>img").attr("src",URLs.HOST+urlLast);
                 }
                 mArticleContentStr = article.toString();
                 return mArticleContentStr;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
//            Log.d("doc", doc.toString().trim());
            mArticleContentTv.setText(result);
            mWebView.getSettings().setJavaScriptEnabled(false);  
            mWebView.loadData(mArticleContentStr, "text/html; charset=UTF-8", "utf-8");
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
        
    }
}

