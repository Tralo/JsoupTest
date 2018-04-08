package com.study.meizi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MyRunnable implements Runnable {
	private MeiItem item;
	private String dirname = "D:/Pic/";
	private String url;
	private Map<String, String> header = new HashMap<String, String>();
	private NotifyListener listener;
	private AtomicInteger count;

	private void init() {
		header.put("Host", "i.meizitu.net");
		header.put("Pragma", "no-cache");
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		header.put("Cache-Control", "no-cache");
		header.put("Connection", "keep-alive");
		header.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3355.4 Safari/537.36");
		header.put("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
	}

	public MyRunnable(MeiItem item, NotifyListener listener, AtomicInteger count) {
		this.item = item;
		this.dirname = this.dirname + item.getTitle();
		this.url = item.getUrl();
		this.listener = listener;
		this.count = count;
		init();
	}

	@Override
	public void run() {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements els = doc.select("div.content").select("div.pagenavi").select("a");

			String pageStr = els.get(els.size() - 2).select("span").text();

			dirname = dirname + "(总共 " + pageStr + " 张)";

			File file = new File(dirname);
			if (!file.exists()) {
				file.mkdirs();
			}

			int totalPage = Integer.parseInt(pageStr);
			for (int i = 1; i <= totalPage; i++) {
				String fileName = dirname + "/" + i + ".jpg";
				String picSiteUrl = url + "/" + i;
				Document picDoc = Jsoup.connect(picSiteUrl).get();
				String picUrl = picDoc.select("div.main-image").select("p").select("img").attr("src");
				System.out.println(picUrl);
				downImages(fileName, picUrl);
			}
			count.decrementAndGet();
			if (count.intValue() == 0) {
				listener.notifyToExit();
			}

		} catch (IOException e) {
		}

	}

	/**
	 * 下载图片到指定目录
	 *
	 * @param filePath
	 *            文件路径
	 * @param imgUrl
	 *            图片URL
	 */
	public void downImages(String filePath, String imgUrl) {
		Response response;
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		try {
			Connection connection = Jsoup.connect(imgUrl).ignoreContentType(true);
			header.put("Referer", imgUrl);
			connection.headers(header);
			response = connection.execute();
			byte[] img = response.bodyAsBytes();

			File file = new File(filePath);

			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(img);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}