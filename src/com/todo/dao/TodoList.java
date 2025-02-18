package com.todo.dao;

//import java.io.BufferedReader;
//import java.io.FileReader;
//import com.todo.service.TodoSortByDate;
//import com.todo.service.TodoSortByName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import com.todo.service.DbConnnect;

public class TodoList {
	Connection conn;

	public TodoList() {
		this.conn = DbConnnect.getConnection();
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date, d_Day, year, month, date)"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getD_Day());
			pstmt.setInt(7, t.getYear());
			pstmt.setInt(8, t.getMonth());
			pstmt.setInt(9, t.getDate());

			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}


	public int deleteItem(int index) {
		String sql = "delete from list where id = ? ;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updateItem(TodoItem t) {
		String sql = "update list set title = ?, memo = ? , category = ?, current_date = ?, due_date = ?, d_Day = ?, year = ?, month = ?, date = ?"
				+ " where id = ? ;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getD_Day());
			pstmt.setInt(7, t.getYear());
			pstmt.setInt(8, t.getMonth());
			pstmt.setInt(9, t.getDate());
			pstmt.setInt(10, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return count;
	}

	public int completeItem(TodoItem t, int is_completed) {
		String sql = "update list set is_completed = ? where id = ? ;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, is_completed);
			pstmt.setInt(2, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}


	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs =stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date =rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");

				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				t.completeChecking();
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<TodoItem> getList(String keyword) {

		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" + keyword +"%";
		try {
			String sql = "SELECT * FROM list where title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date =rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");

				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				t.completeChecking();
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getList(int comp) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		String sql = "SELECT * FROM list where is_completed = ?;";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comp);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date =rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");

				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				t.completeChecking();
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	public ArrayList<TodoItem> getListCategory(String keyword) {

		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ? ;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date =rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");

				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				t.completeChecking();
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {

		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if (ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date =rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");

				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				t.completeChecking();
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT id FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
				count++;				
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<TodoItem> getLateList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list where d_Day < 0 and is_completed = 0 ";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date =rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				t.completeChecking();
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	public ArrayList<String> getCategories() {

		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
				list.add(rs.getString("category"));
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void bootItem(ArrayList<TodoItem> list) {
		PreparedStatement pstmt;
		String sql = "update list set d_Day = ?, year = ?, month = ?, date = ? where id = ? ;";
		int i = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			while (i < list.size()) {
				pstmt.setInt(1, list.get(i).getD_Day());
				pstmt.setInt(2, list.get(i).getYear());
				pstmt.setInt(3, list.get(i).getMonth());
				pstmt.setInt(4, list.get(i).getDate());
				pstmt.setInt(5, list.get(i).getId());
				pstmt.executeUpdate();
				i++;
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public Boolean isDuplicate(String title) {
		for (TodoItem item : getList()) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}

	//	public void importData(String filename) {
	//		try {
	//			BufferedReader br = new BufferedReader(new FileReader(filename));
	//			String Line;
	//			String sql = "insert into list (title, memo, category, current_date, due_date)" 
	//					+ " values (?, ?, ?, ?, ?) ;";
	//			int records = 0;
	//			while((Line = br.readLine()) != null){
	//				StringTokenizer st = new StringTokenizer(Line, "##");
	//				String category = st.nextToken();
	//				String title = st.nextToken();
	//				String description = st.nextToken();
	//				String due_date = st.nextToken();
	//				String current_date = st.nextToken();
	//				
	//				PreparedStatement pstmt = conn.prepareStatement(sql);
	//				pstmt.setString(1, title);
	//				pstmt.setString(2, description);
	//				pstmt.setString(3, category);
	//				pstmt.setString(4, current_date);
	//				pstmt.setString(5, due_date);
	//				int count = pstmt.executeUpdate();
	//				if(count > 0)
	//					records++;
	//				pstmt.close();
	//			}
	//			System.out.println( records + "record read!!");
	//			br.close();
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//		
	//	}
}
