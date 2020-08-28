package com.example.nasa.Model;

public class Data {
    String nasa_id;
    String title;
    String description;

    public String getNasa_id() {
        return nasa_id;
    }

    public void setNasa_id(String nasa_id) {
        this.nasa_id = nasa_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Data(String nasa_id, String title, String description) {
        this.nasa_id = nasa_id;
        this.title = title;
        this.description = description;
    }
}
