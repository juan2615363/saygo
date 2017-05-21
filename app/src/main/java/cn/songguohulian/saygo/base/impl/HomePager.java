package cn.songguohulian.saygo.base.impl;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.songguohulian.saygo.R;
import cn.songguohulian.saygo.adapter.HomePagerAdapter;
import cn.songguohulian.saygo.base.BasePager;


/**
 * @author Ziv
 * @data 2017/5/19
 * @time 11:16
 * <p>
 * 主页
 */
public class HomePager extends BasePager {

    private RecyclerView recyclerView;
    private HomePagerAdapter adapter;


    public HomePager(Context context) {
        super(context);
    }


    @Override
    public View initView() {
        mRootView = View.inflate(context, R.layout.home_pager, null);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.xrecyclerview);
        return mRootView;
    }

    @Override
    public void initData() {
        System.out.println("首页初始化啦...");

        //设置适配器
        adapter = new HomePagerAdapter(context);
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(context, 1);

        //设置布局管理者
        recyclerView.setLayoutManager(manager);
    }

}
