package com.yjm.doctor.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;

import com.yjm.doctor.Config;
import com.yjm.doctor.Constant;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.baseInterface.IActivity;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.LoginActivity;
import com.yjm.doctor.util.auth.UserService;

import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/5.
 */

public abstract class BaseActivity extends AppCompatActivity implements IActivity{

    private ProgressDialog dialog;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(Constant.isClose){
            finish();
        }
        if(Constant.islogin){
            if(this instanceof LoginActivity){
                Log.e("islogin","islogin");
            }else{
                finish();
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void showDialog(String msg) {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage(msg);
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }

    }

    public void closeDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
