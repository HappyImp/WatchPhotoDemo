package com.example.administrator.watchphotodemo.view;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.watchphotodemo.R;
import com.example.administrator.watchphotodemo.constant.Constant;
import com.example.administrator.watchphotodemo.databinding.ActivityDemoBinding;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.f2prateek.dart.Nullable;

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

public class PhotoBrowserActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, DelectSelectDialog.OnSelectLayout
,PhotoFragment.SaveImpl{

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

    private boolean mNumIsShow = false;
    private boolean mIsSelected = false;
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    private PagerAdapter mAdapter;

    private int mPosition = 0;


    private String mSelectNum = "0";

    private DeletePop mDeletePop;

    private ArrayList<String> mTempData;
    private DelectSelectDialog mDialog;

    ActivityDemoBinding bindding;


    public static void watchPhoto(Activity activity, String url, String actionKey) {
        ArrayList<String> datas = new ArrayList<>();
        datas.add(url);
        PhotoBrowserActivity.watchPhoto(activity, datas, actionKey);
    }

    public static void watchPhoto(Activity activity, String url, String actionKey, String filePath) {
        ArrayList<String> datas = new ArrayList<>();
        datas.add(url);
        PhotoBrowserActivity.watchPhoto(activity, datas, actionKey, filePath);
    }

    public static void watchPhoto(Activity activity, ArrayList<String> photoList, String actionKey, String filePath) {
        Intent intent = new Intent(activity, PhotoBrowserActivity.class);
        intent.putExtra(PHOTO_LIST, photoList);
        intent.putExtra(Constant.PhotoBroAction.ACTION_KEY, actionKey);
        intent.putExtra("fileurl", filePath);
        activity.startActivityForResult(intent, Constant.REQUEST_CODE);
    }

    public static void watchPhoto(Activity activity, ArrayList<String> photoList, String actionKey) {
        Intent intent = new Intent(activity, PhotoBrowserActivity.class);
        intent.putExtra(PHOTO_LIST, photoList);
        intent.putExtra(Constant.PhotoBroAction.ACTION_KEY, actionKey);
        activity.startActivityForResult(intent, Constant.REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_demo);
        bindding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
        initGetIntent();
        ButterKnife.bind(this);
        bindData();
        initView();
        mDeletePop = new DeletePop(this, getWindow().getDecorView());
        mDialog = DelectSelectDialog.newInstance(getString(R.string.make_sure_delete), getString(R.string.delete), getString(R.string.cancel));
        mDialog.setOnSelectLayout(this);
        bindding.setSelectnum(mSelectNum);
    }


    private void initGetIntent() {
//        action = (String) getIntent().getExtras().get("actionkey");
//        mdatas=getIntent().getStringArrayListExtra(PHOTO_LIST);
        Dart.inject(this);
        if(mSaveUrl==null||mSaveUrl.equals("")||mSaveUrl=="")
        {
            mSaveUrl=Constant.DEFAULT_FILE_PATH;
        }
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
            PhotoFragment fragment=PhotoFragment.newInstance(mdatas.get(i),mSaveUrl);
            fragment.setImp(this);
            mFragments.add(fragment);
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
                switch (actionKey)
                {
                    case Constant.RESULT_CODE_SAVE:
                        Intent ii=new Intent();
                        ii.putStringArrayListExtra(Constant.CALLBACK_DATA_CODE,mTempData);
                        this.setResult(Constant.CALLBACK_CODE_SAVE, ii);
                }
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
//                        intent.putExtra(Constant.CALLBACK_DATA_CODE, mdatas.get(mPosition));
//                        this.setResult(Constant.CALLBACK_CODE_SAVE, intent);
                        PhotoFragment photoFragment = (PhotoFragment) mFragments.get(mPosition);
                        photoFragment.saveBitmap();
//                        this.finish();
                        break;
                    case Constant.RESULT_CODE_SELETE:
                        if (mIsSelected) {
                            btnTitleRight.setBackgroundResource(R.mipmap.btn_correct);
                            mTempData.remove(mdatas.get(mPosition));
                            mIsSelected = !mIsSelected;
                        } else {
                            btnTitleRight.setBackgroundResource(R.mipmap.btn_correct_normal);
                            mTempData.add(mdatas.get(mPosition));
                            mIsSelected = !mIsSelected;
                        }
                        Log.d("DemoActivity", "mSelectNum:" + mSelectNum);
                        mSelectNum = mTempData.size() + "";
                        break;
                }
                bindding.setSelectnum(mSelectNum);
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

        mTempData.add(mdatas.get(mPosition));
        Intent intent = new Intent();
        intent.putExtra(Constant.CALLBACK_DATA_CODE, mTempData);
        this.setResult(Constant.CALLBACK_CODE_DELECT, intent);
        mAdapter.remove(mPosition);
        mDeletePop.showPopupWindow();
    }

    @Override
    public void onClickThird(String tag) {
        mDialog.dismiss();
    }

    @Override
    public void saveFileSuccess(String filePath) {
        mTempData.add(filePath);
    }


    /**
     * viewPager适配器 内部类
     */
    private class PagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        private FragmentManager fragmentManager;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public PagerAdapter(FragmentManager fragmentManager,
                            ArrayList<Fragment> fragments) {
            super(fragmentManager);
            this.fragments = fragments;
            this.fragmentManager = fragmentManager;
            notifyDataSetChanged();
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (getCount() > 0) {
                return POSITION_NONE;
            }
            PhotoBrowserActivity.this.finish();
            return super.getItemPosition(object);
        }


        public void remove(int pos) {
            fragments.remove(pos);
            notifyDataSetChanged();
        }
    }


    @Override
    protected void onDestroy() {
        // TODO: 2016/6/8 如果发生oom 在此添加清除缓存方法。
        mDeletePop.dismiss();
        super.onDestroy();
    }

}

