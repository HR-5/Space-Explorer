package com.example.nasa.ImgModel;

public class Asset {
    Collection collection;

    public Collection getCollections() {
        return collection;
    }

    public void setCollections(Collection collections) {
        this.collection = collections;
    }

    public Asset(Collection collections) {
        this.collection = collections;
    }
}
