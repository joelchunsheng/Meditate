package com.android.meditate.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.meditate.MainActivity;
import com.android.meditate.R;
import com.android.meditate.Register.RegisterActivity;
import com.android.meditate.Username.UsernameActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        emailInput = (EditText) findViewById(R.id.loginEmail);
        passwordInput = (EditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Login Button Clicked!");

                String emailInputText = emailInput.getEditableText().toString();
                String passwordInputText = passwordInput.getEditableText().toString();

                 if (!emailInputText.isEmpty() && !passwordInputText.isEmpty()){
                     auth.signInWithEmailAndPassword(emailInputText, passwordInputText)
                             .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     if (task.isSuccessful()){
                                         // Sign in successful
                                         Log.v(TAG, "Email Sign In Successful"); // Log sign in with email successful
                                         FirebaseUser currentUser = auth.getCurrentUser(); // Gets current logged in user
                                         String uid = currentUser.getUid(); // Gets user UID
                                         saveUID(uid); // saves user UID to sharedPref
                                         getUserInfo(uid); // gets user info with UID and saves it to sharedPref
                                         Intent intent = new Intent(LoginActivity.this, MainActivity.class); // Intent to MainActivity
                                         startActivity(intent);
                                         finish();
                                     }
                                     else{
                                         Log.v(TAG, "Email Sign In Failed"); // Log sign in with email failed
                                         Toast.makeText(getApplicationContext(), "Sign in Failed", Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             });
                 } else{
                     Toast.makeText(getApplicationContext(), "Please fill in both fields", Toast.LENGTH_SHORT).show();
                 }

            }
        });

        // SET UP SIGN UP LINK IN LOGIN ACTIVITY
        TextView loginSignUpTextView = (TextView) findViewById(R.id.loginSignUpText);
        String loginSignUpText = "Not registered with us? Sign Up here!";

        SpannableString loginSignUpSS = new SpannableString(loginSignUpText);
        ClickableSpan loginSignUpClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent toSignUp = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(toSignUp);
            }
        };

        loginSignUpSS.setSpan(loginSignUpClickableSpan, 24, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginSignUpTextView.setText(loginSignUpSS);
        loginSignUpTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
    
    //method to retrieve user data from firestore and save to firestore
    //call this methods upon successful login. (in firebase auth login code)

    //Save UID in shared prefrence
    private void saveUID(String uid){
        Log.i(TAG, "saving UID");
        SharedPreferences userPref = this.getSharedPreferences("com.android.meditate.User", Context.MODE_PRIVATE);
        userPref.edit().putString("UID", uid).apply();
    }

    // get user info from firestore
    private void getUserInfo(final String uid){
        Log.i(TAG, "retrieving user info");
        final SharedPreferences userPref = this.getSharedPreferences("com.android.meditate.User", Context.MODE_PRIVATE);

        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(uid);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                        Log.d(TAG, "DocumentSnapshot data: " + document.get("purchased"));

                        // save name
                        userPref.edit().putString("name", document.getString("name")).apply();

                        // save coins
                        userPref.edit().putInt("coins", Integer.parseInt(document.getString("coins"))).apply();

                        // save hours
                        userPref.edit().putFloat("hours", Float.parseFloat(document.getString("hours"))).apply();

                        // save purchased (if applicable)
                        try{
                            List<String> group = (List<String>) document.get("purchased"); //retrieve purchased list
                            userPref.edit().putStringSet("purchased", transformList(group)).apply();
                        }
                        catch (Exception e){
                            Log.d(TAG, "Error saving purchase list");
                        }

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

    // convert list to hashset to save to shared pref
    private Set transformList(List<String> list){
        Set<String> set = new HashSet<String>();

        for(String guide : list){
            set.add(guide);
        }
        Log.d(TAG, "HashSet: " + set);
        return set;
    }

}