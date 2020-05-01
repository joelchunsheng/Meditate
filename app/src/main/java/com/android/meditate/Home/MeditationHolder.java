package com.android.meditate.Home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.meditate.R;

public class MeditationHolder extends RecyclerView.ViewHolder {

    ImageView mImageView;
    TextView mTitle, mDes;

    public MeditationHolder(@NonNull View itemView) {
        super(itemView);
        this.mImageView = itemView.findViewById(R.id.cardImageView);
        this.mTitle = itemView.findViewById(R.id.cardTitleTxt);
        this.mDes = itemView.findViewById(R.id.cardDesTxt);
    }
}
