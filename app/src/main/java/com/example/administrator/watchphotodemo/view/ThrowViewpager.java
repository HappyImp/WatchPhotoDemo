package com.example.administrator.watchphotodemo.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/8
 */
public class ThrowViewpager extends ViewPager {
    public ThrowViewpager(Context context) {
        super(context);
    }

    public ThrowViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 此处抛出手势异常，以防崩溃
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
    }
}
