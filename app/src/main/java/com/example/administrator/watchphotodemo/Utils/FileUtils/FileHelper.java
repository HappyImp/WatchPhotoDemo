package com.example.administrator.watchphotodemo.Utils.FileUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.example.administrator.watchphotodemo.BitmapUtils.PicassoIML;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/14
 */
public class FileHelper {
    String mSaveUrl;
    Bitmap bm;

    public FileHelper(String url, Bitmap bm) {
        mSaveUrl = url;
        this.bm=bm;
    }

    public String saveFile()  {
        return FileUtils.bitmapSave(mSaveUrl,bm);
    }
}
