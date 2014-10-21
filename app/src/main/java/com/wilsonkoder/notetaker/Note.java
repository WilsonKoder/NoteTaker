//Wilson Koder 30/07/14
//Copyright (c) Wilson Koder 2014
//"NoteTaker" for Android OS
//wilsonkoder111+dev@gmail.com


package com.wilsonkoder.notetaker;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable{
    private String title;
    private String note;
    private Date date;

    public Note(String title, String note, Date date) {
        super();
        this.title = title;
        this.note = note;
        this.date = date;


    }
    public String getTitle() {
        return title;
    }
    public void setTitle() {
        this.title = title;
    }
    public String getNote() {
        return note;
    }
    public void setNote() {
        this.note = note;
    }
    public Date getDate() {
        return date;
    }
    public void setDate() {
        this.date = date;
    }
}
