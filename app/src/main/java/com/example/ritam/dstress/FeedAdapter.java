package com.example.ritam.dstress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by shubham on 31/1/18.
 */

public class FeedAdapter extends ArrayAdapter<CustomPost> {
    List<CustomPost> posts;
    Context context;

    public FeedAdapter(Context context, List<CustomPost> posts) {
        super(context, 0, posts);
        this.posts = posts;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_row, parent, false);
        }
        CustomPost current = posts.get(position);
        TextView title = listItemView.findViewById(R.id.title_text);
        ImageView image = listItemView.findViewById(R.id.post_image);
        title.setText(current.getTitle());
//        URL url = null;
//        try {
//            url = new URL(current.getImageUrl());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        Bitmap bmp = null;
//        try {
//            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        image.setImageBitmap(bmp);
        Glide.with(context).load(current.getImageUrl())
               // .override(600,200)
               // .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
        return listItemView;
    }
}