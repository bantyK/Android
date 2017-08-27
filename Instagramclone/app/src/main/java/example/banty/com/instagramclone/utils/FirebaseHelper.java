package example.banty.com.instagramclone.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.models.User;
import example.banty.com.instagramclone.models.UserAccountSettings;
import example.banty.com.instagramclone.models.UserSetting;
import example.banty.com.instagramclone.register.RegisterActivity;

public class FirebaseHelper {
    private static final String TAG = "FirebaseHelper";
    private Context mContext;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private StorageReference mStorageReference;

    private String currentUserId;
    private double mPhotoUploadProgress = 0;

    public FirebaseHelper(Context context) {
        mContext = context;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();

        if (mFirebaseAuth != null && mFirebaseAuth.getCurrentUser() != null)
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
            if (mContext != null) {
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

        return new UserSetting(user, userAccountSettings);
    }

    private void getUserDataFromDB(String userId, User user, DataSnapshot ds) {
        if (userId != null) {
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
        } catch (NullPointerException npe) {
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

    public void updateEmail(String email) {
        Log.d(TAG, "updateEmail: updating email to : " + email);

        myRef.child(mContext.getString(R.string.firebase_user_node))
                .child(currentUserId)
                .child(mContext.getString(R.string.field_email))
                .setValue(email);
    }

    public void sendVerificationEmail() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent to " + user.getEmail());
                            } else {
                                Toast.makeText(mContext, "Couldn't send verification email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Log.d(TAG, "sendVerificationEmail: user is null");
        }
    }

    public void updateDisplayName(String displayName) {
        Log.d(TAG, "updateEmail: updating display name to : " + displayName);

        myRef.child(mContext.getString(R.string.firebase_user_account_settings_node))
                .child(currentUserId)
                .child(mContext.getString(R.string.field_display_name))
                .setValue(displayName);
    }

    public void updateWebsite(String website) {
        Log.d(TAG, "updateWebsite: updating website to : " + website);

        myRef.child(mContext.getString(R.string.firebase_user_account_settings_node))
                .child(currentUserId)
                .child(mContext.getString(R.string.field_website))
                .setValue(website);
    }

    public void updateDescription(String description) {
        Log.d(TAG, "updateDescription: updating description to : " + description);

        myRef.child(mContext.getString(R.string.firebase_user_account_settings_node))
                .child(currentUserId)
                .child(mContext.getString(R.string.field_description))
                .setValue(description);
    }

    public void updatePhoneNumber(long phoneNumber) {
        Log.d(TAG, "updatePhoneNumber: updating phone number to : " + phoneNumber);

        myRef.child(mContext.getString(R.string.firebase_user_node))
                .child(currentUserId)
                .child(mContext.getString(R.string.field_phone_number))
                .setValue(phoneNumber);
    }

    public int getUserImageCount(DataSnapshot dataSnapshot) {
        int count = 0;
        for (DataSnapshot ds : dataSnapshot.
                child(mContext.getString(R.string.user_photo_node))
                .child(mFirebaseAuth.getCurrentUser().getUid())
                .getChildren()) {

            count += 1;

        }
        return count;
    }

    public void uploadPhoto(String photoType, String caption, int count, String imageURL) {
        if (photoType.equalsIgnoreCase(mContext.getString(R.string.new_photo))) {
            //uploading an image into firebase database
            Log.d(TAG, "uploadPhoto: uploading a new photo");
            String uid = mFirebaseAuth.getCurrentUser().getUid();
            //This create a path where image is going to get stored in firebase storage
            String imagePath = FilePaths.FIREBASE_IMAGE_STORAGE + uid + "/photo" + (count + 1);
            StorageReference storageReference = mStorageReference.child(imagePath);

            Bitmap bitmap = ImageUtils.getBitmap(imageURL);
            byte[] bytes = ImageUtils.getByteFromBitmap(bitmap, 100);
            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(bytes);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri firebaseUrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(mContext, "Photo sucessfully uploaded", Toast.LENGTH_SHORT).show();

                    //add the new photo to photo node and user_photos node

                    //navigate to the main feed so that user can see their newly uploaded photo
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: photo upload failed : " + e.getMessage());
                    Toast.makeText(mContext, "Photo upload failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    if (progress - 15 > mPhotoUploadProgress) {
                        //show the toast only when the new progress is 15 units higher than the old progress
                        Toast.makeText(mContext, "Photo upload progress : " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }

                    Log.d(TAG, "onProgress: upload progress : " + progress);
                }
            });

        } else if (photoType.equalsIgnoreCase(mContext.getString(R.string.new_profile_photo))) {
            //uploading a new profile photo
            Log.d(TAG, "uploadPhoto: uploading a new profile photo");
        }
    }
}
