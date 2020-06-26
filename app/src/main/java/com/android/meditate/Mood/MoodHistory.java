package com.android.meditate.Mood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.meditate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MoodHistory extends AppCompatActivity {
    private static final String TAG = "Mood History";

    RecyclerView moodRecyclerView;
    MoodAdapter moodAdapter;
    FirebaseFirestore db;
    ArrayList<MoodModel> moodList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        moodRecyclerView = findViewById(R.id.moodHistoryRecycler);
        moodRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        // retrieve from firestore
        db.collection("users").document("wumxM5qn4tYAyYSzMXdhZawvITW2").collection("mood")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            moodList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                //save document to list
                                MoodModel moodModel = new MoodModel();
                                moodModel.setDate(document.getString("date"));
                                moodModel.setMood(document.getString("mood"));
                                moodModel.setSummary(document.getString("summary"));

                                //set mood image
                                String mood = document.getString("mood");

                                if (mood.equals("Happy")){
                                    moodModel.setImage(R.drawable.happy_emoji);
                                }
                                else if (mood.equals("Sad")){
                                    moodModel.setImage(R.drawable.sad_emoji);
                                }
                                else if (mood.equals("Stressed")){
                                    moodModel.setImage(R.drawable.stress_emoji);
                                }
                                else if (mood.equals("Angry")){
                                    moodModel.setImage(R.drawable.angry_emoji);
                                }
                                else{
                                    //error, display default mood
                                    moodModel.setImage(R.drawable.empty_mood);
                                }
                                moodList.add(moodModel); // add to mood list
                            }

                            moodAdapter = new MoodAdapter(moodList);
                            moodRecyclerView.setAdapter(moodAdapter);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }



//    private ArrayList<MoodModel> getList(){
//        moodModels = new ArrayList<>();
//
//        MoodModel moodModel = new MoodModel();
//        moodModel.setDate("1");
//        moodModel.setMonth("June");
//        moodModel.setMood("Happy");
//        moodModel.setSummary("Good things happened today!");
//        moodModel.setImage(R.drawable.happy_emoji);
//        moodModels.add(moodModel);
//
//        return moodModels;
//    }

}
