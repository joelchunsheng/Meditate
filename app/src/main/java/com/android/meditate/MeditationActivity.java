package com.android.meditate;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MeditationActivity extends AppCompatActivity {

    TextView meditateTitle, meditateDes;
    ImageView meditateImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        ActionBar actionBar = getSupportActionBar();

        meditateTitle = findViewById(R.id.meditateTitleTxt);
        meditateDes = findViewById(R.id.meditateDesTxt);
        meditateImage = findViewById(R.id.meditateImageView);

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

    }
}
