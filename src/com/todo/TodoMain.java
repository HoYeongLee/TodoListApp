package com.todo;

import java.io.File;
import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
		
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		
		File f = new File("todolist.txt");
		
		if(f.exists()) {
			TodoUtil.loadList(l, "todolist.txt");
			TodoUtil.listAll(l);
			System.out.println(l.getList().size() + "개의 항목을 읽었습니다.");
		}
		else
			System.out.println("todolist.txt 파일이 없습니다.");
		
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				if(l.getList().isEmpty()) {
					System.out.println("저장된 메모가 없습니다!");
					break;
				}
				l.sortByName();
				System.out.println("제목순으로 저장했습니다.");
				isList = true;
				break;

			case "ls_name_desc":
				if(l.getList().isEmpty()) {
					System.out.println("저장된 메모가 없습니다!");
					break;
				}
				l.sortByName();
				l.reverseList();
				System.out.println("제목역순으로 정렬했습니다.");
				isList = true;
				break;
				
			case "ls_date":
				if(l.getList().isEmpty()) {
					System.out.println("저장된 메모가 없습니다!");
					break;
				}
				l.sortByDate();
				System.out.println("날짜순으로 정렬했습니다.");
				isList = true;
				break;

			case "exit":
				quit = true;
				TodoUtil.saveList(l, "todolist.txt");
				if(l.getList().isEmpty()) {
					System.out.println("저장된 메모가 없습니다!");
					break;
				}
				System.out.println("모든 데이터를 저장했습니다.");
				break;
				
			case "help":
				Menu.displaymenu();
				break;
			default:
				System.out.println("\n잘못 입력하셨습니다. (help - 도움말)");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
		
	}
}
