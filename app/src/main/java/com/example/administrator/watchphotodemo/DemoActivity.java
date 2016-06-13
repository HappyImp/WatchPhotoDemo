package com.example.administrator.watchphotodemo;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.watchphotodemo.constant.Constant;
import com.example.administrator.watchphotodemo.view.DelectSelectDialog;
import com.example.administrator.watchphotodemo.view.DeletePop;
import com.example.administrator.watchphotodemo.view.PhotoFragment;
import com.example.administrator.watchphotodemo.view.ThrowViewpager;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.f2prateek.dart.Nullable;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 两个静态方法，通过传activity,url或者url容器,以及一个action码
 * 在activity的回调中使用
 * {
 * Constant.CALLBACK_CODE_SELETE
 * Constant.CALLBACK_CODE_DELECT
 * Constant.CALLBACK_CODE_SAVE    获得回调的url容器
 * }
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/8
 */

public class DemoActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, DelectSelectDialog.OnSelectLayout {

    //获取列表码与请求码
    static final String PHOTO_LIST = "PHOTO_LIST";

    @Bind(R.id.vp_photo_pager)
    ThrowViewpager vpPhotoPager;
    @Bind(R.id.btn_title_back)
    TextView btnTitleBack;
    @Bind(R.id.tv_title_num)
    TextView tvTitleNum;
    @Bind(R.id.btn_title_right)
    ImageView btnTitleRight;
    @InjectExtra(Constant.PhotoBroAction.ACTION_KEY)
    String actionKey;
    @InjectExtra(PHOTO_LIST)
    ArrayList<String> mdatas;
    @Nullable
    @InjectExtra("fileurl")
    String mSaveUrl = "";
    @Bind(R.id.btn_select_complete)
    TextView btnSelectComplete;
    @Bind(R.id.ic_bottom_watch)
    RelativeLayout icBottomWatch;
    @Bind(R.id.tv_select_num)
    TextView tvSelectNum;

    private boolean mNumIsShow = false;
    private boolean mIsSelected = false;
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    private PagerAdapter mAdapter;

    private int mPosition = 0;
    private int mSelectNum = 0;

    private DeletePop mDeletePop;

    private ArrayList<String> mTempData;

    private DelectSelectDialog mDialog;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    public static void watchPhoto(Activity activity, String url, String actionKey) {
        ArrayList<String> datas = new ArrayList<>();
        datas.add(url);
        DemoActivity.watchPhoto(activity, datas, actionKey);
    }

    public static void watchPhoto(Activity activity, String url, String actionKey, String filePath) {
        ArrayList<String> datas = new ArrayList<>();
        datas.add(url);
        DemoActivity.watchPhoto(activity, datas, actionKey, filePath);
    }

    public static void watchPhoto(Activity activity, ArrayList<String> photoList, String actionKey, String filePath) {
        Intent intent = new Intent(activity, DemoActivity.class);
        intent.putExtra(PHOTO_LIST, photoList);
        intent.putExtra(Constant.PhotoBroAction.ACTION_KEY, actionKey);
        intent.putExtra("fileurl", filePath);
        activity.startActivityForResult(intent, Constant.REQUEST_CODE);
    }

    public static void watchPhoto(Activity activity, ArrayList<String> photoList, String actionKey) {
        Intent intent = new Intent(activity, DemoActivity.class);
        intent.putExtra(PHOTO_LIST, photoList);
        intent.putExtra(Constant.PhotoBroAction.ACTION_KEY, actionKey);
        activity.startActivityForResult(intent, Constant.REQUEST_CODE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        DemoActivityBindding bingding = DataBindingUtil.setContentView(this, R.layout.include_bottom_watch);

        initGetIntent();
        ButterKnife.bind(this);
        bindData();
        initView();
        mDeletePop = new DeletePop(this, getWindow().getDecorView());
        mDialog = DelectSelectDialog.newInstance(getString(R.string.make_sure_delete), getString(R.string.delete), getString(R.string.cancel));
        mDialog.setOnSelectLayout(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void initGetIntent() {
//        action = (String) getIntent().getExtras().get("actionkey");
//        mdatas=getIntent().getStringArrayListExtra(PHOTO_LIST);
        Dart.inject(this);
    }

    private void bindData() {

    }


    private void initView() {
        mTempData = new ArrayList<>();
        //通过attion判断选择界面
        switch (actionKey) {
            case Constant.RESULT_CODE_DELETE:
                btnTitleRight.setBackgroundResource(R.mipmap.btn_delete);
                break;
            case Constant.RESULT_CODE_SAVE:
                mNumIsShow = true;
                btnTitleRight.setBackgroundResource(R.mipmap.btn_save);
                break;
            case Constant.RESULT_CODE_SELETE:
                mNumIsShow = true;
                btnTitleRight.setBackgroundResource(R.mipmap.btn_correct);
                icBottomWatch.setVisibility(View.VISIBLE);
                break;
        }
        //通过data.size来创建多个fragment
        for (int i = 0; i < mdatas.size(); i++) {
            mFragments.add(PhotoFragment.newInstance(mdatas.get(i)));
        }
        mAdapter = new PagerAdapter(getSupportFragmentManager(), mFragments);
        vpPhotoPager.setAdapter(mAdapter);
        vpPhotoPager.setOnPageChangeListener(this);

    }


    @OnClick({R.id.btn_title_back, R.id.tv_title_num, R.id.btn_title_right, R.id.btn_select_complete})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_title_back:
                this.finish();
                break;
            case R.id.btn_select_complete:
                this.setResult(Constant.CALLBACK_CODE_SELETE, intent);
                this.finish();
                break;
            case R.id.tv_title_num:
                // TODO: 2016/6/9 预留中间点击事件
                break;
            case R.id.btn_title_right:
                switch (actionKey) {
                    case Constant.RESULT_CODE_DELETE:
                        mDialog.show(getSupportFragmentManager(), "");
//                        this.finish();
                        break;
                    case Constant.RESULT_CODE_SAVE:
                        intent.putExtra(Constant.CALLBACK_DATA_CODE, mdatas.get(mPosition));
                        this.setResult(Constant.CALLBACK_CODE_SAVE, intent);
                        this.finish();
                        break;
                    case Constant.RESULT_CODE_SELETE:
                        if (mIsSelected) {
                            mSelectNum--;
                            btnTitleRight.setBackgroundResource(R.mipmap.btn_correct);
                            mTempData.remove(mdatas.get(mPosition));
                            mIsSelected = !mIsSelected;
                        } else {
                            mSelectNum++;
                            btnTitleRight.setBackgroundResource(R.mipmap.btn_correct_normal);
                            mTempData.add(mdatas.get(mPosition));
                            mIsSelected = !mIsSelected;
                        }
                        break;
                }
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mNumIsShow) {
            tvTitleNum.setText(getString(R.string.title_num, position + 1, mdatas.size()));
        }
        if (actionKey.equals(Constant.RESULT_CODE_SELETE)) {
            if (mTempData.contains(mdatas.get(position))) {
                btnTitleRight.setBackgroundResource(R.mipmap.btn_correct_normal);
                mIsSelected = true;
            } else {
                btnTitleRight.setBackgroundResource(R.mipmap.btn_correct);
                mIsSelected = false;
            }
        }
        mPosition = position;

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClickFirst(String str1, String tag) {

    }

    @Override
    public void onClickSecond(String str2, String tag) {
        Intent intent = new Intent();
        intent.putExtra(Constant.CALLBACK_DATA_CODE, mdatas.get(mPosition));
        this.setResult(Constant.CALLBACK_CODE_DELECT, intent);
        mDeletePop.showPopupWindow();
    }

    @Override
    public void onClickThird(String tag) {
        mDialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Demo Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.administrator.watchphotodemo/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Demo Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.administrator.watchphotodemo/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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
            return fragments.get(position);
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

