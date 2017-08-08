package example.banty.com.instagramclone.register;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.activities.BaseActivity;
import example.banty.com.instagramclone.models.User;
import example.banty.com.instagramclone.utils.FirebaseHelper;


public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";

    //Firebase Auth
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private FirebaseHelper firebaseHelper; //util class for firebase methods

    private EditText emailEditText, passwordEditText, usernameEditText;
    private Button registerButton;
    private ProgressBar progressBar;
    private TextView pleaseWaitTextView;

    private String currentUserId;

    private String email;
    private String password;
    private String username;

    private View.OnClickListener registerButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setProgressBarVisibility(View.VISIBLE);
            email = emailEditText.getText().toString();
            password = passwordEditText.getText().toString();
            username = usernameEditText.getText().toString();
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
                Toast.makeText(RegisterActivity.this, "One or more field empty", Toast.LENGTH_SHORT).show();
            } else {
                registerNewUser(email, password);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
        firebaseHelper = new FirebaseHelper(this);
        setFirebaseAuth();
    }

    private void setFirebaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                if (mFirebaseAuth.getCurrentUser() != null) {
                    //User is signed in
                    final FirebaseUser user = firebaseAuth.getCurrentUser();
                    Log.d(TAG, "onAuthStateChanged: current user id = " + user.getUid());
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Username should be unique, If same existing username is used, add a random string to the username
                            if (firebaseHelper.checkIfUsernameExist(username, dataSnapshot)) {
                                Log.d(TAG, "onDataChange: user already exists");
                                String randomStringToAppend = myRef.push().getKey().substring(3, 10); // generates a random string
                                username += randomStringToAppend;
                            }
                            User userToAdd = new User(user.getUid(), email, 111, username);
                            firebaseHelper.addUserToDatabase(userToAdd, myRef);
                            Toast.makeText(RegisterActivity.this, "Account created", Toast.LENGTH_SHORT).show();

                            //Sign out the user, until the email is verified
                            mFirebaseAuth.signOut();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    finish();
                } else {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged: user signed out");

                }
            }
        };
    }

    private void sendVerificationEmail() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent to " + user.getEmail());
                            } else {
                                Toast.makeText(RegisterActivity.this, "Couldn't send verification email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Log.d(TAG, "sendVerificationEmail: user is null");
        }
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

    private void setProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
        pleaseWaitTextView.setVisibility(visibility);
    }

    private void initUI() {
        emailEditText = (EditText) findViewById(R.id.input_email);
        passwordEditText = (EditText) findViewById(R.id.input_password);
        usernameEditText = (EditText) findViewById(R.id.input_username);

        registerButton = (Button) findViewById(R.id.button_register);

        progressBar = (ProgressBar) findViewById(R.id.register_loading_progress_bar);
        pleaseWaitTextView = (TextView) findViewById(R.id.please_wait_text);

        setProgressBarVisibility(View.GONE);
        registerButton.setOnClickListener(registerButtonClicked);
    }

    private void registerNewUser(String email, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            currentUserId = mFirebaseAuth.getCurrentUser().getUid();
                            setProgressBarVisibility(View.GONE);
                            sendVerificationEmail();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            setProgressBarVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
 