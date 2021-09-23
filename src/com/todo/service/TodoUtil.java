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
		
		System.out.print("������ �Է��ϼ���. \n" + "> " );
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.print("�̹� ����ϴ� �����Դϴ�. \n" + " �ٸ� ������ �Է��ϼ���.");
			return;
		}
		
		sc.nextLine();
		System.out.print("������ �Է��ϼ���~ \n" + "> ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("�޸� �߰��߽��ϴ�!");
		
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		if(l.getList().isEmpty()) {
			System.out.println("����� �޸� �����ϴ�!");
			return;
		}
		
		
		TodoUtil.listAll(l);
		System.out.print("������ �޸��� ������ �Է��ϼ���~ \n" + "> ");
		String title = sc.next();
		
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("�޸� �����߽��ϴ�!");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		if(l.getList().isEmpty()) {
			System.out.println("����� �޸� �����ϴ�!");
			return;
		}
		
		TodoUtil.listAll(l);
		System.out.print("�����ϰ� ���� �޸��� ������ �Է��ϼ���~ \n" + "> ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("������ �������� �ʽ��ϴ�!");
			return;
		}

		System.out.print("���ο� ������ �Է��ϼ���~ \n" + "> ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
			return;
		}
		
		sc.nextLine();
		System.out.print("���ο� ������ �Է��ϼ���~ \n" + "> ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("�޸� �����Ǿ����ϴ�.");
			}
		}

	}

	public static void listAll(TodoList l) {
		if(l.getList().isEmpty()) {
			System.out.println("����� �޸� �����ϴ�!");
			return;
		}
		System.out.println("\n<����� �޸�>");
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
