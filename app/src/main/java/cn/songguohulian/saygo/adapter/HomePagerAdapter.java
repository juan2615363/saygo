package cn.songguohulian.saygo.adapter;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingle.widget.ShapeLoadingDialog;
import com.zcw.togglebutton.ToggleButton;

import java.net.DatagramPacket;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.songguohulian.saygo.R;
import cn.songguohulian.saygo.activity.TicketActivity;
import cn.songguohulian.saygo.bean.Couples;
import cn.songguohulian.saygo.bean.Single;
import cn.songguohulian.saygo.bean.TicketBean;
import cn.songguohulian.saygo.service.Tools;
import cn.songguohulian.saygo.utils.EssentialUtils;
import cn.songguohulian.saygo.utils.LogUtil;
import cn.songguohulian.saygo.utils.ToastUtil;
import cn.songguohulian.saygo.view.CityPickerDialog;


/**
 * @author Ziv
 * @data 2017/5/19
 * @time 14:58
 * <p>
 * HomePagerAdapter
 * <p>
 * <p>
 * // 判断选择日期大于等于当前日期的话可以进行购票否则失效
 */

public class HomePagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;


    public static final int TOPIMAGE = 0; // 顶部图片

    public static final int QUERY = 1; // 查询票


    // 默认类型
    public int currentType = TOPIMAGE;
    private final LayoutInflater mLayoutInflater;


    public HomePagerAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TOPIMAGE) {
            return new TopImageViewHolder(mLayoutInflater.inflate(R.layout.homepager_topimage, null), mContext);
        } else if (viewType == QUERY) {
            return new QueryViewHolder(mLayoutInflater.inflate(R.layout.homepager_query, null), mContext);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TOPIMAGE) {
            TopImageViewHolder bannerViewHolder = (TopImageViewHolder) holder;
            bannerViewHolder.setData();
        } else if (getItemViewType(position) == QUERY) {
            QueryViewHolder queryViewHolder = (QueryViewHolder) holder;
            queryViewHolder.setData();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    /**
     * 根据位置得到类型-系统调用
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case TOPIMAGE:
                currentType = TOPIMAGE;
                break;
            case QUERY:
                currentType = QUERY;
                break;

        }
        return currentType;
    }


    private class TopImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView topHImage;

        public TopImageViewHolder(View itemView, Context mContext) {
            super(itemView);
            topHImage = (ImageView) itemView.findViewById(R.id.homepager_topimage);
        }

        public void setData() {

        }
    }

    private class QueryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private boolean flag;
        private TextView place_of_departure; // 出发地
        private TextView destination; //目的地
        private LinearLayout change_place; // 改变地址
        private LinearLayout choose_date; // 选择日期
        private DatePickerDialog birthdayDialog;
        private String birthdayStr = null; //选择的生日拼接字符串
        private TextView date, week; // 日期
        private ToggleButton toggleBtn;
        private Button query_ticket; // 查询
        private ShapeLoadingDialog loadingDialog;
        private String chooseTime; // 选择的时间
        private String nowTime; // 当前时间


        public QueryViewHolder(View itemView, Context mContext) {
            super(itemView);
            place_of_departure = (TextView) itemView.findViewById(R.id.place_of_departure);
            destination = (TextView) itemView.findViewById(R.id.destination);
            change_place = (LinearLayout) itemView.findViewById(R.id.change_place);
            choose_date = (LinearLayout) itemView.findViewById(R.id.choose_date);
            date = (TextView) itemView.findViewById(R.id.date);
            toggleBtn = (ToggleButton) itemView.findViewById(R.id.toggleBtn);
            query_ticket = (Button) itemView.findViewById(R.id.query_ticket);
            week = (TextView) itemView.findViewById(R.id.week);

            // 初始化本地时间 设置给控件
            initDataWeek();

            initBirthdayDataPicker();
        }

        private void initDataWeek() {
            Calendar now = Calendar.getInstance();
            String[] split = ((now.get(Calendar.YEAR)) + "年" + (now.get(Calendar.MONTH) + 1) + "月" + now.get(Calendar.DAY_OF_MONTH) + "日").split("年");
            date.setText(split[1]);

            nowTime = "" + (now.get(Calendar.YEAR)) + (now.get(Calendar.MONTH) + 1) + now.get(Calendar.DAY_OF_MONTH);
            // 默认选择时间为当前系统时间
            chooseTime = "" + (now.get(Calendar.YEAR)) + (now.get(Calendar.MONTH) + 1) + now.get(Calendar.DAY_OF_MONTH);

            LogUtil.e(nowTime);

            // 转换为date
            week.setText(EssentialUtils.getWeekOfDate(EssentialUtils.createdAtToDate((now.get(Calendar.YEAR)) + "年" + (now.get(Calendar.MONTH) + 1) + "月" + now.get(Calendar.DAY_OF_MONTH) + "日")));
        }

        public void setData() {
            place_of_departure.setOnClickListener(this);
            destination.setOnClickListener(this);
            change_place.setOnClickListener(this);
            choose_date.setOnClickListener(this);
            query_ticket.setOnClickListener(this);

            //
            toggleBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
                @Override
                public void onToggle(boolean on) {
                    if (on) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
            });

        }

        // 点击事件
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.place_of_departure:
                    showChooseCity1();
                    break;
                case R.id.destination:
                    showChooseCity2();
                    break;
                case R.id.change_place:
                    // 设置旋转动画
                    settingAnimation();
                    // 交换两个值
                    changgePlace();
                    break;
                case R.id.choose_date:
                    birthdayDialog.show();
                    break;
                case R.id.query_ticket:
                    // 判断选择日期大于等于当前日期的话可以进行购票否则失效
                    LogUtil.e("当前日期:" + nowTime);
                    LogUtil.e("选择日期:" + chooseTime);
                    // 2017521               >            2017522
                    if (Long.valueOf(chooseTime) >= Long.valueOf(nowTime)) {

                        LogUtil.e("可以进行购票");
                        loadingDialog = new ShapeLoadingDialog(mContext);
                        if (flag) {
                            // 情侣票 优惠
                            couplesTicket();
                        } else {
                            // 单身票查询
                            singleTicket();
                        }

                    } else {

                        LogUtil.e("已经发车了,不可购票!");
                        ToastUtil.toastShort(mContext, "当前日期已经失效,不可进行购票!");
                        return;

                    }

                    break;
            }
        }

        private void couplesTicket() {
            /**
             *
             * 查询的格式为:时间(不加年份) + 起点 + 终点
             * */
            loadingDialog.setLoadingText("正在查询车票列表..");
            loadingDialog.show();

            // 执行查询操作
            final String trip_date = date.getText().toString().trim(); // 出行日期
            final String trip_place = place_of_departure.getText().toString().trim(); // 出发地
            final String trip_destination = destination.getText().toString().trim(); //目的地

            // 执行查询操作
            BmobQuery<Couples> query = new BmobQuery<Couples>();
            query.addWhereEqualTo("line", trip_date + trip_place + trip_destination);
            query.findObjects(mContext, new FindListener<Couples>() {
                /**
                 * @param list
                 * 查询到数据了
                 */
                @Override
                public void onSuccess(List<Couples> list) {
                    loadingDialog.dismiss();
                    if (list.size() > 0) {
                        String line = list.get(0).getLine(); // 路线
                        String price = list.get(0).getPrice(); // 价格
                        String ticket = list.get(0).getTicket(); // 余票
                        String time = list.get(0).getTime(); // 时间

                        TicketBean ticketBean = new TicketBean(line, price, ticket, trip_date, trip_place, trip_destination, time);

                        Intent intent = new Intent(mContext, TicketActivity.class);
                        intent.putExtra("ticket", ticketBean);
                        mContext.startActivity(intent);

                    } else {
                        ToastUtil.toastShort(mContext, "没有查询到车票");
                        return;
                    }
                }

                /**
                 * @param i
                 * @param s
                 * 未查询到车票信息
                 */
                @Override
                public void onError(int i, String s) {
                    loadingDialog.dismiss();
                    ToastUtil.toastShort(mContext, "没有查询到车票");
                    return;
                }
            });
        }

        private void singleTicket() {
            /**
             *
             * 查询的格式为:时间 + 起点 + 终点
             * */
            loadingDialog.setLoadingText("正在查询车票列表..");
            loadingDialog.show();

            // 执行查询操作
            final String trip_date = date.getText().toString().trim(); // 出行日期
            final String trip_place = place_of_departure.getText().toString().trim(); // 出发地
            final String trip_destination = destination.getText().toString().trim(); //目的地

            BmobQuery<Single> query = new BmobQuery<Single>();
            query.addWhereEqualTo("line", trip_date + trip_place + trip_destination);
            query.findObjects(mContext, new FindListener<Single>() {
                /**
                 * @param list
                 * 查询到数据了
                 */
                @Override
                public void onSuccess(List<Single> list) {
                    loadingDialog.dismiss();
                    if (list.size() > 0) {

                        String line = list.get(0).getLine(); // 路线
                        String price = list.get(0).getPrice(); // 价格
                        String ticket = list.get(0).getTicket(); // 余票
                        String time = list.get(0).getTime(); // 出行时间

                        TicketBean ticketBean = new TicketBean(line, price, ticket, trip_date, trip_place, trip_destination, time);
                        Intent intent = new Intent(mContext, TicketActivity.class);
                        intent.putExtra("ticket", ticketBean);
                        mContext.startActivity(intent);

                    } else {
                        ToastUtil.toastShort(mContext, "没有查询到车票");
                        return;
                    }

                }

                /**
                 * @param i
                 * @param s
                 * 未查询到车票信息
                 */
                @Override
                public void onError(int i, String s) {
                    loadingDialog.dismiss();
                    ToastUtil.toastShort(mContext, "没有查询到车票");
                    return;
                }
            });
        }

        /**
         * 初始化日期popup
         */
        private void initBirthdayDataPicker() {

            Calendar c = Calendar.getInstance();

            birthdayDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    birthdayStr = year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日";

                    // 选择时间
                    chooseTime = "" + year + (monthOfYear + 1) + dayOfMonth;
                    LogUtil.e(chooseTime);


                    String[] split = birthdayStr.split("年");
                    date.setText(split[1]);

                    week.setText(EssentialUtils.getWeekOfDate(EssentialUtils.createdAtToDate(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日")));
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        }

        // 交换出行地
        private void changgePlace() {
            String chufadi = (String) place_of_departure.getText();
            String mudidi = (String) destination.getText();
            String temp = null;

            temp = chufadi;
            chufadi = mudidi;
            mudidi = temp;

            place_of_departure.setText(chufadi);
            destination.setText(mudidi);
        }

        // 出发地选择
        private void showChooseCity1() {
            if (!Tools.isFastDoubleClick()) {
                new CityPickerDialog(mContext,
                        new CityPickerDialog.OnCityPikerListener() {
                            @Override
                            public void onCityPicker(String province,
                                                     String city) {
                                place_of_departure.setText(city);
                            }
                        }).show();
            }
        }

        // 目的地选择
        private void showChooseCity2() {
            if (!Tools.isFastDoubleClick()) {
                new CityPickerDialog(mContext,
                        new CityPickerDialog.OnCityPikerListener() {
                            @Override
                            public void onCityPicker(String province,
                                                     String city) {
                                destination.setText(city);
                            }
                        }).show();
            }
        }

        private void settingAnimation() {
            AnimationSet animationSet = new AnimationSet(false);
            RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

            rotateAnimation.setFillAfter(true);
            rotateAnimation.setDuration(300);
            animationSet.addAnimation(rotateAnimation);
            // 播放动画
            change_place.startAnimation(animationSet);
        }
    }


}
