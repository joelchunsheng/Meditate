package com.android.meditate.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = (EditText) findViewById(R.id.loginEmail);
        passwordInput = (EditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInputText = emailInput.getEditableText().toString();
                String passwordInputText = passwordInput.getEditableText().toString();

                if (emailInputText.contains(" ") || (!(emailInputText.contains("@"))) || emailInputText.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please provide a valid Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (passwordInputText.contains(" ") || passwordInputText.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please provide a valid password", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.v(TAG, "Login Button Clicked!");
                Intent toMain = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(toMain);
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

//    @Override
//    protected void onPause(){
//        super.onPause();
//        finish();
//    }
}