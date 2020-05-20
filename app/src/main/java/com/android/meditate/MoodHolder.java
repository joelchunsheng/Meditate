package com.android.meditate;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MoodHolder extends RecyclerView.ViewHolder {

    ImageView moodImage;
    TextView date, month, mood, summary;
    public MoodHolder(@NonNull View itemView) {
        super(itemView);

        this.moodImage = itemView.findViewById(R.id.moodImageView);
        this.date = itemView.findViewById(R.id.dateTextView);
        this.month = itemView.findViewById(R.id.monthTextView);
        this.mood = itemView.findViewById(R.id.moodTextView);
        this.summary = itemView.findViewById(R.id.summaryTextView);

    }
}
