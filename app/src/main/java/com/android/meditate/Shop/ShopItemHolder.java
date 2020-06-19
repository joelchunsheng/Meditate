package com.android.meditate.Shop;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.meditate.R;

public class ShopItemHolder extends RecyclerView.ViewHolder {

    ImageView cardImg, lockImg;
    TextView title, des;
    CardView cardView;
    LinearLayout item;

     ShopItemHolder(@NonNull View itemView) {
        super(itemView);

         this.cardImg = itemView.findViewById(R.id.shopItemImg);
         this.lockImg = itemView.findViewById(R.id.lockImg);
         this.title = itemView.findViewById(R.id.guideTitle);
         this.des = itemView.findViewById(R.id.guideDes);
         this.item = itemView.findViewById(R.id.shopItem);

     }
}
