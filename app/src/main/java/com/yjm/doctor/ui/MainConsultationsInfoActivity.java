package com.yjm.doctor.ui;

import android.widget.TextView;

import com.yjm.doctor.R;
import com.yjm.doctor.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by zx on 2017/12/11.
 */

public class MainConsultationsInfoActivity extends BaseActivity{

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
    TextView address;

    @Override
    public int initView() {
        return R.layout.activity_appointment_info;
    }

    @Override
    public void finishButton() {

    }
}
