package cn.songguohulian.saygo.base.impl;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import cn.songguohulian.saygo.base.BasePager;


/**
 *
 * @author Ziv
 * @data 2017/5/19
 * @time 11:17
 *
 * 我的
 */
public class MinePager extends BasePager {

	public MinePager(Context context) {
		super(context);
	}

	@Override
	public void initData() {
		System.out.println("我的初始化啦...");

		// 要给帧布局填充布局对象
		TextView view = new TextView(context);
		view.setText("我的");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);

		flContent.addView(view);



	}

}
