package cn.songguohulian.saygo.utils;

import android.content.Context;
import android.widget.Toast;

import c.b.BP;
import c.b.PListener;

/**
 * Created by Administrator on 2017/5/21.
 */

public class PayUtils {

    private Context context; // 上下文
    private String title; // 标题
    private String descript; // 商品描述
    private double money; // 价格
    private boolean aliOrWechat; // 支付方式
    private String orderNumber; // 订单号

    public PayUtils(Context context, boolean aliOrWechat, double money, String descript, String title) {
        this.context = context;
        this.aliOrWechat = aliOrWechat;
        this.money = money;
        this.descript = descript;
        this.title = title;
    }

    // 支付宝支付
    private void aliPay() {
        BP.pay(title, descript, money, aliOrWechat, new PListener() {
            @Override
            public void orderId(String s) {
                // 获取订单号
                orderNumber = s;
            }

            @Override
            public void succeed() {
                Toast.makeText(context, "支付成功!", Toast.LENGTH_SHORT).show();
                // 支付成功之后 将信息提交到服务器
            }

            @Override
            public void fail(int i, String s) {
                Toast.makeText(context, "支付失败!", Toast.LENGTH_SHORT).show();
                return;
            }

            @Override
            public void unknow() {
                Toast.makeText(context, "未知原因!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
