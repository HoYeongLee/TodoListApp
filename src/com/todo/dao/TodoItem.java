package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String desc;
    private String current_date;


    public TodoItem(String title, String desc){
        this.title=title;
        this.desc=desc;
        Date t = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy³â MM¿ù ddÀÏ");
        this.current_date = s.format(t);
    }
    
    public String toSaveString() {
		return title + "##" + desc + "##" + current_date +"\n";
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

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    @Override
    public String toString() {
    	return "[" + title + "]" + "-" + desc + " \"" + current_date + "\"";
    }
    
}
