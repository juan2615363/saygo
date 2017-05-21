package cn.songguohulian.saygo.bean;

import cn.bmob.v3.BmobObject;

/**
 *
 * @author Ziv
 * @data 2017/5/20
 * @time 21:55
 *
 * 单人票bean
 */
public class Single extends BmobObject{
    // 线路
    private String line;
    private String price;
    private String ticket; // 余票
    private String time; // 时间

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
