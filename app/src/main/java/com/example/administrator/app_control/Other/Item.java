package com.example.administrator.app_control.Other;

public class Item {
    String ID;
    String description;
    String time;
    String isRepeat;
    String repeatDes;
    String isActive;

    public void Item (String ID, String name , String time , String repeat,String repeatype,String isActive){
        ID = this.ID;
        name = this.description;
        time = this.time;
        repeat = this.isRepeat;
        repeatype = this.repeatDes;
        isActive = this.isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsActive() {
        return isActive;
    }

    public String getID() {
        return ID;
    }

    public String getDescription() {
        return description;
    }

    public String getIsRepeat() {
        return isRepeat;
    }

    public String getRepeatDes() {
        return repeatDes;
    }

    public String getTime() {
        return time;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsRepeat(String isRepeat) {
        this.isRepeat = isRepeat;
    }

    public void setRepeatDes(String repeatDes) {
        this.repeatDes = repeatDes;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
