package com.example.administrator.watchphotodemo.view;


import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.administrator.watchphotodemo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DelectSelectDialog extends DialogFragment implements View.OnClickListener {


    private View parent;
    private TextView tv_boy;
    private TextView tv_girl;
    private TextView tv_cancel;

    private String str1;
    private String str2;
    private String str3;
    private String tag;

    public DelectSelectDialog() {
    }


    private static final String STR1 = "str1";
    private static final String STR2 = "str2";
    private static final String STR3 = "str3";

    public static DelectSelectDialog newInstance(String str1, String str2, String str3) {
        DelectSelectDialog newFragment = new DelectSelectDialog();
        Bundle bundle = new Bundle();
        bundle.putString(STR1, str1);
        bundle.putString(STR2, str2);
        bundle.putString(STR3, str3);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        this.str1 = args.getString(STR1);
        this.str2 = args.getString(STR2);
        this.str3 = args.getString(STR3);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        parent = inflater.inflate(R.layout.pop_make_sure, container, false);
        tag = getTag();
        initUI();
        return parent;
    }

    private void initUI() {
        tv_boy = (TextView) parent.findViewById(R.id.dialog_select_text);
        tv_girl = (TextView) parent.findViewById(R.id.dialog_select_sex_girl);
        tv_cancel = (TextView) parent.findViewById(R.id.dialog_select_cancel);
        tv_boy.setText(str1);
        tv_girl.setText(str2);
        tv_cancel.setText(str3);
        tv_boy.setOnClickListener(this);
        tv_girl.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        parent.findViewById(R.id.dialog_select_sex_other).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        //dialog 占满屏幕
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Window window = getDialog().getWindow();
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        if (l == null) {
            dismiss();
            return;
        }
        switch (v.getId()) {
            case R.id.dialog_select_text:
                l.onClickFirst(tv_boy.getText().toString(), tag);
                break;

            case R.id.dialog_select_sex_girl:
                l.onClickSecond(tv_girl.getText().toString(), tag);
                break;

            case R.id.dialog_select_cancel:
                l.onClickThird(tag);
                break;

            case R.id.dialog_select_sex_other:
                break;

            default:
                break;
        }
        dismiss();
    }

    private OnSelectLayout l;

    public interface OnSelectLayout {
        void onClickFirst(String str1, String tag);

        void onClickSecond(String str2, String tag);

        void onClickThird(String tag);
    }

    public void setOnSelectLayout(OnSelectLayout l) {
        this.l = l;
    }
}
