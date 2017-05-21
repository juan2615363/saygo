package cn.songguohulian.saygo.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/21.
 */

public class PayError extends BmobObject {
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
