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

	@Override
	public String toString() {
		return "VerseRange [start=" + start + ", end=" + end + "]";
	}
}
