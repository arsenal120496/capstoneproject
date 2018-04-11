package com.example.administrator.app_control.Other;

import java.util.ArrayList;

public class ItemListView {

    public ItemListView(){}

    private static ArrayList<Item> list = null;

    public static void setList(ArrayList<Item> list) {
        ItemListView.list = list;
    }

    public static ArrayList<Item> getList() {
        return list;
    }
}
