package com.chcraft.preach.data;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreachSearcher {
	public static String[] TESTAMENTS = {"창세기",
										  "출애굽기",
										  "레위기",
										  "민수기",
										  "신명기",
										  "여호수아",
										  "사사기",
										  "룻기",
										  "사무엘상",
										  "사무엘하",
										  "열왕기상",
										  "열왕기하",
										  "역대상",
										  "역대하",
										  "에스라",
										  "느헤미아",
										  "에스더",
										  "욥기",
										  "시편",
										  "잠언",
										  "전도서",
										  "아가",
										  "이사야",
										  "예레미야",
										  "예레미야애가",
										  "에스겔",
										  "다니엘",
										  "호세아",
										  "요엘",
										  "아모스",
										  "오바댜",
										  "요나",
										  "미가",
										  "나훔",
										  "하박국",
										  "스바냐",
										  "학개",
										  "스가랴",
										  "말라기",
										  "마태복음",
										  "마가복음",
										  "누가복음",
										  "요한복음",
										  "사도행전",
										  "로마서",
										  "고린도전서",
										  "고린도후서",
										  "갈라디아서",
										  "에베소서",
										  "빌립보서",
										  "골로새서",
										  "데살로니가전서",
										  "데살로니가후서",
										  "디모데전서",
										  "디모데후서",
										  "디도서",
										  "빌레몬서",
										  "히브리서",
										  "야고보서",
										  "베드로전서",
										  "베드로후서",
										  "요한일서",
										  "요한이서",
										  "요한삼서",
										  "유다서",
										  "요한계시록"
										  };
	
	//설교 내용을 성서 이름을 기준으로 저장
	//성서명, 장, 설교
	private static Map<String, Map<Integer, List<Preach>>> indexingByTestament;
	//설교 내용을 날짜를 기준으로 저장
	//년도 , 월 , 설교
	private static Map<Integer, Map<Integer, List<Preach>>> indexingByDate;
	
	private static boolean hasData;
	
	static {
		hasData = false;
		indexingByTestament = new HashMap<>();
		for(String testament : TESTAMENTS) {
			indexingByTestament.put(testament, new HashMap<>());
		}
		indexingByDate = new HashMap<>();
	}
	
	public static void init() throws IOException, ClassNotFoundException {
		File file = new File("./data/data");
		//check file exist
		boolean fileExist = !file.createNewFile();
		
		if(fileExist) {
			hasData = true;
			//read data
			try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
				List<Preach> data = new ArrayList<>();
				Preach object;
				try {
					while((object = (Preach)in.readObject())!= null) {
						data.add(object);
					}
				} catch(EOFException e) {
				}
				
				for(Preach preach : data) {
					//indexing by testament에 저장
					String testament = preach.getTestament();
					int chapter = preach.getChapter();
					Map<Integer, List<Preach>> byTestaments = indexingByTestament.get(testament);
					if(!byTestaments.containsKey(chapter)) {
						byTestaments.put(chapter, new ArrayList<>());
					}
					List<Preach> byTestamentsChapter = byTestaments.get(chapter);
					byTestamentsChapter.add(preach);
					
					//indexing by date에 저장
					int year = preach.getDate().getYear();
					int month = preach.getDate().getMonth();
					if(!indexingByDate.containsKey(year)) {
						indexingByDate.put(year, new HashMap<>());
					}
					Map<Integer, List<Preach>> byYear = indexingByDate.get(year);
					if(!byYear.containsKey(month)) {
						byYear.put(month, new ArrayList<>());
					}
					List<Preach> byYearMonth = byYear.get(month);
					byYearMonth.add(preach);
				}
			}
		} else {
			//no data
			hasData = false;
			return;
		}
	}
	
	public static void add(Preach preach) {
		//indexing by testament에 저장
		String testament = preach.getTestament();
		int chapter = preach.getChapter();
		Map<Integer, List<Preach>> byTestaments = indexingByTestament.get(testament);
		if(!byTestaments.containsKey(chapter)) {
			byTestaments.put(chapter, new ArrayList<>());
		}
		List<Preach> byTestamentsChapter = byTestaments.get(chapter);
		byTestamentsChapter.add(preach);
		
		//indexing by date에 저장
		int year = preach.getDate().getYear();
		int month = preach.getDate().getMonth();
		if(!indexingByDate.containsKey(year)) {
			indexingByDate.put(year, new HashMap<>());
		}
		Map<Integer, List<Preach>> byYear = indexingByDate.get(year);
		if(!byYear.containsKey(month)) {
			byYear.put(month, new ArrayList<>());
		}
		List<Preach> byYearMonth = byYear.get(month);
		byYearMonth.add(preach);
		hasData = true;
	}
	
	public static List<Preach> search(int year, int month){
		List<Preach> result;
		//if year does not exist, return empty List
		if(!indexingByDate.containsKey(year)) {
			return new ArrayList<>();
		}
		
		//if month does not exist, return empty List
		if(!indexingByDate.get(year).containsKey(month)) {
			return new ArrayList<>();
		}
		
		result = indexingByDate.get(year).get(month);
		return result;
	}
	
	public static List<Preach> search(String testament, int chapter){
		List<Preach> result;
		//if testament not exist(that's not going to happen.), return empty List
		if(!indexingByTestament.containsKey(testament)) {
			return new ArrayList<>();
		}
		
		//if chapter does not exist, return empty List
		if(!indexingByTestament.get(testament).containsKey(chapter)) {
			return new ArrayList<>();
		}
		
		result = indexingByTestament.get(testament).get(chapter);
		return result;
	}
	
	public static boolean hasData() {
		return hasData;
	}
}
