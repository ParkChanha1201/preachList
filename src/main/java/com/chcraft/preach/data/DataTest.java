package com.chcraft.preach.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataTest {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		File file = new File("./data/data");
		
		Preach p1 = new Preach(2021, 9, 24, "오늘 설교", "요한복음", 1, 1, 3);
		Preach p2 = new Preach(2021, 12, 24, "오늘 설교", "창세기", 5, 3, 3);
		
		//test with List
		List<Preach> test = new ArrayList<>();
		
		test.add(p1);
		test.add(p2);

		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));) {
			//write to file
			out.writeObject(test);
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
				out.writeObject(p1);
				out.writeObject(p2);
				
				//read test
				Object data = in.readObject();
				Object data2 = in.readObject();
				Object data3 = in.readObject();
				
				Preach[] list = (Preach[])data;				
				Preach pp1 = (Preach)data2;
				Preach pp2 = (Preach)data3;
				
				for(Preach preach : list) {
					System.out.println(preach);
				}
				
				System.out.println(pp1);
				System.out.println(pp2);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		*/
		
		//데이터 검색 테스트
		PreachSearcher.init(); //index 채우기

		String testament = "요한복음";
		int chapter = 1;
		search(testament, chapter);
		
		int year = 2021;
		int month = 12;
		search(year, month);
	}
	
	public static void search(String testament, int chapter) {
		List<Preach> result = PreachSearcher.getPreachListByTestamentAndChapter(testament, chapter);
		
		System.out.println("search keyword : " + testament + " " + chapter + "장(편)");
		for(Preach p : result) {
			System.out.println(p);
		}
	}
	
	public static void search(int year, int month) {
		List<Preach> result = PreachSearcher.getPreachListByYearAndMonth(year, month);
		
		System.out.println("search keyword : " + year + "년 " + month + "월");
		for(Preach p : result) {
			System.out.println(p);
		}
	}
}
