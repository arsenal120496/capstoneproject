package com.example.administrator.app_control.Other;

public class Item {
    String name;
    String time;
    String repeat;

    public void Item ( String name , String time , String repeat){
        name = this.name;
        time = this.time;
        repeat = this.repeat;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getRepeat() {
        return repeat;
    }
}
