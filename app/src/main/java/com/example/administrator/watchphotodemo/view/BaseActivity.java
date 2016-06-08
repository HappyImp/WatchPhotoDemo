package com.example.administrator.watchphotodemo.view;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;


import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements
        OnClickListener {

    protected boolean isondesk = true;
    protected ProgressDialog mLoadingDialog;
    private AnimationDrawable animationDrawable;
    protected BaseActivity _this = BaseActivity.this;

    @Override
    protected void onDestroy() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
            mLoadingDialog = null;
        }
        super.onDestroy();
    }

    public void changeDots(int listsize) {

    }

    public void changeDots0(int listsize) {

    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }//强制设置为竖屏

        //只取activity类名作为hashmap的key
//        String name = getClass().getName();
//        name = name.substring(name.lastIndexOf(".") + 1);
//        BaseApplication.addDestoryActivity(this, name);

        refreshContentView();
    }

    protected void refreshContentView() {
        int layoutId = getLayoutId();
        if (mLoadingDialog == null) {
            mLoadingDialog = new ProgressDialog(this);
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setCanceledOnTouchOutside(false);
        }
        setContentView(layoutId);
        ButterKnife.bind(this);
        initGetIntent();
        initView();
        initData();
    }

    protected void setLoadingDialogMessage(String msg) {
        mLoadingDialog.setMessage(msg);
    }

    /**
     * 初始化intent数据
     */
    protected abstract void initGetIntent();


    /**
     * 显示加载中对话框
     */
    public void showLoadingDialog() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (!mLoadingDialog.isShowing()) {
                    mLoadingDialog.show();
//					animationDrawable.start();
                }

            }
        });

    }

    /**
     * 隐藏加载对话框
     */
    public void dismissLoadingDialog() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
//					animationDrawable.stop();
                }
            }
        });

    }

    /**
     * 返回内容View布局
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化View
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();


    @Override
    public void onClick(View v) {
    }

    /**
     * 刷新数据（重新联网后调用）
     */
    public abstract void refreshData();

    public interface OnRightViewClickListener {
        public void OnClick(View view);
    }

    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }


}
