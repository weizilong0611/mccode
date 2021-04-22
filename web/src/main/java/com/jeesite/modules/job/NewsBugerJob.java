package com.jeesite.modules.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import com.jeesite.common.utils.SpringUtils;
import com.jeesite.modules.geo.service.McGeofolderService;
import com.jeesite.modules.news.entity.McNews;
import com.jeesite.modules.news.service.McNewsService;
import com.jeesite.modules.utils.HttpUtils;

public class NewsBugerJob {
	
	public static void main(String []args) throws Exception {
		new NewsBugerJob().doJob("http://lycy.gnzrmzf.gov.cn/lctp1.htm",
				".wzlist li a", 
				"林地图片",
				"form .title",
				"[id^='vsb_content']",
				"form .rq span:eq(1)",
				"form .rq span:eq(3)");
	}
	public static void doJob() {
	/**/	new NewsBugerJob().doJob("http://www.gnzrmzf.gov.cn/ztzl/stwmjs.htm",
				".g-table .ul-tbody a",
				"生态文明建设",
				".article .top .tit",
				"[id^='vsb_content']",
				".article .top .info span:eq(2)",
				".article .top .info span:eq(1)");
		
		new NewsBugerJob().doJob("http://sthj.gnzrmzf.gov.cn/hjzl/ssthjzl.htm",
				".line_u7_0 a", 
				"水生态环境质量",
				"form .title",
				"[id^='vsb_content']",
				"form .rq span:eq(1)",
				"form .rq span:eq(2)");
		///////////////
		new NewsBugerJob().doJob("http://lycy.gnzrmzf.gov.cn/zygh1.htm",
				".wzlist li a", 
				"资源保护",
				"form .title",
				"[id^='vsb_content']",
				"form .rq span:eq(1)",
				"form .rq span:eq(3)");
		new NewsBugerJob().doJob("http://lycy.gnzrmzf.gov.cn/zcfg1.htm",
				".wzlist li a", 
				"政策法规",
				"form .title",
				"[id^='vsb_content']",
				"form .rq span:eq(1)",
				"form .rq span:eq(3)");
		new NewsBugerJob().doJob("http://lycy.gnzrmzf.gov.cn/lctp1.htm",
				".wzlist li a", 
				"林地图片",
				"form .title",
				"[id^='vsb_content']",
				"form .rq span:eq(1)",
				"form .rq span:eq(3)");
 
	}
	public void doJob(String url,String newsUrl,String columnName,String columnTitle,String columnContent,String columnAuthor,String source) {
		System.out.println("Start:准备愉快地抓新闻~");
		try {
			McNewsService mcNewsService =  null;
			try {
				mcNewsService = SpringUtils.getBean("mcNewsService");
			}catch(Exception err) {
				
			}
			
			Document document = Jsoup.connect(url).timeout(5000).get();
			Elements elements = document.select(newsUrl);
			List<String> newsUrls = new ArrayList<String>();
			for (Element element : elements) {
				if(element.hasAttr("href")) {
					newsUrls.add(element.absUrl("href"));
				}
			}
			
			for (String subUrl : newsUrls) {
				if(subUrl.contentEquals("http://lycy.gnzrmzf.gov.cn/info/1099/1112.htm")) {
					System.out.println();
				}
				try {
					McNews news = new  McNews();
					document = Jsoup.connect(subUrl).timeout(5000).get();
					Element title = document.selectFirst(columnTitle);
					news.setColumnTitle(title.text());
					news.setColumnName(columnName);
					news.setColumnAuthor(document.selectFirst(columnAuthor).text());
					
					for (Element element : document.getAllElements()) {
						if(element.hasAttr("href")) {
							element.attr("href",element.absUrl("href"));
						}
					}
					
					Elements imgs = document.selectFirst(columnContent).select("img");
					for (Element img : imgs) {
						img.attr("src",img.absUrl("src"));
					}
					news.setColumnContent(document.selectFirst(columnContent).html());
					news.setSource(document.selectFirst(source).text());

					if(null!= mcNewsService && mcNewsService.findOneBySql("select * from mc_news where column_title = '"+news.getColumnTitle()+"'") == null){
						mcNewsService.save(news);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception err) {
			err.printStackTrace();
		}
		//McNews
		
		System.out.println("End:准备愉快地抓新闻~");
	}

}
