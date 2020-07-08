package com.android.meditate.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.meditate.Login.LoginActivity;
import com.android.meditate.R;
import com.android.meditate.Username.UsernameActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText registerEmail;
    private EditText registerPassword;
    private EditText registerConfirmPassword;
    private Button registerButton;
    TextView cancel;

    private final static String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();

        registerEmail = (EditText) findViewById(R.id.registerEmail);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        registerConfirmPassword = (EditText) findViewById(R.id.registerConfirmPassword);
        registerButton = (Button) findViewById(R.id.registerButton);
        cancel = (TextView) findViewById(R.id.cancelTxt) ;

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Register Button clicked");

                final String emailInput = registerEmail.getEditableText().toString();
                final String passwordInput = registerPassword.getEditableText().toString();
                String confirmPasswordInput = registerConfirmPassword.getEditableText().toString();

                if (emailInput.isEmpty() || passwordInput.isEmpty() || confirmPasswordInput.isEmpty()){ // Checks if all fields are filled in
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else{
                    if (passwordInput.equals(confirmPasswordInput)){ // Check if both passwords match
                        auth.createUserWithEmailAndPassword(emailInput, passwordInput)
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            // Register Successful
                                            Log.v(TAG, "Register Successful");

                                            // Automatically logs in new user after registration
                                            auth.signInWithEmailAndPassword(emailInput, passwordInput)
                                                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()){
                                                                FirebaseUser currentUser = auth.getCurrentUser(); // Get current user logged in
                                                                String uid = currentUser.getUid(); // Gets uid of current user
                                                                generateUserInfo(uid, RegisterActivity.this); // Generates new user info and saves it to firestore and sharedPref
                                                                saveUID(uid); // Saves user uid to sharedPref
                                                                Intent toUsername = new Intent(RegisterActivity.this, UsernameActivity.class);
                                                                startActivity(toUsername);
                                                                finish();
                                                            }
                                                            else{
                                                                Toast.makeText(getApplicationContext(), "Registration failed. Please try again", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                        else{
                                            // Register failed
                                            Log.v(TAG, "Registration Failed");
                                            Toast.makeText(getApplicationContext(), "Registration failed. Please try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                    }
                    else if (!(passwordInput.equals(confirmPasswordInput))){
                        Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.v(TAG, "Error occured when trying to compare passwords");
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(toLogin);
            }
        });
    }

    //Save UID in shared prefrence
    private void saveUID(String uid){
        Log.i(TAG, "saving UID");
        SharedPreferences userPref = this.getSharedPreferences("com.android.meditate.User", Context.MODE_PRIVATE);
        userPref.edit().putString("UID", uid).apply();
    }

    // Generate new user info, save to firestore and sharedPref
    public static void generateUserInfo(final String uid, Activity activity) {
        Log.i(TAG, "Generating new user info");
        final SharedPreferences userPref = activity.getSharedPreferences("com.android.meditate.User", Context.MODE_PRIVATE);

        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Generate default information
        Map<String, Object> newUserInfo = new HashMap<>();
        newUserInfo.put("coins", 100);
        newUserInfo.put("name", "");
        List<String> list = new ArrayList<>();
        newUserInfo.put("purchased", list);

        db.collection("users").document(uid).set(newUserInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.v(TAG, "DocumentSnapshot added with ID: " + uid);

                        // Set sharedPref to default values
                        userPref.edit().putInt("coins", 100).apply();
                        userPref.edit().putString("name", "").apply();
                        userPref.edit().putStringSet("purchased", new HashSet<String>()).apply();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(TAG, "Encountered Exception " + e + " when trying to create a document");
                    }
                });
    }
}
