package cn.songguohulian.saygo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import cn.songguohulian.saygo.R;
import cn.songguohulian.saygo.fragment.ContentFragment;

/**
 *
 * @author Ziv
 * @data 2017/5/17
 * @time 18:43
 *
 * 主界面
 */

public class MainActivity extends FragmentActivity {

    private static final String TAG_CONTENT = "TAG_CONTENT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_main_menu, new ContentFragment(),TAG_CONTENT);
        transaction.commit();
    }

    private void initData() {

    }


    private void initEvent() {

    }

    // 获取主页fragment对象
    public ContentFragment getContentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        ContentFragment fragment = (ContentFragment) fm
                .findFragmentByTag(TAG_CONTENT);// 根据标记找到对应的fragment
        return fragment;
    }
}
