package com.truyayong.oldhouse.data;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

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
    String userLocation;

    /**
     * 关注他的人
     */
    BmobRelation followedUser;

    /**
     * 关注他的人数
     */
    Integer followedUserCount;

    /**
     * 他关注的人
     */
    BmobRelation followUser;

    /**
     * 他关注的人数
     */
    Integer followUserCount;

    /**
     * 他关注的文章数
     */
    Integer followArticleCount;

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

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String location) {
        this.userLocation = location;
    }

    public BmobRelation getFollowUser() {
        return followUser;
    }

    public void setFollowUser(BmobRelation followUser) {
        this.followUser = followUser;
    }

    public Integer getFollowUserCount() {
        return followUserCount;
    }

    public void setFollowUserCount(Integer followUserCount) {
        this.followUserCount = followUserCount;
    }

    public BmobRelation getFollowedUser() {
        return followedUser;
    }

    public void setFollowedUser(BmobRelation followedUser) {
        this.followedUser = followedUser;
    }

    public Integer getFollowedUserCount() {
        return followedUserCount;
    }

    public void setFollowedUserCount(Integer followedUserCount) {
        this.followedUserCount = followedUserCount;
    }

    public Integer getFollowArticleCount() {
        return followArticleCount;
    }

    public void setFollowArticleCount(Integer followArticleCount) {
        this.followArticleCount = followArticleCount;
    }
}
