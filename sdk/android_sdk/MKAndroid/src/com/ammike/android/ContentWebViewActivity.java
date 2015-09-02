/**
 * 工程名: MKAndroid
 * 文件名: ContentWebViewActivity.java
 * 包名: com.ammike.android
 * 日期: 2015年9月1日下午1:36:31
 * QQ: 378640336
 *
*/

package com.ammike.android;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

/**
 * 类名: ContentWebViewActivity <br/>
 * 功能: TODO 添加功能描述. <br/>
 * 日期: 2015年9月1日 下午1:36:31 <br/>
 *
 * @author   leixun
 * @version   
 */
public class ContentWebViewActivity extends Activity{
	WebView mWebView;
	Elements contentData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_web);
		initView();
		new Loadhtml(getIntent().getStringExtra("url")).execute("","","");
	}
	
	private void initView(){
		mWebView = (WebView)findViewById(R.id.content_web);
		mWebView.getSettings().setJavaScriptEnabled(true);  
	}
	
	class Loadhtml extends AsyncTask<String, String, String>
    {
        ProgressDialog bar;
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
            	doc = Jsoup.connect(urls).timeout(5000).get();
                 Document content = Jsoup.parse(doc.toString());
                 contentData = content.getElementsByAttributeValue("class","containerTop fixed");
                 Log.d("", contentData.toString());
                 Elements temp = content.getElementsByClass("containerCont");
                 for(Element element : temp ){
                	 contentData.add(element);
                 }
                 
                
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
            bar.dismiss();
            mWebView.loadData(contentData.html(), "text/html; charset=UTF-8", null);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        
            bar = new ProgressDialog(ContentWebViewActivity.this);
            bar.setMessage("正在加载数据····");
            bar.setIndeterminate(false);
            bar.setCancelable(false);
            bar.show();
        }
        
    }
}

