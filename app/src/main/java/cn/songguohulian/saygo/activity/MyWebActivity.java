package cn.songguohulian.saygo.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.songguohulian.saygo.R;
import cn.songguohulian.saygo.base.BaseActivity;

/**
 * Created by Administrator on 2017/5/18.
 */
public class MyWebActivity extends BaseActivity{

    @Bind(R.id.webView)
    public WebView mWebView;

    @Bind(R.id.progressBar)
    public ProgressBar progressBar;

    public String url;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_myweb);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {

        // 接受值
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("URL");
        //WebView加载web资源
        mWebView.loadUrl(url);

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        //启用支持javascript
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根

                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }

            }
        });
    }

    @Override
    protected void initEvent() {

    }
}
