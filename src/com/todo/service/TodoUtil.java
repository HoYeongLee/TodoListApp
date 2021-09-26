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
			System.out.println("����� ������ �����ϴ�!");
			return;
		}
		System.out.println("\n<��ü ���, �� "+ l.getList().size() +  "��>");
		for (TodoItem item : l.getList()) {
			System.out.println(i++  +". " + item.toString());
		}
	}
	
	public static void listCategory(TodoList l) {
		Set<TodoItem> cate = new HashSet<>(l.getList());
		int i  = 0;
		
		if(l.getList().isEmpty()) {
			System.out.println("����� ������ �����ϴ�!");
			return;
		}
		
		for (TodoItem item : cate) {
			if(cate.size() ==  i+1) 
				System.out.println(item.getCategory());
			else
				System.out.print(item.getCategory() + " / ");
			i++;
		}
		System.out.println("�� " + i + "���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.");
	}
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
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
		
		
		System.out.print("ī�װ��� �Է��ϼ���. \n" + "> " );
		category  = sc.next();
		sc.nextLine();
		
		System.out.print("�������ڸ� �Է��ϼ���. ���� - yyyy/mm/dd \n" + "> " );
		due_date = sc.next();
		sc.nextLine();
		
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		list.addItem(t);
		System.out.println("����Ʈ�� �߰��߽��ϴ�!");
		
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title, new_category, new_due_date;
		
		if(l.getList().isEmpty()) {
			System.out.println("����� ������ �����ϴ�!");
			return;
		}
		
		TodoUtil.listAll(l);
		
		System.out.print("������ �׸��� ��ȣ�� �Է��ϼ���~ \n" + "> ");
		
		try {
			title = l.getList().get(sc.nextInt()-1).getTitle();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("��ȣ�� �߸��Է��ϼ̽��ϴ�.");
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
		
		System.out.print("�� ī�װ��� �Է��ϼ���. \n" + "> " );
		new_category  = sc.next();
		sc.nextLine();
		
		System.out.print("�� �������ڸ� �Է��ϼ���. ���� - yyyy/mm/dd \n" + "> ");
		new_due_date = sc.next();
		sc.nextLine();
		
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
				l.addItem(t);
				System.out.println("������ �����Ǿ����ϴ�.");
			}
		}
		
	}
	
	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title;
		
		if(l.getList().isEmpty()) {
			System.out.println("����� ������ �����ϴ�!");
			return;
		}
		
		TodoUtil.listAll(l);
		System.out.print("������ ������ ��ȣ�� �Է��ϼ���~ \n" + "> ");
		try {
			title = l.getList().get(sc.nextInt()-1).getTitle();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("��ȣ�� �߸��Է��ϼ̽��ϴ�.");
			return;
		}
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("������ �����߽��ϴ�!");
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
			System.out.println("��� �����͸� �����߽��ϴ�.");
		} catch (FileNotFoundException e) {
			System.out.println("����� ������ �����ϴ�!");
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
			System.out.println("todolist.txt ������ �����ϴ�.");
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
		System.out.println("�� " + i + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void findCategory(TodoList l , String target) {
		int i  = 0;
		for (TodoItem item : l.getList()) {
			if (item.getCategory().contains(target)) {
				System.out.println((1+l.indexOf(item))  +". " + item.toString());
			i++;
			}
		}
		System.out.println("�� " + i + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	
	
}
