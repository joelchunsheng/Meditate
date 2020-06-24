package com.android.meditate.Username;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.meditate.MainActivity;
import com.android.meditate.R;

public class UsernameActivity extends AppCompatActivity {

    private EditText usernameInput;
    private Button usernameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        usernameInput = (EditText) findViewById(R.id.usernameInput);
        usernameButton = (Button) findViewById(R.id.usernameButton);

        usernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInputText = usernameInput.getEditableText().toString();
                if (usernameInputText.contains(" ") || usernameInputText.isEmpty()){
                    Toast.makeText(UsernameActivity.this, "Please provide a valid Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent toMain = new Intent(UsernameActivity.this, MainActivity.class);
                startActivity(toMain);
            }
        });

    }

    // TO DO
    // retrieve UID from user shared pref
    // Save username to firestore
    // also save coins -> 100 and hours -> 0 to firestore

    //save name, coins, hours to shared pref
}
