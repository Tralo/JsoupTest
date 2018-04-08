
package com.study.csdn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {
	public static void main(String[] args) {
		int type = 5;
		Set<Item> items = new HashSet<>();
		String url = "https://www.csdn.net/nav/" + Data.map.get(type);
		System.out.println(url);
		
		Callback callback = new Callback() {

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
					System.out.println("开始获取.......");
					Document doc = Jsoup.connect(url).get();
					Elements els = doc.select("li.clearfix");
					List<Item> datas = new ArrayList<>();
					
					for(Element e : els) {
						String url = e.select("div.title").select("h2.csdn-tracking-statistics").select("a[href]").attr("href");
						String title = e.select("div.title").select("h2.csdn-tracking-statistics").select("a").text();
						String nickname = e.select("dl.list_userbar").select("dd.name").select("a").text();
						String time = e.select("dl.list_userbar").select("dd.time").select("a").text().trim();
						String readCount = "阅读量: " + e.select("div.read_num").select("p.num").text();
						if(isEmpty(readCount) || isEmpty(time) || isEmpty(nickname) || isEmpty(url) || isEmpty(title)) {
							continue;
						}
						Item item = new Item();
						item.setUrl(url);
						item.setNickname(nickname);
						item.setReadCount(readCount);
						item.setTitle(title);
						item.setTime(time);
//						System.out.println(item);
						datas.add(item);
					}
//					System.out.println(datas);
					callback.showData(datas);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
		}).start();
		
		
		
		
	}
	
	interface Callback{
		void showData(List<Item> datas);
	}
	
	public static boolean isEmpty(String str) {
		if(str == null || str.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}
	
}
