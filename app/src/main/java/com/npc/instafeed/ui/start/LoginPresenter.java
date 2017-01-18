package com.npc.instafeed.ui.start;

import com.npc.instafeed.app.AppSetting;
import com.npc.instafeed.base.BasePresenter;

/**
 * Created by USER on 16/01/2017.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    AppSetting appSetting;

    public LoginPresenter() {
        appSetting = AppSetting.getInstance();
    }

    @Override
    protected void initView(LoginView view) {
        this.view = view;
    }

    public void saveToken(String token){
        appSetting.setToken(token);
        if (view != null){
            view.onTokenSaved();
        }
    }
}
