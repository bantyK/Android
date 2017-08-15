package example.banty.com.instagramclone.activities.share;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.banty.com.instagramclone.R;

/**
 * Created by banty on 15/8/17.
 */

public class GallaryFragment extends Fragment{

    private static final String TAG = "GallaryFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_galary, container, false);

        return view;
    }
}
