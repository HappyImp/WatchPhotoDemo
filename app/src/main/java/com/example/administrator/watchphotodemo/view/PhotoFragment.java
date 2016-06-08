package com.example.administrator.watchphotodemo.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.watchphotodemo.BitmapUtils.PicassoIML;
import com.example.administrator.watchphotodemo.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/8
 */
public class PhotoFragment extends BaseFragment {

    @Bind(R.id.pv_photo)
    PhotoView pvPhoto;

    String mDemoUrl="http://pkimg.image.alimmdn.com/upload/20160317/3642c693bd04d200d050a34de9392967.JPG";

    private String url=null;
    public static PhotoFragment newInstance(String photoData) {
        Bundle args = new Bundle();
        PhotoFragment fragment = new PhotoFragment();
        args.putString("photo",photoData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_photo;
    }

    @Override
    protected void initView(View contentView) {
        ButterKnife.bind(this, contentView);
        if(getArguments()!=null)
        {
            url=getArguments().getString("photo");
            PicassoIML.loadImage(pvPhoto,url,getContext());
        }


    }

    @Override
    protected void initData() {
    }
    @Override
    public void doAfterReConnectNewWork() {

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}