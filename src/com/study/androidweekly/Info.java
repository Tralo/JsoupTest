package com.study.androidweekly;

public class Info {

	private String imgUrl;
	private String url;
	private String title;
	private String time;
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Info [imgUrl=" + imgUrl + ", url=" + url + ", title=" + title + ", time=" + time + "]";
	}
	
	
	
	
}
