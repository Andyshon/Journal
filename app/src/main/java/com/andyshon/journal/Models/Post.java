package com.andyshon.journal.Models;

/**
 * Created by andyshon on 17.07.18.
 */

public class Post {
    private int id;
    private String name;
    private byte[] image;


    public Post(int id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Post(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
