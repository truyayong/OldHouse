package com.truyayong.oldhouse.data;

import cn.bmob.v3.BmobUser;

/**
 * Created by alley_qiu on 2016/12/4.
 */

public class User extends BmobUser {
    /**
     * 用户名
     */
    String userName;
    /**
     * 性别
     */
    Boolean gender;
    /**
     * 用户签名
     */
    String userDescription;
    /**
     * 用户头像
     */
    String userHeadUrl;
    /**
     * 居住地
     */
    String location;

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
