package com.android.meditate.Mood;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.meditate.R;
import com.google.android.material.textfield.TextInputLayout;

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
    AlertDialog.Builder dialog;
    TextInputLayout dialogTxt;

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
                dialog("Happy");
            }
        });

        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Sad Clicked");
                selectedMoodImg.setImageResource(R.drawable.sad_emoji);
                dialog("Sad");
            }
        });

        stressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Stressed Clicked");
                selectedMoodImg.setImageResource(R.drawable.stress_emoji);
                dialog("Stressed");
            }
        });

        angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Angry Clicked");
                selectedMoodImg.setImageResource(R.drawable.angry_emoji);
                dialog("Angry");
            }
        });

        // check if it is a new day
        // if same day, show selected mood
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
        //if different day, reset mood
        else{
            selectedMoodImg.setImageResource(R.drawable.empty_mood);
        }

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    public void dialog(final String mood){
        dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Almost done");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.mood_dialog, null);
        dialogTxt = (TextInputLayout) dialogView.findViewById(R.id.dialogTxt);

        dialog.setCancelable(false);
        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                String dialogText = dialogTxt.getEditText().getText().toString();
                moodPreferences.edit().putString("Mood", mood).apply();
                moodPreferences.edit().putString("Date", getDateTime()).apply();
                moodPreferences.edit().putString("Summary", dialogText).apply();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                selectedMoodImg.setImageResource(R.drawable.empty_mood);
            }
        });
        dialog.setView(dialogView);
        dialog.show();
    }

}
