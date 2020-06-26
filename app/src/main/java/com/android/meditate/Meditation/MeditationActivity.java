package com.android.meditate.Meditation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.meditate.MainActivity;
import com.android.meditate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MeditationActivity extends AppCompatActivity {
    private static final String TAG = "Meditate Activity";

    TextView meditateTitle, artistTxt, durationTxt, genreTxt;
    ImageView playPauseImage, exit;
    MediaPlayer mediaPlayer;
    CardView  playCard;
    FirebaseFirestore db;
    ArrayList<MeditationGuide> meditationGuideArrayList;
    String guideTitle, guideArtist, guideDuration;

    Boolean playPause = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        // In Activity's onCreate() for instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        meditateTitle = findViewById(R.id.meditateTitleTxt); // meditation title
        artistTxt = findViewById(R.id.artistTxt); // meditation artist
        durationTxt = findViewById(R.id.durationTxt); // meditation duration
        genreTxt = findViewById(R.id.genreTxt); // meditation genre
        exit = findViewById(R.id.meditateExit);

        //meditateDes = findViewById(R.id.meditateDesTxt); // meditation des

        playCard = findViewById(R.id.playCard); // play pause btn
        playPauseImage = findViewById(R.id.playPauseImg); // play pause img

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
                Intent toHome = new Intent(MeditationActivity.this, MainActivity.class);
                startActivity(toHome);
                finish();
            }
        });

        // instantiating media player
        mediaPlayer = new MediaPlayer();

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
                                    meditationGuide.setDuration(document.getString("Duration"));
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
                                playCard.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(!mediaPlayer.isPlaying()){
                                            mediaPlayer.start(); //start audio
                                            //set text fields based on audio
                                            meditateTitle.setText(guideTitle);
                                            artistTxt.setText(guideArtist);
                                            durationTxt.setText(guideDuration);
                                            genreTxt.setText(mTitle);
                                            //change card text and img
                                            playPauseImage.setImageResource(R.drawable.ic_pause_white_24dp);
                                        }
                                        else{
                                            mediaPlayer.pause(); //stop audio
                                            //change card text and img
                                            playPauseImage.setImageResource(R.drawable.ic_play_arrow_white_24dp);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
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


}
