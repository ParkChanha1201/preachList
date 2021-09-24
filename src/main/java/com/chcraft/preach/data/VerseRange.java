package com.chcraft.preach.data;

import java.io.Serializable;

public class VerseRange implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int start;
	private int end;
	
	public VerseRange() {
	}

	public VerseRange(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "VerseRange [start=" + start + ", end=" + end + "]";
	}
}
