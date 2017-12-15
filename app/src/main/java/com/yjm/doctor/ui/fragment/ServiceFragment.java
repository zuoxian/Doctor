package com.yjm.doctor.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yjm.doctor.R;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.base.BaseFragment;
import com.yjm.doctor.util.auth.UserService;

import butterknife.BindView;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/10.
 */

public class ServiceFragment extends BaseFragment<UserBean> {

    private User user;

    @BindView(R.id.username)
    TextView mUserName;

    @BindView(R.id.positional)
    TextView mPositional;

    @BindView(R.id.hospital)
    TextView mHospital;

    @BindView(R.id.tooltitle)
    TextView mTooltitle;

    @BindView(R.id.toolicon)
    RelativeLayout mToolicon;

    @BindView(R.id.toolfinish)
    TextView mToolFinish;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_service;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(null != mTooltitle)mTooltitle.setText("服务");
        if(null != mToolicon)mToolicon.setVisibility(View.GONE);
        if(null != mToolFinish)mToolFinish.setVisibility(View.GONE);
    }


    @Override
    protected void onLoadData() {
        if(null != getActivity()) {
            user = UserService.getInstance(getActivity()).getActiveAccountInfo();
            if(null != mUserName && !TextUtils.isEmpty(user.getUsername())){
                mUserName.setText(user.getUsername());
            }


        }
    }


    @Override
    public void success(UserBean userBean, Response response) {

    }

    @Override
    public void failure(RetrofitError error) {

    }
}
