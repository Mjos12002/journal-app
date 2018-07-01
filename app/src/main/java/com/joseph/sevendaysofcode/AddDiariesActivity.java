package com.joseph.sevendaysofcode;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.flags.impl.DataUtils;
import com.joseph.sevendaysofcode.Utilities.Diary;
import com.joseph.sevendaysofcode.Utilities.DiaryDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddDiariesActivity extends AppCompatActivity {

    TextView tvServerResponse;
    EditText etDiaryDescription, etDiaryTitle;
    String stTitle, stDescription, stUsername, status;
    Button btnAddNew;
    private static String DATABASE_NAME = "diaries_db";
    private DiaryDatabase diaryDatabase;
    String id, description, title, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diaries);
        stUsername = "None";
        status = "New";
        init();
        diaryDatabase = Room.databaseBuilder(getApplicationContext(), DiaryDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
        if(getIntent().getExtras().getString("username") != null){
            stUsername = getIntent().getExtras().getString("username");
        }
        if(getIntent().getExtras().getString("status") != null){
            status = getIntent().getExtras().getString("status");
            id = getIntent().getExtras().getString("id");
            description = getIntent().getExtras().getString("desc");
            title = getIntent().getExtras().getString("title");
            date = getIntent().getExtras().getString("date");
            etDiaryTitle.setText(title);
            etDiaryDescription.setText(description);
            btnAddNew.setText("Update");
        }

    }

    public void addDiary(View v){
        stTitle = etDiaryTitle.getText().toString();
        stDescription = etDiaryDescription.getText().toString();
        if(TextUtils.isEmpty(stTitle) || TextUtils.isEmpty(stDescription)){
            Toast.makeText(this, "No information added", Toast.LENGTH_SHORT).show();
        }else
            new PersisitDiaries().execute(null, null, null);
    }

    public void init(){
        tvServerResponse = (TextView)findViewById(R.id.tvServerResponse);
        etDiaryDescription = (EditText)findViewById(R.id.et_diary_description);
        etDiaryTitle = (EditText)findViewById(R.id.et_diary_title);
        btnAddNew = (Button)findViewById(R.id.btn_add_diary);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mn_load_all:
                Intent loadAllDiariesIntent = new Intent(getApplicationContext(), AllDiariesActivity.class);
                startActivity(loadAllDiariesIntent);
                return true;
            default:
                finish();
        }
        return true;
    }

    class PersisitDiaries extends AsyncTask<Void, Void, String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvServerResponse.setText(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String message = "";
            Date dateNow = new Date( );
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
            try {
                if(status.equals("New")) {
                    Diary diary = new Diary();
                    diary.setDiaryID(0);
                    diary.setTitle(stTitle);
                    diary.setDescription(stDescription);
                    diary.setDate(ft.format(dateNow));
                    diaryDatabase.daoOperation()
                            .insertOneDiary(diary);
                    List<Diary> diaries = diaryDatabase.daoOperation().getAllDiary();
                    message = "Information saved successfully";
                }else{

                    Diary diary = new Diary();
                    diary.setDiaryID(Integer.parseInt(id));
                    diary.setDate(date);
                    diary.setDescription(description);
                    diary.setTitle(title);
                    diaryDatabase.daoOperation().insertOneDiary(diary);
                    List<Diary> diaries = diaryDatabase.daoOperation().getAllDiary();
                    message = "Information updated successfully";
                }
            }catch (Exception ex){
                message = "An error occured " + ex.toString();
            }
            return message;
        }
    }
}
