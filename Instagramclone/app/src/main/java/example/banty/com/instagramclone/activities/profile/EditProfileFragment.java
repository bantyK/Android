package example.banty.com.instagramclone.activities.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;
import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.dialog.ConfirmPasswordDialog;
import example.banty.com.instagramclone.models.User;
import example.banty.com.instagramclone.models.UserAccountSettings;
import example.banty.com.instagramclone.models.UserSetting;
import example.banty.com.instagramclone.utils.FirebaseHelper;
import example.banty.com.instagramclone.utils.UniversalImageLoader;


public class EditProfileFragment extends Fragment implements ConfirmPasswordDialog.OnConfirmPasswordListener {

    private static final String TAG = "EditProfileFragment";

    private View checkButton;

    //UI Wdigets
    private EditText displayNameEditText, usernameEditText, websiteEditText, descriptionEditText, emailEditText, phoneNumberEditText;
    private CircleImageView displayPicture;
    private TextView changePhotoTextView;

    //Firebase Variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private String userID;
    private UserSetting mUserSettings;
    private FirebaseHelper firebaseHelper;

    private View.OnClickListener saveUserInfoIntoDB = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            saveProfileSetting();
        }
    };

    private void saveProfileSetting() {
        //retrieve the data from widgets
        //check that the user name is unique
        //save into DB

        final String displayName = displayNameEditText.getText().toString();
        final String username = usernameEditText.getText().toString();
        final String website = websiteEditText.getText().toString();
        final String description = descriptionEditText.getText().toString();
        final String email = emailEditText.getText().toString();
        final long phoneNumber = Long.parseLong(phoneNumberEditText.getText().toString());


        //Case 1: User changed their username
        if (!mUserSettings.getUser().getUsername().equals(username)) {
            checkIfUserNameExist(username);
        }

        //If user made a change to his email
        changeEmail(email);

        // If user has changed his/her display name
        changeDisplayName(displayName);

        // If user has changed his/her website name
        changeWebsite(website);

        //If user has changed his/her description
        changeDescription(description);

        //If user has changed his/her phone number
        changePhoneNumber(phoneNumber);
    }

    private void changePhoneNumber(long phoneNumber) {
        if(mUserSettings.getUser().getPhone_number() != phoneNumber) {
            firebaseHelper.updatePhoneNumber(phoneNumber);
        }
    }

    private void changeDescription(String description) {
        if(!mUserSettings.getSettings().getDescription().equals(description)) {
            firebaseHelper.updateDescription(description);
        }
    }

    private void changeWebsite(String website) {
        if(!mUserSettings.getSettings().getWebsite().equals(website)) {
            firebaseHelper.updateWebsite(website);
        }
    }

    private void changeEmail(String email) {
        if (!mUserSettings.getUser().getEmail().equals(email)) {
            // 1. Re-authenticate
            //     -- confirm the email and password

            // 2. Check if email is already registered

            // 3. Change the email (All 3 steps are performed in onConfirmPassword())

            //capturing the user password from the ConfirmPasswordFragment
            ConfirmPasswordDialog confirmPasswordDialog = new ConfirmPasswordDialog();
            confirmPasswordDialog.show(getFragmentManager(), getString(R.string.confirm_password_dialog));
            confirmPasswordDialog.setTargetFragment(EditProfileFragment.this, 1);
        }
    }

    private void changeDisplayName(String displayName) {
        if(!mUserSettings.getSettings().getDisplay_name().equals(displayName)) {
            //user has changed the display name
            firebaseHelper.updateDisplayName(displayName);
        }
    }

    /*
        Check is @param username exits in DB
        @param username
     */
    private void checkIfUserNameExist(final String username) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        //query to get all the usernames from the DB
        Query query = ref
                .child(getString(R.string.firebase_user_node))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //query returns a datasnapshot, only if a match is found by the query
                if (!dataSnapshot.exists()) {
                    //username does not exist
                    //add the username
                    Toast.makeText(getActivity(), "Adding " + username, Toast.LENGTH_SHORT).show();
                    new FirebaseHelper(getActivity()).updateUsername(username);
                }

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.exists()) {
                        Toast.makeText(getActivity(), username + " already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        setUpUI(view);
        setFirebaseAuth();
        setBackButton(view);
        initImageLoader();

        return view;
    }

    private void setUpUI(View view) {
        changePhotoTextView = (TextView) view.findViewById(R.id.change_profile_photo);
        displayPicture = (CircleImageView) view.findViewById(R.id.profile_photo);
        usernameEditText = (EditText) view.findViewById(R.id.username);
        displayNameEditText = (EditText) view.findViewById(R.id.display_name);
        websiteEditText = (EditText) view.findViewById(R.id.website);
        descriptionEditText = (EditText) view.findViewById(R.id.description);
        emailEditText = (EditText) view.findViewById(R.id.email);
        phoneNumberEditText = (EditText) view.findViewById(R.id.phone_number);

        checkButton = view.findViewById(R.id.iv_checkmark);
        checkButton.setOnClickListener(saveUserInfoIntoDB);
    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getContext());
        ImageLoader.getInstance().init(universalImageLoader.getImageLoaderConfig());
    }


    public void setBackButton(View view) {
        ImageView backButton = (ImageView) view.findViewById(R.id.iv_back_arrow);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }


    private void setFirebaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        userID = mFirebaseAuth.getCurrentUser().getUid();
        firebaseHelper = new FirebaseHelper(getContext());
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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //retrieve user information from database
                if (mFirebaseAuth.getCurrentUser() != null) {
                    String userId = mFirebaseAuth.getCurrentUser().getUid();
                    setProfileWidgets(new FirebaseHelper(getContext()).getUserAccountSetting(dataSnapshot, userId));
                    //retrieve images for user
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setProfileWidgets(UserSetting userSetting) {
        mUserSettings = userSetting;
        User user = userSetting.getUser();
        UserAccountSettings userAccountSettings = userSetting.getSettings();

        if (user != null && userAccountSettings != null) {
            usernameEditText.setText(user.getUsername());
            displayNameEditText.setText(userAccountSettings.getDisplay_name());
            websiteEditText.setText(userAccountSettings.getWebsite());
            descriptionEditText.setText(userAccountSettings.getDescription());

            emailEditText.setText(user.getEmail());
            phoneNumberEditText.setText(String.valueOf(user.getPhone_number()));
            UniversalImageLoader.setImage(userAccountSettings.getProfile_photo(), displayPicture, null, "");
        }
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

    @Override
    public void onConfirmPassword(String password) {
        Log.d(TAG, "onConfirmPassword: got the password : " + password);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(mFirebaseAuth.getCurrentUser().getEmail(), password);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: User re-authenticated.");
                            checkIfEmailAlreadyExist(emailEditText.getText().toString().trim());
                        } else {
                            Log.d(TAG, "onComplete: User re-authenticated failed.");
                        }
                    }
                });

    }

    private void checkIfEmailAlreadyExist(final String email) {
        //check if user email is not already present in firebase DB
        mFirebaseAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                if (task.isSuccessful()) {
                    //email already exist
                    try {
                        if (task.getResult().getProviders().size() == 1) {
                            Log.d(TAG, "onComplete: Email already in use");
                            Toast.makeText(getActivity(), "Email already in use", Toast.LENGTH_SHORT).show();
                        } else {
                            //email is available, so update it
                            Log.d(TAG, "onComplete: updating user's email");
                            updateUserEmail(email);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onComplete: Exception : " + e.getMessage());
                    }
                }
            }
        });
    }

    /*
       Update user's email in the firebase authentication system
    * */
    private void updateUserEmail(final String email) {
        Log.d(TAG, "updateUserEmail: updating email to : " + email);
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                            Toast.makeText(getActivity(), "Email successfully updated", Toast.LENGTH_SHORT).show();
                            //send the verification email to user's new email
                            firebaseHelper.sendVerificationEmail();
                            //update the user's email in the firebase DB also
                            firebaseHelper.updateEmail(email);
                            //signout the user so that user can sign in again with new email
                            mFirebaseAuth.signOut();

                        }
                    }
                });
    }
}

