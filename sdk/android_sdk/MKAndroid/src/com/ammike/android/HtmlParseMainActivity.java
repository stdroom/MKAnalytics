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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ammike.android.adapter.HtmlAdapter;
import com.ammike.android.widget.PullToRefreshView;
import com.ammike.android.widget.PullToRefreshView.OnFooterRefreshListener;

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
	PullToRefreshView pullView ;
	RelativeLayout bar;
	ArrayList<HashMap<String,String>> list;
	HtmlAdapter adapter ;
	Button btn;
	TextView titleTv;
	boolean isPullRrefreshFlag;
	String nextUrl;
	String hosts = "http://ielts.xiaoma.com/yasijijing/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_html_parse);
		initView();
		titleTv.setText(getIntent().getStringExtra("title"));
		hosts = getIntent().getStringExtra("url");
		list = new ArrayList<HashMap<String,String>>();
		adapter = new HtmlAdapter(HtmlParseMainActivity.this, list);
        listView.setAdapter(adapter);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				list.clear();
				new Loadhtml(hosts).execute(null,null,null);
			}
		});
		new Loadhtml(hosts).execute(null,null,null);
	}
	
	private void initView(){
		btn = (Button)findViewById(R.id.refresh_btn);
		titleTv = (TextView)findViewById(R.id.title_tv);
		bar = (RelativeLayout)findViewById(R.id.layout_loading_bar);
		listView = (ListView)findViewById(R.id.article_listview);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(HtmlParseMainActivity.this, 
						list.get(position).get("url")+"",Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(HtmlParseMainActivity.this,ContentWebViewActivity.class);
				intent.putExtra("url", list.get(position).get("url")+"");
				startActivity(intent);
			}
			
		});
		pullView = (PullToRefreshView)findViewById(R.id.article_pullview);
		pullView.setOnFooterRefreshListener(new OnFooterRefreshListener() {
			
			@Override
			public void onFooterRefresh(PullToRefreshView view) {
				if(isPullRrefreshFlag){
					new Loadhtml(nextUrl).execute("","","");
				} else {
					pullView.onFooterRefreshComplete();
				}
			}
		});
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
                 Elements divs = content.getElementsByClass("articleList");
                 Elements nextdivs = content.getElementsByClass("paging");
                 Document nextdivcontions = Jsoup.parse(nextdivs.toString());
                 Elements nextelement = nextdivcontions.getElementsByTag("a");
                 isPullRrefreshFlag = false;
                 for(Element element:nextelement){
                	 if("下一页".equals(element.text())){
                		 isPullRrefreshFlag = true;
                		 nextUrl = hosts + element.attr("href").trim();
                		 break;
                	 }
                	 Log.d("element",element.toString());
                 }
                 Document divcontions = Jsoup.parse(divs.toString());
                 Elements element = divcontions.getElementsByTag("li");
                 Log.d("element", element.toString());
                 
                 for(Element linkss : element)
                 {	 
                	 Elements ele = linkss.children();
                	 String time = "";
                	 String title = "";
                	 String link = "";
                	 for(Element links: ele){
                		 if(links.hasAttr("class")){
            				 if("time".equals(links.attr("class"))){
	                			 time = links.text();
	                			 continue;
                			 }
                		 }else{
                			 title = links.getElementsByTag("a").text();
                    		 link   = links.select("a").attr("href").trim();
                		 }
                		 
                	 }
                	 HashMap<String,String> values = new HashMap<String,String>();
            		 values.put("title", title);
            		 values.put("time", time);
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
            if(list!=null && list.size()==0){
            	bar.setVisibility(View.VISIBLE);
            	pullView.setVisibility(View.GONE);
            }
        }
        
    }
}

