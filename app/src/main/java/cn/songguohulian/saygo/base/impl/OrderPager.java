package cn.songguohulian.saygo.base.impl;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cn.songguohulian.saygo.R;
import cn.songguohulian.saygo.base.BasePager;
import cn.songguohulian.saygo.utils.ToastUtil;

import static android.R.id.list;

/**
 * @author Ziv
 * @data 2017/5/19
 * @time 11:17
 * <p>
 * 订单查询
 */
public class OrderPager extends BasePager {

    private ListView orderPager_listView;
    private String[] names = {"订单状态查询",  "出行状态查询", "支付状态查询"};
    private int[] namesPic = {R.drawable.order_order_state, R.drawable.order_go_state, R.drawable.order_pay_state};

    public OrderPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        mRootView = View.inflate(context, R.layout.order_pager, null);
        return mRootView;
    }

    @Override
    public void initData() {
        System.out.println("订单初始化啦...");

        orderPager_listView = (ListView) mRootView.findViewById(R.id.orderPager_listview);

        // 设置适配器
        orderPager_listView.setAdapter(new MyListViewAdapter());

        // ListView点击事件
        orderPager_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        ToastUtil.toastShort(context, "订单状态查询");
                        break;
                    case 1:
                        ToastUtil.toastShort(context, "出行状态查询");
                        break;
                    case 2:
                        ToastUtil.toastShort(context, "支付状态查询");
                        break;

                }
            }
        });
    }

    class MyListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int i) {
            return names[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            View view;
            ViewHolder holder;
            // 判断convertView的状态，来达到复用效果
            if (null == convertView) {
                // 如果convertView为空，则表示第一次显示该条目，需要创建一个view
                view = View.inflate(context, R.layout.item_order_lisetview, null);
                //新建一个viewholder对象
                holder = new ViewHolder();
                //将findviewbyID的结果赋值给holder对应的成员变量
                holder.order_item_tv = (TextView) view.findViewById(R.id.item_order_tv);
                holder.order_item_pic = (ImageView) view.findViewById(R.id.item_order_img);
                // 将holder与view进行绑定
                view.setTag(holder);
            } else {
                // 否则表示可以复用convertView
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            // 直接操作holder中的成员变量即可，不需要每次都findViewById
            holder.order_item_tv.setText(names[i]);
            holder.order_item_pic.setBackgroundResource(namesPic[i]);

            return view;
        }

    }

    //内部类
    class ViewHolder {
        ImageView order_item_pic;
        TextView order_item_tv;
    }
}

