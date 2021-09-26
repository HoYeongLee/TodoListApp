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
		
		TodoUtil.loadList(l, "todolist.txt");
		TodoUtil.listAll(l);
		
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
				System.out.println("제목순으로 정렬했습니다.");
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				isList = true;
				break;
				
			case "ls_date_asc":
				l.sortByDate();
				isList = true;
				break;
				
			case "ls_date_desc":
				l.reverseDate();
				isList = true;
				break;
				
			case "exit":
				quit = true;
				TodoUtil.saveList(l, "todolist.txt");
				break;
				
			case "help":
				Menu.displaymenu();
				break;
				
			case "find":
				TodoUtil.findList(l, sc.next());
				break;
				
			case "find_cate":
				TodoUtil.findCategory(l, sc.next());
				break;
		
			case "ls_cate":
				TodoUtil.listCategory(l);
				break;
				
			default:
				System.out.println("\n잘못 입력하셨습니다. (help - 도움말)");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
		
	}
}
