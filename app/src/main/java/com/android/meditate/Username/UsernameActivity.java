package com.android.meditate.Username;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.meditate.Login.LoginActivity;
import com.android.meditate.MainActivity;
import com.android.meditate.R;
import com.android.meditate.Register.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UsernameActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText usernameInput;
    private Button usernameButton;

    private static final String TAG = "UsernameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        auth = FirebaseAuth.getInstance();

        // Check if user still exists in database
        auth.getCurrentUser().reload()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Manual Sign in required", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UsernameActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

        usernameInput = (EditText) findViewById(R.id.usernameInput);
        usernameButton = (Button) findViewById(R.id.usernameButton);

        usernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Username Button Clicked");
                final String usernameInputText = usernameInput.getEditableText().toString();
                if (usernameInputText.contains(" ") || usernameInputText.isEmpty()){
                    Toast.makeText(UsernameActivity.this, "Please provide a valid Username", Toast.LENGTH_SHORT).show();
                    return;
                }

                final SharedPreferences userPref = UsernameActivity.this.getSharedPreferences("com.android.meditate.User", Context.MODE_PRIVATE);

                // Access a Cloud Firestore instance from your Activity
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                try{
                    DocumentReference docRef = db.collection("users").document(auth.getCurrentUser().getUid());

                    docRef.update("name", usernameInputText)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) { // Database update successful
                                    Log.v(TAG, "Successfully updated name field in database"); // Log success
                                    userPref.edit().putString("name", usernameInputText); // Edit sharedPref "name" field
                                    Intent toMain = new Intent(UsernameActivity.this, MainActivity.class); // Intent to MainActivity
                                    startActivity(toMain); // start MainActivity
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() { // Database update failed
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.v(TAG, "Failed to update name field in database"); // Log failure
                                    Toast.makeText(getApplicationContext(), "Failed to set Username. Please try again.", Toast.LENGTH_SHORT).show(); // Toast message for failure
                                }
                            });

                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Unable to retrieve user data", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "Cannot find document. Document may be deleted");
                    Intent intent = new Intent(UsernameActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    // TO DO
    // retrieve UID from user shared pref
    // Save username to firestore
    // also save coins -> 100 and hours -> 0 to firestore and empty array called purchased
    //

    //save name, coins, hours, array (setHash) to shared pref (look at my login getUserInfo)
}
