package com.android.meditate.Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.meditate.R;

import java.util.ArrayList;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    View v;
    private RecyclerView mRecyclerView;
    private MeditationAdapter myAdapter;
    private ArrayList<MeditationModel> listGuides;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        MeditationAdapter recycleradpter = new MeditationAdapter(getContext(),listGuides);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity())) ;  //create recycler view in linearlayout
        mRecyclerView.setAdapter(recycleradpter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // create list of meditation guides
        listGuides = new ArrayList<>();
        listGuides.add(new MeditationModel("Sleep", "Feel the night. Watch its beauty.", R.drawable.sleep));
        listGuides.add(new MeditationModel("Stress & Anxiety", "Soothes your soul.", R.drawable.calm));
        listGuides.add(new MeditationModel("Breathe", "Stop, relax and breathe.", R.drawable.nature));
        listGuides.add(new MeditationModel("Midnight Thoughts", "Calm your heart.", R.drawable.moon));
        listGuides.add(new MeditationModel("Work Out", "Get your heart pumping.", R.drawable.man));
        listGuides.add(new MeditationModel("Concentration", "Focus and relax.", R.drawable.mind));

        try{
            //retrieve purchased guides from shared pref
            SharedPreferences userPref = this.getActivity().getSharedPreferences("com.android.meditate.User", Context.MODE_PRIVATE);
            Set<String> fetch = userPref.getStringSet("purchased", null);

            // add purchase guides to list
            for (String guide: fetch){
                if (guide.equalsIgnoreCase("10 min guides")){
                    listGuides.add(new MeditationModel("10 min guides", "Quick guides anytime anywhere.", R.drawable.clock));
                }
                else if (guide.equalsIgnoreCase("White Noise")){
                    listGuides.add(new MeditationModel("White Noise", "Better sleep. Ease anxiety.", R.drawable.wave));
                }
                else if (guide.equalsIgnoreCase("Nature")){
                    listGuides.add(new MeditationModel("Nature", "Love the world as your own self.", R.drawable.tree));
                }
                else if (guide.equalsIgnoreCase("Self care")){
                    listGuides.add(new MeditationModel("Self care", "Accept yourself, love yourself.", R.drawable.self));
                }
                else if (guide.equalsIgnoreCase("Rainy days")){
                    listGuides.add(new MeditationModel("Rainy days", "Get warm and comfortable.", R.drawable.water));
                }
                else if (guide.equalsIgnoreCase("Piano")){
                    listGuides.add(new MeditationModel("Piano", "Soothing piano music for you.", R.drawable.piano));
                }
                else if (guide.equalsIgnoreCase("Slow down")){
                    listGuides.add(new MeditationModel("Slow down", "A huge part of recovery and life is slowing down.", R.drawable.slow));
                }
                else{
                    Log.i(TAG, "Error retrieving purchased guides");
                }
            }
        }catch (Exception e){
            Log.i(TAG, "ERROR showing purchased guides");
        }


    }

}
