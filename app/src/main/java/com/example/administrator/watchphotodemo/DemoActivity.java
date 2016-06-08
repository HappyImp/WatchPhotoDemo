package com.example.administrator.watchphotodemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.watchphotodemo.view.PhotoFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DemoActivity extends AppCompatActivity {

    static String PHOTO_LIST = "PHOTO_LIST";
    @Bind(R.id.vp_photo_pager)
    ViewPager vpPhotoPager;
    private int mCurrentTab = 1;

    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    ArrayList<String> mdatas = new ArrayList<>();
    private PagerAdapter mAdapter;

    public static void watchPhoto(Activity activity, String url) {
        ArrayList<String> datas = new ArrayList<>();
        datas.add(url);
        DemoActivity.watchPhoto(activity, datas);
    }

    public static void watchPhoto(Activity activity, ArrayList<String> photoList) {
        Intent intent = new Intent(activity, DemoActivity.class);
        intent.putExtra(PHOTO_LIST, photoList);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        mdatas.add("http://pkicdn.image.alimmdn.com/old/newuploads/8bdaaa6d4e1fbc5e03385ef454f9577e.jpg");
        mdatas.add("http://pkicdn.image.alimmdn.com/old/newuploads/0b79e02a47d57002de8200b31f2657cc.jpg");
        mdatas.add("http://pkicdn.image.alimmdn.com/old/newuploads/2579cfef9f9fa1e22128243d92ff001a.jpg");
        mdatas.add("http://pkicdn.image.alimmdn.com/old/newuploads/7efc96106624cc602f3336d7f5c6f086.JPG");
        for (int i = 0; i < mdatas.size(); i++) {
            mFragments.add(PhotoFragment.newInstance(mdatas.get(i)));
        }
        mAdapter=new PagerAdapter(getSupportFragmentManager(),mFragments);
        vpPhotoPager.setAdapter(mAdapter);
    }



    private class PagerAdapter extends FragmentPagerAdapter
    {
        private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }
        public PagerAdapter(FragmentManager fragmentManager,
                              ArrayList<Fragment> fragments){
            super(fragmentManager);this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}

