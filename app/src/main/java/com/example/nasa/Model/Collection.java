package com.example.nasa.Model;

import java.util.ArrayList;

public class Collection {
    ArrayList<Item> items;

    public Collection(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
