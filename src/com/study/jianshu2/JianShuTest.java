package com.study.jianshu2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JianShuTest {
	
	
	public static void main(String[] args) {
		int page = 4;
		String url = "https://www.jianshu.com/c/3fde3b545a35?order_by=added_at&page=" + page;
		String url_prefix = "https://www.jianshu.com";
		
		
		CallBack callBack = new CallBack() {
			@Override
			public void showData(List<Item> datas) {
				for(Item item : datas) {
					System.out.println(item);
				}
			}
		};
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					Document doc = Jsoup.connect(url).get();
					List<Item> datas = new ArrayList<>();
					Elements els = doc.select("ul.note-list").select("li");
					for(Element el : els) {
						Item item = new Item();
						item.setUrl(url_prefix + el.select("a.title").attr("href"));
						item.setTitle(el.select("a.title").text());
						item.setContent(el.select("p.abstract").text().trim());
						item.setReadCount(el.select("div.meta").select("a").get(0).text().trim());
						if(el.hasClass("have-img")) {
							item.setImgUrl(el.select("a.wrap-img").select("img").attr("src"));
						} 
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
		void showData(List<Item> datas);
	}
	

}
