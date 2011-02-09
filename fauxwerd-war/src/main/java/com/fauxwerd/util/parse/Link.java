package com.fauxwerd.util.parse;

public class Link {
	
	private String href = null;
	private String text = null;
	private int score = -1;
	
	public Link () {
		
	}
	
	public Link (String href) {
		this.href = href;
	}
	
	public Link (String href, String text, int score) {
		this.href = href;
		this.text = text;
		this.score = score;
	}

	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

}
