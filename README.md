# WatchPhotoDemo

图片浏览组件demo


#图片浏览器
##关于配置

####使用第三方框架 dart2,picasso(图片加载),PhotoView  以下为gradle所需要的包

    compile 'com.github.chrisbanes:PhotoView:1.2.6'
    compile 'com.squareup.picasso:picasso:2.5.2'
    compile 'com.jakewharton:butterknife:7.0.0'
    compile 'com.f2prateek.dart:dart:2.0.0'
    provided 'com.f2prateek.dart:dart-processor:2.0.0'

####因为使用到dataBinding
需要在对应APP包中开启相关功能

    android {
    dataBinding {
        enabled = true }
    }


----------


##关于使用
####统一使用PhotoBrowserActivity.watchPhoto()方法
####内部有4种带参被复写的方法（调用）


    /**
    * Activit代表当前的activity（用于跳转）
    * url为需要浏览的图片地址()，也可代替传ArrayList<String> 代表需要浏览的图片集
    * ArrayList<String> 表示浏览图片的图片集
    * actionKey为需要执行操作的操作码：
    * {
	    * Constant.RESULT_CODE_SELETE //表示做选择操作 "RESULT_CODE_SELETE"
	    * Constant.RESULT_CODE_DELETE //表示做删除操作 "RESULT_CODE_DELETE"
	    * Constant.RESULT_CODE_SAVE //表示做保存操作 "RESULT_CODE_SAVE"
	    * 一点注意事项：如果做保存操作的时候没有传入保存的路径 ，会有一个默认的保存路径
	    * 该路径可以在Constant中进行更改 DEFAULT_FILE_PATH 修改该字段即可
    * }
    */
    public static void watchPhoto(Activity activity, String url, String actionKey);
    public static void watchPhoto(Activity activity, String url, String actionKey, String filePath);
    public static void watchPhoto(Activity activity, ArrayList<String> photoList, String actionKey, String filePath);
    public static void watchPhoto(Activity activity, ArrayList<String> photoList, String actionKey);


####关于回调使用(在activity)

    

    /**
    * 在回调中通过resultcode判断是否是通过PhotoBrowserActivity传回来的回调
    * {
	    *  Constant.CALLBACK_CODE_SELETE //表示通过选择操作回来的回调 000123456
	    *  Constant.CALLBACK_CODE_DELECT //表示通过删除操作的回调 100101010
	    *  Constant.CALLBACK_CODE_SAVE //表示通过保存回来的回调 25412644
    * }
    * 最后通过接收回调得到的ArrayList<String> 
    * Constant.CALLBACK_DATA_CODE //为请求回调得到数据的数据码
    *  urls=data.getExtras().getStringArrayList(Constant.CALLBACK_DATA_CODE);
    * /
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String url;
        ArrayList<String> urls=new ArrayList<>();
        switch (resultCode) {
            case Constant.CALLBACK_CODE_SELETE:
                Log.d("MainDemoActivity", "callback_selete");
                break;
            case Constant.CALLBACK_CODE_DELECT:
                urls=  data.getExtras().getStringArrayList(Constant.CALLBACK_DATA_CODE);
                for (int i=0;i<urls.size();i++)
                {
                    Log.d("MainDemoActivity", urls.get(i));
                }
                Log.d("MainDemoActivity", "callback_delect");
                break;
            case Constant.CALLBACK_CODE_SAVE:
                urls=data.getExtras().getStringArrayList(Constant.CALLBACK_DATA_CODE);
                for (int i=0;i<urls.size();i++)
                {
                    Log.d("MainDemoActivity", urls.get(i));
                }
                Log.d("MainDemoActivity", "callback_save");
                break;
        }
    }
    


----------
###此处贴上Constant常量的具体值，以便后期更改或者转移

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


----------
## 配置包说明

###Bean包，预留给后期需要更改一些界面时做数据绑定字段更改


----------


###BitmapUtils 里面封装一个简单使用Picasso的代理方法。 后期可用于更替图片加载框架。


----------


###Utils包 主要是一些工具类
####file工具包 用于对文件流进行处理


----------
