package example.banty.com.instagramclone.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.activities.login.LoginActivity;


public class SignOutFragment extends Fragment {

    private static final String TAG = "SignOutFragment";

    //Firebase Variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressBar mProgressBar;
    private TextView signOutTextView, signingOutTextView;
    private Button signOutButton;
    private View.OnClickListener signOutButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mFirebaseAuth.signOut();
            getActivity().finish();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_out,container,false);

        setUpUIElements(view);
        setFirebaseAuth();

        return view;
    }

    private void setUpUIElements(View view) {
        signOutTextView = (TextView) view.findViewById(R.id.confirm_sign_out_text);
        mProgressBar = (ProgressBar) view.findViewById(R.id.sign_out_progress_bar);
        signOutButton = (Button) view.findViewById(R.id.sign_out_button);
        signingOutTextView = (TextView) view.findViewById(R.id.signing_out_text);

        signOutButton.setOnClickListener(signOutButtonClicked);
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
                    //navigating back to  login activity
                    launchLoginActivity();
                }
            }
        };
    }

    private void launchLoginActivity() {
        Intent loginActivityIntent = new Intent(getContext(), LoginActivity.class);
        //clears the activity stack
        loginActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginActivityIntent);
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
