package com.netease.bean;

import java.io.Serializable;

/**
 * 用户类：卖家1 买家0
 */
public class Person implements Serializable {
    private int id;
    private String username;
    private String password;
    private String nickName;
    private int usertype;//买家0，卖家1

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + ", nickName='" + nickName + '\'' + ", usertype=" + usertype + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }
}
