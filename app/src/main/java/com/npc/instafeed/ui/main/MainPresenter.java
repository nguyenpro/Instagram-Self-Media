package com.npc.instafeed.ui.main;

import com.npc.instafeed.api.ServerAPI;
import com.npc.instafeed.app.App;
import com.npc.instafeed.app.AppSetting;
import com.npc.instafeed.app.Constant;
import com.npc.instafeed.base.BasePresenter;
import com.npc.instafeed.models.MediaResponse;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by USER on 17/01/2017.
 */

public class MainPresenter extends BasePresenter<MainView> {

    private ServerAPI serverAPI;
    private AppSetting appSetting;

    public MainPresenter(){
        serverAPI = App.getServerAPI();
        appSetting = AppSetting.getInstance();
    }

    @Override
    protected void initView(MainView view) {
        this.view = view;
    }

    public void loadMedia(){
        String accessToken = appSetting.getToken();
        serverAPI.getSelfMedia(accessToken, Constant.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MediaResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null){
                            view.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(MediaResponse mediaResponse) {
                        if (view != null){
                            view.onLoadFirstMedia(mediaResponse, mediaResponse.pagination != null && mediaResponse.pagination.next_url != null && !mediaResponse.pagination.next_url.isEmpty());
                        }
                    }
                });
    }

    public void loadMediaNext(String nextMaxId){
        String accessToken = appSetting.getToken();
        serverAPI.getSelfMediaNext(accessToken, Constant.PAGE_SIZE, nextMaxId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MediaResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null){
                            view.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(MediaResponse mediaResponse) {
                        if (view != null) {
                            if (mediaResponse.meta.code == 400) {
                                appSetting.logout();
                                view.goOut();
                            } else {
                                view.onLoadMediaSuccess(mediaResponse, mediaResponse.pagination != null && mediaResponse.pagination.next_url != null && !mediaResponse.pagination.next_url.isEmpty());
                            }
                        }
                    }
                });
    }
}
