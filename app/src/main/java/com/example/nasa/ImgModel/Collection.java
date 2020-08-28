package com.example.nasa.ImgModel;

import java.util.ArrayList;

public class Collection {
    ArrayList<Items> items;
    String version;
    String href;

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Collection(ArrayList<Items> items, String version, String href) {
        this.items = items;
        this.version = version;
        this.href = href;
    }
}
