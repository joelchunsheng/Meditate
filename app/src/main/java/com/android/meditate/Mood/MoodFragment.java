package com.android.meditate.Mood;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.meditate.R;

import java.text.DateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoodFragment extends Fragment {
    private static final String TAG = "Mood";
    View v;
    TextView date;
    CardView happy, sad, stressed, angry;
    ImageView selectedMoodImg;
    SharedPreferences moodPreferences;
    String retrievedMood;
    String retrievedDate;

    public MoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_mood, container, false);

        // display current date
        date = (TextView) v.findViewById(R.id.datetxt);
        date.setText(getDateTime());

        happy = (CardView) v.findViewById(R.id.happyCardView);
        sad = (CardView) v.findViewById(R.id.sadCardView);
        stressed = (CardView) v.findViewById(R.id.stressedCardView);
        angry = (CardView) v.findViewById(R.id.angryCardView);
        selectedMoodImg = (ImageView) v.findViewById(R.id.currentMoodImage);

        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Happy Clicked");
                selectedMoodImg.setImageResource(R.drawable.happy_emoji);
                moodPreferences.edit().putString("Mood", "Happy").apply();
                moodPreferences.edit().putString("Date", getDateTime()).apply();
            }
        });

        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Sad Clicked");
                selectedMoodImg.setImageResource(R.drawable.sad_emoji);
                moodPreferences.edit().putString("Mood", "Sad").apply();
                moodPreferences.edit().putString("Date", getDateTime()).apply();
            }
        });

        stressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Stressed Clicked");
                selectedMoodImg.setImageResource(R.drawable.stress_emoji);
                moodPreferences.edit().putString("Mood", "Stressed").apply();
                moodPreferences.edit().putString("Date", getDateTime()).apply();
            }
        });

        angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Angry Clicked");
                selectedMoodImg.setImageResource(R.drawable.angry_emoji);
                moodPreferences.edit().putString("Mood", "Angry").apply();
                moodPreferences.edit().putString("Date", getDateTime()).apply();
            }
        });

        if (retrievedDate.equalsIgnoreCase(getDateTime())){
            // if same date
            if (retrievedMood.equalsIgnoreCase("Happy")){
                selectedMoodImg.setImageResource(R.drawable.happy_emoji);
            }
            else if (retrievedMood.equalsIgnoreCase("Sad")){
                selectedMoodImg.setImageResource(R.drawable.sad_emoji);
            }
            else if (retrievedMood.equalsIgnoreCase("Stressed")){
                selectedMoodImg.setImageResource(R.drawable.stress_emoji);
            }
            else if (retrievedMood.equalsIgnoreCase("Angry")){
                selectedMoodImg.setImageResource(R.drawable.angry_emoji);
            }
            else{
                selectedMoodImg.setImageResource(R.drawable.empty_mood);
            }
        }
        else{
            //if different date
            selectedMoodImg.setImageResource(R.drawable.empty_mood);
        }

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get date time

        moodPreferences = this.getActivity().getSharedPreferences("com.android.meditate.Mood", Context.MODE_PRIVATE);

        // retrieve mood preference
        retrievedMood = moodPreferences.getString("Mood", "");
        retrievedDate = moodPreferences.getString("Date", "");
        Log.i(TAG, retrievedMood);
    }

    private static final int[] BUTTON_IDS = {
            R.id.happyCardView,
            R.id.sadCardView,
            R.id.stressedCardView,
            R.id.angryCardView,
    };



    public String getDateTime(){
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        return currentDate;
    }

}
