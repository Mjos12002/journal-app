package com.joseph.sevendaysofcode.Utilities;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.icu.text.Replaceable;

import java.util.List;

@Dao
public interface DaoOperation {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOneDiary(Diary diary);
    @Update
    void updateDiary(Diary diary);
    @Delete
    void deleteDiary(Diary diary);
    @Query("SELECT diaryID, title, description, date from DIARY")
    List<Diary> getAllDiary();
    @Query("SELECT diaryID, title, description, date from Diary where diaryID = :id")
    Diary getOneRecord(int id);
    @Query("UPDATE Diary set description = :description, date =:date, title=:title where diaryID=:id")
    void updateRecord(String description, String date, String title, int id);

}

