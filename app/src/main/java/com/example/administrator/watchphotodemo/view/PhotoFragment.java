package com.example.administrator.watchphotodemo.view;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.watchphotodemo.BitmapUtils.PicassoIML;
import com.example.administrator.watchphotodemo.R;
import com.example.administrator.watchphotodemo.Utils.FileUtils.FileHelper;
import com.f2prateek.dart.HensonNavigable;

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
    private Bitmap bitmap;

    private String url=null;
    private String savePath=null;

    private SaveImpl imp;
    public void setImp(SaveImpl imp) {
        this.imp = imp;
    }
    public static PhotoFragment newInstance(String photoData) {
        return newInstance(photoData,"");
    }

    public static PhotoFragment newInstance(String photoData,String filePath) {
        Bundle args = new Bundle();
        PhotoFragment fragment = new PhotoFragment();
        args.putString("photo",photoData);
        args.putString("filepath",filePath);
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
            savePath=getArguments().getString("filepath");
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
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    public void saveBitmap()
    {
        bitmap = ((BitmapDrawable)pvPhoto.getDrawable()).getBitmap();
        FileHelper helper=new FileHelper(savePath,bitmap);
        String saveSuccessPath=helper.saveFile();
        if(!saveSuccessPath.equals(""))
        {
            Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
            imp.saveFileSuccess(saveSuccessPath);
        }
        else Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
    }

    public interface SaveImpl
    {
        void saveFileSuccess(String filePath);
    }

}
