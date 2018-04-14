package com.study.xxbiquge;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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
	private static String keywork;
	private static String url_prefix = "https://www.xxbiquge.com";
	private static String url = null;
	private static String path_prefix = "D:/笔趣阁crawl/";
	private static String dirname = null;
	private static String targetTitle = null;
	
	private static AtomicInteger count = new AtomicInteger(0);
	
	
	private static ExecutorService fixThreadPool = Executors.newFixedThreadPool(5);
	
	
	private static ExitListener exitListener = new ExitListener() {
		@Override
		public void exitSystem() {
			System.out.println("下载完成，退出系统");
			fixThreadPool.shutdown();
			System.exit(0);
		}
	};
	
	/**
	 * 请求过长时的回调接口实现
	 */
	private static RestartListener restartListener = new RestartListener() {
		@Override
		public void restartAgain() {
			startSearch();
		}
	};
	/**
	 * 获取数据后的回调接口
	 */
	private static CallBack callBack = new CallBack() {
		
		@Override
		public void showData(List<NovelItem> datas) {
			int index = 1;
			System.out.println("开始下载各个章节.....");
			for(NovelItem novelItem : datas) {
//				System.out.println(novelItem);
				count.incrementAndGet();
				fixThreadPool.execute(new NovelRunnable(novelItem, dirname, header, index,exitListener,count));
				index++;
			}
		}
	};
	
	
	public static void main(String[] args) {
		System.out.print("请输入书名:  ");
		Scanner scanner = new Scanner(System.in);
		keywork = scanner.nextLine();
		url = url_prefix + "/search.php?keyword=" + keywork;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				startSearch();
			}
		}).start();
	}
	
	private static String targetUrl = null;
	
	
	/**
	 * 1.开始搜索
	 */
	public static void startSearch() {
		System.out.println("开始搜索....");
		try {
			Document doc = Jsoup.connect(url).headers(header).timeout(10000).get();
			
			Element targetEl = doc.select("div.result-game-item-detail").get(4);
			targetUrl = targetEl.select("a.result-game-item-title-link").attr("href");
			targetTitle = targetEl.select("a.result-game-item-title-link").select("span").text();
			System.out.println(targetUrl);
			System.out.println(targetTitle);
			//创建目录
			dirname = path_prefix + targetTitle;
			File file = new File(dirname);
			if(!file.exists()) {
				file.mkdirs();
			}
			dirname = dirname + "/";
			
			searchAllArticleItems();
		} catch (IOException e) {
			e.printStackTrace();
			restartListener.restartAgain();
		}
	}
	/**
	 * 2.搜索所有章节的title和url
	 */
	public static void searchAllArticleItems() {
		if(targetUrl != null && !targetUrl.equals("")) {
			try {
				Document doc = Jsoup.connect(targetUrl).headers(header).timeout(10000).get();
				Elements els = doc.select("div.box_con").get(1).select("div.box_con").select("dd");
				List<NovelItem> datas = new ArrayList<NovelItem>();
				for(Element el : els) {
					String title = el.select("a[href]").text();
					String url = url_prefix + el.select("a[href]").attr("href");
					NovelItem item = new NovelItem();
					item.setTitle(title);
					item.setUrl(url);
					datas.add(item);
					
				}
				callBack.showData(datas);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	interface CallBack{
		void showData(List<NovelItem> datas);
	}
	interface RestartListener{
		void restartAgain();
	}
	interface ExitListener{
		void exitSystem();
	}
}
