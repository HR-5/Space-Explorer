package com.example.nasa.Model;

public class Apod {
    String date;
    String media_type;
    String url;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Apod(String date, String media_type, String url) {
        this.date = date;
        this.media_type = media_type;
        this.url = url;
    }
}
