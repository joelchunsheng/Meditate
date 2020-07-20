package com.mad.meditate.Mood;

import android.content.Context;
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
import android.widget.TextView;

import com.mad.meditate.Meditation.MeditationActivity;
import com.mad.meditate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoodFragment extends Fragment {
    private static final String TAG = "Mood";
    View v;

    TextView date, mtMonth;
    static CardView happy, sad, stressed, angry, recommededCard;
    RecyclerView recyclerView;

    SharedPreferences userPref;

    ArrayList<moodCalendarModel> moodCalendarModelArrayList;
    moodCalendarAdapter adapter;

    FirebaseFirestore db;

    static String retrievedMood, selectedMood, retrieveDocId, retrieveUID;
    int extraDays;

    Calendar c = Calendar.getInstance();

    public MoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_mood, container, false);

        date = (TextView) v.findViewById(R.id.datetxt);
        date.setText(getDateTime());
        happy = (CardView) v.findViewById(R.id.happyCardView);
        sad = (CardView) v.findViewById(R.id.sadCardView);
        stressed = (CardView) v.findViewById(R.id.stressedCardView);
        angry = (CardView) v.findViewById(R.id.angryCardView);
        mtMonth = (TextView) v.findViewById(R.id.mtCurrentMonth);
        mtMonth.setText(new SimpleDateFormat("MMMM YYYY").format(c.getTime()));
        recommededCard = (CardView) v.findViewById(R.id.recommededCard);
        recyclerView = (RecyclerView) v.findViewById(R.id.moodCalendarRecycler);

        // Recommended guide
        recommededCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent meditationActivity = new Intent(getActivity(), MeditationActivity.class);
                meditationActivity.putExtra("iTitle", "Stress & Anxiety");
                meditationActivity.putExtra("iDes", "Soothes your soul.");
                startActivity(meditationActivity);
            }
        });

        // set up recycler view for calendar
        adapter = new moodCalendarAdapter(getContext(), moodCalendarModelArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        recyclerView.setAdapter(adapter);

        // retrieve mood from firebase and populate recyclerview
        getMoodMonth(db, c, retrieveUID,moodCalendarModelArrayList, adapter, extraDays);

        //mood card onclick
        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Happy Clicked");
                selectedMood = "Happy"; // set selected mood

                // Set colour of card
                happy.setCardBackgroundColor(Color.parseColor("#C9E4DE"));
                sad.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                stressed.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                angry.setCardBackgroundColor(Color.parseColor("#EDEDEB"));

                // write to firebase
                if (retrieveDocId == null){
                    // if no existing DocID
                    // write new mood
                    writeMood(db, selectedMood, c, retrieveUID);
                }
                else{
                    //else update mood
                    replaceMood(db, selectedMood, c, retrieveUID);
                }


                // Update recyclerview
                updateMoodTable(moodCalendarModelArrayList, "Happy", extraDays);
                adapter.notifyDataSetChanged();

            }
        });

        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Sad Clicked");
                selectedMood = "Sad";

                // Set colour of card
                happy.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                sad.setCardBackgroundColor(Color.parseColor("#C6DEF1"));
                stressed.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                angry.setCardBackgroundColor(Color.parseColor("#EDEDEB"));

                // write to firebase
                if (retrieveDocId == null){
                    // if no existing DocID
                    // write new mood
                    writeMood(db, selectedMood, c, retrieveUID);
                }
                else{
                    //else update mood
                    replaceMood(db, selectedMood, c, retrieveUID);
                }

                // Update recyclerview
                updateMoodTable(moodCalendarModelArrayList, "Sad", extraDays);
                adapter.notifyDataSetChanged();

            }
        });

        stressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Stressed Clicked");
                selectedMood = "Stressed";

                // Set colour of card
                happy.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                sad.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                stressed.setCardBackgroundColor(Color.parseColor("#FAEDCB"));
                angry.setCardBackgroundColor(Color.parseColor("#EDEDEB"));

                // write to firebase
                if (retrieveDocId == null){
                    // if no existing DocID
                    // write new mood
                    writeMood(db, selectedMood, c,retrieveUID);
                }
                else{
                    // else update mood
                    replaceMood(db, selectedMood, c,retrieveUID);
                }

                //Update recyclerview
                updateMoodTable(moodCalendarModelArrayList, "Stressed", extraDays);
                adapter.notifyDataSetChanged();

            }
        });

        angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Angry Clicked");
                selectedMood = "Angry";

                // Set colour of card
                happy.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                sad.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                stressed.setCardBackgroundColor(Color.parseColor("#EDEDEB"));
                angry.setCardBackgroundColor(Color.parseColor("#F7D9C4"));

                // write to firebase
                if (retrieveDocId == null){
                    writeMood(db, selectedMood, c, retrieveUID);
                }
                else{
                    replaceMood(db, selectedMood, c, retrieveUID);
                }

                //update recyclerview
                updateMoodTable(moodCalendarModelArrayList, "Angry", extraDays);
                adapter.notifyDataSetChanged();

            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        // retrieve user preference
        userPref = this.getActivity().getSharedPreferences("com.android.meditate.User", Context.MODE_PRIVATE);

        //get userID
        retrieveUID = userPref.getString("UID", "");

        int todayDate = Integer.parseInt(new SimpleDateFormat("d").format(c.getTime())); // gets current day

        getTodayMood(db, c, retrieveUID, todayDate);


        moodCalendarModelArrayList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();   // this takes current date
        cal.set(Calendar.DAY_OF_MONTH, 1);

        extraDays = blankDays(new SimpleDateFormat("E").format(cal.getTime()));

        // creates the blank spaces for 1st week
        for (int i = 1; i<= extraDays; i++){
            moodCalendarModelArrayList.add(new moodCalendarModel("", "blank"));
        }

        // sets up list
        for (int day = 1; day <= todayDate; day++){
            moodCalendarModelArrayList.add(new moodCalendarModel(Integer.toString(day), "blank"));
        }

    }

    // find out the number of blank cards in 1st week
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

    // check for current day mood
    private static void getTodayMood(FirebaseFirestore db, Calendar c, String uid, int date){
        db.collection("users").document(uid).collection("mood")
                .whereEqualTo("filter", new SimpleDateFormat("MMMM YYYY").format(c.getTime()))
                .whereEqualTo("date" , Integer.toString(date))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "=>" + document.getData());

                                // get doc id
                                retrieveDocId =  document.getId();

                                // get selected mood
                                retrievedMood = document.getString("mood");
                                System.out.println(retrievedMood);

                                selectedMood = retrievedMood;

                                // change bg color for mood card
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
                                else{
                                    // nothing
                                }

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());

                            // doc id
                            retrieveDocId =  null;

                            // mood
                            retrievedMood = null;
                        }
                    }
                });
    }

    //creates a need mood document
    private static void writeMood(FirebaseFirestore db, String mood, Calendar c, String uid ){
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
    private static void replaceMood(FirebaseFirestore db, String mood, Calendar c, String uid){
        Map<String, Object> moodObj = new HashMap<>();
        moodObj.put("date", new SimpleDateFormat("d").format(c.getTime()));
        moodObj.put("mood", mood);
        moodObj.put("filter", new SimpleDateFormat("MMMM YYYY").format(c.getTime()));
        moodObj.put("timestamp", FieldValue.serverTimestamp());

        db.collection("users").document(uid).collection("mood").document(retrieveDocId)
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
