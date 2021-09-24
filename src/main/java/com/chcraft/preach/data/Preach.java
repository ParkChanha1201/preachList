package com.chcraft.preach.data;

import java.io.Serializable;

public class Preach implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//날짜
	private PreachDate date;
	//설교제목
	private String title;
	//성서 장 : O절 ~ O절
	private String testament;
	private int chapter;
	private VerseRange verseRange;
	
	public Preach() {
	}

	public Preach(int year, int month, int date, String title, String testament, int chapter, int start,
			int end) {
		this.date = new PreachDate(year, month, date);
		this.title = title;
		this.testament = testament;
		this.chapter = chapter;
		this.verseRange = new VerseRange(start, end);
	}
	
	public PreachDate getDate() {
		return date;
	}

	public void setDate(PreachDate date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTestament() {
		return testament;
	}

	public void setTestament(String testament) {
		this.testament = testament;
	}

	public int getChapter() {
		return chapter;
	}

	public void setChapter(int chapter) {
		this.chapter = chapter;
	}

	public VerseRange getVerseRange() {
		return verseRange;
	}

	public void setVerseRange(VerseRange verseRange) {
		this.verseRange = verseRange;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Preach [preachDate=" + date + ", title=" + title + ", testament=" + testament + ", chapter="
				+ chapter + ", verseRange=" + verseRange + "]";
	}
}
