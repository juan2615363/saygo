package cn.songguohulian.saygo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import cn.bmob.v3.Bmob;
import cn.songguohulian.saygo.utils.MyConstant;

/**
 *
 * @author Ziv
 * @data 2017/5/17
 * @time 18:34
 *
 * Activity的基类
 */

public abstract class BaseActivity extends Activity {
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();

        transparentTitle();


        initView();

        // 初始化BmobSDK
        Bmob.initialize(this, MyConstant.BMOBAPPKEY);
        // 初始化Mob短信SDK
        cn.smssdk.SMSSDK.initSDK(mContext, MyConstant.MOBAPPKEY, MyConstant.MOBAPPSECRET);

        initData();

        initEvent();
    }

    private void transparentTitle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();
}
