package cn.songguohulian.saygo.bean;

import cn.bmob.v3.BmobUser;

/**
 *
 * @author Ziv
 * @data 2017/5/18
 * @time 16:19
 *
 * 用户bean
 */


public class SayUser extends BmobUser {

    private String phoneNumber;
    private String passWord;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SayUser) {
            SayUser u = (SayUser)o;

            return this.getObjectId().equals(u.getObjectId());
        }
        return super.equals(o);
    }
}
