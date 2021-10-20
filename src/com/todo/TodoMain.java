package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
			
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean quit = false;
//		l.importData("todolist.txt");
		l.bootItem(l.getList());
		TodoUtil.lateListAll(l);
		TodoUtil.listAll(l);
		
		Menu.displaymenu();
		do {
			Menu.prompt();
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

			case "ls_name":
				System.out.println("제목순으로 정렬했습니다.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("제목역순으로 정렬했습니다.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("날짜순으로 정렬했습니다.");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("날짜역순으로 정렬했습니다.");
				TodoUtil.listAll(l, "due_date", 0);
				break;
				
			case "exit":
				quit = true;
				break;
				
			case "help":
				Menu.displaymenu();
				break;
				
			case "find":
				String keyword =sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
				break;
				
			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.findCategory(l, cate);
				break;
		
			case "ls_cate":
				TodoUtil.listCategory(l);
				break;
				
			case "ls_comp":
				TodoUtil.listAll(l, 1);
				break;
				
			case "ls_uncomp":
				TodoUtil.listAll(l, 0);
				break;
				
			case "comp":
				String number = sc.nextLine();
				TodoUtil.completeItem(l, number, true);
				break;
				
			case "uncomp":
				number = sc.nextLine();
				TodoUtil.completeItem(l, number, false);
				break;
				
			case "ls_month":
				number = sc.nextLine();
				TodoUtil.listAllMonth(l);
				break;
				
			case "ls_lated":
				TodoUtil.lateListAll(l);
				break;
				
			default:
				System.out.println("\n잘못 입력하셨습니다. (help - 도움말)");
				break;
			}
			
		} while (!quit);
		
	}
}
