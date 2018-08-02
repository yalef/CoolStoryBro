package com.example.len.coolstorybro;

public class Story {
    String title;
    String number;
    String content;
    public Story(String title, String number, String content){
        this.title = title;
        this.number = number;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
