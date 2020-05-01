package com.android.meditate.Home;

import android.content.Context;
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
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
