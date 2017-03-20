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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    protected EditText passwordEditText;
    protected EditText emailEditText;
    protected Button signUpButton;

    private FirebaseAuth mFirebaseAuth;
    private Intent mainActivityLauncherIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseAuth = FirebaseAuth.getInstance();
        setUpUIAndClickListeners();
    }

    private void setUpUIAndClickListeners() {
        passwordEditText = (EditText)findViewById(R.id.passwordField);
        emailEditText = (EditText)findViewById(R.id.emailField);
        signUpButton = (Button)findViewById(R.id.signupButton);

        setUpClickListeners();
    }

    private void setUpClickListeners() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    private void signUpUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            showSignUpErrorDialog(null);
        } else {
            signUp(email,password);
        }
    }

    private void showSignUpErrorDialog(String message) {
        String dialogMessage = message == null ? getResources().getString(R.string.signup_error_message) : message;
        AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(SignUpActivity.this);
        errorDialogBuilder.setMessage(dialogMessage)
                .setTitle(R.string.signup_error_title)
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = errorDialogBuilder.create();
        dialog.show();
    }

    private void signUp(String email, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            launchMainActivity();
                        } else {
                            showSignUpErrorDialog(task.getException().getMessage());
                        }
                    }
                });
    }

    private void launchMainActivity() {
        mainActivityLauncherIntent = new Intent(this, MainActivity.class);
        addIntentFlags(mainActivityLauncherIntent);
        startActivity(mainActivityLauncherIntent);
    }

    private void addIntentFlags(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}
