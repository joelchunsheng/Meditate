package com.android.meditate.Shop;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.meditate.Home.MeditationModel;
import com.android.meditate.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {
    View v;
    private RecyclerView mRecyclerView;
    private ArrayList<ShopItemModel> shopList;


    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_shop, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.shopRecyclerView);
        ShopItemAdapter recycleradpter = new ShopItemAdapter(getContext(),shopList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity())) ;  //create recycler view in linearlayout
        mRecyclerView.setAdapter(recycleradpter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        shopList = new ArrayList<>();
        shopList.add(new ShopItemModel("Nature", "Love the world as your own self", R.drawable.calm, R.drawable.baseline_lock_black_24dp));
        shopList.add(new ShopItemModel("Sunset", "Love the world as your own self", R.drawable.calm, R.drawable.baseline_lock_black_24dp));
        shopList.add(new ShopItemModel("Rain", "Love the world as your own self", R.drawable.calm, R.drawable.baseline_lock_black_24dp));
        shopList.add(new ShopItemModel("Title1", "Love the world as your own self", R.drawable.calm, R.drawable.baseline_lock_black_24dp));
        shopList.add(new ShopItemModel("Title1", "Love the world as your own self", R.drawable.calm, R.drawable.baseline_lock_black_24dp));
        shopList.add(new ShopItemModel("Title1", "Love the world as your own self", R.drawable.calm, R.drawable.baseline_lock_black_24dp));
        shopList.add(new ShopItemModel("Title1", "Love the world as your own self", R.drawable.calm, R.drawable.baseline_lock_black_24dp));
        shopList.add(new ShopItemModel("Piano", "Love the world as your own self", R.drawable.calm, R.drawable.baseline_lock_black_24dp));

    }
}
