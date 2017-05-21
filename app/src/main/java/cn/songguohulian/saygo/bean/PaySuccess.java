package cn.songguohulian.saygo.bean;

import cn.bmob.v3.BmobObject;

/**
 *
 * @author Ziv
 * @data 2017/5/21
 * @time 17:10
 *
 * 支付成功bean
 */


public class PaySuccess extends BmobObject{

    private String username; // 用户名
    private String ordernumber; // 订单号

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }
}
