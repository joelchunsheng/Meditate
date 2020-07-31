package com.mad.meditate.AvatarSelect;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.meditate.R;

public class AvatarSelectViewHolder extends RecyclerView.ViewHolder {

    public CardView avatarSelectItemView;
    public ImageView avatarSelectItemImageView;

    public AvatarSelectViewHolder(@NonNull View itemView) {
        super(itemView);

        avatarSelectItemView = (CardView) itemView.findViewById(R.id.avatarSelectItemView);
        avatarSelectItemImageView = (ImageView) itemView.findViewById(R.id.avatarSelectItemImageView);
    }
}
