package com.mad.meditate.EditProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mad.meditate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditPasswordActivity extends AppCompatActivity {
    private final static String TAG = "EditPasswordActivity";

    ImageButton backBtn;
    EditText currentPass, newPass, confrimPass;
    CardView save, cancel;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        backBtn = findViewById(R.id.editPassBackBtn);
        currentPass = findViewById(R.id.cPasswordEditTxt);
        newPass = findViewById(R.id.nPasswordEditTxt);
        confrimPass = findViewById(R.id.rPasswordEditTxt);
        save = findViewById(R.id.savePasswordCard);
        cancel = findViewById(R.id.cancelPasswordCard);

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
                final String pass = newPass.getText().toString().trim();
                String cPass = confrimPass.getText().toString().trim();


                if (currentPass.getText().toString().trim().isEmpty() || newPass.getText().toString().trim().isEmpty() || confrimPass.getText().toString().trim().isEmpty()){
                    Toast.makeText(EditPasswordActivity.this, "Field(s) are empty", Toast.LENGTH_SHORT).show();
                }
                else if(newPass.length() < 8){
                    Toast.makeText(EditPasswordActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                }
                else if (!pass.equals(cPass)){
                    Toast.makeText(EditPasswordActivity.this, "New password does not match", Toast.LENGTH_SHORT).show();
                }
                else{
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPass.getText().toString().trim());

                    // Prompt the user to re-provide their sign-in credentials
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "Password updated");
                                                    Toast.makeText(EditPasswordActivity.this, "Password updated", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Log.d(TAG, "Error password not updated");
                                                    Toast.makeText(EditPasswordActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Log.d(TAG, "Error auth failed");
                                        Toast.makeText(EditPasswordActivity.this, "Current password incorrect", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }
}
