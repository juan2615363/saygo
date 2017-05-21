package cn.songguohulian.saygo.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.mingle.widget.ShapeLoadingDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import c.b.BP;
import c.b.PListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.songguohulian.saygo.R;
import cn.songguohulian.saygo.bean.PayError;
import cn.songguohulian.saygo.bean.PaySuccess;
import cn.songguohulian.saygo.bean.SayUser;
import cn.songguohulian.saygo.bean.TicketBean;
import cn.songguohulian.saygo.utils.LogUtil;
import cn.songguohulian.saygo.utils.MyConstant;
import cn.songguohulian.saygo.utils.PayUtils;
import cn.songguohulian.saygo.utils.ToastUtil;


/**
 * @author Ziv
 * @data 2017/5/20
 * @time 22:23
 * <p>
 * 车票详情
 */
public class TicketActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.departureDate)
    public TextView departureDate; // 出发日期

    @Bind(R.id.departureTime)
    public TextView departureTime; // 出发时间

    @Bind(R.id.start_place)
    public TextView start_place; // 出发地

    @Bind(R.id.end_place)
    public TextView end_place; // 目的地

    @Bind(R.id.ticket_price)
    public TextView ticket_price; //票价

    @Bind(R.id.remaining_votes)
    public TextView remaining_votes; // 余票

    @Bind(R.id.buy)
    public Button buy; //立即购买

    @Bind(R.id.ticket_back)
    public LinearLayout ticket_back; // 返回上级

    @Bind(R.id.titile_names)
    public TextView titile_names; // 标题

    private PopupWindow popupWindow;
    private RelativeLayout rl_aliPay;
    private TextView alipay, wechat, cancle;

    private String getPrice;
    private String getdate;
    private String getDestination;
    private String get_go_off;
    private String getLine;
    private String get_place_of_departure;
    private String get_surplus_ticket;

    private String orderNumber; // 订单号
    private ShapeLoadingDialog shapeLoadingDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        // 绑定控件
        ButterKnife.bind(this);

        // 初始化数据
        initData();


        // 初始化popoupwindow
        initPopupWindow();

        // 初始化Bmob支付
        BP.init(MyConstant.BMOBAPPKEY);


        // 获取当前用户
        SayUser user = BmobUser.getCurrentUser(getApplicationContext(), SayUser.class);
        String username = user.getUsername();
        LogUtil.e("当前登录用户:" + username);


    }


    private void initData() {
        // 取数据
        TicketBean ticket = (TicketBean) getIntent().getSerializableExtra("ticket");
        if (ticket != null) {


            // 价格
            getPrice = ticket.getPrice();
            // 出行日期
            getdate = ticket.getDate();
            //目的地
            getDestination = ticket.getDestination();
            // 出发时间
            get_go_off = ticket.getGo_off();
            //线路
            getLine = ticket.getLine();
            // 出发地
            get_place_of_departure = ticket.getPlace_of_departure();
            // 剩余票数
            get_surplus_ticket = ticket.getSurplus_ticket();



            // 设置控件

            // 1.1 设置标题
            titile_names.setText(get_place_of_departure + " 至 " + getDestination);

            // 1.2 设置时间 截取年之后的字符串
            departureDate.setText(getdate);

            // 1.3 设置出发地
            start_place.setText(get_place_of_departure);

            // 1.4 设置目的地
            end_place.setText(getDestination);

            // 设置价格
            ticket_price.setText("¥" + getPrice);

            // 设置余票
            remaining_votes.setText("剩余" + get_surplus_ticket + "张");

            // 设置时间
            departureTime.setText(get_go_off);
        } else {
            // 进行提示
            ToastUtil.toastShort(getApplicationContext(), "获取车票详情失败!请联系说走官方人员,进行修复!");
            return;
        }
    }

    /**
     * 初始化popup
     */
    private void initPopupWindow() {
        View view = getLayoutInflater().inflate(R.layout.activity_pay, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        rl_aliPay = (RelativeLayout) view.findViewById(R.id.popup_zhifu_relative);
        alipay = (TextView) view.findViewById(R.id.popup_zhifubao);
        wechat = (TextView) view.findViewById(R.id.popup_weixin);
        cancle = (TextView) view.findViewById(R.id.popup_zhifucancel);

        rl_aliPay.setOnClickListener(this);
        alipay.setOnClickListener(this);
        wechat.setOnClickListener(this);
        cancle.setOnClickListener(this);
        buy.setOnClickListener(this);
        ticket_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ticket_back:
                onBackPressed();
                break;
            case R.id.buy:
                // 弹出选择支付方式
                popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_ticket, null), Gravity.BOTTOM, 0, 0);
                break;

            case R.id.popup_zhifubao:
                if (!checkPackageInstalled("com.eg.android.AlipayGphone",
                        "https://www.alipay.com")) { // 支付宝支付要求用户已经安装支付宝客户端
                    Toast.makeText(TicketActivity.this, "请安装支付宝客户端", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                // 调用支付宝接口
                aliPayStart();
                popupWindow.dismiss();
                break;

            case R.id.popup_weixin:
                ToastUtil.toastShort(getApplicationContext(), "微信支付接口暂时关闭,请选用其他支付方式!");
                popupWindow.dismiss();
                break;

            case R.id.popup_zhifucancel:
                popupWindow.dismiss();
                break;
            case R.id.popup_zhifu_relative:
                popupWindow.dismiss();
                break;
        }
    }

    /**
     * 检查某包名应用是否已经安装
     *
     * @param packageName 包名
     * @param browserUrl  如果没有应用市场，去官网下载
     * @return
     */
    private boolean checkPackageInstalled(String packageName, String browserUrl) {
        try {
            // 检查是否有支付宝客户端
            getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            // 没有安装支付宝，跳转到应用市场
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + packageName));
                startActivity(intent);
            } catch (Exception ee) {// 连应用市场都没有，用浏览器去支付宝官网下载
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(browserUrl));
                    startActivity(intent);
                } catch (Exception eee) {
                    Toast.makeText(TicketActivity.this,
                            "系统检测到您为安装支付宝或微信,快马加鞭去安装一个吧!O(∩_∩)O",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }

    // 支付宝支付
    public void aliPayStart() {
        BP.pay(get_place_of_departure + " 至 " + getDestination + "车票预购", get_place_of_departure + " 至 " + getDestination + "车票预购", (Double.valueOf(getPrice)), true, new PListener() {
            @Override
            public void orderId(String s) {
                LogUtil.e("订单号为:" + s);
                orderNumber = s;

            }

            @Override
            public void succeed() {
                Toast.makeText(getApplicationContext(), "支付成功!", Toast.LENGTH_SHORT).show();
                // 支付成功之后 将信息提交到服务器
                popupWindow.dismiss();

                // 获取当前用户
                SayUser user = BmobUser.getCurrentUser(getApplicationContext(), SayUser.class);
                String username = user.getUsername();
                LogUtil.e("当前登录用户:" + username);
                // 获取支付成功的订单号 上传到服务器

                PaySuccess paySuccess = new PaySuccess();
                paySuccess.setUsername(username);
                paySuccess.setOrdernumber(orderNumber);

                paySuccess.save(getApplicationContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        // 添加数据成功
                        LogUtil.e("添加成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        LogUtil.e("添加失败" + s);
                    }
                });


            }

            @Override
            public void fail(int i, String s) {
                Toast.makeText(getApplicationContext(), "支付失败!", Toast.LENGTH_SHORT).show();
                LogUtil.e("支付失败的订单号:" + orderNumber);
                popupWindow.dismiss();


                SayUser user = BmobUser.getCurrentUser(getApplicationContext(), SayUser.class);
                String username = user.getUsername();
                LogUtil.e("当前登录用户:" + username);

                // 支付失败之后同样的将当前用户和订单号上传至服务器
                PayError payError = new PayError();
                payError.setUsername(username);
                payError.setOrdernumber(orderNumber);
                payError.save(getApplicationContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        LogUtil.e("上传成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        LogUtil.e("上传失败" + s);
                    }
                });

                return;
            }

            @Override
            public void unknow() {
                Toast.makeText(getApplicationContext(), "未知原因!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
