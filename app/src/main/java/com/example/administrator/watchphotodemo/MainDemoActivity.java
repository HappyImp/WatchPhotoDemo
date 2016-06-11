package com.example.administrator.watchphotodemo;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.administrator.watchphotodemo.constant.Constant;
import com.example.administrator.watchphotodemo.view.BaseActivity;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.HensonNavigable;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/8
 */
public class MainDemoActivity extends BaseActivity {
    ArrayList<String> mdatas = new ArrayList<>();
    @Bind(R.id.btn_select)
    Button btnSelect;
    @Bind(R.id.btn_delete)
    Button btnDelete;
    @Bind(R.id.btn_save)
    Button btnSave;


    @Override
    protected void initGetIntent() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }


    @Override
    public void initData() {
        mdatas.add("http://pkicdn.image.alimmdn.com/old/newuploads/8bdaaa6d4e1fbc5e03385ef454f9577e.jpg");
        mdatas.add("http://pkicdn.image.alimmdn.com/old/newuploads/0b79e02a47d57002de8200b31f2657cc.jpg");
        mdatas.add("http://pkicdn.image.alimmdn.com/old/newuploads/2579cfef9f9fa1e22128243d92ff001a.jpg");
        mdatas.add("http://pkicdn.image.alimmdn.com/old/newuploads/7efc96106624cc602f3336d7f5c6f086.JPG");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String url;
        switch (resultCode) {
            case Constant.CALLBACK_CODE_SELETE:
                Log.d("MainDemoActivity", "callback_selete");
                break;
            case Constant.CALLBACK_CODE_DELECT:
                url= (String) data.getExtras().get(Constant.CALLBACK_DATA_CODE);
                Log.d("MainDemoActivity", url);
                Log.d("MainDemoActivity", "callback_delect");
                break;
            case Constant.CALLBACK_CODE_SAVE:
                url= (String) data.getExtras().get(Constant.CALLBACK_DATA_CODE);
                Log.d("MainDemoActivity", url);
                Log.d("MainDemoActivity", "callback_save");
                break;
        }
    }

    @Override
    public void refreshData() {

    }

    @OnClick({R.id.btn_select, R.id.btn_delete, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select:
                DemoActivity.watchPhoto(this, mdatas, Constant.RESULT_CODE_SELETE);
                break;
            case R.id.btn_delete:
                DemoActivity.watchPhoto(this, mdatas, Constant.RESULT_CODE_DELETE);
                break;
            case R.id.btn_save:
                DemoActivity.watchPhoto(this, mdatas, Constant.RESULT_CODE_SAVE);
                break;
        }
    }

}
