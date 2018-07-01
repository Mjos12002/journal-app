package com.joseph.sevendaysofcode.Utilities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Diary {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int diaryID;
    public String title;
    public String description;
    public String date;

    public Diary(){}
    public int getDiaryID(){
        return this.diaryID;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDiaryID(int diaryID){
        this.diaryID = diaryID;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
