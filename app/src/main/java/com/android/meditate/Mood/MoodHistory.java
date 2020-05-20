package com.android.meditate.Mood;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.meditate.R;

import java.util.ArrayList;

public class MoodHistory extends AppCompatActivity {

    RecyclerView moodRecyclerView;
    MoodAdapter moodAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Mood History");

        moodRecyclerView = findViewById(R.id.moodHistoryRecycler);
        moodRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        moodAdapter = new MoodAdapter(this, getList());
        moodRecyclerView.setAdapter(moodAdapter);

    }

    private ArrayList<MoodModel> getList(){
        ArrayList<MoodModel> moodModels = new ArrayList<>();

        MoodModel moodModel = new MoodModel();
        moodModel.setDate("1");
        moodModel.setMonth("June");
        moodModel.setMood("Happy");
        moodModel.setSummary("Good things happened today!");
        moodModel.setImage(R.drawable.happy_emoji);
        moodModels.add(moodModel);

        return moodModels;
    }

}
