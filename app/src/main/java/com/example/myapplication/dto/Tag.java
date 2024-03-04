package com.example.myapplication.dto;

/**
 * Tag DTO used for storing id and text of the Tags.
 */
public class Tag {
    private final int id;
    private final String name;

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
