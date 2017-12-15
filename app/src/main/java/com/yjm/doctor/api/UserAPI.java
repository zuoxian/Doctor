package com.yjm.doctor.api;

import com.yjm.doctor.model.BusinessSettingBean;
import com.yjm.doctor.model.Level;
import com.yjm.doctor.model.LevelBean;
import com.yjm.doctor.model.Message;
import com.yjm.doctor.model.UserBean;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by zx on 2017/12/7.
 */

public interface UserAPI {

    @POST("/login")
    void login(@Query("username") String username, @Query("password") String password, @Query("isAdmin") int isAdmin, Callback<UserBean> callback);

    @POST("/getVCode")
    void getVCode(@Query("mobile") String mobile, @Query("tokenId") String tokenId, Callback<UserBean> callback);

    @POST("/register")
    void register(@Query("username") String username, @Query("password") String password, @Query("isAdmin") int isAdmin,@Query("vcode") String vcode, Callback<UserBean> callback);

    @POST("/addDoctorInfo")
    void addDoctorInfo(@Query("id") int id, @Query("realName") String realName, @Query("hospitalName") String hospitalName, @Query("departmentName") String departmentName, @Query("level") int level, Callback<UserBean> callback);

    @POST("/getLevels")
    void getLevels(Callback<LevelBean> callback);

    @POST("/get")
    void getUserInfo(Callback<UserBean> callback);

    @POST("/get")
    void getUserInfoByTokenId(
            @Query("tokenId") String tokenId ,
            Callback<UserBean> callback);


    @POST("/getConfig")
    void getBusinessSetting(
            @Query("tokenId") String tokenId ,
            Callback<BusinessSettingBean> callback);

    @POST("/updateConfig")
    void updateBusinessSetting(
            @Query("tokenId") String tokenId ,
            @Query("acceptAppointment") boolean acceptAppointment ,
            @Query("acceptConsultation") boolean acceptConsultation ,
            Callback<Message> callback);



}
