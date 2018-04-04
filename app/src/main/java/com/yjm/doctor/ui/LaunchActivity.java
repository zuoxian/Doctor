package com.yjm.doctor.ui;


import android.util.Log;

import com.yjm.doctor.Constant;
import com.yjm.doctor.R;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.util.ActivityJumper;

import butterknife.OnClick;

/**
 * Created by zx on 2017/12/5.
 */

public class LaunchActivity extends BaseActivity {


    @OnClick(R.id.login_button)
    public void login(){
        Log.i("userlogin","login");
        ActivityJumper.getInstance().buttonJumpTo(this,LoginActivity.class);
//        finish();
    }

    @OnClick(R.id.register_button)
    public void register(){
        Constant.islogin = false;
        Log.i("userlogin","register");
        ActivityJumper.getInstance().buttonJumpTo(this,RegisterActivity.class);
//        finish();
    }

    @Override
    public int initView() {
        return R.layout.activity_launch;
    }

    @Override
    public void finishButton() {

    }
}
