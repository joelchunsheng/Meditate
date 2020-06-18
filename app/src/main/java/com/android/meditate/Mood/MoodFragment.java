package com.android.meditate.Mood;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.android.meditate.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoodFragment extends Fragment {
    private static final String TAG = "Mood";
    View v;
    TextView date;
    CardView happy, sad, stressed, angry, history;
    ImageView selectedMoodImg;
    SharedPreferences moodPreferences;
    String retrievedMood, retrievedDate, retrieveSummary;
    AlertDialog.Builder dialog;
    TextInputLayout dialogTxt;
    EditText dialogEditTxt;

    FirebaseFirestore db;

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
        history = (CardView) v.findViewById(R.id.historyCardView);

        //mood card onclick
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
        //if different day, reset mood and summary
        // clear documentid in shared pref
        else{
            selectedMoodImg.setImageResource(R.drawable.empty_mood);
            moodPreferences.edit().putString("DocID", "").apply();
            moodPreferences.edit().putString("Summary", "").apply();
        }

        //history card
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to mood history activity
                Intent moodHistoryActivity = new Intent(getActivity(), MoodHistory.class);
                startActivity(moodHistoryActivity);
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        moodPreferences = this.getActivity().getSharedPreferences("com.android.meditate.Mood", Context.MODE_PRIVATE);
        // retrieve mood preference
        retrievedMood = moodPreferences.getString("Mood", "");
        retrievedDate = moodPreferences.getString("Date", "");
        Log.i(TAG, retrievedMood);

        //retrieve uid from shared preference

    }

    // list of mood cards
    private static final int[] BUTTON_IDS = {
            R.id.happyCardView,
            R.id.sadCardView,
            R.id.stressedCardView,
            R.id.angryCardView,
    };


    // Get current date time
    public String getDateTime(){
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        return currentDate;
    }

    // create dialog for summary input
    public void dialog(final String mood){
        dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Almost done");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.mood_dialog, null);
        dialogTxt = (TextInputLayout) dialogView.findViewById(R.id.dialogTxt);
        dialogEditTxt = (EditText) dialogView.findViewById(R.id.dialogEditTxt);

        //retrieve summary from shared pref
        retrieveSummary = moodPreferences.getString("Summary", "");

        // if summary is not blank, show saved summary
        if (!retrieveSummary.equalsIgnoreCase("You did not write anything on this day.")){
            dialogEditTxt.setText(retrieveSummary);
        }

        dialog.setCancelable(false);
        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                String dialogText = dialogTxt.getEditText().getText().toString().trim();

                // if summary text field empty
                if (dialogText.isEmpty()){
                    moodPreferences.edit().putString("Mood", mood).apply();
                    moodPreferences.edit().putString("Date", getDateTime()).apply();
                    moodPreferences.edit().putString("Summary", "You did not write anything on this day.").apply();
                }
                else{
                    moodPreferences.edit().putString("Mood", mood).apply();
                    moodPreferences.edit().putString("Date", getDateTime()).apply();
                    moodPreferences.edit().putString("Summary", dialogText).apply();
                }

                // write to firestore
                // if docID is blank -> new day
                // create new doc
                if (moodPreferences.getString("DocID", "").equals("")){
                    // write to firestore
                    writeMood(db, getDateTime(), mood, dialogText, moodPreferences);
                }
                else{
                    // same day
                    // replace old data
                    replaceMood(db, getDateTime(), mood, dialogText, moodPreferences);
                }

                Toast.makeText(getContext(), "Mood saved", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                retrievedMood = moodPreferences.getString("Mood", "");

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
        });
        dialog.setView(dialogView);
        dialog.show();
    }

    //creates a need mood document
    private static void writeMood(FirebaseFirestore db, String date, String mood, String summary, final SharedPreferences moodPreferences){
        Map<String, Object> moodObj = new HashMap<>();
        moodObj.put("date", getDate(date, "D"));
        moodObj.put("month", getDate(date, "M"));
        moodObj.put("mood", mood);
        moodObj.put("summary", summary);

        db.collection("users").document("wumxM5qn4tYAyYSzMXdhZawvITW2").collection("mood")
                .add(moodObj)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        moodPreferences.edit().putString("DocID", documentReference.getId()).apply();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    //replace existing mood document
    private static void replaceMood(FirebaseFirestore db, String date, String mood, String summary, final SharedPreferences moodPreferences){
        Map<String, Object> moodObj = new HashMap<>();
        moodObj.put("date", getDate(date, "D"));
        moodObj.put("month", getDate(date, "M"));
        moodObj.put("mood", mood);
        moodObj.put("summary", summary);

        db.collection("users").document("wumxM5qn4tYAyYSzMXdhZawvITW2").collection("mood").document(moodPreferences.getString("DocID", ""))
                .set(moodObj)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "Error writing document", e);
                    }
                });
    }

    public static String getDate(String date, String part){

        String [] fullDate = date.split(",");
        String [] dateSplit = fullDate[1].split(" ");

        if (part.equalsIgnoreCase("D")){
            return dateSplit[2];
        }
        else{
            return dateSplit[1];
        }

    }

}
