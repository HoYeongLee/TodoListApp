package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void listAll(TodoList l) {
		int i = 1;
		
		if(l.getList().isEmpty()) {
			System.out.println("저장된 내용이 없습니다!");
			return;
		}
		System.out.println("\n<전체 목록, 총 "+ l.getList().size() +  "개>");
		for (TodoItem item : l.getList()) {
			System.out.println(i++  +". " + item.toString());
		}
	}
	
	public static void listCategory(TodoList l) {
		Set<TodoItem> cate = new HashSet<>(l.getList());
		int i  = 0;
		
		if(l.getList().isEmpty()) {
			System.out.println("저장된 내용이 없습니다!");
			return;
		}
		
		for (TodoItem item : cate) {
			if(cate.size() ==  i+1) 
				System.out.println(item.getCategory());
			else
				System.out.print(item.getCategory() + " / ");
			i++;
		}
		System.out.println("총 " + i + "개의 카테고리가 등록되어 있습니다.");
	}
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("제목을 입력하세요. \n" + "> " );
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.print("이미 사용하는 제목입니다. \n" + " 다른 제목을 입력하세요.");
			return;
		}
		
		sc.nextLine();
		System.out.print("내용을 입력하세요~ \n" + "> ");
		desc = sc.nextLine().trim();
		
		
		System.out.print("카테고리를 입력하세요. \n" + "> " );
		category  = sc.next();
		sc.nextLine();
		
		System.out.print("마감일자를 입력하세요. 형식 - yyyy/mm/dd \n" + "> " );
		due_date = sc.next();
		sc.nextLine();
		
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		list.addItem(t);
		System.out.println("리스트를 추가했습니다!");
		
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title, new_category, new_due_date;
		
		if(l.getList().isEmpty()) {
			System.out.println("저장된 내용이 없습니다!");
			return;
		}
		
		TodoUtil.listAll(l);
		
		System.out.print("수정할 항목의 번호를 입력하세요~ \n" + "> ");
		
		try {
			title = l.getList().get(sc.nextInt()-1).getTitle();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("번호를 잘못입력하셨습니다.");
			return;
		}
		
		System.out.print("새로운 제목을 입력하세요~ \n" + "> ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		
		sc.nextLine();
		System.out.print("새로운 내용을 입력하세요~ \n" + "> ");
		String new_description = sc.nextLine().trim();
		
		System.out.print("새 카테고리를 입력하세요. \n" + "> " );
		new_category  = sc.next();
		sc.nextLine();
		
		System.out.print("새 마감일자를 입력하세요. 형식 - yyyy/mm/dd \n" + "> ");
		new_due_date = sc.next();
		sc.nextLine();
		
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
				l.addItem(t);
				System.out.println("내용이 수정되었습니다.");
			}
		}
		
	}
	
	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title;
		
		if(l.getList().isEmpty()) {
			System.out.println("저장된 내용이 없습니다!");
			return;
		}
		
		TodoUtil.listAll(l);
		System.out.print("삭제할 내용의 번호를 입력하세요~ \n" + "> ");
		try {
			title = l.getList().get(sc.nextInt()-1).getTitle();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("번호를 잘못입력하셨습니다.");
			return;
		}
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("내용을 삭제했습니다!");
				break;
			}
		}
	}


	public static void saveList(TodoList l,String filename) {
		try {
			
			Writer w = new FileWriter(filename);
			
			for(TodoItem item : l.getList())
				w.write(item.toSaveString());
			w.close();
			System.out.println("모든 데이터를 저장했습니다.");
		} catch (FileNotFoundException e) {
			System.out.println("저장된 내용이 없습니다!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		String title = null , desc = null, category = null, due_date = null, current_date = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String oneline;
			while((oneline = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline, "##");
				
				category = st.nextToken();
				title = st.nextToken();
				desc = st.nextToken();
				due_date = st.nextToken();
				current_date = st.nextToken();
				
				TodoItem t = new TodoItem(title,desc,category,due_date);
				t.setCurrent_date(current_date);
				l.addItem(t);
			}
		} catch (FileNotFoundException e) {
			System.out.println("todolist.txt 파일이 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void findList(TodoList l , String target) {
		int i  = 0;
		for (TodoItem item : l.getList()) {
			if (item.getTitle().contains(target) || item.getDesc().contains(target)) {
				System.out.println((1+l.indexOf(item))  +". " + item.toString());
			i++;
			}
		}
		System.out.println("총 " + i + "개의 항목을 찾았습니다.");
	}
	
	public static void findCategory(TodoList l , String target) {
		int i  = 0;
		for (TodoItem item : l.getList()) {
			if (item.getCategory().contains(target)) {
				System.out.println((1+l.indexOf(item))  +". " + item.toString());
			i++;
			}
		}
		System.out.println("총 " + i + "개의 항목을 찾았습니다.");
	}
	
	
	
}
