package com.android.meditate.Mood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.meditate.R;

import java.util.ArrayList;

public class MoodAdapter extends RecyclerView.Adapter<MoodHolder> {

//    Context c;
    ArrayList<MoodModel> moodList;

    public MoodAdapter(ArrayList<MoodModel> moodList) {
//        this.c = c;
        this.moodList = moodList;
    }

    @NonNull
    @Override
    public MoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.moodhistoryrow, null);

        return new MoodHolder(view); // return view to holder class
    }

    @Override
    public void onBindViewHolder(@NonNull MoodHolder holder, int position) {
        holder.date.setText(moodList.get(position).getDate());
        holder.month.setText(moodList.get(position).getMonth());
        holder.mood.setText(moodList.get(position).getMood());
        holder.summary.setText(moodList.get(position).getSummary());
        holder.moodImage.setImageResource(moodList.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return moodList.size();
    }
}
