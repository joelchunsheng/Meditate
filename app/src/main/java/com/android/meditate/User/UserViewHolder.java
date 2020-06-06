package com.android.meditate.User;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.meditate.R;

public class UserViewHolder extends RecyclerView.ViewHolder {

    public CardView settingCard;
    public TextView settingText;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        settingCard = itemView.findViewById(R.id.userSettingsCardView);
        settingText = itemView.findViewById(R.id.userSettingsTitle);
    }
}
