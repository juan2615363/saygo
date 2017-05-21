package cn.songguohulian.saygo.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import cn.songguohulian.saygo.R;
import cn.songguohulian.saygo.base.BaseActivity;
import cn.songguohulian.saygo.utils.LogUtil;
import cn.songguohulian.saygo.utils.MyConstant;
import cn.songguohulian.saygo.utils.SpTools;

/**
 *
 * @author Ziv
 * @data 2017/5/17
 * @time 17:52
 *
 * 启动界面 采用3.5秒跳转到登录界面
 */

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            // 如果当前activity已经退出，那么我就不处理handler中的消息
            if(isFinishing()) {
                return;
            }
            // 判断进入主页面还是登录页面
            goMainOrLogin();
        }
    };
    private void goMainOrLogin() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 判断用户是否登陆过
                if (SpTools.getBoolean(SplashActivity.this, MyConstant.ISLOGIN)) {
                    // 登录过
                    LogUtil.e("登录过");
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    LogUtil.e("没有登陆过");
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }).start();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void initData() {
        // 发送3.5秒延时消息
        handler.sendMessageDelayed(Message.obtain(), 1500);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 清除消息
        handler.removeCallbacksAndMessages(null);
    }
}
