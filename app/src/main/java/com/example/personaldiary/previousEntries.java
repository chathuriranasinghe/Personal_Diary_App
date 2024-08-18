package com.example.personaldiary;

import io.realm.RealmObject;

public class previousEntries extends RealmObject {
    String title;
    String description;
    String date;
    String time;
    //long createdTime;
    private byte[] imageData;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public long getCreatedTime() {
//        return createdTime;
//    }

//    public void setCreatedTime(long createdTime) {
//        this.createdTime = createdTime;
//    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public byte[] getImageData(){
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }


    public previousEntries(){
        this.title = title;
        this.description = description;
        //this.createdTime = createdTime;
        this.date = date;
        this.time = time;
    }

}

