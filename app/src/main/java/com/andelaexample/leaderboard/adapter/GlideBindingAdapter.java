package com.andelaexample.leaderboard.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.andelaexample.leaderboard.R;
import com.bumptech.glide.Glide;

public class GlideBindingAdapter {

    @BindingAdapter("setImageFromUrl")
    public static void setImageFromUrl(ImageView imageView, String url) {

        Context context = imageView.getContext();

        Glide.with(context)
                .load(url)
                .centerInside()
                .into(imageView);
    }


}
