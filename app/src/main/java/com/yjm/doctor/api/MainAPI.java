package com.yjm.doctor.api;

import com.yjm.doctor.model.Appointment;
import com.yjm.doctor.model.AppointmentBean;
import com.yjm.doctor.model.AppointmentDetailInfo;
import com.yjm.doctor.model.BannerBean;
import com.yjm.doctor.model.ConsultationBean;
import com.yjm.doctor.model.DataTypeBean;
import com.yjm.doctor.model.Message;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.model.UserChar;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by zx on 2017/12/10.
 */

public interface MainAPI {

    @GET("/banners")
    void banner(@Query("tokenId") String tokenId, @Query("source") String source, Callback<BannerBean> callback);

    @GET("/appointments")
    void appointments(@Query("tokenId") String tokenId, @Query("isReply") int isReply, @Query("query") String query, @Query("page") int page, @Query("rows") int rows, Callback<AppointmentBean> callback);

    @GET("/getAppointmentDetail") //加号详情
    void appointmentInfo(@Query("id") int id,Callback<AppointmentBean> callback);

    @GET("/consultations")
    void consultations(@Query("tokenId") String tokenId, @Query("isReply") int isReply, @Query("query") String query, @Query("page") int page, @Query("rows") int rows, Callback<ConsultationBean> callback);


    @POST("/updateAppointmentStatus")
    void updateAppointmentStatus(@Query("id") int id, @Query("appointStatus") int appointStatus, @Query("refuseReason") String refuseReason, Callback<UserBean> callback);

    @POST("/basedata")
    void baseData(@Query("dataType") String dataType, Callback<DataTypeBean> callback);

    @POST("/getAppointmentDetail")
    void getAppointmentDetail(@Query("id") int id, Callback<AppointmentDetailInfo> callback);


    @POST("/updateNewestConsultation")
    void updateNewestConsultation(@Query("senderType") int senderType,@Query("receiverId") int receiverId,@Query("hxAccount") String hxAccount,@Query("lastContent") String lastContent, @Query("mtype") String mtype, Callback<Message> callback);

    @POST("/getByHx")
    void getByHx(@Query("hxAccounts") String hxAccounts,Callback<UserChar> callback);

}
