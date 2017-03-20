package com.example.admin.firebasetesting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    protected EditText emailEditText;
    protected EditText passwordEditText;
    protected Button logInButton;
    protected TextView signUpTextView;
    private FirebaseAuth mFirebaseAuth;
    private Intent mainActivityLauncherIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //initialise Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        setUpUIAndClickListeners();
    }

    private void setUpClickListeners() {
        //set up click listeners for signup TextView and login Button
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUpActivity();
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInUser();
            }
        });
    }

    private void logInUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            showLogInErrorDialog(null);
        } else {
            logInUser(email,password);
        }
    }

    private void logInUser(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            launchMainActivity();
                        } else {
                            showLogInErrorDialog(task.getException().getMessage());
                        }
                    }
                });
    }

    private void launchMainActivity() {
        mainActivityLauncherIntent = new Intent(LogInActivity.this, MainActivity.class);
        addIntentFlags(mainActivityLauncherIntent);
        startActivity(mainActivityLauncherIntent);
    }

    private void addIntentFlags(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    private void showLogInErrorDialog(String message) {
        String dialogMessage = message == null ? getResources().getString(R.string.login_error_message) : message;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(dialogMessage)
                .setTitle(R.string.login_error_title)
                .setPositiveButton(android.R.string.ok,null);
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    private void startSignUpActivity() {
        Intent signUpActivityIntent = new Intent(this,SignUpActivity.class);
        startActivity(signUpActivityIntent);
    }

    private void setUpUIAndClickListeners() {
        signUpTextView = (TextView) findViewById(R.id.signUpText);
        emailEditText = (EditText) findViewById(R.id.emailField);
        passwordEditText = (EditText) findViewById(R.id.passwordField);
        logInButton = (Button) findViewById(R.id.loginButton);

        setUpClickListeners();
    }
}
