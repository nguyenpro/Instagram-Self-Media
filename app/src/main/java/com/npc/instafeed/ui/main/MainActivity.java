package com.npc.instafeed.ui.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.npc.instafeed.R;
import com.npc.instafeed.base.BaseActivity;
import com.npc.instafeed.models.Media;
import com.npc.instafeed.models.MediaResponse;
import com.npc.instafeed.ui.start.LoginActivity;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.rvMedia) RecyclerView rvMedia;
    @BindView(R.id.swipeLayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    MainPresenter mainPresenter;
    MediaAdapter mediaAdapter;

    boolean isNext = false;
    String nextMaxId;

    boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpRecycleView();
        swipeRefreshLayout.setOnRefreshListener(this);
        mainPresenter = new MainPresenter();
        mainPresenter.initView(this);
        mainPresenter.loadMedia();
    }


    @Override
    public void onLoadMediaSuccess(MediaResponse mediaResponse, boolean isNext) {
        this.isNext = isNext;
        this.loading = true;
        List<Media> medias = mediaResponse.data;
        if (isNext){
            nextMaxId = mediaResponse.pagination.next_max_id;
            medias.add(null);
            mediaAdapter.addMediaList(medias);
        }else{
            mediaAdapter.addMediaList(mediaResponse.data);
        }

    }

    @Override
    public void onLoadFirstMedia(MediaResponse mediaResponse, boolean isNext) {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        this.isNext = isNext;
        this.loading = true;
        List<Media> medias = mediaResponse.data;
        if (isNext) {
            nextMaxId = mediaResponse.pagination.next_max_id;
            medias.add(null);
        }
        mediaAdapter.setMediaList(medias);
    }

    @Override
    public void onError(String msg) {
        showDialog(msg);
    }

    @Override
    public void goOut() {
        startActivity(LoginActivity.class, null, true);
    }

    private void setUpRecycleView(){
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvMedia.setLayoutManager(mLayoutManager);
        mediaAdapter = new MediaAdapter();
        rvMedia.setAdapter(mediaAdapter);
        rvMedia.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down{
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            if (isNext) {
                                mainPresenter.loadMediaNext(nextMaxId);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        mainPresenter.loadMedia();
    }
}
