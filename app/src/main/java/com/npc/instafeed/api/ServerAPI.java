package com.npc.instafeed.api;

import com.npc.instafeed.models.MediaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by USER on 16/01/2017.
 */

public interface ServerAPI {

    @GET("/v1/users/self/media/recent/")
    Observable<MediaResponse> getSelfMedia(@Query("access_token") String accessToken, @Query("count") int count);

    @GET("/v1/users/self/media/recent/")
    Observable<MediaResponse> getSelfMediaNext(@Query("access_token") String accessToken, @Query("count") int count, @Query("max_id") String maxId);
}
