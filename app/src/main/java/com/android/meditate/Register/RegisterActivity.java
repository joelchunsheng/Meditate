package com.android.meditate.Register;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.meditate.R;
import com.android.meditate.Username.UsernameActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerEmail;
    private EditText registerPassword;
    private EditText registerConfirmPassword;
    private Button registerButton;

    private final static String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();

        registerEmail = (EditText) findViewById(R.id.registerEmail);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        registerConfirmPassword = (EditText) findViewById(R.id.registerConfirmPassword);
        registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = registerEmail.getEditableText().toString();
                String passwordInput = registerPassword.getEditableText().toString();
                String confirmPasswordInput = registerConfirmPassword.getEditableText().toString();

                if (emailInput.contains(" ") || (!(emailInput.contains("@"))) || emailInput.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please provide a valid Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (passwordInput.contains(" ") || passwordInput.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please provide a valid password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (passwordInput.equals(confirmPasswordInput)){
                    Intent toUsername = new Intent(RegisterActivity.this, UsernameActivity.class);
                    startActivity(toUsername);
                }
                else if (!(passwordInput.equals(confirmPasswordInput))){
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.v(TAG, "Error occured when trying to compare passwords");
                }
            }
        });
    }

    //TO DO
    // Register
    // save UID to shared pref
}
