package com.dgstore.model;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    private static UserInfo instance;
    private List<String> userInfoList;

    private UserInfo() {
        userInfoList = new ArrayList<>();
    }

    public static UserInfo getInstance() {
        if (instance == null) {
            synchronized (UserInfo.class) {
                if (instance == null) {
                    instance = new UserInfo();
                }
            }
        }
        return instance;
    }

    public List<String> getUserInfoList() {
        System.out.println("x: " + userInfoList);
        return userInfoList;

    }

    public void setUserInfoList(List<String> userInfoList) {
        this.userInfoList = userInfoList;
    }

}


