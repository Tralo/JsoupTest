package com.study.jianshu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JianShuSimple {
	
	public static void main(String[] args) {
		String url = "https://www.jianshu.com/p/5ad013eb5364?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation";
		
		CallBack callBack = new CallBack() {

			@Override
			public void showDatas(List<Item> datas) {
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
					Elements els = doc.select("div.show-content-free").select("p");
					List<Item> datas = new ArrayList<>();
					for(Element e : els) {
						String url = e.select("a[href]").attr("href");
						String temp = e.select("a[href]").text().trim();
						String title =temp.substring(0, temp.indexOf("- ¾ò½ð")).trim();
						e.select("a[href]").remove();
						e.select("br").remove();
						String content = e.text().trim().replace(" ", "");
						Item item = new Item();
						item.setContent(content);
						item.setTitle(title);
						item.setUrl(url);
						datas.add(item);
					}
					callBack.showDatas(datas);
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				
				
				
			}
		}).start();
		
		
	}
	
	interface CallBack{
		
		void showDatas(List<Item> datas);
		
	}
	

}
