package com.chcraft.preach.data;

import java.io.Serializable;
import java.util.Comparator;

public class PreachDate implements Serializable, Comparator<PreachDate>{
	private int year;
	private int month;
	private int date;

	public PreachDate() {
	}

	public PreachDate(int year, int month, int date) {
		this.year = year;
		this.month = month;
		this.date = date;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + date;
		result = prime * result + month;
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PreachDate other = (PreachDate) obj;
		if (date != other.date)
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PreachDate [year=" + year + ", month=" + month + ", date=" + date + "]";
	}

	@Override
	public int compare(PreachDate o1, PreachDate o2) {
		if(Integer.compare(o1.year, o2.year) != 0) {
			return Integer.compare(o1.year, o2.year);
		} else if(Integer.compare(o1.month, o2.month) != 0){
			return Integer.compare(o1.month, o2.month);
		} else if(Integer.compare(o1.date, o2.date) != 0) {
			return Integer.compare(o1.date, o2.date);
		}
		
		return 0;
	}
}
