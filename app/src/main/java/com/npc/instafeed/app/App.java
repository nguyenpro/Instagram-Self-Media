package com.npc.instafeed.app;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.npc.instafeed.api.ServerAPI;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;

/**
 * Created by USER on 16/01/2017.
 */

public class App extends Application {

    private static ServerAPI serverAPI;

    @Override
    public void onCreate() {
        super.onCreate();
        HttpLoggingInterceptor httpLoggingInterceptor = provideHttpLoggingInterceptor();
        OkHttpClient client = provideOkHttpClientDefault(httpLoggingInterceptor);
        RxJavaPlugins.getInstance().registerErrorHandler(new RxJavaErrorHandler() {
            @Override
            public void handleError(Throwable e) {
                Log.w("Error", e);
            }
        });
        serverAPI = provideRestApi(client);
        Hawk.init(this)
                .build();
    }

    static ServerAPI provideRestApi(@NonNull OkHttpClient okHttpClient) {
        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).create();
        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());



        return builder.build().create(ServerAPI.class);
    }

    public static ServerAPI getServerAPI(){
        return serverAPI;
    }

    OkHttpClient provideOkHttpClientDefault(HttpLoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new CustomInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        return client;
    }

    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    public static class CustomInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder originRequest = chain.request().newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json");
            return processResponse(chain.proceed(originRequest.build()));
        }
    }

    public static Response processResponse(Response response){
        try {
            String jsonString = response.body().string();
            Response.Builder builder = response.newBuilder();
            builder.body(ResponseBody.create(MediaType.parse("application/json"), jsonString));
            return builder.build();
        } catch (Exception e) {
            Log.d("TAG", "processResponse: "+e.toString());
            return response;
        }
    }
}
