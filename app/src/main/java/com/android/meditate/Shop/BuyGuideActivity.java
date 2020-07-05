package com.android.meditate.Shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.meditate.R;

public class BuyGuideActivity extends AppCompatActivity {

    private ImageView packageImage;
    private TextView packageTitle, packageAuthor, packageDescription;
    private Button buyPackageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_guide);

        packageImage = (ImageView) findViewById(R.id.packageImage);
        packageTitle = (TextView) findViewById(R.id.packageTitle);
        packageAuthor = (TextView) findViewById(R.id.packageAuthor);
        packageDescription = (TextView) findViewById(R.id.packageDescription);
        buyPackageButton = (Button) findViewById(R.id.packageBuyButton);
    }

    @Override
    protected void onStart(){
        super.onStart();

        // Get intent with extras from ShopFragment
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String description = intent.getStringExtra("description");
        String picture = intent.getStringExtra("picture");
        int cost = intent.getIntExtra("cost", 0);

        packageTitle.setText(title);
        packageAuthor.setText(author);
        packageDescription.setText(description);
        packageImage.setImageURI(Uri.parse("android.resource://" + getPackageName() + "/" + picture));
        buyPackageButton.setText("Buy for " + cost + " coins");
    }
}
