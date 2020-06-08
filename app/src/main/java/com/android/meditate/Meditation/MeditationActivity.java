package com.android.meditate.Meditation;

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

import com.android.meditate.R;

import java.io.IOException;

public class MeditationActivity extends AppCompatActivity {

    TextView meditateTitle, meditateDes, playPauseTxt;
    ImageView meditateImage, playPauseImage;
    MediaPlayer mediaPlayer;
    CardView iconCard, playCard;

    Boolean playPause = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        meditateTitle = findViewById(R.id.meditateTitleTxt);
        meditateDes = findViewById(R.id.meditateDesTxt);
        meditateImage = findViewById(R.id.meditateImageView);
        iconCard = findViewById(R.id.headerCard);
        playCard = findViewById(R.id.playCard);
        playPauseTxt = findViewById(R.id.playPauseText);
        playPauseImage = findViewById(R.id.playPauseImg);

        //get data from intent
        Intent intent = getIntent();
        String mTitle = intent.getStringExtra("iTitle");
        String mDes = intent.getStringExtra("iDes");
        byte[] mBytes = getIntent().getByteArrayExtra("iImage");
        //decode image
        Bitmap bitmap = BitmapFactory.decodeByteArray(mBytes, 0 , mBytes.length);

        //display
        meditateTitle.setText(mTitle);
        meditateDes.setText(mDes);
        meditateImage.setImageBitmap(bitmap);

        //set up header card
        setUpHeaderCard(mTitle, iconCard);

        //play/pause button
        playCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if true -> playing
                if (playPause){
                    //show pause
                    playPauseTxt.setText("PAUSE");
                    playPauseImage.setImageResource(R.drawable.ic_pause_white_24dp);
                    playPause = false;
                }
                //else if false -> not playing
                else{
                    playPauseTxt.setText("PLAY");
                    playPauseImage.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                    playPause = true;
                }


            }
        });




    }

    //get up card bg
    public static void setUpHeaderCard(String mTitle, CardView iconCard){
        if (mTitle.equalsIgnoreCase("Sleep")){
            iconCard.setCardBackgroundColor(Color.parseColor("#C6DEF1"));
        }
        else if (mTitle.equalsIgnoreCase("Stress & Anxiety")){
            iconCard.setCardBackgroundColor(Color.parseColor("#FFCDB2"));
        }
        else if (mTitle.equalsIgnoreCase("Breathe")){
            iconCard.setCardBackgroundColor(Color.parseColor("#DBCDF0"));
        }
        else if (mTitle.equalsIgnoreCase("Midnight Thoughts")){
            iconCard.setCardBackgroundColor(Color.parseColor("#E2CFC4"));
        }
        else if (mTitle.equalsIgnoreCase("Work Out")){
            iconCard.setCardBackgroundColor(Color.parseColor("#C9E4DE"));
        }
        else{
            iconCard.setCardBackgroundColor(Color.parseColor("#D2D2CF"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        clearMediaPlayer();
    }

    private void clearMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        Log.i("Meditation Activity", "Media Player released");
    }



    // music player
//        mediaPlayer = new MediaPlayer();
//
//        try{
//            mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/meditate-258b2.appspot.com/o/sleep%2Fsleepmeditation.mp3?alt=media&token=d93b4a7c-16fe-4ebb-a6af-e9187d608b7e");
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                }
//            });
//            mediaPlayer.prepare();
//        }catch (IOException e){
//            e.printStackTrace();
//        }

    //Play and Pause Btn
//        playBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(!mediaPlayer.isPlaying()){
//                    mediaPlayer.start();
//                    playBtn.setImageResource(R.drawable.ic_pause_black_24dp);
//                }
//                else{
//                    mediaPlayer.pause();
//                    playBtn.setImageResource(R.drawable.ic_play_arrow_black_24dp);
//                }
//
//            }
//        });

}
