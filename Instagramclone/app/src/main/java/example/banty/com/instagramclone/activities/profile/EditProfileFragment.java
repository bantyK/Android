package example.banty.com.instagramclone.activities.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.utils.UniversalImageLoader;


public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";

    private ImageView profilePhoto;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile,container,false);

        profilePhoto = (ImageView) view.findViewById(R.id.profile_photo);

        initImageLoader();
        setProfileImage();

        return view;
    }
    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getContext());
        ImageLoader.getInstance().init(universalImageLoader.getImageLoaderConfig());
    }
    private void setProfileImage() {
        String imageURL = "www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2015/01/podcast-event.jpg?itok=zsMimlTM";
        UniversalImageLoader.setImage(imageURL,profilePhoto,null,"https://");
    }
}
