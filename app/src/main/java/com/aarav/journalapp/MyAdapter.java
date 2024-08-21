package com.aarav.journalapp;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Journal> journalArrayList;
    Context context;

    public MyAdapter(ArrayList<Journal> journalArrayList, Context context) {
        this.journalArrayList = journalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Journal journal = journalArrayList.get(position);
        holder.title.setText(journal.getTitle());
        holder.thoughts.setText(journal.getThoughts());
        holder.name.setText(journal.getUserName());

        String imageUrl = journal.getImageUrl();
        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(journal.getTimestamp().getSeconds() * 1000);
        holder.dateAdded.setText(timeAgo);

        Glide.with(context).load(imageUrl).fitCenter().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return journalArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title, thoughts, dateAdded, name;
        ImageView image, share;
        String userId, username;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.journal_title_list);
            thoughts = itemView.findViewById(R.id.journal_thoughts_list);
            dateAdded = itemView.findViewById(R.id.journal_timestamp_list);
            name = itemView.findViewById(R.id.journal_row_username);
            image = itemView.findViewById(R.id.journal_image_list);

            share = itemView.findViewById(R.id.journal_row_share_btn);

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
