package com.jihao.imtest.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jiahao on 16/5/23.
 */
public class UserInfo implements Serializable {
    @SerializedName("_id")
    private String id;
    private String account;
    private long expired;
    private String mobile;
    private String sid;
    private String token;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_type")
    private int userType;
    @SerializedName("userid")
    private long userId;

    @SerializedName("accid")
    private String accId;
    @SerializedName("yunxin_token")
    private String yunxinToken;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getExpired() {
        return expired;
    }

    public void setExpired(long expired) {
        this.expired = expired;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public String getYunxinToken() {
        return yunxinToken;
    }

    public void setYunxinToken(String yunxinToken) {
        this.yunxinToken = yunxinToken;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", expired=" + expired +
                ", mobile='" + mobile + '\'' +
                ", sid='" + sid + '\'' +
                ", token='" + token + '\'' +
                ", userName='" + userName + '\'' +
                ", userType=" + userType +
                ", userId=" + userId +
                ", accId='" + accId + '\'' +
                ", yunxinToken='" + yunxinToken + '\'' +
                '}';
    }
}
