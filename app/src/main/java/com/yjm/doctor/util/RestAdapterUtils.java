package com.yjm.doctor.util;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.yjm.doctor.BuildConfig;
import com.yjm.doctor.Config;
import com.yjm.doctor.util.auth.UserService;

import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;


/**
 * Created by zx on 2017/12/5.
 */
public class RestAdapterUtils {

    /**
     * support offline cache
     * @param endpoint
     * @param service
     * @param ctx
     * @param <T>
     * @return
     */
    public static <T> T getRestAPI(String endpoint, final Class<T> service, final Context ctx) {

        RestAdapter.LogLevel level;

        if (BuildConfig.DEBUG) {
            level = RestAdapter.LogLevel.FULL;
        } else {
            level = RestAdapter.LogLevel.NONE;
        }

        OkHttpClient okHttpClient = new OkHttpClient();

        try{
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(ctx.getCacheDir(), cacheSize);
            okHttpClient.setCache(cache);
            TimeUnit unit=null;
            okHttpClient.setConnectTimeout(6000,TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(5000,TimeUnit.MILLISECONDS);
        }catch (Exception ex){
//            Logger.e(ex, "cache error");
        }


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(level)
                .setEndpoint(endpoint)
                .setClient(new OkClient(okHttpClient))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {

                        if (NetworkUtils.isNetworkAvaliable(ctx)) {
                            int maxAge = 600; // read from cache for 10 minute
                            request.addHeader("Cache-Control", "public, max-age=" + maxAge);
                        } else {
                            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                            request.addHeader("Cache-Control",
                                    "public, only-if-cached, max-stale=" + maxStale);
                        }
                        request.addHeader("Transfer-Encoding","chunked");
                        UserService userService = UserService.getInstance();
                        if(Config.userId > 0) {
                            request.addHeader("tokenId", userService.getTokenId(Config.userId));
                        }

                    }
                }).build();

        return restAdapter.create(service);
    }

    public static <T> T getRestAPI(String endpoint, final Class<T> service) {

        RestAdapter.LogLevel level;

        if (BuildConfig.DEBUG) {
            level = RestAdapter.LogLevel.FULL;
        } else {
            level = RestAdapter.LogLevel.NONE;
        }
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(3000, TimeUnit.MILLISECONDS);
        client.setReadTimeout(5000, TimeUnit.MILLISECONDS);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(level)
                .setEndpoint(endpoint)
                .setClient(new OkClient(client))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        Log.i("main","request  ======"+request.toString());
                        request.addHeader("Transfer-Encoding","chunked");
                        UserService userService = UserService.getInstance();
                        if(Config.userId > 0) {
                            request.addHeader("tokenId", userService.getTokenId(Config.userId));
                        }

                    }
                }).build();

        return restAdapter.create(service);
    }



}
