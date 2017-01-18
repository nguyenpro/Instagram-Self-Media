package com.npc.instafeed.ui.main;

import android.support.v4.widget.SwipeRefreshLayout;

import com.npc.instafeed.models.MediaResponse;

/**
 * Created by USER on 17/01/2017.
 */

public interface MainView extends SwipeRefreshLayout.OnRefreshListener {
    void onLoadMediaSuccess(MediaResponse mediaResponse, boolean isNext);
    void onLoadFirstMedia(MediaResponse mediaResponse, boolean isNext);
    void onError(String msg);
    void goOut();
}
