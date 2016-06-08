package com.example.administrator.watchphotodemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.watchphotodemo.constant.Constant;
import com.example.administrator.watchphotodemo.view.PhotoFragment;
import com.example.administrator.watchphotodemo.view.ThrowViewpager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DemoActivity extends AppCompatActivity {

    //获取列表码与请求码
    static String PHOTO_LIST = "PHOTO_LIST";

    @Bind(R.id.vp_photo_pager)
    ThrowViewpager vpPhotoPager;
    @Bind(R.id.btn_title_back)
    TextView btnTitleBack;
    @Bind(R.id.tv_title_num)
    TextView tvTitleNum;
    @Bind(R.id.btn_title_right)
    TextView btnTitleRight;

    private String action;

    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    ArrayList<String> mdatas = new ArrayList<>();
    private PagerAdapter mAdapter;

    public static void watchPhoto(Activity activity, String url, String actionKey) {
        ArrayList<String> datas = new ArrayList<>();
        datas.add(url);
        DemoActivity.watchPhoto(activity, datas, actionKey);
    }

    public static void watchPhoto(Activity activity, ArrayList<String> photoList, String actionKey) {
        Intent intent = new Intent(activity, DemoActivity.class);
        intent.putExtra(PHOTO_LIST, photoList);
        intent.putExtra("actionkey", actionKey);
        activity.startActivityForResult(intent, Constant.REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        initGetIntent();
        ButterKnife.bind(this);
        initView();
    }

    private void initGetIntent() {
        action = (String) getIntent().getExtras().get("actionkey");
        mdatas=getIntent().getStringArrayListExtra(PHOTO_LIST);
    }


    private void initView() {
        //通过attion判断选择界面
        switch (action) {
            case Constant.RESULT_CODE_DELETE:
                Log.d("DemoActivity", "delete");
                break;
            case Constant.RESULT_CODE_SAVE:
                Log.d("DemoActivity", "save");
                break;
            case Constant.RESULT_CODE_SELETE:
                Log.d("DemoActivity", "select");
                break;
        }
        //通过data.size来创建多个fragment
        for (int i = 0; i < mdatas.size(); i++) {
            mFragments.add(PhotoFragment.newInstance(mdatas.get(i)));
        }
        mAdapter = new PagerAdapter(getSupportFragmentManager(), mFragments);
        vpPhotoPager.setAdapter(mAdapter);
    }



    @OnClick({R.id.btn_title_back, R.id.tv_title_num, R.id.btn_title_right})
    public void onClick(View view) {
        Intent intent=new Intent();
        intent.putStringArrayListExtra(Constant.CALLBACK_PHOTOLIST,mdatas);
        switch (view.getId()) {
            case R.id.btn_title_back:
                this.finish();
                break;
            case R.id.tv_title_num:
                // TODO: 2016/6/9 预留中间点击事件
                break;
            case R.id.btn_title_right:
                switch (action)
                {
                    case Constant.RESULT_CODE_DELETE:
                        this.setResult(Constant.CALLBACK_CODE_DELECT,intent);
                        this.finish();
                        break;
                    case Constant.RESULT_CODE_SAVE:
                        this.setResult(Constant.CALLBACK_CODE_SAVE,intent);
                        this.finish();
                        break;
                    case Constant.RESULT_CODE_SELETE:
                        this.setResult(Constant.CALLBACK_CODE_SELETE,intent);
                        this.finish();
                        break;
                }
                break;
        }
    }


    /**
     * viewPager适配器 内部类
     */
    private class PagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public PagerAdapter(FragmentManager fragmentManager,
                            ArrayList<Fragment> fragments) {
            super(fragmentManager);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("PagerAdapter", "position:" + position);
//            return position>=0?null:fragments.get(position);
            try {
                return fragments.get(position);
            } catch (IllegalArgumentException e) {
                return fragments.get(0);
            }
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO: 2016/6/8 如果发生oom 在此添加清除缓存方法。 
        super.onDestroy();
    }
}

