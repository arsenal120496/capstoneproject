package com.example.administrator.app_control.Other;

public class Item {
    int ID;
    String description;
    int isActive;
    int isRepeat;
    String repeatDes;
    String time;

    public void Item(int ID, String name, String time, String repeatype, int isActive, int isRepeat) {
        ID = this.ID;
        name = this.description;
        time = this.time;
        repeatype = this.repeatDes;
        isActive = this.isActive;
        isRepeat = this.isRepeat;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return this.description;
    }

    public int getIsActive() {
        return this.isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getRepeatDes() {
        return this.repeatDes;
    }

    public String getTime() {
        return this.time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRepeatDes(String repeatDes) {
        this.repeatDes = repeatDes;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsRepeat() {
        return this.isRepeat;
    }

    public void setIsRepeat(int isRepeat) {
        this.isRepeat = isRepeat;
    }
}
