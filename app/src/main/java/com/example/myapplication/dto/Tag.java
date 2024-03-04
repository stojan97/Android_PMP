package com.example.myapplication.dto;

public class Tag {
    private final int id;
    private final String label;

    public Tag(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }
}
