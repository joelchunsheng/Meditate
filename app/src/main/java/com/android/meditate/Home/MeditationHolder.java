package com.android.meditate.Home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.meditate.Meditation.MeditationClickListener;
import com.android.meditate.R;

public class MeditationHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView mImageView;
    TextView mTitle, mDes;
    CardView cardView;
    MeditationClickListener meditationClickListener;

    MeditationHolder(@NonNull View itemView) {
        super(itemView);
        this.mImageView = itemView.findViewById(R.id.cardImageView);
        this.mTitle = itemView.findViewById(R.id.cardTitleTxt);
        this.mDes = itemView.findViewById(R.id.cardDesTxt);
        this.cardView = itemView.findViewById(R.id.cardview);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        this.meditationClickListener.onItemClickListener(v, getLayoutPosition());
    }

    public void setItemClickListener(MeditationClickListener ic){
        this.meditationClickListener = ic;
    }
}
