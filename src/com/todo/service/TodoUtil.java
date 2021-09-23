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
	
	public static void createItem(TodoList list) {
		
		String title, desc;
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
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("메모를 추가했습니다!");
		
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		if(l.getList().isEmpty()) {
			System.out.println("저장된 메모가 없습니다!");
			return;
		}
		
		
		TodoUtil.listAll(l);
		System.out.print("삭제할 메모의 제목을 입력하세요~ \n" + "> ");
		String title = sc.next();
		
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("메모를 삭제했습니다!");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		if(l.getList().isEmpty()) {
			System.out.println("저장된 메모가 없습니다!");
			return;
		}
		
		TodoUtil.listAll(l);
		System.out.print("변경하고 싶은 메모의 제목을 입력하세요~ \n" + "> ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("제목이 존재하지 않습니다!");
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
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("메모가 수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		if(l.getList().isEmpty()) {
			System.out.println("저장된 메모가 없습니다!");
			return;
		}
		System.out.println("\n<저장된 메모>");
		for (TodoItem item : l.getList()) {
			 System.out.println(item.toString());
		}
	}
	
	public static void saveList(TodoList l,String filename) {
		try {
			
			Writer w = new FileWriter(filename);
			
			for(TodoItem item : l.getList())
				w.write(item.toSaveString());
			w.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String oneline;
			
			while((oneline = br.readLine()) != null) {
				if(oneline.equals("\n"))
					break;
				StringTokenizer st = new StringTokenizer(oneline, "##");
				TodoItem t = new TodoItem(st.nextToken(), st.nextToken());
				l.addItem(t);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
