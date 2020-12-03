package com.brandmaker.mediapoolmalbridge.model.brandmaker.theme;

import java.util.List;

/**
 * BM Theme
 */
public class BMTheme {

    String id;
    String name;
    List<BMTheme> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<BMTheme> getChildren() {
        return children;
    }
}
