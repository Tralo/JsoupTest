package com.study.jianshu2;

public class Item {
	
	
	
	private String title;
	private String content;
	private String imgUrl;
	private String readCount;
	private String url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getReadCount() {
		return readCount;
	}
	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Item [title=" + title + ", content=" + content + ", imgUrl=" + imgUrl + ", readCount=" + readCount
				+ ", url=" + url + "]";
	}
	
	
	

}
