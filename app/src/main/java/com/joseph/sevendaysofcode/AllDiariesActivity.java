package com.joseph.sevendaysofcode;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.joseph.sevendaysofcode.Utilities.DiariesAdapter;
import com.joseph.sevendaysofcode.Utilities.Diary;
import com.joseph.sevendaysofcode.Utilities.DiaryDatabase;

import java.util.ArrayList;
import java.util.List;

public class AllDiariesActivity extends AppCompatActivity {

    TextView tvUsername;
    private List<Diary> diaryList;
    private RecyclerView recyclerView;
    private DiariesAdapter mAdapter;
    DiaryDatabase diaryDatabase;
    private static String DATABASE_NAME = "diaries_db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_diaries);
        init();
        new LoadAllDiaries().execute(null, null, null);

    }

    public void init(){

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        diaryDatabase = Room.databaseBuilder(getApplicationContext(), DiaryDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    public void addDiary(View v){

    }

    class LoadAllDiaries extends AsyncTask<Void, Void, List<Diary>> {
        @Override
        protected List<Diary> doInBackground(Void... voids) {
            List<Diary> listOfDiaries = diaryDatabase.daoOperation().getAllDiary();
            return listOfDiaries;
        }

        @Override
        protected void onPostExecute(List<Diary> diaries) {
            super.onPostExecute(diaries);
            mAdapter = new DiariesAdapter(diaries);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
