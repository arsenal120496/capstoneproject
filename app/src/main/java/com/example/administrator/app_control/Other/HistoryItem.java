package com.example.administrator.app_control.Other;

public class HistoryItem {

    private String name;
    private String time;
    private String area;

    public HistoryItem(String name, String time, String area){
        this.name = name;
        this.time = time;
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

