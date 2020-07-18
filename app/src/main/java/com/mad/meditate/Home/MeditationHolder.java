package com.mad.meditate.Home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.meditate.R;

public class MeditationHolder extends RecyclerView.ViewHolder{

    ImageView mImageView;
    TextView mTitle, mDes;
    CardView cardView;

    MeditationHolder(@NonNull View itemView) {
        super(itemView);
        this.mImageView = itemView.findViewById(R.id.cardImageView);
        this.mTitle = itemView.findViewById(R.id.cardTitleTxt);
        this.mDes = itemView.findViewById(R.id.cardDesTxt);
        this.cardView = itemView.findViewById(R.id.cardview);
    }

}
