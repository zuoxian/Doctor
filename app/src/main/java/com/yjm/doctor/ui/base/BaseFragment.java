package com.yjm.doctor.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yjm.doctor.application.YjmApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit.Callback;

/**
 * Created by zx on 2017/12/5.
 */

public abstract class BaseFragment<T> extends Fragment implements Callback<T>{

    private Activity activity;

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        unbinder=ButterKnife.bind(this, view);
        onLoadData();
        return view;
    }

    protected abstract int getLayoutRes();

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract void onLoadData();



    public Context getContext() {
        activity = getActivity();

        if (activity == null) {
            return YjmApplication.getInstance();
        }

        return activity;
    }


}
