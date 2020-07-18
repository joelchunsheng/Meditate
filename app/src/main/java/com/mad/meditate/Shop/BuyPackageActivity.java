package com.mad.meditate.Shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.meditate.Login.LoginActivity;
import com.mad.meditate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class BuyPackageActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private ImageView packageImage;
    private TextView packageTitle, packageDescription;
    private Button buyPackageButton;
    private ImageButton exitButton;
    private CardView piCard;

    private static final String TAG = "BuyPackageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_guide);

        auth = FirebaseAuth.getInstance();

        packageImage = (ImageView) findViewById(R.id.packageImage);
        packageTitle = (TextView) findViewById(R.id.packageTitle);
        packageDescription = (TextView) findViewById(R.id.packageDescription);
        buyPackageButton = (Button) findViewById(R.id.packageBuyButton);
        exitButton = (ImageButton) findViewById(R.id.exitButton);
        piCard = (CardView) findViewById(R.id.piCard);

        // Get intent with extras from ShopFragment
        Intent intent = getIntent();
        final String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        int pictureId = intent.getIntExtra("pictureId", 0);
        final int cost = intent.getIntExtra("cost", 0);
        String backgroundColor = intent.getStringExtra("backgroundColor");

        packageTitle.setText(title);
        packageDescription.setText(description);
        packageImage.setImageResource(pictureId);
        packageImage.setBackgroundColor(Color.parseColor(backgroundColor));
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //set up card view
        setUpCard(title, piCard);

        FirebaseUser currentUser = auth.getCurrentUser();
        final String uid = currentUser.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("users").document(uid);

        // Set Purchase Button Text
        setButtonText(docRef, cost, uid);

        // Set Purchase Button onClickListener
        buyPackageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (buyPackageButton.getText().toString().equalsIgnoreCase("Loading...")){
                    Toast.makeText(getApplicationContext(), "Retrieving package", Toast.LENGTH_SHORT).show();
                }
                else if (buyPackageButton.getText().toString().equalsIgnoreCase("Purchased")){ // If Purchased, show Toast and return
                    Toast.makeText(getApplicationContext(), "Package Already Purchased", Toast.LENGTH_SHORT).show();
                }
                else{
                    // Create AlertDialog for confirmation
                    createAlertDialog("Confirm Purchase", "Do you want to buy " + title + " for " + cost + " coins?", false, "Yes",
                            "No", docRef, cost, uid);
                }

            }
        });
    }

    private void setButtonText(DocumentReference docRef, final int cost, final String uid){
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        Log.v(TAG, "DocumentSnapshot data: " + document.getData());
                        if (((ArrayList<String>) document.get("purchased")).contains(packageTitle.getText().toString())){
                            buyPackageButton.setText("Purchased");
                        }
                        else{
                            buyPackageButton.setText("Buy for " + cost + " coins");
                        }
                    }
                    else{
                        Log.v(TAG, "Unable to retrieve user data for UID - " + uid);
                    }
                }
                else{
                    Log.v(TAG, "Unable to connect to database");
                }
            }
        });
    }

    private void createAlertDialog(String title, String message, boolean cancellable, String positiveText, String negativeTest, final DocumentReference docRef, final int packageCost, final String uid){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(cancellable);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "Making Transaction");
                makeTransaction(uid, docRef, packageCost);
            }
        });
        builder.setNegativeButton(negativeTest, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "Cancelled Transaction");
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void makeTransaction(final String uid, final DocumentReference docRef, final int cost){
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        SharedPreferences userPref = BuyPackageActivity.this.getSharedPreferences("com.android.meditate.User", Context.MODE_PRIVATE);
                        Log.v(TAG, "DocumentSnapshot data: " + document.getData());
                        int coins = document.getLong("coins").intValue();
                        if (coins >= cost){ // Sufficient Coins
                            // Deduct Coins
                            coins -= cost;
                            docRef.update("coins", coins);
                            // Update COINS in sharedPreferences
                            userPref.edit().putInt("coins", coins).apply();
                            // Update purchased ARRAY in Firestore and SET in sharedPreferences
                            List<String> purchased = (ArrayList<String>) document.get("purchased");
                            purchased.add(packageTitle.getText().toString());
                            docRef.update("purchased", purchased);
                            userPref.edit().putStringSet("purchased", LoginActivity.transformList(purchased)).apply();
                            buyPackageButton.setText("Purchased");
                        }
                        else{ // Insufficient Coins
                            Toast.makeText(getApplicationContext(), "Not Enough Coins", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Log.v(TAG, "Unable to retrieve user data for UID - " + uid);
                    }
                }
                else{
                    Log.v(TAG, "Unable to connect to database");
                }
            }
        });
    }

    private void setUpCard(String title, CardView cardView){
        if (title.equals("10 min guides")){
            cardView.setCardBackgroundColor(Color.parseColor("#C6DEF1"));
        }
        else if(title.equals("White Noise")){
            cardView.setCardBackgroundColor(Color.parseColor("#F7D9C4"));
        }
        else if(title.equals("Nature")){
            cardView.setCardBackgroundColor(Color.parseColor("#E2CFC4"));
        }
        else if(title.equals("Self care")){
            cardView.setCardBackgroundColor(Color.parseColor("#DBCDF0"));
        }
        else if(title.equals("Rainy days")){
            cardView.setCardBackgroundColor(Color.parseColor("#FAEDCB"));
        }
        else if(title.equals("Piano")){
            cardView.setCardBackgroundColor(Color.parseColor("#E2E2DF"));
        }
        else if(title.equals("Slow down")){
            cardView.setCardBackgroundColor(Color.parseColor("#C9E4DE"));
        }
        else{
            cardView.setCardBackgroundColor(Color.parseColor("#C6DEF1"));
        }
    }
}
