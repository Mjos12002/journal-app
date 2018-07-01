package com.joseph.sevendaysofcode.Utilities;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Diary.class}, version = 3, exportSchema = false)
public abstract class DiaryDatabase extends RoomDatabase {
    public abstract DaoOperation daoOperation();
}
