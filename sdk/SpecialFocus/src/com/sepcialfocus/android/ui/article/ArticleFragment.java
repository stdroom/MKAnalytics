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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mike.aframe.database.KJDB;
import com.mike.aframe.utils.MD5Utils;
import com.mike.aframe.utils.PreferenceHelper;
import com.sepcialfocus.android.BaseApplication;
import com.sepcialfocus.android.BaseFragment;
import com.sepcialfocus.android.R;
import com.sepcialfocus.android.bean.ArticleItemBean;
import com.sepcialfocus.android.bean.NavBean;
import com.sepcialfocus.android.configs.AppConstant;
import com.sepcialfocus.android.ui.adapter.ArticleListAdapter;
import com.sepcialfocus.android.ui.widget.PullToRefreshView;
import com.sepcialfocus.android.ui.widget.PullToRefreshView.OnFooterRefreshListener;

/**
 * 类名: ArticleFragment <br/>
 * 功能描述: TODO 添加功能描述. <br/>
 * 日期: 2015年9月2日 上午10:27:26 <br/>
 *
 * @author leixun
 * @version 
 */
public class ArticleFragment extends BaseFragment{
	private ArrayList<ArticleItemBean> mArticleList;
	private PullToRefreshView mArticle_pullview;
	private ListView mArticle_listview;
	private ArticleListAdapter mArticleAdapter;
	private View mView;
	private Context mContext;
	private String urls = "";
	
	boolean isPullRrefreshFlag;
	String nextUrl;
	private KJDB kjDb = null;
	
	public ArticleFragment(){
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = getActivity();
		kjDb = KJDB.create(mContext);
		Bundle args = getArguments();
        if (null !=  args) {
            if (args.containsKey("key")) {
                this.urls = args.getString("key");
            }
        }
        readNativeData();
        
	}
	
	@Override
	protected void initView() {
		mLoadingLayout = (RelativeLayout)mView.findViewById(R.id.layout_loading_bar);
		mArticle_listview = (ListView)mView.findViewById(R.id.article_listview);
		mArticle_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mContext,ArticleDetailActivity.class);
				intent.putExtra("key", mArticleList.get(position));
				startActivity(intent);
			}
		});
		mArticle_pullview = (PullToRefreshView)mView.findViewById(R.id.article_pullview);
		mArticle_pullview.setOnFooterRefreshListener(new OnFooterRefreshListener() {
			@Override
			public void onFooterRefresh(PullToRefreshView view) {
				if(isPullRrefreshFlag){
					new Loadhtml(nextUrl).execute("","","");
				} else {
					mArticle_pullview.onFooterRefreshComplete();
				}
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_article, null);
		initView();
		mArticleAdapter = new ArticleListAdapter(mContext, mArticleList);
		mArticle_listview.setAdapter(mArticleAdapter);
		initData();
		return mView;
	}
	
	public void notifyData(NavBean bean){
		if(bean!=null){
			this.urls = bean.getMenuUrl();
			readNativeData();
			if(mArticle_listview== null){
				return;
			}
			mArticleAdapter = new ArticleListAdapter(mContext, mArticleList);
			mArticle_listview.setAdapter(mArticleAdapter);
		}
	}
	
	private void initData(){
		if(null==mArticleList || mArticleList.size()==0){
			new Loadhtml(urls).execute("","","");
		}
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
                 Element article = content.getElementById("article");
                 Element nextPage = content.getElementById("pages");
                 Elements nextelement = nextPage.getElementsByTag("a");
                 isPullRrefreshFlag = false;
                 for(Element element:nextelement){
                	 if("下一页".equals(element.text())){
                		 isPullRrefreshFlag = true;
                		 nextUrl = ArticleFragment.this.urls + element.attr("href").trim();
                		 break;
                	 }
                	 Log.d("element",element.toString());
                 }
                 if(!isPullRrefreshFlag){
                	 nextUrl = "";
                 }
                 Log.d("element", article.toString());
                 Elements elements = article.children();
                 ArrayList<ArticleItemBean> tempList = new ArrayList<ArticleItemBean>();
                 for(Element linkss : elements)
                 {	 
                	 if(!linkss.hasAttr("class") || !"post".equals(linkss.attr("class"))){
                		 continue;
                	 }
                	 String title = "";
                 	 String imgUrl = "";
                 	 String summary = "";
                 	 String link = "";
                	 Elements titleImg = linkss.getElementsByTag("img");
                	 for(Element bean : titleImg){
                		 if(bean.hasAttr("alt")){	// 标题
                			 title = bean.attr("alt");
                		 }
                		 if(bean.hasAttr("src")){	// 图像地址
                			 imgUrl = bean.attr("src");
                		 }
                	 }
                	 
                	 Elements contentUrl = linkss.getElementsByClass("summary");
                	 for(Element bean:contentUrl){
                		 if(bean.hasAttr("class")){	// 内容摘要
                			 summary = bean.text();
                		 }
                		 Elements contentBean = bean.getElementsByTag("a");
                		 for(Element bean2:contentBean){
                			 if(bean2.hasAttr("href")){	// 跳转链接
                				 link = bean2.attr("href");
                			 }
                		 }
                	 }
                	 String time = "";
                	 ArrayList<String> tags = new ArrayList<String>();
                	 String tagUrl = "";
                	 Elements timeTag = linkss.getElementsByClass("postmeta");
                	 for(Element links: timeTag){
                		 Elements spans = links.getElementsByTag("span");
                		 for(Element bean:spans){
                			 if(bean.hasAttr("class")){
                				 if("left_author_span".equals(bean.attr("class"))){
                					 time = bean.text();
                					 continue;
                				 }
                				 if("left_tag_span".equals(bean.attr("class"))){
                					 Elements childen = bean.children();
                					 for(Element child:childen){
                						 if(child.hasAttr("href")){
                							 tagUrl = child.attr("href");
                							 tags.add(child.text());
                						 }
                					 }
                				 }
                			 }
                		 }
                		 
                		 
                	 }
                	 ArticleItemBean bean = new ArticleItemBean();
                	 bean.setTitle(title);
                	 bean.setDate(time);
                	 bean.setImgUrl(imgUrl);
                	 bean.setSummary(summary);
                	 bean.setUrl(link);
                	 bean.setTags(tags);
                	 bean.setMd5(MD5Utils.md5(link));
                	 ArticleItemBean selectBean = kjDb.findById(bean.getMd5(), ArticleItemBean.class);
                	 if(selectBean==null){
                		 mArticleList.add(bean);
                	 }
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


	@Override
	public void onDestroy() {
		super.onDestroy();
		BaseApplication.globalContext.saveObject(mArticleList, MD5Utils.md5(urls));
		PreferenceHelper.write(mContext, 
    			AppConstant.URL_NEXT_PAGE_FILE, MD5Utils.md5(urls),nextUrl);
	}

	private void readNativeData(){
		try{
        	mArticleList = (ArrayList<ArticleItemBean>)
        			BaseApplication.globalContext.readObject(MD5Utils.md5(urls));
        	nextUrl = PreferenceHelper.readString(mContext, 
        			AppConstant.URL_NEXT_PAGE_FILE, MD5Utils.md5(urls));
        	if(nextUrl!=null && !"".equals(nextUrl)){
        		isPullRrefreshFlag = true;
        	}
        	if(mArticleList==null){
        		mArticleList = new ArrayList<ArticleItemBean>();
            }
        }catch(Exception e){
        	e.printStackTrace();
        	mArticleList = new ArrayList<ArticleItemBean>();
        }
	}

}

