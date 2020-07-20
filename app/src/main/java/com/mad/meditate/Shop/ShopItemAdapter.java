package com.mad.meditate.Shop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.meditate.R;

import java.util.ArrayList;
import java.util.List;

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemHolder> {

    Context c;
    ArrayList<ShopItemModel> shopItemModelList;

    List<Integer> packagePriceList;
    List<String> cardViewColors;

    public ShopItemAdapter(Context c, ArrayList<ShopItemModel> shopItemModelList) {
        this.c = c;
        this.shopItemModelList = shopItemModelList;

        // Prices for different packages
        packagePriceList = new ArrayList<>();
        packagePriceList.add(1000); // 10 min guides
        packagePriceList.add(1000); // White Noise
        packagePriceList.add(1000); // Nature
        packagePriceList.add(1000); // Self Care
        packagePriceList.add(1000); // Rainy Days
        packagePriceList.add(1000); // Piano
        packagePriceList.add(1000); // Slow Down
        packagePriceList.add(1000); // Others (else)

        // Colors for different cardViews
        cardViewColors = new ArrayList<>();
        cardViewColors.add("#C6DEF1"); // 10 min guides
        cardViewColors.add("#F7D9C4"); // White Noise
        cardViewColors.add("#E2CFC4"); // Nature
        cardViewColors.add("#DBCDF0"); // Self Care
        cardViewColors.add("#FAEDCB"); // Rainy Days
        cardViewColors.add("#E2E2DF"); // Piano
        cardViewColors.add("#C9E4DE"); // Slow Down
        cardViewColors.add("#C6DEF1"); // Others (else)
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

        holder.cardView.setCardBackgroundColor(Color.parseColor(cardViewColors.get(position)));

//        if (shopItemModelList.get(position).getTitle().equalsIgnoreCase("10 min guides")){
//            holder.cardView.setCardBackgroundColor(Color.parseColor("#C6DEF1"));
//        }
//        else if (shopItemModelList.get(position).getTitle().equalsIgnoreCase("White Noise")){
//            holder.cardView.setCardBackgroundColor(Color.parseColor("#F7D9C4"));
//        }
//        else if (shopItemModelList.get(position).getTitle().equalsIgnoreCase("Nature")){
//            holder.cardView.setCardBackgroundColor(Color.parseColor("#E2CFC4"));
//        }
//        else if (shopItemModelList.get(position).getTitle().equalsIgnoreCase("Self care")){
//            holder.cardView.setCardBackgroundColor(Color.parseColor("#DBCDF0"));
//        }
//        else if (shopItemModelList.get(position).getTitle().equalsIgnoreCase("Rainy days")){
//            holder.cardView.setCardBackgroundColor(Color.parseColor("#FAEDCB"));
//        }
//        else if (shopItemModelList.get(position).getTitle().equalsIgnoreCase("Piano")){
//            holder.cardView.setCardBackgroundColor(Color.parseColor("#E2E2DF"));
//        }
//        else if (shopItemModelList.get(position).getTitle().equalsIgnoreCase("Slow down")){
//            holder.cardView.setCardBackgroundColor(Color.parseColor("#C9E4DE"));
//        }
//        else {
//            holder.cardView.setCardBackgroundColor(Color.parseColor("#C6DEF1"));
//        }

        setOnClickListener(holder.itemView, shopItemModelList.get(position).getTitle(), shopItemModelList.get(position).getDes(),
                shopItemModelList.get(position).getMeditateImg(), packagePriceList.get(position), cardViewColors.get(position));

    }

    @Override
    public int getItemCount() {
        return shopItemModelList.size();
    }

    private void setOnClickListener(View v, final String packageTitle, final String packageDescription, final int packagePictureId, final int packageCost, final String packageBgColor){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BuyPackageActivity.class);
                intent.putExtra("title", packageTitle);
                intent.putExtra("description", packageDescription);
                intent.putExtra("pictureId", packagePictureId);
                intent.putExtra("cost", packageCost);
                intent.putExtra("backgroundColor", packageBgColor);
                view.getContext().startActivity(intent);
            }
        });

    }
}
