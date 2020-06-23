package com.android.meditate.Mood;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoodFragment extends Fragment {
    private static final String TAG = "Mood";
    View v;
    TextView date;
    CardView happy, sad, stressed, angry, history, save;
    SharedPreferences moodPreferences;
    String retrievedMood, retrievedDate, retrieveSummary, selectedMood, retrieveDocId;
    TextInputLayout dialogTxt;
    EditText dialogEditTxt;
    @ServerTimestamp Date time;

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

        // card view
        happy = (CardView) v.findViewById(R.id.happyCardView);
        sad = (CardView) v.findViewById(R.id.sadCardView);
        stressed = (CardView) v.findViewById(R.id.stressedCardView);
        angry = (CardView) v.findViewById(R.id.angryCardView);
//        history = (CardView) v.findViewById(R.id.historyCardView);
        save = (CardView) v.findViewById(R.id.saveCard);

        // edit text
        dialogTxt = (TextInputLayout) v.findViewById(R.id.dialogTxt);
        dialogEditTxt = (EditText) v.findViewById(R.id.dialogEditTxt);


        //mood card onclick
        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Happy Clicked");
                happy.setCardBackgroundColor(Color.parseColor("#C9E4DE"));
                sad.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                stressed.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                angry.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                selectedMood = "Happy";

            }
        });

        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Sad Clicked");
                happy.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                sad.setCardBackgroundColor(Color.parseColor("#C6DEF1"));
                stressed.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                angry.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                selectedMood = "Sad";

            }
        });

        stressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Stressed Clicked");
                happy.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                sad.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                stressed.setCardBackgroundColor(Color.parseColor("#E2CFC4"));
                angry.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                selectedMood = "Stressed";

            }
        });

        angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Angry Clicked");
                happy.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                sad.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                stressed.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                angry.setCardBackgroundColor(Color.parseColor("#F7D9C4"));
                selectedMood = "Angry";

            }
        });

        // if same day -> display previous mood and summary
        if (retrievedMood != null){

            dialogEditTxt.setText(retrieveSummary);

            if (retrievedMood.equalsIgnoreCase("Happy")){
                happy.setCardBackgroundColor(Color.parseColor("#C9E4DE"));
            }
            else if (retrievedMood.equalsIgnoreCase("Sad")){
                sad.setCardBackgroundColor(Color.parseColor("#C6DEF1"));
            }
            else if (retrievedMood.equalsIgnoreCase("Stressed")){
                stressed.setCardBackgroundColor(Color.parseColor("#E2CFC4"));
            }
            else if (retrievedMood.equalsIgnoreCase("Angry")){
                angry.setCardBackgroundColor(Color.parseColor("#F7D9C4"));
            }
        }


        // save card onclick
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String reflection = dialogTxt.getEditText().getText().toString().trim();

                if (selectedMood != null && !reflection.isEmpty()){

                    //save to shared pref
                    moodPreferences.edit().putString("Mood", selectedMood).apply();
                    moodPreferences.edit().putString("Date", getDateTime()).apply();
                    moodPreferences.edit().putString("Summary", reflection).apply();

                // save to firebase
                    if (retrieveDocId == null){
                        writeMood(db, getDateTime(), selectedMood, reflection, moodPreferences);
                    }
                    else{
                        replaceMood(db, getDateTime(), selectedMood, reflection, moodPreferences);
                    }
                    retrieveDocId = moodPreferences.getString("DocID", ""); // retrieve Doc Id
                    Toast.makeText(getContext(), "Mood saved", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //history card
//        history.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // navigate to mood history activity
//                Intent moodHistoryActivity = new Intent(getActivity(), MoodHistory.class);
//                startActivity(moodHistoryActivity);
//            }
//        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        // access mood preference
        moodPreferences = this.getActivity().getSharedPreferences("com.android.meditate.Mood", Context.MODE_PRIVATE);
        // retrieve mood preference

        retrievedDate = moodPreferences.getString("Date", ""); // retrieve date

        //same day
        if (retrievedDate.equalsIgnoreCase(getDateTime())){
            retrievedMood = moodPreferences.getString("Mood", ""); // retrieve mood
            retrieveSummary = moodPreferences.getString("Summary", ""); // retrieve summary
            retrieveDocId = moodPreferences.getString("DocID", ""); // retrieve Doc Id
            selectedMood = retrievedMood;
        }


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

    //creates a need mood document
    private static void writeMood(FirebaseFirestore db, String date, String mood, String summary, final SharedPreferences moodPreferences){
        Map<String, Object> moodObj = new HashMap<>();
        moodObj.put("date", date);
        moodObj.put("mood", mood);
        moodObj.put("summary", summary);
        moodObj.put("timestamp", FieldValue.serverTimestamp());

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
        moodObj.put("date", date);
        moodObj.put("mood", mood);
        moodObj.put("summary", summary);
        moodObj.put("timestamp", FieldValue.serverTimestamp());

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


}
