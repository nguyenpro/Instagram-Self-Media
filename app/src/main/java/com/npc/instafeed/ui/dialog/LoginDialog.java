package com.npc.instafeed.ui.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.npc.instafeed.R;
import com.npc.instafeed.app.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by USER on 16/01/2017.
 */

public class LoginDialog extends DialogFragment {

    @BindView(R.id.wvLogin) WebView wvLogin;
    String url;

    LoginResultListener listener;

    public void setListener(LoginResultListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_login, container, false);
        ButterKnife.bind(this, view);
        return  view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = "https://api.instagram.com/oauth/authorize/?client_id="+ Constant.CLIENT_ID +"&redirect_uri="
                + Constant.REDRIECT_URI + "&response_type=token&scop=basic+public_content";
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = 700;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebSettings settings = wvLogin.getSettings();
        settings.setJavaScriptEnabled(true);
        wvLogin.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        wvLogin.setWebViewClient(new AuthWebViewClient());
        wvLogin.loadUrl(url);
    }

    public class AuthWebViewClient extends WebViewClient
    {

        public void onPageFinished(WebView view, String url) {

        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(LoginDialog.this.getActivity(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            if (url.startsWith(Constant.REDRIECT_URI))
            {
                System.out.println(url);
                String parts[] = url.split("=");
                String request_token = parts[1]; //This is your request token.
                Log.d("TAG", "shouldOverrideUrlLoading: "+request_token);
                listener.onResult(request_token);
                LoginDialog.this.dismiss();
                return true;
            }
            return false;
        }
    }

    public interface LoginResultListener{
        void onResult(String token);
    }
}
