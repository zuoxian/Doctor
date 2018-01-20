package com.yjm.doctor.api;

import com.yjm.doctor.model.Account;
import com.yjm.doctor.model.AccountBean;
import com.yjm.doctor.model.BusinessSettingBean;
import com.yjm.doctor.model.Comment;
import com.yjm.doctor.model.CommentBean;
import com.yjm.doctor.model.DepartMentBean;
import com.yjm.doctor.model.HospitalBean;
import com.yjm.doctor.model.Level;
import com.yjm.doctor.model.LevelBean;
import com.yjm.doctor.model.Message;
import com.yjm.doctor.model.MessageInfo;
import com.yjm.doctor.model.PatientBean;
import com.yjm.doctor.model.SMessageBean;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.model.UserConfig;
import com.yjm.doctor.model.UserConfigBean;
import com.yjm.doctor.model.UserPatientInfoBean;
import com.yjm.doctor.ui.view.layout.BalanceListBean;

import java.io.File;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

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

    @POST("/dataGrid")
    void getBalanceList( //@Query("tokenId") String tokenId ,

            @Query("date") String date ,
            @Query("page") int page ,
            @Query("rows") int rows ,
            Callback<BalanceListBean> callback);

    @POST("/forgetPwd")
    void forgetPwd(@Query("username") String username, @Query("password") String password, @Query("isAdmin") int isAdmin, @Query("vcode") String vcode, @Query("tokenId") String tokenId, Callback<UserBean> callback);


    @POST("/myPatients")
    void getPatients(@Query("query") String query, @Query("page") int page, @Query("rows") int rows,Callback<PatientBean> callback);


    @POST("/myPatientDetail")
    void getUserPatientInfo(
            @Query("userId") int userId ,
            Callback<UserPatientInfoBean> callback);

    @POST("/getAccount")
    void getAccount(Callback<AccountBean> callback);




    @POST("/updateAccount")
    void updateAccount(
            @Query("bankAccount") String bankAccount,
            @Query("bankPhone") String bankPhone,
            @Query("bankIdNo") String bankIdNo,
            @Query("bankCode") String bankCode,
            @Query("bankName") String bankName,
            @Query("bankCard") String bankCard,
            @Query("alipay") String alipay,
            Callback<Message> callback);


    @POST("/edit")
    void updateUserInfo(
            @Query("realName") String realName,
            @Query("sex") int sex,
            @Query("email") String email,
            @Query("hospital") int hospital,
            @Query("department") int department,
            @Query("level") int level,
            @Query("speciality") String speciality,
            Callback<UserConfigBean> callback

    );

    @Multipart
    @POST("/edit")
    void updateUserInfo(
            @Part("headImageFile") TypedFile headImageFile,
            Callback<UserConfigBean> callback

    );


    @POST("/myComments")
    void getComments(Callback<CommentBean> callback);

    @POST("/hospitalList")
    void hospitalList(Callback<HospitalBean> callback);

    @POST("/departmentList")
    void departmentList(Callback<DepartMentBean> callback);

    @POST("/dataGrid")
    void getMessage( @Query("page") int page, @Query("rows") int rows,Callback<SMessageBean> callback);


    @POST("/detail")
    void getMessageInfo( @Query("id") int id,Callback<MessageInfo> callback);


}
