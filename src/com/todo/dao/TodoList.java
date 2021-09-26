package com.todo.dao;

import java.util.*;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
	}

	public void addItem(TodoItem t) {
		list.add(t);
	}

	public void deleteItem(TodoItem t) {
		list.remove(t);
	}

	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
	}


	public void listAll() {
		int i = 1;
		System.out.println("\n<��ü ���, �� "+ list.size() +  "��>");
		for (TodoItem myitem : list) {
			System.out.println(i++  +". " + myitem.toString());
		}
	}
	
	public void sortByName() {
		Collections.sort(list, new TodoSortByName());
	}
	
	public void reverseList() {
		if(list.isEmpty()) {
			System.out.println("����� �޸� �����ϴ�!");
			return;
		}
		Collections.reverse(list);
		System.out.println("���񿪼����� �����߽��ϴ�.");	
	}

	public void sortByDate() {
		if(list.isEmpty()) {
			System.out.println("����� �޸� �����ϴ�!");
			return;
		}
		Collections.sort(list, new TodoSortByDate());
		System.out.println("��¥������ �����߽��ϴ�.");
	}
	
	public void reverseDate() {
		if(list.isEmpty()) {
			System.out.println("����� �޸� �����ϴ�!");
			return;
		}
		Collections.sort(list, new TodoSortByDate());
		Collections.reverse(list);
		System.out.println("��¥ �������� �����߽��ϴ�.");
	}
	

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
}
