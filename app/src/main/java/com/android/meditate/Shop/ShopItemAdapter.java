package com.android.meditate.Shop;

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
        holder.lockImg.setImageResource(shopItemModelList.get(position).getLockImg());

        if (shopItemModelList.get(position).getLockImg() == R.drawable.baseline_check_circle_black_24dp){
            holder.lockImg.setColorFilter(Color.parseColor("#2A9D8F"));
        }

        if (shopItemModelList.get(position).getTitle().equalsIgnoreCase("10 min guides")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#C6DEF1"));
        }
        else if (shopItemModelList.get(position).getTitle().equalsIgnoreCase("White Noise")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#F7D9C4"));
        }
        else if (shopItemModelList.get(position).getTitle().equalsIgnoreCase("Nature")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#E2CFC4"));
        }
        else if (shopItemModelList.get(position).getTitle().equalsIgnoreCase("Self care")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#DBCDF0"));
        }
        else if (shopItemModelList.get(position).getTitle().equalsIgnoreCase("Rainy days")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FAEDCB"));
        }
        else if (shopItemModelList.get(position).getTitle().equalsIgnoreCase("Piano")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#E2E2DF"));
        }
        else if (shopItemModelList.get(position).getTitle().equalsIgnoreCase("Slow down")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#C9E4DE"));
        }
        else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#C6DEF1"));
        }

    }

    @Override
    public int getItemCount() {
        return shopItemModelList.size();
    }
}
