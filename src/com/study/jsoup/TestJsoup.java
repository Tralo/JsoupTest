package com.study.jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;import java.util.concurrent.ConcurrentHashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.CORBA.PRIVATE_MEMBER;

public class TestJsoup {

	public static void main(String[] args) {
		int page = 1;
		
		
		Callback callback = new Callback() {
			@Override
			public void showDatas(List<Item> itemList) {
				for(Item item : itemList) {
					System.out.println(item + "\n");
				}
			}
			
		};
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<Item> itemList = new ArrayList<>();
				Document doc;
				try {
					doc = Jsoup.connect(
							"https://www.jianshu.com/c/d1591c322c89")
							.get();
					Elements infos = doc.select("ul.note-list");
					
					for(Element info : infos) {
						Item item = new Item();
						String writer = info.select("a.nickname").text();
						String img_url = info.select("a.avatar").select("img").attr("src");
						String title = info.select("a.title").text();
						String url = "https://www.jianshu.com"+info.select("a.title").attr("href");
						String content = info.select("p.abstract").text().trim().replace(" ", "");
						item.setImg_url(img_url);
						item.setUrl(url);
						item.setTitle(title);
						item.setContent(content);
						item.setWriter(writer);
						itemList.add(item);
					}
					callback.showDatas(itemList);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	
	
	interface Callback{
		void showDatas(List<Item> itemList);
	}
	
	
	
	

}
