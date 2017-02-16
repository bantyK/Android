package com.example.admin.movies;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class ImageLoader {
    public static View loadImageWithGlide(Context context, String thumbURL, ImageView imageView) {
        Glide.with(context)
                .load(thumbURL)
                .centerCrop()
                .crossFade()
                .into(imageView);

        return imageView;
    }
}
