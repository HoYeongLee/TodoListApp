package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class TodoItem {
	
	private int id;
    private String title;
    private String desc;
    private String current_date;
    private String category;
	private String due_date;
	private int is_completed;
	private String check;
	private int d_Day;
	private int year;
	private int month;
	private int date;

    public TodoItem(String title, String desc, String category, String due_date){
        this.title=title;
        this.desc=desc;
        this.category = category;
        this.due_date = due_date;
        Date t = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy/MM/dd - kk:mm:ss");
        this.current_date = s.format(t);
        this.is_completed = 0;
        completeChecking();
        
        StringTokenizer st = new StringTokenizer(due_date, "/");
		this.year = Integer.parseInt(st.nextToken());
		this.month = Integer.parseInt(st.nextToken());
		this.date = Integer.parseInt(st.nextToken());
		takeD_Day();
    }
    
    public void takeD_Day() {
    	//비교할 날짜
		Calendar target = Calendar.getInstance();
		target.set(year, month-1, date);
		
		// 현재 날짜
		Calendar present = Calendar.getInstance();
 		
		// 5. D- Day 계산
		long time =  (target.getTimeInMillis() - present.getTimeInMillis())/(24*60*60*1000);
		this.d_Day = (int) time;
    }

    public void completeChecking() {
		if(is_completed == 0)
			this.check =  "";
		else
			this.check = "[V]";
	}
    
//    public String toSaveString() {
//		return category + "##" + title + "##" + desc + "##" + due_date + "##" + current_date +"\n";
//	}
    
    public String dDaynotification() {
    	if(d_Day > 0)
    		return "D - " + d_Day;
    	else if (d_Day == 0)
    		return "D-day";
    	else
    		return "D + " + -1 * d_Day;
    	
	}
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
    	return category;
    }
    
    public void setCategory(String category) {
    	this.category = category;
    }
    
    public String getDue_date() {
    	return due_date;
    }
    
    public void setDue_date(String due_date) {
    	this.due_date = due_date;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }

    @Override
    public String toString() {
    	return  id + ". " + "[" + category + "] " + title + check + " - " + desc + " - "+  due_date + " - " + " \"" + current_date + "\""  + "  " + dDaynotification() ;
    }

	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}
	
	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public int getD_Day() {
		return d_Day;
	}

	public void setD_Day(int d_Day) {
		this.d_Day = d_Day;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setMonth(int month) {
		this.month = month;
	}
    
}
