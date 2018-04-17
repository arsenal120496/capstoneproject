package com.example.administrator.app_control.Other;

public class Item {
    int ID;
    String description;
    String time;
    int isRepeat;
    String repeatDes;
    int isActive;

    public void Item (int ID, String name , String time , int isRepeat,String repeatype,int isActive){
        ID = this.ID;
        name = this.description;
        time = this.time;
        isRepeat = this.isRepeat;
        repeatype = this.repeatDes;
        isActive = this.isActive;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public int getIsActive() {
        return isActive;
    }

    public int getIsRepeat() {
        return isRepeat;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public void setIsRepeat(int isRepeat) {
        this.isRepeat = isRepeat;
    }

    public String getRepeatDes() {
        return repeatDes;
    }

    public String getTime() {
        return time;
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
}
