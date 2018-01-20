package com.yjm.doctor.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.MainAPI;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.model.AppointmentDetailInfo;
import com.yjm.doctor.model.AppointmentInfo;
import com.yjm.doctor.model.Customer;
import com.yjm.doctor.model.Patient;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.model.UserPatientInfo;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/11.
 */

public class UserAppointmentsInfoActivity extends BaseActivity implements Callback<UserBean>{

    MainAPI mainAPI;

    UserAPI userAPI;

    @BindView(R.id.user_icon)
    SimpleDraweeView mUserIcon;

    @BindView(R.id.username)
    TextView mUserName;

    @BindView(R.id.user_info)
    TextView mUserInfo;

    @BindView(R.id.des)
    TextView mDes;

    @BindView(R.id.money)
    TextView mMoney;

    @BindView(R.id.time)
    TextView mTime;

    @BindView(R.id.address)
    TextView mAddress;


    @BindView(R.id.info_status)
    TextView mInfoStatus;

    @BindView(R.id.reason)
    EditText mReason;

    @BindView(R.id.agree)
    TextView mAgree;

    @BindView(R.id.refuse)
    TextView mRefuse;

    @BindView(R.id.status_layout)
    LinearLayout mStatusLayout;

    @BindView(R.id.detail_layout)
    LinearLayout mDetailLayout;

    @BindView(R.id.detail)
    TextView mDetail;

    @BindView(R.id.status)
    TextView mStatus;

    private UserPatientInfo item;

    private User user;

    private int requestType = 0;

    private boolean reason = false;

    @OnClick(R.id.agree)
    void agree(){
        requestType = 0;
        if(null != item && 0 < item.getId())
            mainAPI.updateAppointmentStatus(item.getId(),1,"",this);
    }

    @OnClick(R.id.refuse)
    void refuse(){
        requestType = 1;
        if(null == mReason) return;
        if(false == reason) {
            mReason.setVisibility(View.VISIBLE);
            reason = true;
        }else{
            if(!TextUtils.isEmpty(mReason.getText().toString())){

                if(null != item && 0 < item.getId())
                    mainAPI.updateAppointmentStatus(item.getId(),3,mReason.getText().toString(),this);
            }else{
                SystemTools.show_msg(this,"拒绝理由不能为空~");
            }


        }

    }



    @Override
    public int initView() {
        item = (UserPatientInfo) this.getIntent().getSerializableExtra("object1");
        user = (User) this.getIntent().getSerializableExtra("object2");
        Log.i("app","app info :"+item.toString());
        mainAPI = RestAdapterUtils.getRestAPI(Config.HOME_APPOINTMENT_INFO, MainAPI.class,this);

        return R.layout.activity_appointment_info;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null == item)return ;



        if(null != mUserIcon && user != null && !TextUtils.isEmpty(user.getPicUrl()))
            mUserIcon.setImageURI(Uri.parse(user.getPicUrl()));

        Patient customer = user.getPatient();

        if(null != customer){
            if(null != mUserName){
                mUserName.setText(customer.getRealName());
            }

            if(null != mUserInfo){
                String sex = "女";
                if(Config.SEX_MALE == customer.getSex())
                    sex ="男";
                String mobile="";
                if(null != user) {
                    mobile =user.getMobile();
                }
                mUserInfo.setText("性别：" + sex + "  年龄：" + customer.getAge() + "岁  电话：" + mobile);
            }
        }

        if(null != mDes){
            if(!TextUtils.isEmpty(item.getAppointMessage())){
                mDes.setText(item.getAppointMessage());
            }
        }

        if(null != mMoney){
            mMoney.setText("¥ "+user.getAmount());
        }

        if(null != mTime){
            if(!TextUtils.isEmpty(item.getAppointTime())){
                mTime.setText(item.getAppointTime());
            }
        }

        if(null != mAddress){
            if(!TextUtils.isEmpty(item.getAppointAddress())){
                mTime.setText(item.getAppointAddress());
            }
        }
    }

    @Override
    public void finishButton() {

    }

    String status = "待确认";
    @Override
    protected void onResume() {
        super.onResume();
        if(null != item && 0 < item.getId()) {
            mainAPI.getAppointmentDetail(item.getId(), new Callback<AppointmentDetailInfo>() {
                @Override
                public void success(AppointmentDetailInfo appointmentDetailInfo, Response response) {
                    if(null != appointmentDetailInfo && true == appointmentDetailInfo.getSuccess()){
                        AppointmentInfo appointmentInfo = appointmentDetailInfo.getObj();

                        if(null != mAgree)mAgree.setVisibility(View.GONE);
                        if(null != mRefuse)mRefuse.setVisibility(View.GONE);

                        if(null != mStatusLayout )mStatusLayout.setVisibility(View.VISIBLE);
                        if(!TextUtils.isEmpty(item.getAppointStatus()) && !TextUtils.isEmpty(item.getStatus())){
                            if ("0" .equals(item.getAppointStatus()) && "1" .equals(item.getStatus()) ) {
                                status = "未回复";
                                if(null != mAgree)mAgree.setVisibility(View.VISIBLE);
                                if(null != mRefuse)mRefuse.setVisibility(View.VISIBLE);
                            }
                            if ("1" .equals(item.getAppointStatus()) && "1" .equals(item.getStatus()) ) {
                                status = "患者待确认";
                            }
                            if ("2" .equals(item.getAppointStatus()) && "1" .equals(item.getStatus()) ) {
                                status = "患者已确认";
                            }
                            if ("3" .equals(item.getAppointStatus()) && "1" .equals(item.getStatus()) ) {
                                status = "已拒绝";
                            }

                        }
                        if(null != mStatus) {
                            mStatus.setText(status);
                            if(null != mStatus)mStatus.setTextColor(getResources().getColor(R.color.btn_logout_normal));
                        }
                        if(!TextUtils.isEmpty(appointmentInfo.getRefuseReason())){
                            if(null != mDetailLayout)mDetailLayout.setVisibility(View.VISIBLE);
                            if(null != mDetail)mDetail.setText(appointmentInfo.getRefuseReason());
                        }
                    }

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }

    @Override
    public void success(UserBean userBean, Response response) {

        if (null != userBean && true == userBean.getSuccess()) {
            if (null != mInfoStatus && 0 == requestType) {//同意
//                mInfoStatus.setText("加号信息（已同意）");
                if(null != mStatusLayout){
                    mStatusLayout.setVisibility(View.VISIBLE);
                }
                if(null != mStatus){
                    mStatus.setText("已同意");
                }

            } else {//拒绝
                mReason.setVisibility(View.GONE);
//                mInfoStatus.setText("加号信息（已拒绝）");
                reason = false;
                mReason.setVisibility(View.GONE);
                if(null != mStatusLayout){
                    mStatusLayout.setVisibility(View.VISIBLE);
                }
                if(null != mStatus){
                    mStatus.setText("已拒绝");
                }
                if(null != mDetailLayout){
                    mDetailLayout.setVisibility(View.VISIBLE);
                }
                if(null != mDetail && null != mReason && !TextUtils.isEmpty(mReason.getText()))
                    mDetail.setText(mReason.getText().toString());
                if(null != mStatusLayout){
                    mStatusLayout.setVisibility(View.VISIBLE);
                }

//                finish();
            }
            if(null != mRefuse)mRefuse.setVisibility(View.GONE);
            if(null != mAgree)mAgree.setVisibility(View.GONE);
            if(null != mStatus)mStatus.setTextColor(getResources().getColor(R.color.btn_logout_normal));
        }else if(null != userBean && !TextUtils.isEmpty(userBean.getMsg())){
            SystemTools.show_msg(this,userBean.getMsg());
        }

    }



    @Override
    public void failure(RetrofitError error) {
        SystemTools.show_msg(this,"请求失败,请重试");

        if(null != error && error.getMessage().contains("path $.obj")){
            userAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, this,"");
            final UserService userService = UserService.getInstance(this);
            final User user = userService.getActiveAccountInfo();
            userAPI.login(user.getMobile(),userService.getPwd(user.getId()),2,new Callback<UserBean>(){

                @Override
                public void success(UserBean userBean, Response response) {
                    if(null != userBean && null != userBean.getObj() && !TextUtils.isEmpty(userBean.getObj().getTokenId())){
                        userService.setTokenId(user.getId(),userBean.getObj().getTokenId());
                        if (1 == requestType) {//同意
                            if(null != item && 0 < item.getId())
                                mainAPI.updateAppointmentStatus(item.getId(),1,"",this);
                        }else{
                            if(!TextUtils.isEmpty(mReason.getText().toString())){

                                if(null != item && 0 < item.getId())
                                    mainAPI.updateAppointmentStatus(item.getId(),3,mReason.getText().toString(),this);
                            }
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        }
    }
}
