package com.mad.meditate.Meditation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.meditate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MeditationActivity extends AppCompatActivity {
    private static final String TAG = "Meditate Activity";

    TextView meditateTitle, artistTxt, durationTxt, genreTxt, timerTxt;
    ImageButton exit;
    MediaPlayer mediaPlayer;
    ImageButton playBtn;
    FirebaseFirestore db;
    ArrayList<MeditationGuide> meditationGuideArrayList;
    String guideTitle, guideArtist;
    int guideDuration, cMinutes;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        meditateTitle = findViewById(R.id.meditateTitleTxt); // meditation title
        artistTxt = findViewById(R.id.artistTxt); // meditation artist
        durationTxt = findViewById(R.id.durationTxt); // meditation duration
        genreTxt = findViewById(R.id.genreTxt); // meditation genre
        exit = findViewById(R.id.mBackImg);
        timerTxt = findViewById(R.id.timerTxt);
        playBtn = findViewById(R.id.playImageBtn);

        //get data from intent
        Intent intent = getIntent();
        final String mTitle = intent.getStringExtra("iTitle");
        String mDes = intent.getStringExtra("iDes");

        //display
        meditateTitle.setText(mTitle);
        artistTxt.setText(mDes);
        durationTxt.setText("");
        genreTxt.setText(mTitle);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // instantiating media player
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                System.out.println("Completed");
                playBtn.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                assignCoins();
            }
        });

        // Access a Cloud Firestore instance from Activity
        db = FirebaseFirestore.getInstance();

        db.collection(mTitle.toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        try{
                            if (task.isSuccessful()) {
                                meditationGuideArrayList =  new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.i(TAG, document.getId() + " => " + document.getData());
                                    MeditationGuide meditationGuide = new MeditationGuide();
                                    meditationGuide.setArtist(document.getString("Artist"));
                                    meditationGuide.setUrl(document.getString("URL"));
                                    meditationGuide.setName(document.getString("Name"));
                                    meditationGuide.setDuration(document.getLong("Duration").intValue());
                                    meditationGuideArrayList.add(meditationGuide);
                                }

                                //choose random index
                                int index = randomIndex(meditationGuideArrayList.size());
                                System.out.println(index);

                                //populate text fields with selected guide
                                guideTitle = meditationGuideArrayList.get(index).getName();
                                guideArtist = meditationGuideArrayList.get(index).getArtist();
                                guideDuration = meditationGuideArrayList.get(index).getDuration();

                                //set up music player
                                try{
                                    mediaPlayer.setDataSource(meditationGuideArrayList.get(index).getUrl());
                                    mediaPlayer.prepare();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }

                                //Play and Pause Btn
                                playBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(!mediaPlayer.isPlaying()){
                                            mediaPlayer.start(); //start audio
                                            //set text fields based on audio
                                            meditateTitle.setText(guideTitle);
                                            artistTxt.setText(guideArtist);
                                            durationTxt.setText(guideDuration + " min");
                                            genreTxt.setText(mTitle);
                                            updateTimer();
                                            //change card text and img
                                            playBtn.setImageResource(R.drawable.ic_pause_white_24dp);
                                        }
                                        else{
                                            mediaPlayer.pause(); //stop audio
                                            //change card text and img
                                            playBtn.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                                            handler.removeCallbacks(updater);

                                        }
                                    }
                                });
                            } else {
                                Log.i(TAG, "Error getting documents.", task.getException());
                            }
                        }catch (Exception e){
                            Log.i(TAG, "ERROR!");

                        }

                    }
                });

    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateTimer();
            long currentDuration = mediaPlayer.getCurrentPosition();
            timerTxt.setText(milliSecondsToTimer(currentDuration));
        }
    };

    private void updateTimer(){
        handler.postDelayed(updater ,1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updater);
        clearMediaPlayer();
    }

    private void clearMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        Log.i("Meditation Activity", "Media Player released");
    }

    public static int randomIndex(int size){
        Random rand = new Random();
        // Obtain a number between list size.
        int n = rand.nextInt(size);

        return n;
    }

    private String milliSecondsToTimer(Long milliseconds){
        String timerString = "";
        String secondsString;

        int hour = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);


        if (hour > 0){
            timerString = hour + ":";
        }

        if (seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;
        }

        timerString = timerString + minutes + ":" + secondsString;
        return timerString;
    }

    private void assignCoins(){
        // update firebase
        final SharedPreferences userPref = this.getSharedPreferences("com.android.meditate.User", Context.MODE_PRIVATE);

        //get userID
        String retrieveUID = userPref.getString("UID", "");
        final int retrieveCoin = userPref.getInt("coins", 0);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference userRef = db.collection("users").document(retrieveUID);

        // Set the "isCapital" field of the city 'DC'
        userRef
                .update("coins", retrieveCoin + 25)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        userPref.edit().putInt("coins", retrieveCoin + 25).apply();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });

        Toast.makeText(this, "25 coins earned", Toast.LENGTH_LONG).show();
    }
}
