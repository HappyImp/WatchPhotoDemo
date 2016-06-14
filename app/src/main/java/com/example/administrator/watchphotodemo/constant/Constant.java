package com.example.administrator.watchphotodemo.constant;

import android.os.Environment;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/6/8
 */
public  class Constant {
    public static final String RESULT_CODE_SELETE="RESULT_CODE_SELETE";
    public static final String RESULT_CODE_DELETE="RESULT_CODE_DELETE";
    public static final String RESULT_CODE_SAVE="RESULT_CODE_SAVE";
    public static final int CALLBACK_CODE_SELETE=000123456;
    public static final int CALLBACK_CODE_DELECT=100101010;
    public static final int CALLBACK_CODE_SAVE=25412644;
    public static final String CALLBACK_PHOTOLIST="CALLBACK_PHOTOLIST";
    public static final int REQUEST_CODE = 00001111;
    public static final String CALLBACK_DATA_CODE="CALLBACK_DATA_CODE";
    public static final String DEFAULT_FILE_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/zzz";//调用save方法后默认的存放路径

    public class PhotoBroAction{
        public static final String ACTION_KEY="ACTION_KEY";
        public static final String DELETE_ACTION="DELETE_ACTION";
        public static final String FILE_URL="FILE_URL";
    }
}
