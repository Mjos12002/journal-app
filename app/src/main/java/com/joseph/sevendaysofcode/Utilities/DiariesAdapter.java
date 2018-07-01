package com.joseph.sevendaysofcode.Utilities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.joseph.sevendaysofcode.AddDiariesActivity;
import com.joseph.sevendaysofcode.AllDiariesActivity;
import com.joseph.sevendaysofcode.R;

import java.util.List;

public class DiariesAdapter extends RecyclerView.Adapter<DiariesAdapter.MyViewHolder> {

    List<Diary> diaryList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDescription, tvTitle, tvDate, tvDelete, tvEdit;

        public MyViewHolder(View view) {
            super(view);
            tvDescription = (TextView) view.findViewById(R.id.tv_view_title);
            tvTitle = (TextView) view.findViewById(R.id.tv_view_description);
            tvDate = (TextView) view.findViewById(R.id.tv_view_date);
            tvEdit = (TextView) view.findViewById(R.id.tv_edit);
            tvDelete = (TextView) view.findViewById(R.id.tv_delete);

        }
    }

    public DiariesAdapter(List<Diary> diaryList) {
        this.diaryList = diaryList;
    }

    @NonNull
    @Override
    public DiariesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_diaries_list_row, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull DiariesAdapter.MyViewHolder holder, final int position) {
        Context context;
        Diary movie = diaryList.get(position);
        holder.tvDescription.setText(movie.getDescription());
        holder.tvTitle.setText(movie.getTitle());
        holder.tvDate.setText(movie.getDate());
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Diary diary = diaryList.get(position);
                int id = diary.getDiaryID();
                String desc = diary.getDescription();
                String title = diary.getTitle();
                String date = diary.getDate();
                Intent intent = new Intent(v.getContext(), AddDiariesActivity.class);
                intent.putExtra("date", date);
                intent.putExtra("desc", desc);
                intent.putExtra("id", String.valueOf(id));
                intent.putExtra("title", title);
                intent.putExtra("status", "update");
                v.getContext().startActivity(intent);

            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int recordID = diaryList.get(position).getDiaryID();
                Intent intent = new Intent(v.getContext(), RemoveDiary.class);
                intent.putExtra("row_id", String.valueOf(recordID));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }
}
