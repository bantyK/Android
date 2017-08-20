package example.banty.com.instagramclone.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.utils.UniversalImageLoader;
import example.banty.com.instagramclone.views.SquareImageView;

public class GridImageAdapter extends ArrayAdapter {

    private Context context;
    private int layoutResource;
    private String append;
    private ArrayList<String> imageURLs;


    public GridImageAdapter(Context context, int resource, String append, ArrayList<String> urls) {
        super(context, resource);
        this.context = context;
        this.layoutResource = resource;
        this.append = append;
        this.imageURLs = urls;

        initImageLoader();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return imageURLs.get(position);
    }

    @Override
    public int getCount() {
        return imageURLs.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.profileImage = (SquareImageView) convertView.findViewById(R.id.grid_image_view);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.grid_image_progress_bar);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String imageURL = (String) getItem(position);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(this.append + imageURL, viewHolder.profileImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if (viewHolder.progressBar != null) {
                    viewHolder.progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if (viewHolder.progressBar != null) {
                    viewHolder.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (viewHolder.progressBar != null) {
                    viewHolder.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if (viewHolder.progressBar != null) {
                    viewHolder.progressBar.setVisibility(View.GONE);
                }
            }
        });

        return convertView;

    }

    private static class ViewHolder {
        SquareImageView profileImage;
        ProgressBar progressBar;

    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(context);
        ImageLoader.getInstance().init(universalImageLoader.getImageLoaderConfig());
    }
}
