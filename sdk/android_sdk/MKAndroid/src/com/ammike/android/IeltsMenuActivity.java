/**
 * 工程名: MKAndroid
 * 文件名: IeltsMenuActivity.java
 * 包名: com.ammike.android
 * 日期: 2015年9月1日下午5:00:03
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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ammike.android.adapter.HtmlAdapter;
import com.ammike.android.widget.PullToRefreshView;

/**
 * 类名: IeltsMenuActivity <br/>
 * 功能: TODO 添加功能描述. <br/>
 * 日期: 2015年9月1日 下午5:00:03 <br/>
 *
 * @author   leixun
 * @version  	 
 */
public class IeltsMenuActivity extends Activity{
	ListView listView;
	PullToRefreshView pullView ;
	RelativeLayout bar;
	ArrayList<HashMap<String,String>> list;
	HtmlAdapter adapter ;
	boolean isPullRrefreshFlag;
	String hosts = "http://ielts.xiaoma.com/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ielts_menu);
		initView();
		list = new ArrayList<HashMap<String,String>>();
		adapter = new HtmlAdapter(IeltsMenuActivity.this, list);
        listView.setAdapter(adapter);
		new Loadhtml(hosts).execute(null,null,null);
	}
	
	private void initView(){
		bar = (RelativeLayout)findViewById(R.id.layout_loading_bar);
		listView = (ListView)findViewById(R.id.article_listview);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(IeltsMenuActivity.this, 
						list.get(position).get("url")+"",Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(IeltsMenuActivity.this,HtmlParseMainActivity.class);
				intent.putExtra("url", list.get(position).get("url")+"");
				intent.putExtra("title", list.get(position).get("title")+"");
				startActivity(intent);
			}
			
		});
		pullView = (PullToRefreshView)findViewById(R.id.article_pullview);
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
            	doc = Jsoup.connect(urls).timeout(5000).get();
                 Document content = Jsoup.parse(doc.toString());
                 Elements divs = content.getElementsByClass("subBox");
                 Document divcontions = Jsoup.parse(divs.toString());
                 Elements element = divcontions.getElementsByTag("li");
                 Log.d("element", element.toString());
                 
                 for(Element linkss : element)
                 {	 
                	 Elements ele = linkss.children();
                	 String title = "";
                	 String link = "";
                	 for(Element links: ele){
                		 if(links.hasAttr("class")){
	                			 continue;
                		 }else{
                			 title = links.getElementsByTag("a").text();
                    		 link   = links.select("a").attr("href").trim();
                		 }
                		 
                	 }
                	 HashMap<String,String> values = new HashMap<String,String>();
            		 values.put("title", title);
            		 values.put("url", link);
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
            bar.setVisibility(View.GONE);
            pullView.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
            pullView.onFooterRefreshComplete();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            bar.setVisibility(View.VISIBLE);
            pullView.setVisibility(View.GONE);
        }
        
    }
	
}

