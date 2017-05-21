package cn.songguohulian.saygo.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import cn.songguohulian.saygo.R;
import cn.songguohulian.saygo.base.BaseFragment;
import cn.songguohulian.saygo.base.BasePager;
import cn.songguohulian.saygo.base.impl.FindPager;
import cn.songguohulian.saygo.base.impl.HomePager;
import cn.songguohulian.saygo.base.impl.MinePager;
import cn.songguohulian.saygo.base.impl.OrderPager;
import cn.songguohulian.saygo.view.NoScrollViewPager;

/**
 * @author Ziv
 * @data 2017/5/19
 * @time 8:59
 * <p>
 * 主界面的Fragment
 */

public class ContentFragment extends BaseFragment {

    private ViewPager mViewPager;
    private RadioGroup rgGroup;
    private RadioButton rb_home, rb_order, rb_my;

    private ArrayList<BasePager> mPagers;// 五个标签页的集合


    // 初始化布局
    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_content, null);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_content);
        rb_home = (RadioButton) view.findViewById(R.id.rb_home);
        rb_order = (RadioButton) view.findViewById(R.id.rb_order);
        rb_my = (RadioButton) view.findViewById(R.id.rb_my);
        rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
        return view;
    }

    @Override
    public void initData() {


        mPagers = new ArrayList<BasePager>();

        // 添加五个标签页
        mPagers.add(new HomePager(context));
        mPagers.add(new OrderPager(context));
        mPagers.add(new MinePager(context));

        mViewPager.setAdapter(new ContentAdapter());

        // 底栏标签切换监听
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        // 首页
                        // mViewPager.setCurrentItem(0);
                        mViewPager.setCurrentItem(0);// 参2:表示是否具有滑动动画
                        break;
                    case R.id.rb_order:
                        // 新闻中心
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_my:
                        // 智慧服务
                        mViewPager.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rb_home.setChecked(true);
                        break;
                    case 1:
                        rb_order.setChecked(true);
                        break;
                    case 2:
                        rb_my.setChecked(true);
                        break;

                }
                BasePager pager = mPagers.get(position);
                pager.initData();

            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 手动加载第一页数据
        mPagers.get(0).initData();
    }


    class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagers.get(position);
            View view = pager.mRootView;// 获取当前页面对象的布局

//              pager.initData();// 初始化数据, viewpager会默认加载下一个页面,
            // 为了节省流量和性能,不要在此处调用初始化数据的方法

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }


}