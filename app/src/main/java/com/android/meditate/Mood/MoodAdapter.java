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

    Context c;
    ArrayList<MoodModel> moodModels;

    public MoodAdapter(Context c, ArrayList<MoodModel> moodModels) {
        this.c = c;
        this.moodModels = moodModels;
    }

    @NonNull
    @Override
    public MoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.moodhistoryrow, null);

        return new MoodHolder(view); // return view to holder class
    }

    @Override
    public void onBindViewHolder(@NonNull MoodHolder holder, int position) {
        holder.date.setText(moodModels.get(position).getDate());
        holder.month.setText(moodModels.get(position).getMonth());
        holder.mood.setText(moodModels.get(position).getMood());
        holder.summary.setText(moodModels.get(position).getSummary());
        holder.moodImage.setImageResource(moodModels.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return moodModels.size();
    }
}
