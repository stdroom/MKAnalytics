/**
 * 工程名: MKAndroid
 * 文件名: HtmlParseMainActivity.java
 * 包名: com.ammike.android
 * 日期: 2015年8月31日下午5:00:41
 * QQ: 378640336
 *
*/

package com.ammike.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

/**
 * 类名: HtmlParseMainActivity <br/>
 * 功能: TODO 添加功能描述. <br/>
 * 日期: 2015年8月31日 下午5:00:41 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class HtmlParseMainActivity extends Activity{
	ListView listView;
	ArrayList<HashMap<String,String>> list;
	HtmlAdapter adapter ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_html_parse);
		listView = (ListView)findViewById(R.id.listview);
		list = new ArrayList<HashMap<String,String>>();
		new Loadhtml().execute(null,null,null);
	}

	class Loadhtml extends AsyncTask<String, String, String>
    {
        ProgressDialog bar;
        Document doc;
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                 doc = Jsoup.connect("http://ielts.xiaoma.com/yasikouyu").timeout(5000).post();
                 Document content = Jsoup.parse(doc.toString());
                 Elements divs = content.getElementsByClass("articleList");
                 Document divcontions = Jsoup.parse(divs.toString());
                 Elements element = divcontions.getElementsByTag("li");
                 Log.d("element", element.toString());
                 for(Element links : element)
                 {
                     String title = links.getElementsByTag("a").text();
 
                     String link   = links.select("a").attr("href").trim();
                     String url  = link;
                     HashMap<String,String> values = new HashMap<String,String>();
                     values.put("title", title);
                     values.put("url", url);
                     list.add(values);
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
            adapter = new HtmlAdapter(HtmlParseMainActivity.this, list);
            listView.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        
            bar = new ProgressDialog(HtmlParseMainActivity.this);
            bar.setMessage("正在加载数据····");
            bar.setIndeterminate(false);
            bar.setCancelable(false);
            bar.show();
        }
        
    }
}

