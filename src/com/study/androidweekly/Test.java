package com.study.androidweekly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {
	public static void main(String[] args) {
		int page = 4;
		String url = "https://www.androidweekly.cn/page/" + page;
		
		HostnameVerifier hv = new HostnameVerifier() {

			@Override
			public boolean verify(String hostname, SSLSession session) {
				// TODO Auto-generated method stub
				return true;
			}
			
		};
		
		Callback callback = new Callback() {
			
			@Override
			public void showDatas(List<Info> datas) {
				for(Info data : datas) {
					System.out.println(data);
				}
			}
		};
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					try {
						trustAllHttpsCertificates();
						HttpsURLConnection.setDefaultHostnameVerifier(hv);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Document doc = Jsoup.connect(url).get();
					Elements eles = doc.select("article.tag-androiddevweekly");
					List<Info> datas = new ArrayList<>();
					for(Element ele : eles) {
						Info info = new Info();
						// img_url
						String imgContainer = ele.select("div.featured-image-container")
								.attr("style");
						
						String imgUrl ="";
						try {
							String temp = imgContainer.substring(imgContainer.indexOf("(") + 1,imgContainer.indexOf(")"));
							if(!temp.contains("https")) {
								
								imgUrl = "https://www.androidweekly.cn" + imgContainer.substring(imgContainer.indexOf("(") + 1,imgContainer.indexOf(")"));
							} else {
								imgUrl = imgContainer.substring(imgContainer.indexOf("(") + 1,imgContainer.indexOf(")"));
							}
							
						} catch (Exception e) {
							imgUrl = "";
						}
						String url = "https://www.androidweekly.cn"+ele.select("h2.title").select("a[href]").attr("href");
						String title = "https://www.androidweekly.cn"+ele.select("h2.title").select("a[href]").text();
						ele.select("span.date").select("i.material-icons").remove();
						String time = ele.select("span.date").text().trim();
						
						info.setImgUrl(imgUrl);
						info.setTime(time);
						info.setTitle(title);
						info.setUrl(url);
						datas.add(info);
					}
					callback.showDatas(datas);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	interface Callback{
		void showDatas(List<Info> datas);
	}
	private static void trustAllHttpsCertificates() throws Exception {  
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];  
        javax.net.ssl.TrustManager tm = new miTM();  
        trustAllCerts[0] = tm;  
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext  
                .getInstance("SSL");  
        sc.init(null, trustAllCerts, null);  
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc  
                .getSocketFactory());  
    }  
  
    static class miTM implements javax.net.ssl.TrustManager,  
            javax.net.ssl.X509TrustManager {  
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
            return null;  
        }  
  
        public boolean isServerTrusted(  
                java.security.cert.X509Certificate[] certs) {  
            return true;  
        }  
  
        public boolean isClientTrusted(  
                java.security.cert.X509Certificate[] certs) {  
            return true;  
        }
  
        public void checkServerTrusted(  
                java.security.cert.X509Certificate[] certs, String authType)  
                throws java.security.cert.CertificateException {  
            return;  
        }
        public void checkClientTrusted(  
                java.security.cert.X509Certificate[] certs, String authType)  
                throws java.security.cert.CertificateException {  
            return;  
        }
    }
	
	

}
