package cn.songguohulian.saygo.bean;

import java.io.Serializable;

/**
 * @author Ziv
 * @data 2017/5/20
 * @time 22:18
 * <p>
 * 车票详情根据类
 */

public class TicketBean implements Serializable {


    private String line; // 路线
    private String price; // 价格
    private String surplus_ticket; // 余票
    private String date; // 出行日期
    private String place_of_departure; // 出发地
    private String destination; // 目的地
    private String go_off; // 出发时间



    public TicketBean() {

    }

    public TicketBean(String line, String price, String surplus_ticket, String date, String place_of_departure, String destination, String go_off) {
        this.line = line;
        this.price = price;
        this.surplus_ticket = surplus_ticket;
        this.date = date;
        this.place_of_departure = place_of_departure;
        this.destination = destination;
        this.go_off = go_off;
    }



    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getGo_off() {
        return go_off;
    }

    public void setGo_off(String go_off) {
        this.go_off = go_off;
    }

    public String getPlace_of_departure() {
        return place_of_departure;
    }

    public void setPlace_of_departure(String place_of_departure) {
        this.place_of_departure = place_of_departure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSurplus_ticket() {
        return surplus_ticket;
    }

    public void setSurplus_ticket(String surplus_ticket) {
        this.surplus_ticket = surplus_ticket;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
