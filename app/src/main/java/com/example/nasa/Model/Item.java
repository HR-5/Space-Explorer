package com.example.nasa.Model;

import java.util.ArrayList;

public class Item {
    ArrayList<Data> data;
    public Item(ArrayList<Data> data) {
        this.data = data;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}
