package example.banty.com.instagramclone.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.activities.BaseActivity;
import example.banty.com.instagramclone.activities.home.HomeActivity;
import example.banty.com.instagramclone.register.RegisterActivity;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    //Firebase Auth
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressBar progressBar;
    private EditText emailEditText, passwordEditText;
    private TextView pleaseWaitTextView;
    private Button loginButton;
    private TextView registerLinkText;

    private View.OnClickListener loginButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "One or more field empty", Toast.LENGTH_SHORT).show();
            } else {
                changeProgressBarVisibility(View.VISIBLE);
                signInUser(email,password);

            }
        }
    };
    private View.OnClickListener registerLinkClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            launchRegisterActivity();
        }
    };

    private void launchRegisterActivity() {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    private void signInUser(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null && currentUser.isEmailVerified()) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                changeProgressBarVisibility(View.GONE);
                                launchHomeActivity();
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                changeProgressBarVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "email not verified, check for verification email in your inbox", Toast.LENGTH_SHORT).show();
                            changeProgressBarVisibility(View.GONE);
                        }
                    }
                });
    }

    private void launchHomeActivity() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        startActivity(homeIntent);
    }

    private void changeProgressBarVisibility(int visisbility) {
        progressBar.setVisibility(visisbility);
        pleaseWaitTextView.setVisibility(visisbility);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUIElements();
        setFirebaseAuth();

    }

    private void initUIElements() {
        progressBar = (ProgressBar) findViewById(R.id.login_loading_progress_bar);
        pleaseWaitTextView = (TextView) findViewById(R.id.please_wait_text);
        emailEditText = (EditText) findViewById(R.id.input_email);
        passwordEditText = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.button_login);
        registerLinkText = (TextView) findViewById(R.id.link_signup);

        pleaseWaitTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        loginButton.setOnClickListener(loginButtonClicked);
        registerLinkText.setOnClickListener(registerLinkClicked);
    }

    private void setFirebaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged: user signed in");

                } else {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged: user signed out");

                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
