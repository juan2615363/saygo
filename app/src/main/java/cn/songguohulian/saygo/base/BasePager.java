package cn.songguohulian.saygo.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.songguohulian.saygo.R;

/**
 * 五个标签页的基类
 * 
 * @author Kevin
 * @date 2015-10-18
 */
public class BasePager {

	public Context context;

	public FrameLayout flContent;// 空的帧布局对象, 要动态添加布局


	public View mRootView;// 当前页面的布局对象

	public BasePager(Context contexts) {
		context = contexts;
		mRootView = initView();
	}

	// 初始化布局
	public View initView() {
		View view = View.inflate(context, R.layout.base_pager, null);
		flContent = (FrameLayout) view.findViewById(R.id.fl_content);
		return view;
	}


	// 初始化数据
	public void initData() {

	}
}
