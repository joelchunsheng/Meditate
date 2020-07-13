package com.android.meditate.EditProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.meditate.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditNameActivity extends AppCompatActivity {
    private static final String TAG = "EditNameActivity";

    ImageButton backBtn;
    EditText usernameTxt;
    CardView save, cancel;
    FirebaseFirestore db;
    FirebaseUser user;

    SharedPreferences userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);

        backBtn = findViewById(R.id.usernameBackBtn);
        usernameTxt = findViewById(R.id.usernameEditTxt);
        save = findViewById(R.id.saveUsernameCard);
        cancel = findViewById(R.id.cancelUsernameCard);

        userPref = this.getSharedPreferences("com.android.meditate.User", Context.MODE_PRIVATE);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!usernameTxt.getText().toString().trim().isEmpty()){
                    DocumentReference washingtonRef = db.collection("users").document(user.getUid());
                    washingtonRef
                            .update("name", usernameTxt.getText().toString().trim())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                    Toast.makeText(EditNameActivity.this, "Username updated", Toast.LENGTH_LONG).show();
                                    userPref.edit().putString("name", usernameTxt.getText().toString().trim()).apply();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error updating document", e);
                                    Toast.makeText(EditNameActivity.this, "Update failed", Toast.LENGTH_LONG).show();
                                }
                            });
                }
                else{
                    Toast.makeText(EditNameActivity.this, "Field is empty", Toast.LENGTH_LONG).show();
                }

                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

    }
}
