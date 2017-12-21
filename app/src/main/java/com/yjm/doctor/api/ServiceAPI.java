package com.yjm.doctor.api;

import com.yjm.doctor.model.GridBean;
import com.yjm.doctor.model.GridInfo;
import com.yjm.doctor.model.Message;
import com.yjm.doctor.model.UserBean;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by zx on 2017/12/20.
 */

public interface ServiceAPI {
    @POST("/dataGrid")
    void getGrid(
            Callback<GridBean> callback);


    @POST("/del")
    void delGrid(@Query("id") int id,
            Callback<GridBean> callback);

    @POST("/add")
    void addGrid(@Query("time") int time, @Query("closeDateStr") String closeDateStr,
                 Callback<Message> callback);


}
