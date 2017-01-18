package com.npc.instafeed.base;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.Window;

import org.parceler.Parcels;

import butterknife.ButterKnife;

/**
 * Created by USER on 16/01/2017.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new Explode());
            getWindow().setEnterTransition(new Explode());
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    protected void startActivityWithShareElement(Class<?> cls, @Nullable Object data, String robotName,
                                                 View androidRobotView, boolean finish){
        Intent intent = new Intent(this, cls);
        ActivityOptions options = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions
                    .makeSceneTransitionAnimation(this, androidRobotView, robotName);
            startActivity(intent, options.toBundle());
            if (finish){
                this.finish();
            }
        }else{
            startActivity(cls, data, finish);
        }
    }

    protected void startActivity(Class<?> cls, @Nullable Object data, boolean isFinish){
        Intent intent = new Intent(this, cls);
        if (data != null){
            intent.putExtra("data", Parcels.wrap(data));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            this.startActivity(intent);
        }
        if (isFinish){
            this.finish();
        }
    }

    protected void showDialog(String msg){
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
