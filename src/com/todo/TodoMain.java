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
			System.out.println(l.getList().size() + "���� �׸��� �о����ϴ�.");
		}
		else
			System.out.println("todolist.txt ������ �����ϴ�.");
		
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
					System.out.println("����� �޸� �����ϴ�!");
					break;
				}
				l.sortByName();
				System.out.println("��������� �����߽��ϴ�.");
				isList = true;
				break;

			case "ls_name_desc":
				if(l.getList().isEmpty()) {
					System.out.println("����� �޸� �����ϴ�!");
					break;
				}
				l.sortByName();
				l.reverseList();
				System.out.println("���񿪼����� �����߽��ϴ�.");
				isList = true;
				break;
				
			case "ls_date":
				if(l.getList().isEmpty()) {
					System.out.println("����� �޸� �����ϴ�!");
					break;
				}
				l.sortByDate();
				System.out.println("��¥������ �����߽��ϴ�.");
				isList = true;
				break;

			case "exit":
				quit = true;
				TodoUtil.saveList(l, "todolist.txt");
				if(l.getList().isEmpty()) {
					System.out.println("����� �޸� �����ϴ�!");
					break;
				}
				System.out.println("��� �����͸� �����߽��ϴ�.");
				break;
				
			case "help":
				Menu.displaymenu();
				break;
			default:
				System.out.println("\n�߸� �Է��ϼ̽��ϴ�. (help - ����)");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
		
	}
}
