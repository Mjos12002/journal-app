package com.joseph.sevendaysofcode.Utilities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.joseph.sevendaysofcode.AllDiariesActivity;
import com.joseph.sevendaysofcode.R;

import java.util.List;

public class RemoveDiary extends AppCompatActivity {

    DiaryDatabase diaryDatabase;
    private static String DATABASE_NAME = "diaries_db";
    Diary diaryToDelete;
    int recordID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diaryDatabase = Room.databaseBuilder(getApplicationContext(), DiaryDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
        String row = getIntent().getExtras().getString("row_id");
        recordID = Integer.parseInt(row);
        Toast.makeText(this, row, Toast.LENGTH_SHORT).show();
        new DeleteRow().execute(null, null, null);
    }

    class DeleteRow extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(getApplicationContext(), AllDiariesActivity.class);
            startActivity(intent);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String message = "";
            try {
                Diary diary = diaryDatabase.daoOperation().getOneRecord(recordID);
                diaryDatabase.daoOperation().deleteDiary(diary);
                message = "Deleted successfully";
            }catch (Exception ex){
                message = "Error";
            }
            return message;
        }
    }
}
