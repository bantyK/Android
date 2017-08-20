package example.banty.com.instagramclone.activities.share;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import example.banty.com.instagramclone.R;

/**
 * Created by banty on 15/8/17.
 */

public class GallaryFragment extends Fragment{

    private static final String TAG = "GallaryFragment";

    // UI Widgets
    private GridView gridview;
    private ImageView gallaryImage;
    private ProgressBar mProgressBar;
    private Spinner directorySpinner;
    private ImageView closeImage;
    private TextView nextText;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_galary, container, false);
        initUIElements(view);
        return view;
    }

    private void initUIElements(View view) {
        gridview = (GridView) view.findViewById(R.id.grid_view);
        gallaryImage = (ImageView) view.findViewById(R.id.gallary_image_view);
        mProgressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        directorySpinner = (Spinner) view.findViewById(R.id.spinner_directory);
        closeImage = (ImageView) view.findViewById(R.id.iv_close_share);
        nextText = (TextView) view.findViewById(R.id.tv_next);

        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        
        nextText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to final share screen");
            }
        });

        
    }
}
