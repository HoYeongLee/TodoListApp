package com.todo.service;

import java.util.*;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void listAll(TodoList l) {
		if(l.getList().isEmpty()) {
			System.out.println("����� ������ �����ϴ�!");
			return;
		}
		System.out.printf("\n<��ü ���, �� %d ��> \n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
		
	public static void listAll(TodoList l, String orderby, int ordering) {
		if(l.getList().isEmpty()) {
			System.out.println("����� ������ �����ϴ�!");
			return;
		}
		
		System.out.printf("\n<��ü ���, �� %d ��> \n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, int comp) {
		if(l.getList().isEmpty()) {
			System.out.println("����� ������ �����ϴ�!");
			return;
		}
		int count = 0;
		
		for (TodoItem item : l.getList(comp)) {
			System.out.println(item.toString());
			count++;
		}
		
		if(comp  == 1)
			System.out.println("�� " + count + "���� �׸��� �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("�� " + count + "���� �׸��� �̿Ϸ�Ǿ����ϴ�.");
	
	}
	
	
	public static void listAllMonth(TodoList l) {
		if(l.getList().isEmpty()) {
			System.out.println("����� ������ �����ϴ�!");
			return;
		}
		ArrayList<TodoItem> t = l.getOrderedList("due_date", 1);
		
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		int count = 1;
		int index = 0; 

		while(index <t.size()-1) {
			while( t.get(index).getDue_date().substring(0, 7).equals(t.get(++index).getDue_date().substring(0, 7)) ) {
				count++;
				list.add(t.get(index-1));
				if(index == t.size()-1) {
					list.add(t.get(index));
					break;
				}
			}
			if(t.get(index-1).getDue_date().substring(0, 7).equals(t.get(index).getDue_date().substring(0, 7)) == false)
				list.add(t.get(index-1));
			
			System.out.printf("\n<%d�� %d��>,  �� %d ��\n",t.get(index-1).getYear(), t.get(index-1).getMonth(), count);
			
			for (TodoItem item : list) {
				System.out.println(item.toString());
			}
			list.clear();
			count = 1;
			if( index == t.size()-1 && t.get(index-1).getDue_date().substring(0, 7).equals(t.get(index).getDue_date().substring(0, 7)) == false) {
				System.out.printf("\n<%d�� %d��>,  �� %d ��\n",t.get(index).getYear(), t.get(index).getMonth(), count);
				System.out.println(t.get(index).toString());
				}
			
			}
	}
	
	
	public static void listCategory(TodoList l) {
		if(l.getList().isEmpty()) {
			System.out.println("����� ������ �����ϴ�!");
			return;
		}
		
		int count = 0;
		for (String item : l.getCategories()) {
			System.out.println(item + " ");
			count++;
		}
		System.out.println("�� " + count + "���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.");
	}
	
	public static void createItem(TodoList l) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("������ �Է��ϼ���. \n" + "> " );
		
		title = sc.next();
		if (l.isDuplicate(title)) {
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
		
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		if(l.addItem(t) > 0);
			System.out.println("����Ʈ�� �߰��߽��ϴ�!");
		
	}

	public static void updateItem(TodoList l) {
		String new_title, new_desc, new_category, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		if(l.getList().isEmpty()) {
			System.out.println("����� ������ �����ϴ�!");
			return;
		}
		
		TodoUtil.listAll(l);
		System.out.print("������ �׸��� ��ȣ�� �Է��ϼ���~ \n" + "> ");
		int index = sc.nextInt();
		
		
		System.out.print("���ο� ������ �Է��ϼ���~ \n" + "> ");
		new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
			return;
		}
		
		System.out.print("�� ī�װ��� �Է��ϼ���. \n" + "> " );
		new_category  = sc.next();
		sc.nextLine();
		
		System.out.print("���ο� ������ �Է��ϼ���~ \n" + "> ");
		new_desc = sc.nextLine().trim();
		
		System.out.print("�� �������ڸ� �Է��ϼ���. ���� - yyyy/mm/dd \n" + "> ");
		new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date);
		t.setId(index);
		if(l.updateItem(t) > 0)
			System.out.println("�����Ǿ����ϴ�.");
	}
	
	public static void deleteItem(TodoList l) {
		if(l.getList().isEmpty()) {
			System.out.println("����� ������ �����ϴ�!");
			return;
		}
		
		Scanner sc = new Scanner(System.in);
		TodoUtil.listAll(l);
		System.out.print("������ ������ ��ȣ�� �Է��ϼ���~ \n" + "> ");
		
		StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");
		
		int []  numbers = new int[st.countTokens()];
		int i = 0;
		while(st.hasMoreTokens())
			numbers[i++] = Integer.parseInt(st.nextToken());
		 
		Arrays.sort(numbers);
		i = 0;
		for (TodoItem item : l.getList()) {
			if(i == numbers.length)	
				continue;
			if(item.getId() == numbers[i])
				if(l.deleteItem(item.getId()) > 0)
					i++;
		}
		System.out.println("������ �����߽��ϴ�!");
		
	}
	
	public static void findList(TodoList l , String keyword) {
		if(l.getList().isEmpty()) {
			System.out.println("����� ������ �����ϴ�!");
			return;
		}
		
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void findCategory(TodoList l , String cate) {
		if(l.getList().isEmpty()) {
			System.out.println("����� ������ �����ϴ�!");
			return;
		}
		
		int count  = 0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
			}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void completeItem(TodoList l, String index, boolean is_Comp) {
		if(l.getList().isEmpty()) {
			System.out.println("����� ������ �����ϴ�!");
			return ;
		}
		
		
		StringTokenizer st = new StringTokenizer(index, " ");

		int []  numbers = new int[st.countTokens()];
		int i = 0;
		while(st.hasMoreTokens())
			numbers[i++] = Integer.parseInt(st.nextToken());

		i = 0;
		Arrays.sort(numbers);
		
		if(is_Comp == true) {
			for (TodoItem item : l.getList()) {
				if(i == numbers.length)	
					continue;
				if(item.getId() == numbers[i])
					if(l.completeItem(item, 1) > 0)
						i++;
			}
			System.out.println("�Ϸ� üũ�Ͽ����ϴ�.");
		}
		
		else {
			for (TodoItem item : l.getList()) {
				if(i == numbers.length)	
					break;
				if(item.getId() == numbers[i])
					if(l.completeItem(item, 0) > 0)
						i++;
			}
			System.out.println("üũ���� �߽��ϴ�.");
		}
		
	}
	
	public static void lateListAll(TodoList l) {
		if(l.getList().isEmpty()) {
			System.out.println("����� ������ �����ϴ�!");
			return;
		}
		System.out.printf("\n< ������ ����� �� %d �� �ֽ��ϴ�.> \n", l.getLateList().size());
		for (TodoItem item : l.getLateList()) {
			System.out.println(item.toString());
		}
		
		
	}
	
	
} 
