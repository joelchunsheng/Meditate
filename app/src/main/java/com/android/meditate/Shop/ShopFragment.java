package com.android.meditate.Shop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.meditate.Home.MeditationModel;
import com.android.meditate.R;

import java.util.ArrayList;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {
    View v;
    private RecyclerView mRecyclerView;
    private TextView coinTxt;
    private ArrayList<ShopItemModel> shopList;
    private static final String TAG = "ShopActivity";
    private int coins;



    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_shop, container, false);

        coinTxt = (TextView) v.findViewById(R.id.coinText);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.shopRecyclerView);

        coinTxt.setText(coins + " coins");

        ShopItemAdapter recycleradpter = new ShopItemAdapter(getContext(),shopList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(recycleradpter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        SharedPreferences userPref = this.getActivity().getSharedPreferences("com.android.meditate.User", Context.MODE_PRIVATE);
        Set<String> fetch = userPref.getStringSet("purchased", null);
        coins = userPref.getInt("coins", 0);

        shopList = new ArrayList<>();
        shopList.add(new ShopItemModel("10 min guides", "Quick guides anytime anywhere.", R.drawable.clock, R.drawable.baseline_lock_black_24dp));
        shopList.add(new ShopItemModel("White Noise", "Better sleep. Ease anxiety.", R.drawable.wave, R.drawable.baseline_lock_black_24dp));
        shopList.add(new ShopItemModel("Nature", "Love the world as your own self.", R.drawable.tree, R.drawable.baseline_lock_black_24dp));
        shopList.add(new ShopItemModel("Self care", "Accept yourself, love yourself.", R.drawable.self, R.drawable.baseline_lock_black_24dp));
        shopList.add(new ShopItemModel("Rainy days", "Get warm and comfortable.", R.drawable.water, R.drawable.baseline_lock_black_24dp));
        shopList.add(new ShopItemModel("Piano", "Soothing piano music for you.", R.drawable.piano, R.drawable.baseline_lock_black_24dp));
        shopList.add(new ShopItemModel("Slow down", "A huge part of recovery and life is slowing down.", R.drawable.slow, R.drawable.baseline_lock_black_24dp));

        try{
            for (String name : fetch){
                Log.i("Set", name);
                if (name.equalsIgnoreCase("10 min guides")){
                    shopList.set(0, new ShopItemModel("10 min guides", "Quick guides anytime anywhere.", R.drawable.clock, R.drawable.baseline_check_circle_black_24dp));
                }
                else if (name.equalsIgnoreCase("White Noise")){
                    shopList.set(1, new ShopItemModel("White Noise", "Better sleep. Ease anxiety.", R.drawable.wave, R.drawable.baseline_check_circle_black_24dp));
                }
                else if (name.equalsIgnoreCase("Nature")){
                    shopList.set(2, new ShopItemModel("Nature", "Love the world as your own self.", R.drawable.tree, R.drawable.baseline_check_circle_black_24dp));
                }
                else if (name.equalsIgnoreCase("Self care")){
                    shopList.set(3, new ShopItemModel("Self care", "Accept yourself, love yourself.", R.drawable.self, R.drawable.baseline_check_circle_black_24dp));
                }
                else if (name.equalsIgnoreCase("Rainy days")){
                    shopList.set(4, new ShopItemModel("Rainy days", "Get warm and comfortable.", R.drawable.water, R.drawable.baseline_check_circle_black_24dp));
                }
                else if (name.equalsIgnoreCase("Piano")){
                    shopList.set(5, new ShopItemModel("Piano", "Soothing piano music for you.", R.drawable.piano, R.drawable.baseline_check_circle_black_24dp));
                }
                else if (name.equalsIgnoreCase("Slow down")){
                    shopList.set(6, new ShopItemModel("Slow down", "A huge part of recovery and life is slowing down.", R.drawable.slow, R.drawable.baseline_check_circle_black_24dp));
                }
                else{
                    Log.i(TAG, "Error updating list with purchased guides");
                }
            }

        }catch (Exception e){
            Log.i(TAG, "No purchased found");

        }

    }
}
