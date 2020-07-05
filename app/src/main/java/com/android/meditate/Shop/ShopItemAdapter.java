package com.android.meditate.Shop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.meditate.R;

import java.util.ArrayList;
import java.util.List;

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemHolder> {

    Context c;
    ArrayList<ShopItemModel> shopItemModelList;

    List<Integer> packagePriceList;

    public ShopItemAdapter(Context c, ArrayList<ShopItemModel> shopItemModelList, List<Integer> packagePriceList) {
        this.c = c;
        this.shopItemModelList = shopItemModelList;

        this.packagePriceList = packagePriceList;
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

        setOnClickListener(holder.cardView, holder.title.getText().toString(), "Author Name", holder.des.getText().toString(), holder.cardImg.getDrawable().toString(), packagePriceList.get(position));

    }

    @Override
    public int getItemCount() {
        return shopItemModelList.size();
    }

    private void setOnClickListener(View v, final String packageTitle, final String packageAuthor, final String packageDescription, final String packagePicture, final int packageCost){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BuyGuideActivity.class);
                intent.putExtra("title", packageTitle);
                intent.putExtra("author", packageAuthor);
                intent.putExtra("description", packageDescription);
                intent.putExtra("picture", packagePicture);
                intent.putExtra("cost", packageCost);
                view.getContext().startActivity(intent);
            }
        });

    }
}
