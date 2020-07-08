package com.android.meditate.EditProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.meditate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfile";

    ImageButton exit;
    TextView name, email;
    CardView nameEdit, passwordEdit;

    FirebaseUser user;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        exit = findViewById(R.id.epBackBtn);
        name = findViewById(R.id.displayNameTxt);
        email = findViewById(R.id.displayEmailTxt);
        nameEdit = findViewById(R.id.nameEditCard);
        passwordEdit = findViewById(R.id.passwordEditCard);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        email.setText(user.getEmail());

        getUserName(db,user, name);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        nameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editNameActivity = new Intent(EditProfileActivity.this, EditNameActivity.class);
                startActivity(editNameActivity);
            }
        });

        passwordEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editPassActivity = new Intent(EditProfileActivity.this, EditPasswordActivity.class);
                startActivity(editPassActivity);
            }
        });


    }

    private void getUserName(FirebaseFirestore db, FirebaseUser user, final TextView nameTxt) {
        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        nameTxt.setText(document.getString("name"));

                    } else {
                        Log.d(TAG, "No such document");
                        nameTxt.setText("");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    nameTxt.setText("");
                }
            }
        });
    }
}
