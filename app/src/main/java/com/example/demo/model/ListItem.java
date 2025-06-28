package com.example.demo.model;

/**
 * 列表项数据模型
 */
public class ListItem {
    private int id;
    private String title;
    private String subtitle;
    private String description;
    private int imageResId;

    public ListItem(int id, String title, String subtitle, String description, int imageResId) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.imageResId = imageResId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
} 