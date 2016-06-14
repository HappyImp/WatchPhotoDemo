package com.example.administrator.watchphotodemo.BitmapUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.IOException;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/8
 */
public class PicassoIML {
    public static void loadImage(ImageView imageView, String url, Context context)
    {
        final PhotoViewAttacher attacher=new PhotoViewAttacher(imageView);
        Picasso.with(context).load(url).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        attacher.update();
                    }
                    @Override
                    public void onError() {
                    }
                });
    }


}
