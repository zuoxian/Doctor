package com.yjm.doctor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.yjm.doctor.Config;
import com.yjm.doctor.Constant;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/8.
 */

public class SplashActivity extends BaseActivity{

    private User mUser;
    private UserAPI userAPI;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            Log.e("userlogin","判断是否登录  "+ Config.userId+",tokenid="+UserService.getInstance(SplashActivity.this).getTokenId(mUser.getId())+"");

            if((null != mUser && 0 != mUser.getId()) && (Config.AUTH_STATUS_SUCCESS == mUser.getStatus() || Config.AUTH_STATUS_AUTH == mUser.getStatus())){
                Log.e("userlogin","已经登录过了"+mUser.toString());
                Config.userId = mUser.getId();
                Config.mobile = mUser.getMobile();

                jumper(MainActivity.class);
                final UserService userService = UserService.getInstance(SplashActivity.this);
                final User user = userService.getActiveAccountInfo();
                userAPI.login(user.getMobile(),userService.getPwd(user.getId()),2,new retrofit.Callback<UserBean>(){

                    @Override
                    public void success(UserBean userBean, Response response) {
                        if(null != userBean && null != userBean.getObj() && !TextUtils.isEmpty(userBean.getObj().getTokenId())){
                            userService.setTokenId(user.getId(),userBean.getObj().getTokenId());
                            userAPI.getUserInfo(this);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }else{
                Log.e("userlogin","没有登录过");
                jumper(LaunchActivity.class);
            }
        finish();

        }
    };

    private void jumper(Class activity){
        Log.e("userlogin","跳转");
        ActivityJumper.getInstance().buttonJumpTo(this,activity);
    }

    @Override
    public int initView() {
        Constant.isClose = false;
        Constant.islogin = false;
        return R.layout.activity_splash;
    }

    @Override
    public void finishButton() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = UserService.getInstance(this).getActiveAccountInfo();
      userAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, this,"");
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
