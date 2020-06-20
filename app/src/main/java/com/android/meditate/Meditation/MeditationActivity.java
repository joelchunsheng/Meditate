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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView meditateTitle, meditateDes, playPauseTxt, artistTxt, durationTxt, genreTxt;
    ImageView meditateImage, playPauseImage;
    MediaPlayer mediaPlayer;
    CardView iconCard, playCard;
    FirebaseFirestore db;
    ArrayList<MeditationGuide> meditationGuideArrayList;
    String guideTitle, guideArtist, guideDuration;

    Boolean playPause = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        meditateTitle = findViewById(R.id.meditateTitleTxt); // meditation title
        artistTxt = findViewById(R.id.artistTxt); // meditation artist
        durationTxt = findViewById(R.id.durationTxt); // meditation duration
        genreTxt = findViewById(R.id.genreTxt); // meditation genre

        //meditateDes = findViewById(R.id.meditateDesTxt); // meditation des
        meditateImage = findViewById(R.id.meditateImageView); // meditation icon

        iconCard = findViewById(R.id.headerCard); //medication icon card
        playCard = findViewById(R.id.playCard); // play pause btn
        playPauseTxt = findViewById(R.id.playPauseText); // play pause text
        playPauseImage = findViewById(R.id.playPauseImg); // play pause img

        //get data from intent
        Intent intent = getIntent();
        final String mTitle = intent.getStringExtra("iTitle");
        String mDes = intent.getStringExtra("iDes");
        byte[] mBytes = getIntent().getByteArrayExtra("iImage");
        //decode image
        Bitmap bitmap = BitmapFactory.decodeByteArray(mBytes, 0 , mBytes.length);

        //display
        meditateTitle.setText(mTitle);
        artistTxt.setText(mDes);
        meditateImage.setImageBitmap(bitmap);
        durationTxt.setText("");
        genreTxt.setText("");

        //set up header card
        setUpHeaderCard(mTitle, iconCard);

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
                                            playPauseTxt.setText("PAUSE");
                                        }
                                        else{
                                            mediaPlayer.pause(); //stop audio
                                            //change card text and img
                                            playPauseImage.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                                            playPauseTxt.setText("PLAY");
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


    //set up card bg
    public static void setUpHeaderCard(String mTitle, CardView iconCard){
        if (mTitle.equalsIgnoreCase("Sleep") || mTitle.equalsIgnoreCase("10 min guides")){
            iconCard.setCardBackgroundColor(Color.parseColor("#C6DEF1"));
        }
        else if (mTitle.equalsIgnoreCase("Stress & Anxiety") || mTitle.equalsIgnoreCase("White Noise")){
            iconCard.setCardBackgroundColor(Color.parseColor("#FFCDB2"));
        }
        else if (mTitle.equalsIgnoreCase("Breathe") || mTitle.equalsIgnoreCase("Self care")){
            iconCard.setCardBackgroundColor(Color.parseColor("#DBCDF0"));
        }
        else if (mTitle.equalsIgnoreCase("Midnight Thoughts") || mTitle.equalsIgnoreCase("Nature")){
            iconCard.setCardBackgroundColor(Color.parseColor("#E2CFC4"));
        }
        else if (mTitle.equalsIgnoreCase("Work Out") || mTitle.equalsIgnoreCase("Slow down")){
            iconCard.setCardBackgroundColor(Color.parseColor("#C9E4DE"));
        }
        else if (mTitle.equalsIgnoreCase("Concentration") || mTitle.equalsIgnoreCase("Piano")){
            iconCard.setCardBackgroundColor(Color.parseColor("#E2E2DF"));
        }
        else{
            iconCard.setCardBackgroundColor(Color.parseColor("#FAEDCB"));
        }
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
