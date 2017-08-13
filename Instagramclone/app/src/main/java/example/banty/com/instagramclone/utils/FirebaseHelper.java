package example.banty.com.instagramclone.utils;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.models.User;
import example.banty.com.instagramclone.models.UserAccountSettings;
import example.banty.com.instagramclone.models.UserSetting;

public class FirebaseHelper {
    private static final String TAG = "FirebaseHelper";
    private Context mContext;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String currentUserId;

    public FirebaseHelper(Context context) {
        mContext = context;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        if(mFirebaseAuth != null && mFirebaseAuth.getCurrentUser() != null)
            currentUserId = mFirebaseAuth.getCurrentUser().getUid();

    }

    public void addUserToDatabase(User user, DatabaseReference myRef) {
        myRef.child(mContext.getString(R.string.firebase_user_node))
                .child(user.getUser_id())
                .setValue(user);

        UserAccountSettings settings = new UserAccountSettings("",
                user.getUsername(),
                0,
                0,
                0,
                "",
                StringMethods.condenseUsername(user.getUsername()),
                "");

        myRef.child(mContext.getString(R.string.firebase_user_account_settings_node))
                .child(user.getUser_id())
                .setValue(settings);
    }


    public UserSetting getUserAccountSetting(DataSnapshot dataSnapshot, String userId) {
        Log.d(TAG, "getUserAccountSetting: retrieving user information from Firebase Database");

        UserAccountSettings userAccountSettings = new UserAccountSettings();
        User user = new User();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            //getting data from User account setting node
            if(mContext != null) {
                if (ds.getKey().equals(mContext.getString(R.string.firebase_user_account_settings_node))) {
                    Log.d(TAG, "getUserAccountSetting: inside user account setting node : " + ds);
                    getUserAccountSettingFromDB(userId, userAccountSettings, ds);
                }


                //getting data from User Node
                if (ds.getKey().equals(mContext.getString(R.string.firebase_user_node))) {
                    Log.d(TAG, "getUserAccountSetting: inside user node : " + ds);
                    getUserDataFromDB(userId, user, ds);
                }
            }
        }

        return new UserSetting(user,userAccountSettings);
    }

    private void getUserDataFromDB(String userId, User user, DataSnapshot ds) {
        if(userId != null) {
            user.setUsername(
                    ds.child(userId)
                            .getValue(User.class)
                            .getUsername()
            );

            user.setEmail(
                    ds.child(userId)
                            .getValue(User.class)
                            .getEmail()
            );

            user.setPhone_number(
                    ds.child(userId)
                            .getValue(User.class)
                            .getPhone_number()
            );

            user.setUser_id(
                    ds.child(userId)
                            .getValue(User.class)
                            .getUser_id()
            );
        }
    }

    private void getUserAccountSettingFromDB(String userId, UserAccountSettings userAccountSettings, DataSnapshot ds) {
        try {
            userAccountSettings.setDisplay_name(
                    ds.child(userId)
                            .getValue(UserAccountSettings.class)
                            .getDisplay_name()
            );

            userAccountSettings.setUsername(
                    ds.child(userId)
                            .getValue(UserAccountSettings.class)
                            .getUsername()
            );

            userAccountSettings.setWebsite(
                    ds.child(userId)
                            .getValue(UserAccountSettings.class)
                            .getWebsite()
            );

            userAccountSettings.setDescription(
                    ds.child(userId)
                            .getValue(UserAccountSettings.class)
                            .getDescription()
            );

            userAccountSettings.setProfile_photo(
                    ds.child(userId)
                            .getValue(UserAccountSettings.class)
                            .getProfile_photo()
            );

            userAccountSettings.setFollowing(
                    ds.child(userId)
                            .getValue(UserAccountSettings.class)
                            .getFollowing()
            );

            userAccountSettings.setFollowers(
                    ds.child(userId)
                            .getValue(UserAccountSettings.class)
                            .getFollowers()
            );
        }catch (NullPointerException npe) {
            Log.d(TAG, "getUserAccountSetting: Null Pointer exception while reading data from Firebase");
            Log.e(TAG, npe.getMessage());
        }
    }


    public void updateUsername(String username) {
        Log.d(TAG, "updateUsername: updating username to :" + username);
        myRef.child(mContext.getString(R.string.firebase_user_node))
                .child(currentUserId)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);

        myRef.child(mContext.getString(R.string.firebase_user_account_settings_node))
                .child(currentUserId)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);
    }
}
