package com.android.meditate.Shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.meditate.R;

import java.util.ArrayList;
import java.util.List;

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemHolder> {

    Context c;
    ArrayList<ShopItemModel> shopItemModelList;

    public ShopItemAdapter(Context c, ArrayList<ShopItemModel> shopItemModelList) {
        this.c = c;
        this.shopItemModelList = shopItemModelList;
    }


    @NonNull
    @Override
    public ShopItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.shopitem,parent,false);
        ShopItemHolder shopItemHolder = new ShopItemHolder(view);

        return shopItemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemHolder holder, int position) {
        holder.title.setText(shopItemModelList.get(position).getTitle());
        holder.des.setText(shopItemModelList.get(position).getDes());
        holder.cardImg.setImageResource(shopItemModelList.get(position).getMeditateImg());

    }

    @Override
    public int getItemCount() {
        return shopItemModelList.size();
    }
}
