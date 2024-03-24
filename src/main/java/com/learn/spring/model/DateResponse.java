package com.learn.spring.model;

public class DateResponse {
    private int year;
    private String text;

    public DateResponse(int year, String text) {
        this.year = year;
        this.text = text;
    }

    public int getYear() {
        return year;
    }

    public String getText() {
        return text;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setText(String text) {
        this.text = text;
    }
}
