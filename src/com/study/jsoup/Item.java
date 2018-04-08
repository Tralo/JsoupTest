package com.study.jsoup;

public class Item {
	
	private String writer;
	private String img_url;
	private String title;
	private String url;
	private String content;
	public Item() {
	}
	
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


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	@Override
	public String toString() {
		return "Item [writer=" + writer + ", img_url=" + img_url + ", title=" + title + ", url=" + url + ", content="
				+ content + "]";
	}

	
}
