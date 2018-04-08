package com.study.meizi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Meizi {
	
	public static void main(String[] args) {
		int page = 4;
		String url = "http://www.mzitu.com/page/" + page;
		
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
		
		CallBack callBack = new CallBack() {
			@Override
			public void crawlPic(List<MeiItem> datas) {
				for(MeiItem item : datas) {
					fixedThreadPool.execute(new MyRunnable(item));
				}
			}
		};
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Document doc = Jsoup.connect(url).get();
					Elements els = doc.select("ul#pins").select("li");
					List<MeiItem> datas = new ArrayList();
					for(Element e : els) {
						String title = e.select("li").select("a[href]").select("img.lazy").attr("alt");
						String url = e.select("li").select("a[href]").attr("href");
						String time = e.select("li").select("span.time").text();
						String view = e.select("li").select("span.view").text();
						MeiItem item = new MeiItem();
						item.setTitle(title);
						item.setTime(time);
						item.setUrl(url);
						item.setView(view);
//						System.out.println(item);
						datas.add(item);
					}
					callBack.crawlPic(datas);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
	}
	interface CallBack{
		void crawlPic(List<MeiItem> datas);
	}
	
}
