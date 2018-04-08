package com.study.meizi;

public class MeiItem {
	private String title;
	private String url;
	private String time;
	private String view;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	@Override
	public String toString() {
		return "MeiItem [title=" + title + ", url=" + url + ", time=" + time + ", view=" + view + "]";
	}

}
