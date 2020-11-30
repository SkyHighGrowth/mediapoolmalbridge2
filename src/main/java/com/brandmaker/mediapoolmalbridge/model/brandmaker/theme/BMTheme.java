package com.brandmaker.mediapoolmalbridge.model.brandmaker.theme;

import java.util.List;


public class BMTheme {
    String id;
    String name;
    boolean leaf;
    boolean disabled;
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

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<BMTheme> getChildren() {
        return children;
    }

    public void setChildren(List<BMTheme> children) {
        this.children = children;
    }
}
