package com.android.meditate.Mood;

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
        getDateTime();

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
            }
        });

        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Sad Clicked");
                selectedMoodImg.setImageResource(R.drawable.sad_emoji);
            }
        });

        stressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Stressed Clicked");
                selectedMoodImg.setImageResource(R.drawable.stress_emoji);
            }
        });

        angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Angry Clicked");
                selectedMoodImg.setImageResource(R.drawable.angry_emoji);
            }
        });

        return v;
    }

    private static final int[] BUTTON_IDS = {
            R.id.happyCardView,
            R.id.sadCardView,
            R.id.stressedCardView,
            R.id.angryCardView,
    };



    public void getDateTime(){
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        date.setText(currentDate);
    }

}
