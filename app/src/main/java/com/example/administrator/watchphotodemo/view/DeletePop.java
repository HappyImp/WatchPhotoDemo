package com.example.administrator.watchphotodemo.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.administrator.watchphotodemo.R;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/12
 */
public class DeletePop extends PopupWindow {

    private View mContentView;
    private View parent;
    public DeletePop(final Activity context, View parent)
    {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView=inflater.inflate(R.layout.pop_delete,null);
        this.setContentView(mContentView);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return false;
            }
        });
        this.parent=parent;
    }

    public void showPopupWindow()
    {
        if(!this.isShowing())
        {
            this.showAtLocation(parent, Gravity.CENTER,0,0);
        }
        else this.dismiss();
    }
}
