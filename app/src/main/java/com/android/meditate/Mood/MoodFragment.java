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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.meditate.Meditation.MeditationActivity;
import com.android.meditate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    TextView date, mtMonth;
    CardView happy, sad, stressed, angry, recommededCard;
    SharedPreferences moodPreferences, userPref;
    String retrievedMood, retrievedDate, selectedMood, retrieveDocId, retrieveUID;
    int extraDays;
    RecyclerView recyclerView;
    ArrayList<moodCalendarModel> moodCalendarModelArrayList;
    Calendar c = Calendar.getInstance();
    moodCalendarAdapter adapter;

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

        //calendar
        mtMonth = (TextView) v.findViewById(R.id.mtCurrentMonth);
        mtMonth.setText(new SimpleDateFormat("MMMM YYYY").format(c.getTime()));

        // recommended card
        recommededCard = (CardView) v.findViewById(R.id.recommededCard);

        recommededCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent meditationActivity = new Intent(getActivity(), MeditationActivity.class);
                meditationActivity.putExtra("iTitle", "Stress & Anxiety");
                meditationActivity.putExtra("iDes", "Soothes your soul.");
                startActivity(meditationActivity);
            }
        });

        recyclerView = (RecyclerView) v.findViewById(R.id.moodCalendarRecycler);

        adapter = new moodCalendarAdapter(getContext(), moodCalendarModelArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        recyclerView.setAdapter(adapter);

        getMoodMonth(db, c, retrieveUID,moodCalendarModelArrayList, adapter, extraDays);

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
                // save mood to shared pref
                moodPreferences.edit().putString("Mood", selectedMood).apply();
                moodPreferences.edit().putString("Date", getDateTime()).apply();
                if (retrieveDocId == null){
                    writeMood(db, selectedMood, c, retrieveUID,moodPreferences);
                }
                else{
                    replaceMood(db, selectedMood, c, retrieveUID,moodPreferences);
                }
                retrieveDocId = moodPreferences.getString("DocID", ""); // retrieve Doc Id
                updateMoodTable(moodCalendarModelArrayList, "Happy", extraDays);
                adapter.notifyDataSetChanged();

                for(moodCalendarModel x : moodCalendarModelArrayList){
                    System.out.println(x.getMood());
                    System.out.println(x.getDate());
                }

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
                // save mood to shared pref
                moodPreferences.edit().putString("Mood", selectedMood).apply();
                moodPreferences.edit().putString("Date", getDateTime()).apply();
                if (retrieveDocId == null){
                    writeMood(db, selectedMood, c, retrieveUID,moodPreferences);
                }
                else{
                    replaceMood(db, selectedMood, c, retrieveUID,moodPreferences);
                }
                retrieveDocId = moodPreferences.getString("DocID", ""); // retrieve Doc Id
                updateMoodTable(moodCalendarModelArrayList, "Sad", extraDays);
                adapter.notifyDataSetChanged();

            }
        });

        stressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Stressed Clicked");
                happy.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                sad.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                stressed.setCardBackgroundColor(Color.parseColor("#FAEDCB"));
                angry.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                selectedMood = "Stressed";
                // save mood to shared pref
                moodPreferences.edit().putString("Mood", selectedMood).apply();
                moodPreferences.edit().putString("Date", getDateTime()).apply();
                if (retrieveDocId == null){
                    writeMood(db, selectedMood, c,retrieveUID, moodPreferences);
                }
                else{
                    replaceMood(db, selectedMood, c,retrieveUID, moodPreferences);
                }
                retrieveDocId = moodPreferences.getString("DocID", ""); // retrieve Doc Id
                updateMoodTable(moodCalendarModelArrayList, "Stressed", extraDays);
                adapter.notifyDataSetChanged();

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
                // save mood to shared pref
                moodPreferences.edit().putString("Mood", selectedMood).apply();
                moodPreferences.edit().putString("Date", getDateTime()).apply();
                if (retrieveDocId == null){
                    writeMood(db, selectedMood, c, retrieveUID, moodPreferences);
                }
                else{
                    replaceMood(db, selectedMood, c, retrieveUID,moodPreferences);
                }
                retrieveDocId = moodPreferences.getString("DocID", ""); // retrieve Doc Id
                updateMoodTable(moodCalendarModelArrayList, "Angry", extraDays);
                adapter.notifyDataSetChanged();

                for (moodCalendarModel x : moodCalendarModelArrayList){
                    System.out.println(x.getMood());
                    System.out.println(x.getDate());

                }

            }
        });

        // if same day -> display previous mood and summary
        if (retrievedMood != null){

            if (retrievedMood.equalsIgnoreCase("Happy")){
                happy.setCardBackgroundColor(Color.parseColor("#C9E4DE"));
            }
            else if (retrievedMood.equalsIgnoreCase("Sad")){
                sad.setCardBackgroundColor(Color.parseColor("#C6DEF1"));
            }
            else if (retrievedMood.equalsIgnoreCase("Stressed")){
                stressed.setCardBackgroundColor(Color.parseColor("#FAEDCB"));
            }
            else if (retrievedMood.equalsIgnoreCase("Angry")){
                angry.setCardBackgroundColor(Color.parseColor("#F7D9C4"));
            }
        }

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        // access mood preference
        moodPreferences = this.getActivity().getSharedPreferences("com.android.meditate.Mood", Context.MODE_PRIVATE);
        // retrieve user preference
        userPref = this.getActivity().getSharedPreferences("com.android.meditate.User", Context.MODE_PRIVATE);

        //get userID
        retrieveUID = userPref.getString("UID", "");

        retrievedDate = moodPreferences.getString("Date", ""); // retrieve date

        //same day
        if (retrievedDate.equalsIgnoreCase(getDateTime())){
            retrievedMood = moodPreferences.getString("Mood", ""); // retrieve mood
            retrieveDocId = moodPreferences.getString("DocID", ""); // retrieve Doc Id
            selectedMood = retrievedMood;
        }

        moodCalendarModelArrayList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();   // this takes current date
        cal.set(Calendar.DAY_OF_MONTH, 1);

        extraDays = blankDays(new SimpleDateFormat("E").format(cal.getTime()));

        for (int i = 1; i<= extraDays; i++){
            moodCalendarModelArrayList.add(new moodCalendarModel("", "blank"));
        }


        int monthMaxDays = Integer.parseInt(new SimpleDateFormat("d").format(c.getTime()));
        for (int day = 1; day <= monthMaxDays; day++){
            moodCalendarModelArrayList.add(new moodCalendarModel(Integer.toString(day), "blank"));
        }


    }

    public int blankDays(String day){

        if (day.equals("Tue")){
            return 1;
        }
        else if (day.equals("Wed")){
            return 2;
        }
        else if (day.equals("Thu")){
            return 3;
        }
        else if (day.equals("Fri")){
            return 4;
        }
        else if (day.equals("Sat")){
            return 5;
        }
        else if (day.equals("Sun")){
            return 6;
        }
        else
            return 0;
    }

    // Get current date time
    public static String getDateTime(){
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        return currentDate;
    }

    //creates a need mood document
    private static void writeMood(FirebaseFirestore db, String mood, Calendar c, String uid ,final SharedPreferences moodPreferences){
        Map<String, Object> moodObj = new HashMap<>();
        moodObj.put("date", new SimpleDateFormat("d").format(c.getTime()));
        moodObj.put("mood", mood);
        moodObj.put("filter", new SimpleDateFormat("MMMM YYYY").format(c.getTime()));
        moodObj.put("timestamp", FieldValue.serverTimestamp());

        db.collection("users").document(uid).collection("mood")
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
    private static void replaceMood(FirebaseFirestore db, String mood, Calendar c, String uid ,final SharedPreferences moodPreferences){
        Map<String, Object> moodObj = new HashMap<>();
        moodObj.put("date", new SimpleDateFormat("d").format(c.getTime()));
        moodObj.put("mood", mood);
        moodObj.put("filter", new SimpleDateFormat("MMMM YYYY").format(c.getTime()));
        moodObj.put("timestamp", FieldValue.serverTimestamp());

        db.collection("users").document(uid).collection("mood").document(moodPreferences.getString("DocID", ""))
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

    // retrieve mood in current month
    private static void getMoodMonth(FirebaseFirestore db, Calendar c, String uid , final ArrayList moodCalendarModelArrayList, final moodCalendarAdapter adapter, final int extra){
        db.collection("users").document(uid).collection("mood")
                .whereEqualTo("filter", new SimpleDateFormat("MMMM YYYY").format(c.getTime()))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                int index = Integer.parseInt(document.getString("date"));
                                moodCalendarModelArrayList.set(index-1+extra, new moodCalendarModel(Integer.toString(index), document.getString("mood")));
                            }
                            adapter.notifyDataSetChanged();

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public static void updateMoodTable(ArrayList moodCalendarModelArrayList, String mood, int extraDays){
        if (mood.equalsIgnoreCase("Happy")){
            moodCalendarModelArrayList.set(moodCalendarModelArrayList.size()-1, new moodCalendarModel(Integer.toString(moodCalendarModelArrayList.size()-extraDays), "Happy"));
        }
        else if (mood.equalsIgnoreCase("Sad")){
            moodCalendarModelArrayList.set(moodCalendarModelArrayList.size()-1, new moodCalendarModel(Integer.toString(moodCalendarModelArrayList.size()-extraDays), "Sad"));
        }
        else if (mood.equalsIgnoreCase("Stressed")){
            moodCalendarModelArrayList.set(moodCalendarModelArrayList.size()-1, new moodCalendarModel(Integer.toString(moodCalendarModelArrayList.size()-extraDays), "Stressed"));
        }
        else if (mood.equalsIgnoreCase("Angry")){
            moodCalendarModelArrayList.set(moodCalendarModelArrayList.size()-1, new moodCalendarModel(Integer.toString(moodCalendarModelArrayList.size()-extraDays), "Angry"));
        }

    }
}
