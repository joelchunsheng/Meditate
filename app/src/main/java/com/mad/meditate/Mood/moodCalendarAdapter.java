package com.mad.meditate.Mood;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mad.meditate.R;

import java.util.ArrayList;

public class moodCalendarAdapter extends RecyclerView.Adapter<moodCalendarHolder> {

    Context c;
    ArrayList<moodCalendarModel> moodCalendarModelArrayList;

    public moodCalendarAdapter(Context c, ArrayList<moodCalendarModel> moodCalendarModelArrayList) {
        this.c = c;
        this.moodCalendarModelArrayList = moodCalendarModelArrayList;
    }

    @NonNull
    @Override
    public moodCalendarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.calendar_item,parent,false);
        moodCalendarHolder moodCalendarHolder = new moodCalendarHolder(view);
        return moodCalendarHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull moodCalendarHolder holder, int position) {
        holder.date.setText(moodCalendarModelArrayList.get(position).getDate());

        if (moodCalendarModelArrayList.get(position).getMood().equalsIgnoreCase("Happy")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#C9E4DE"));
        }
        else if(moodCalendarModelArrayList.get(position).getMood().equalsIgnoreCase("Sad")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#C6DEF1"));
        }
        else if(moodCalendarModelArrayList.get(position).getMood().equalsIgnoreCase("Stressed")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FAEDCB"));
        }
        else if(moodCalendarModelArrayList.get(position).getMood().equalsIgnoreCase("Angry")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#F7D9C4"));
        }
        else{
            holder.cardView.setCardBackgroundColor(Color.parseColor("#E2E2DF"));
        }
    }

    @Override
    public int getItemCount() {
        return moodCalendarModelArrayList.size();
    }
}
