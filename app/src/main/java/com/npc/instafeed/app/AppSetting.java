package com.npc.instafeed.app;

import com.orhanobut.hawk.Hawk;

/**
 * Created by USER on 16/01/2017.
 */

public class AppSetting {
    private static final AppSetting instance = new AppSetting();

    private AppSetting(){}

    public static AppSetting getInstance(){
        return instance;
    }

    String token;

    public String getToken() {
        if (token == null){
            token = Hawk.get(Constant.TOKEN_KEY, null);
        }
        return token;
    }

    public void setToken(String token) {
        Hawk.put(Constant.TOKEN_KEY, token);
        this.token = token;
    }

    public void loadPref(){
        this.token = Hawk.get(Constant.TOKEN_KEY, null);
    }

    public void logout(){
        Hawk.deleteAll();
        loadPref();
    }
}
