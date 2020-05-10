package com.android.meditate.Meditation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.meditate.R;

import java.io.IOException;

public class MeditationActivity extends AppCompatActivity {

    TextView meditateTitle, meditateDes;
    ImageView meditateImage;
    ImageButton playBtn;
    MediaPlayer mediaPlayer;
    boolean t = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        ActionBar actionBar = getSupportActionBar();

        meditateTitle = findViewById(R.id.meditateTitleTxt);
        meditateDes = findViewById(R.id.meditateDesTxt);
        meditateImage = findViewById(R.id.meditateImageView);
        playBtn = findViewById(R.id.btnPlay);

        //get data from intent
        Intent intent = getIntent();

        String mTitle = intent.getStringExtra("iTitle");
        String mDes = intent.getStringExtra("iDes");
        byte[] mBytes = getIntent().getByteArrayExtra("iImage");

        //decode image
        Bitmap bitmap = BitmapFactory.decodeByteArray(mBytes, 0 , mBytes.length);

        actionBar.setTitle(mTitle);
        meditateTitle.setText(mTitle);
        meditateDes.setText(mDes);
        meditateImage.setImageBitmap(bitmap);



        // music player
        mediaPlayer = new MediaPlayer();

        try{
            mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/meditate-258b2.appspot.com/o/sleep%2Fsleepmeditation.mp3?alt=media&token=d93b4a7c-16fe-4ebb-a6af-e9187d608b7e");
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
//                    Toast.makeText(MeditationActivity.this, "Click to start", Toast.LENGTH_SHORT).show();
                }
            });
            mediaPlayer.prepare();
        }catch (IOException e){
            e.printStackTrace();
        }

        //Play and Pause Btn
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    playBtn.setImageResource(R.drawable.ic_pause_black_24dp);
                }
                else{
                    mediaPlayer.pause();
                    playBtn.setImageResource(R.drawable.ic_play_arrow_black_24dp);
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

}
