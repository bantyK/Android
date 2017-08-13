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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    private ImageView profilePhoto;
    private View backButton;

    //UI Wdigets
    private EditText displayNameEditText, usernameEditText, websiteEditText, descriptionEditText, emailEditText, phoneNumberEditText;
    private CircleImageView displayPicture;
    private TextView changePhotoTextView;

    //Firebase Variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        profilePhoto = (ImageView) view.findViewById(R.id.profile_photo);

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
    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getContext());
        ImageLoader.getInstance().init(universalImageLoader.getImageLoaderConfig());
    }

    private void setProfileImage() {
        String imageURL = "www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2015/01/podcast-event.jpg?itok=zsMimlTM";
        UniversalImageLoader.setImage(imageURL, profilePhoto, null, "https://");
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
        User user = userSetting.getUser();
        UserAccountSettings userAccountSettings = userSetting.getSettings();

        if (user != null && userAccountSettings != null) {
            usernameEditText.setText(userAccountSettings.getUsername());
            displayNameEditText.setText(userAccountSettings.getDisplay_name());
            websiteEditText.setText(userAccountSettings.getWebsite());
            descriptionEditText.setText(userAccountSettings.getDescription());

            emailEditText.setText(user.getEmail());
            phoneNumberEditText.setText(String.valueOf(user.getPhone_number()));
            UniversalImageLoader.setImage(userAccountSettings.getProfile_photo(), profilePhoto, null, "");
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
