package com.android.meditate.Home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.meditate.R;

import java.util.ArrayList;
import java.util.List;

public class MeditationAdapter extends RecyclerView.Adapter<MeditationHolder> {

    Context c;
    List<MeditationModel> models; //creates a list of array of models

    public MeditationAdapter(Context c, ArrayList<MeditationModel> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public MeditationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row, null);
        View view = LayoutInflater.from(c).inflate(R.layout.home_row,parent,false);
        MeditationHolder vHolder = new MeditationHolder(view);
        return vHolder; //return view to holder class
    }

    @Override
    public void onBindViewHolder(@NonNull MeditationHolder holder, int position) {
        holder.mTitle.setText(models.get(position).getTitle());
        holder.mDes.setText(models.get(position).getDescription());
        holder.mImageView.setImageResource(models.get(position).getImg());

        if(position==0)
            holder.cardView.setCardBackgroundColor(Color.parseColor("#C6DEF1"));
        else if (position==1)
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFCDB2"));
        else if (position==2)
            holder.cardView.setCardBackgroundColor(Color.parseColor("#DBCDF0"));
        else if (position==3)
            holder.cardView.setCardBackgroundColor(Color.parseColor("#E2CFC4"));
        else if (position==4)
            holder.cardView.setCardBackgroundColor(Color.parseColor("#C9E4DE"));
        else if (position==5)
            holder.cardView.setCardBackgroundColor(Color.parseColor("#D2D2CF"));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
