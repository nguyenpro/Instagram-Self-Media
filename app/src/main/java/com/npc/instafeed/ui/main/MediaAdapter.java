package com.npc.instafeed.ui.main;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.npc.instafeed.R;
import com.npc.instafeed.app.Constant;
import com.npc.instafeed.models.Media;
import com.npc.instafeed.ui.custom.FontTextView;
import com.npc.instafeed.utils.DateTimeUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.internal.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 17/01/2017.
 */

public class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE = 1;
    public static final int FOOTER_TYPE = 2;

    List<Media> mediaList;

    public MediaAdapter() {
        mediaList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEM_TYPE:
                return new MediaHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media, parent, false));
            default:
                return new FooterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MediaHolder) {
            MediaHolder mediaHolder = (MediaHolder) holder;
            mediaHolder.bind(mediaList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public void setMediaList(List<Media> mediaList){
        this.mediaList = mediaList;
        notifyDataSetChanged();
    }

    public void addMediaList(List<Media> mediaList){
        int size = this.mediaList.size();
        this.mediaList.remove(size -1);
        notifyItemRemoved(size - 1);
        size = this.mediaList.size();
        this.mediaList.addAll(mediaList);
        notifyItemRangeInserted(size, mediaList.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (mediaList.size() > 0 && mediaList.size() >= Constant.PAGE_SIZE &&
                position == mediaList.size() - 1 && mediaList.get(position) == null){
            return FOOTER_TYPE;
        }else{
            return ITEM_TYPE;
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder{

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    class MediaHolder extends RecyclerView.ViewHolder implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

        @BindView(R.id.ivUser) CircleImageView ivUser;
        @BindView(R.id.tvUser) FontTextView tvUser;
        @BindView(R.id.tvName) FontTextView tvName;
        @BindView(R.id.tvLikes) FontTextView tvLikes;
        @BindView(R.id.tvComment) FontTextView tvComment;
        @BindView(R.id.tvCaption) FontTextView tvCaption;
        @BindView(R.id.ivImage) ImageView ivImage;
        @BindView(R.id.tvTime) FontTextView tvTime;
        @BindView(R.id.surfView) SurfaceView surfaceView;

        private Context mContext;
        private Media media;
        private MediaPlayer mediaPlayer;
        private SurfaceHolder vidHolder;

        public MediaHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bind(Media media){
            this.media = media;
            if (media.user != null && media.user.full_name != null){
                tvUser.setText(media.user.full_name);
            }
            if (media.user != null && media.user.profile_picture != null){
                Picasso.with(mContext).load(media.user.profile_picture).into(ivUser);
            }
            if (media.likes != null){
                tvLikes.setText(String.valueOf(media.likes.count));
            }
            if (media.comments != null){
                tvComment.setText(String.valueOf(media.comments.count));
            }
            if (media.images != null && media.images.standard_resolution != null &&
                    media.images.standard_resolution.url != null){
                Picasso.with(mContext).load(media.images.standard_resolution.url).into(ivImage);
            }
            if (media.user != null && media.user.username != null){
                tvName.setText(media.user.username);
            }
            if (media.caption != null && media.caption.text != null){
                tvCaption.setText(media.caption.text);
            }
            if (media.caption != null && media.caption.created_time != null){
                tvTime.setText(DateTimeUtils.getChatDisplayTime(mContext, Long.parseLong(media.caption.created_time)));
            }
            if(media.videos != null && media.videos.standard_resolution != null && media.videos.standard_resolution.url != null){
                surfaceView.setVisibility(View.VISIBLE);
                vidHolder = surfaceView.getHolder();
                vidHolder.addCallback(this);
                ivImage.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            mediaPlayer.start();
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDisplay(vidHolder);
                mediaPlayer.setDataSource(media.videos.standard_resolution.url);
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setLooping(true);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }


    }
}
