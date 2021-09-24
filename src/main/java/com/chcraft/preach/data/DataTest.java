package com.chcraft.preach.data;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataTest {
	final static int DATA_COUNT = 100000;
	
	final static int YEAR_LOW = 2000;
	final static int YEAR_HIGH = 2021;
	final static int MONTH_LOW = 1;
	final static int MONTH_HIGH = 12;
	final static int DATE_LOW = 1;
	final static int DATE_HIGH = 31;
	
	final static int CHAPTER_LOW = 1;
	final static int CHAPTER_HIGH = 150;
	
	final static int VERSE_LOW = 1;
	final static int VERSE_HIGH = 99;
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		File file = new File("./data/data");
		
		//test with List
		List<Preach> test = new ArrayList<>();
		
		//generate data and save
//		for(int i = 0; i < DATA_COUNT; i++) {
//			Preach preach = generateData();
//			test.add(preach);
//		}

		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));) {
			//write to file
			for(int i = 0; i < DATA_COUNT; i++) {
				Preach preach = generateData();
				out.writeObject(preach);
			}
			/*
			//read test
			Object data = in.readObject();
			
			List<Preach> list = (List<Preach>)data;				
			
			for(Preach preach : list) {
				System.out.println(preach);
			}
			*/
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		/*
		//test with array
		Preach[] test = new Preach[2];
		test[0] = p1;
		test[1] = p2;
		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));) {
				//write to file
				out.writeObject(test);
				
				//read test
				Object data = in.readObject();
				
				Preach[] list = (Preach[])data;				
				
				for(Preach preach : list) {
					System.out.println(preach);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		*/
		
		//데이터 검색 테스트
		PreachSearcher.init(); //index 채우기

		//year month 검색
		int count = 0;
		for(int i = YEAR_LOW; i <= YEAR_HIGH; i++) {
			for(int j = MONTH_LOW; j <= MONTH_HIGH; j++) {
				List<Preach> result = PreachSearcher.search(i, j);
				if(!result.isEmpty()) {
					for (Preach preach : result) {
						count++;
						if(!verseValidCheck(preach)) {
							System.out.println("데이터생성오류있음");
						}
					}
				}
			}
		}
		System.out.println("year month 검색 개수 : " + count);
		
		//testament chapter 검색
		count = 0;
		for(int i = 0; i < PreachSearcher.TESTAMENTS.length; i++) {
			String testament = PreachSearcher.TESTAMENTS[i];
			for(int j = CHAPTER_LOW; j <= CHAPTER_HIGH; j++) {
				List<Preach> result = PreachSearcher.search(testament, j);
				if(!result.isEmpty()) {
					for (Preach preach : result) {
						count++;
						if(!verseValidCheck(preach)) {
							System.out.println("데이터생성오류있음");
						}
					}
				}
			}
		}
		System.out.println("testament chapter 검색 개수 : " + count);
		
		File searchResult = new File("./result");
//		File lineStoreTest = new File("./data/lineStore");
		
		try(FileWriter w = new FileWriter(searchResult);){
			//year month 검색
			for(int i = YEAR_LOW; i <= YEAR_HIGH; i++) {
				for(int j = MONTH_LOW; j <= MONTH_HIGH; j++) {
					List<Preach> result = PreachSearcher.search(i, j);
					if(!result.isEmpty()) {
						w.write(i + "년 " + j + "월 설교 목록\n");
						for (Preach preach : result) {
							w.write(preach.toString() + "\n");
						}
					}
				}
			}
			//testament chapter 검색
			for(int i = 0; i < PreachSearcher.TESTAMENTS.length; i++) {
				String testament = PreachSearcher.TESTAMENTS[i];
				for(int j = CHAPTER_LOW; j <= CHAPTER_HIGH; j++) {
					List<Preach> result = PreachSearcher.search(testament, j);
					if(!result.isEmpty()) {
						w.write(testament + "의 " + j + "장 설교 목록\n");
						for (Preach preach : result) {
							w.write(preach.toString() + "\n");
						}
					}
				}
			}
		}
		
		//리스트로 저장하는거랑 리스트에 안넣고 저장하는거 읽는 속도 비교
		long start;
		long end;
		try(ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(file));){
			System.out.println("리스트에 안넣고 저장한거 수행시간(ns)");
			start = System.nanoTime();
			List<Preach> list = new ArrayList<>();
			Preach preach;
			try {
				while((preach = (Preach)in2.readObject())!= null) {
					list.add(preach);
				}
			} catch(EOFException e) {
			}
			end = System.nanoTime();
			System.out.println("제대로 읽었는지 체크 : " + list.size());
			System.out.println("수행시간: " + (end - start) + " ns");
		}
		/*
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));){
			System.out.println("리스트로 저장한거 수행시간(ns)");
			start = System.nanoTime();
			List<Preach> list = (List<Preach>)in.readObject();
			end = System.nanoTime();
			System.out.println("제대로 읽었는지 체크 : " + list.size());
			System.out.println("수행시간: " + (end - start) + " ns");
		}
		*/
		//리스트가 미세하게 좋으나 차이가 별로 없으니 리스트에 안넣고 저장해서 확장하기 편하게 할것임
		
		
	}
	
	public static Preach generateData() {
		Random random = new Random();

		//0 - 62
		int testamentIndex = random.nextInt(PreachSearcher.TESTAMENTS.length);
		String testament = PreachSearcher.TESTAMENTS[testamentIndex];
		//2000 - 2021
		int year = random.nextInt(YEAR_HIGH - YEAR_LOW + 1) + YEAR_LOW;
		//1 - 12
		int month = random.nextInt(MONTH_HIGH) + MONTH_LOW;
		//1 - 31
		int date = random.nextInt(DATE_HIGH) + DATE_LOW;
		
		//1 - 150
		int chapter = random.nextInt(CHAPTER_HIGH) + CHAPTER_LOW;
		//1 - 99
		int verseStart = random.nextInt(VERSE_HIGH) + VERSE_LOW;
		//start - 99
		int verseEnd = random.nextInt(VERSE_HIGH - verseStart + 1) + verseStart;
		
		return new Preach(year, month, date, "", testament, chapter, verseStart, verseEnd);
	}
	
	public static boolean verseValidCheck(Preach preach) {
		if(preach.getVerseRange().getEnd() < preach.getVerseRange().getStart()) {
			return false;
		}
		
		return true;
	}
	
	public static void search(String testament, int chapter) {
		List<Preach> result = PreachSearcher.search(testament, chapter);
		
		System.out.println("search keyword : " + testament + " " + chapter + "장(편)");
		for(Preach p : result) {
			System.out.println(p);
		}
	}
	
	public static void search(int year, int month) {
		List<Preach> result = PreachSearcher.search(year, month);
		
		System.out.println("search keyword : " + year + "년 " + month + "월");
		for(Preach p : result) {
			System.out.println(p);
		}
	}
}
