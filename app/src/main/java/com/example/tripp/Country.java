package com.example.tripp;

import java.io.Serializable;

public class Country implements Serializable {
    private int imageRes;

    private String name;

    public Country(String name, int imageRes) {
        this.name = name;
        this.imageRes = imageRes;
    }

    public String getName() {
        return name;
    }

    public int getImageRes() {
        return imageRes;
    }
}
