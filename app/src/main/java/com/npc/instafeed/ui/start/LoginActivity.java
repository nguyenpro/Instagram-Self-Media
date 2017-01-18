package com.npc.instafeed.ui.start;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.npc.instafeed.R;
import com.npc.instafeed.app.AppSetting;
import com.npc.instafeed.base.BaseActivity;
import com.npc.instafeed.ui.dialog.LoginDialog;
import com.npc.instafeed.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.ivIcon) ImageView ivIcon;

    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppSetting.getInstance().getToken() != null){
            startActivity(MainActivity.class, null, true);
        }
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter();
        loginPresenter.initView(this);
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClick(){
        LoginDialog dialog = new LoginDialog();
        dialog.setListener(this);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "Login Dialog");
    }

    @Override
    public void onResult(String token) {
        loginPresenter.saveToken(token);
    }

    @Override
    public void onTokenSaved() {
        startActivityWithShareElement(MainActivity.class, null, "robot", ivIcon, true);
    }
}
