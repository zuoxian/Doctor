package com.yjm.doctor.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.model.AppointmentInfo;
import com.yjm.doctor.model.Customer;
import com.yjm.doctor.model.User;
import com.yjm.doctor.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zx on 2017/12/11.
 */

public class MainAppointmentsInfoActivity extends BaseActivity{

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

    @OnClick(R.id.agree)
    void agree(){

    }

    @OnClick(R.id.refuse)
    void refuse(){

    }

    private AppointmentInfo item;

    @Override
    public int initView() {
        item = (AppointmentInfo) this.getIntent().getSerializableExtra("object");
        Log.i("app","app info :"+item.toString());
        return R.layout.activity_appointment_info;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null == item)return ;

        if(null == item.getUser())return ;

        User user = item.getUser();

        if(null != mUserIcon && user != null && !TextUtils.isEmpty(user.getPic()))
            mUserIcon.setImageURI(Uri.parse(user.getPic()));

        Customer customer = user.getCustomer();

         if(null != customer){
             if(null != mUserName){
                 mUserName.setText(customer.getRealName());
            }

            if(null != mUserInfo){
                 String sex = "女";
                 if(Config.SEX_MALE == customer.getSex())
                     sex ="男";
                 mUserInfo.setText("性别："+sex +"  年龄："+customer.getAge()+"岁  电话："+customer.getPhone());
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
}
