package com.android.meditate.Home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.meditate.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

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

        listGuides = new ArrayList<>();
        listGuides.add(new MeditationModel("Sleep", "Desss", R.drawable.sleep));
        listGuides.add(new MeditationModel("Stress & Anxiety", "Desss", R.drawable.sleep));
        listGuides.add(new MeditationModel("Breathe", "Desss", R.drawable.sleep));
        listGuides.add(new MeditationModel("Midnight Thoughts", "Desss", R.drawable.sleep));
        listGuides.add(new MeditationModel("Work Out", "Desss", R.drawable.sleep));
        listGuides.add(new MeditationModel("Concentration", "Desss", R.drawable.sleep));

    }

}
