package example.banty.com.instagramclone.activities.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.adapters.GridImageAdapter;
import example.banty.com.instagramclone.utils.FilePaths;
import example.banty.com.instagramclone.utils.FileUtils;

/**
 * Created by banty on 15/8/17.
 */

public class GallaryFragment extends Fragment {

    private static final String TAG = "GallaryFragment";

    // UI Widgets
    private GridView gridview;
    private ImageView gallaryImage;
    private ProgressBar mProgressBar;
    private Spinner directorySpinner;
    private ImageView closeImage;
    private TextView nextText;

    private static final int NUM_WIDTH_COLUMNS = 3;
    private final String fileAppend = "file:/";

    private String mSelectedString="";

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
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        directorySpinner = (Spinner) view.findViewById(R.id.spinner_directory);
        closeImage = (ImageView) view.findViewById(R.id.iv_close_share);
        nextText = (TextView) view.findViewById(R.id.tv_next);

        //set up the directory spinner
        setUpSpinner();

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
                Intent intent = new Intent(getActivity(), NextActivity.class);
                intent.putExtra(getString(R.string.selected_image), mSelectedString);
                startActivity(intent);
            }
        });
    }

    private ArrayList<String> getDirectoryList() {
        //check for directories /storage/emulated/0/pictures
        ArrayList<String> directories = new ArrayList<>();
        ArrayList<String> picturesDirectoryPath = new ArrayList<>();
        if(FileUtils.getDirectoryPath(FilePaths.PICTURES) != null) {
             picturesDirectoryPath = FileUtils.getDirectoryPath(FilePaths.PICTURES);
        }


        picturesDirectoryPath.add(FilePaths.CAMERA_DIR);
        picturesDirectoryPath.add(FilePaths.SCREENSHOTS);

        return picturesDirectoryPath;

    }

    private ArrayList<String> getDirectoryNames(ArrayList<String> picturesDirectoryPath) {
        ArrayList<String> directoryNames = new ArrayList<>();

        for(int i=0;i<picturesDirectoryPath.size();i++) {
            int index = picturesDirectoryPath.get(i).lastIndexOf("/");
            String directoryName = picturesDirectoryPath.get(i).substring(index+1);
            directoryNames.add(directoryName);
        }
        return directoryNames;
    }

    private void setUpSpinner() {
        final ArrayList<String> directoryList = getDirectoryList();
        final ArrayList<String> directoryNames = getDirectoryNames(directoryList);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                directoryNames);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        directorySpinner.setAdapter(spinnerAdapter);

        directorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String directoryChosen = directoryList.get(position);
                Log.d(TAG, "onItemSelected: item selected : " + directoryChosen);
                setUpGridView(directoryChosen);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setUpGridView(String selectedDirectory) {
        final ArrayList<String> imageURLs = FileUtils.getFilePaths(selectedDirectory);

        //set same width to all the images inside the grid view
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth / NUM_WIDTH_COLUMNS;
        gridview.setColumnWidth(imageWidth);

        GridImageAdapter gridImageAdapter = new GridImageAdapter(
                getContext(),R.layout.layout_grid_imageview,fileAppend,imageURLs);

        gridview.setAdapter(gridImageAdapter);

        //set the first image as default image when the fragment loads
        if(imageURLs.size() > 0) {
            String firstImageURL = imageURLs.get(0);
            setImageView(firstImageURL, fileAppend);
            mSelectedString = firstImageURL;
        }


        //change the image on the image view when any image is clicked from the gridview
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: selected an image");
                if(imageURLs.size() > 0) {
                    String selectedImageURL = imageURLs.get(position);
                    setImageView(selectedImageURL, fileAppend);
                    mSelectedString = selectedImageURL;
                }
            }
        });
    }

    private void setImageView(String imageURL, String append){
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(append + imageURL, gallaryImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
