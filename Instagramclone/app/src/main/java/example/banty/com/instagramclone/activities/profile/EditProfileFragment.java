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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;
import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.models.User;
import example.banty.com.instagramclone.models.UserAccountSettings;
import example.banty.com.instagramclone.models.UserSetting;
import example.banty.com.instagramclone.utils.FirebaseHelper;
import example.banty.com.instagramclone.utils.UniversalImageLoader;


public class EditProfileFragment extends Fragment {

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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!mUserSettings.getUser().getUsername().equals(username)) {
                    //Case 1: User didn't change their username and email
                    checkIfUserNameExist(username);
                } else {
                    //Case 2: User changed his username and email
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    /*
        Check is @param username exits in DB
        @Param
     */
    private void checkIfUserNameExist(final String username) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref
                .child(getString(R.string.firebase_user_node))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //only if a match is found by the query
                if(!dataSnapshot.exists()) {
                    //add the username
                    Toast.makeText(getActivity(), "Adding " + username, Toast.LENGTH_SHORT).show();
                    new FirebaseHelper(getActivity()).updateUsername(username);
                }

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.exists()) {
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
                String userId = mFirebaseAuth.getCurrentUser().getUid();
                setProfileWidgets(new FirebaseHelper(getContext()).getUserAccountSetting(dataSnapshot, userId));
                //retrieve images for user
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
}
