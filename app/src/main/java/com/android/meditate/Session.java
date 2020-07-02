package com.android.meditate;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Session extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser(); // Gets current user (null if no current user)
        if (currentUser != null){ // If there is a current user (logged in user)
            // check if user still exists in database
            Intent intent = new Intent(Session.this, MainActivity.class); // Intent to UsernameActivity
            startActivity(intent);
        }
    }
}
