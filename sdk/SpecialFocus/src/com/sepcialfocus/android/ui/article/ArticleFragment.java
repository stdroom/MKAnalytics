/**
 * ������: SpecialFocus
 * �ļ���: ArticleFragment.java
 * ����: com.sepcialfocus.android.ui
 * ����: 2015-9-1����8:47:05
 * Copyright (c) 2015, ����С����ӽ����Ƽ����޹�˾ All Rights Reserved.
 * http://www.xiaoma.com/
 * Mail: leixun@xiaoma.cn
 * QQ: 378640336
 *
*/

package com.sepcialfocus.android.ui.article;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.sepcialfocus.android.BaseFragment;
import com.sepcialfocus.android.R;
import com.sepcialfocus.android.ui.adapter.ArticleListAdapter;
import com.sepcialfocus.android.ui.widget.PullToRefreshView;

/**
 * 类名: ArticleFragment <br/>
 * 功能描述: TODO 添加功能描述. <br/>
 * 日期: 2015年9月2日 上午10:27:26 <br/>
 *
 * @author leixun
 * @version 
 */
public class ArticleFragment extends BaseFragment{
	private ArrayList<HashMap<String,String>> mArticleList;
	private PullToRefreshView mArticle_pullview;
	private ListView mArticle_listview;
	private ArticleListAdapter mArticleAdapter;
	private View mView;
	private Context mContext;
	
	public ArticleFragment(){
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = getActivity();
		initView();
		mArticleList = new ArrayList<HashMap<String,String>>();
	}
	
	@Override
	protected void initView() {
		mLoadingLayout = (RelativeLayout)mView.findViewById(R.id.layout_loading_bar);
		mArticle_listview = (ListView)mView.findViewById(R.id.article_listview);
		mArticle_pullview = (PullToRefreshView)mView.findViewById(R.id.article_pullview);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_article, null);
		initView();
		mArticleAdapter = new ArticleListAdapter(mContext, mArticleList);
		mArticle_listview.setAdapter(mArticleAdapter);
		return mView;
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
//                 Elements nextdivs = content.getElementsByClass("paging");
//                 Document nextdivcontions = Jsoup.parse(nextdivs.toString());
//                 Elements nextelement = nextdivcontions.getElementsByTag("a");
//                 isPullRrefreshFlag = false;
//                 for(Element element:nextelement){
//                	 if("下一页".equals(element.text())){
//                		 isPullRrefreshFlag = true;
//                		 nextUrl = hosts + element.attr("href").trim();
//                		 break;
//                	 }
//                	 Log.d("element",element.toString());
//                 }
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
            		 mArticleList.add(values);
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
            setLoadingVisible(false);
            mArticle_pullview.setVisibility(View.VISIBLE);
            mArticleAdapter.notifyDataSetChanged();
            mArticle_pullview.onFooterRefreshComplete();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            if(mArticleList!=null 
            		&& mArticleList.size()==0){
            	setLoadingVisible(true);
            	mArticle_pullview.setVisibility(View.GONE);
            }
        }
        
    }


}

