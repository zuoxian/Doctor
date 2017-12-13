package com.yjm.doctor.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yjm.doctor.R;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.ui.base.BaseFragment;

import butterknife.BindView;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/10.
 */

public class UserFragment extends BaseFragment {


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user;
    }



    @Override
    protected void onLoadData() {

    }


    @Override
    public void success(Object o, Response response) {

    }

    @Override
    public void failure(RetrofitError error) {

    }
}
