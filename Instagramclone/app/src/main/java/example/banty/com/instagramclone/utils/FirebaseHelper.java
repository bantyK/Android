package example.banty.com.instagramclone.utils;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.models.User;
import example.banty.com.instagramclone.models.UserAccountSettings;

public class FirebaseHelper {
    private static final String TAG = "FirebaseHelper";
    private Context mContext;

    public FirebaseHelper(Context context) {
        mContext = context;
    }

    public boolean checkIfUsernameExist(String username, DataSnapshot dataSnapshot) {
        Log.d(TAG, "checkIfUsernameExist: checking usernames in the database");

        for(DataSnapshot ds : dataSnapshot.child("user").getChildren()) {
            User user = ds.getValue(User.class);
            if(username.equals(user.getUsername())) {
                //username already exist
                return true;
            }
        }
        return false;
    }

    public void addUserToDatabase(User user, DatabaseReference myRef) {
        myRef.child(mContext.getString(R.string.firebase_user_node))
                .child(user.getUser_id())
                .setValue(user);

        UserAccountSettings settings = new UserAccountSettings("",user.getUsername(),0,0,0,"",user.getUsername(),"");
        myRef.child(mContext.getString(R.string.firebase_user_account_settings_node))
                .child(user.getUser_id())
                .setValue(settings);
    }


}
