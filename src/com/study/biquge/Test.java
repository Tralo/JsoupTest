package com.study.biquge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.border.TitledBorder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {
	private static Map<String, String> header = new HashMap<String, String>();
	static{
		header.put("Accept-Language", "zh-CN,zh;q=0.9");
		header.put("Connection", "keep-alive");
		header.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3355.4 Safari/537.36");
		header.put("Accept", "*/*");
		header.put("Accept-Encoding", "gzip, deflate, br");
	}
	
	
	public static void main(String[] args) {
		
		String url = "https://www.xxbiquge.com/75_75918/";
		String prefix = "https://www.xxbiquge.com";
		
		CallBack callBack = new CallBack() {
			
			@Override
			public void showData(List<ArticleItem> datas) {
				for(ArticleItem item : datas) {
					System.out.println(item);
				}
				
			}
		};
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Document doc = Jsoup.connect(url).headers(header).timeout(10000).get();
					Elements els = doc.select("div.box_con").get(1).select("div#list").select("dd");
					List<ArticleItem> datas = new ArrayList<ArticleItem>();
					for(Element el : els) {
						/*String title = el.select("a[href]").text();
						System.out.println(title);
						String url = prefix + el.select("a[href]").attr("href");
						System.out.println(url);*/
						String title = el.select("a[href]").text();
						String url = prefix + el.select("a[href]").attr("href");
						ArticleItem item = new ArticleItem();
						item.setTitle(title);
						item.setUrl(url);
						datas.add(item);
					}
					callBack.showData(datas);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}

	
	interface CallBack{
		void showData(List<ArticleItem> datas);
	}
	
}
