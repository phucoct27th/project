package com.phuc.mediaapp;

public class Song {
    private String Title;
    private int file;

    public Song(String title, int file) {
        Title = title;
        this.file = file;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }
}
