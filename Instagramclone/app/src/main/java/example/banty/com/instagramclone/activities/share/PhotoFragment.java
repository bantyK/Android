package example.banty.com.instagramclone.activities.share;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import example.banty.com.instagramclone.R;

/**
 * Created by banty on 15/8/17.
 */

public class PhotoFragment extends Fragment{

    private static final String TAG = "PhotoFragment";
    private static final int GALLARY_FRAGMENT_NUMBER = 0;
    private static final int PHOTO_FRAGMENT_NUMBER = 1;
    private static final int CAMERA_REQUEST_CODE = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        final Button launchCameraButton = (Button) view.findViewById(R.id.button_launch_camera);

        launchCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: launching camera");
                launchCameraButtonClicked();
            }
        });

        return view;
    }

    private void launchCameraButtonClicked() {
        if(((ShareActivity) getActivity()).getCurrentItem() == PHOTO_FRAGMENT_NUMBER) {
            if(((ShareActivity) getActivity()).checkSinglePermission(Manifest.permission.CAMERA)) {
                Log.d(TAG, "launchCameraButtonClicked: starting camera");
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            } else {
                //if there is no permission, navigate to share screen to ask for permissions again
                Intent shareActivityIntent = new Intent(getActivity(), ShareActivity.class);
                //clear the activity stack
                shareActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(shareActivityIntent);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE) {
            Log.d(TAG, "onActivityResult: done taking a photo");
            //navigate to Share activity to publish photo

        }
    }
}
