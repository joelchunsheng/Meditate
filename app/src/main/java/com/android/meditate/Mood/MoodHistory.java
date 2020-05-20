package com.android.meditate.Mood;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.meditate.R;

public class MoodHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Mood History");


    }
}
