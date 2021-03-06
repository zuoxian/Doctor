package com.yjm.doctor.ui;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yjm.doctor.Config;
import com.yjm.doctor.Constant;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.CountDownUtil;
import com.yjm.doctor.util.Helper;
import com.yjm.doctor.util.NetworkUtils;
import com.yjm.doctor.util.ObjectCheck;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SharedPreferencesUtil;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/19.
 */

public class ForgetPwdActivity extends BaseActivity implements Callback<UserBean> {

    @BindView(R.id.username)
    EditText mUserName;

    @BindView(R.id.verification)
    EditText mVerification;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.getverification)
    TextView textView;

    private UserAPI userAPI;

    private int vRequest = 0;
    private SharedPreferencesUtil sharedPreferencesUtil;

    @Override
    public int initView() {
        YjmApplication.toolFinish = false;
        userAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class,this);
        sharedPreferencesUtil = SharedPreferencesUtil.instance(this);
        return R.layout.activity_forget;
    }

    @Override
    public void finishButton() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constant.islogin = true;
    }

    @OnClick(R.id.getverification)
    void getVerification(){
        vUserName();
        if(NetworkUtils.isNetworkAvaliable(this)) {
            vRequest = 1;
            userAPI.getVCode(mUserName.getText().toString(),Config.DEFAULT_TOKENID,this);

        }else{
            SystemTools.show_msg(this, R.string.toast_msg_no_network);
        }
    }

    @OnClick(R.id.register)
    void register(){
        vUserName();
        vCode();
        vPassword();
        if(NetworkUtils.isNetworkAvaliable(this)) {
            vRequest = 2;
            userAPI.forgetPwd(mUserName.getText().toString(),mPassword.getText().toString(),2,mVerification.getText().toString(),Config.DEFAULT_TOKENID,this);
        }else{
            SystemTools.show_msg(this, R.string.toast_msg_no_network);
        }
    }

    private void vUserName(){
        if(null != mUserName) {
            if(TextUtils.isEmpty(mUserName.getText())) {
                mUserName.setError("请输入账号");
                mUserName.findFocus();
                return;
            }
//            if(!(ObjectCheck.getInstance().checkPhoneNum(mUserName.getText().toString()))){
//                mUserName.setError("请输入正确的账号");
//                mUserName.findFocus();
//                return;
//            }
        }
    }

    private void vPassword(){
        if(null != mPassword){
            if(TextUtils.isEmpty(mPassword.getText())) {
                mPassword.findFocus();
                mPassword.setError("请输入密码");
                return;
            }
            if(!(ObjectCheck.getInstance().checkPwd(mPassword.getText().toString()))){
                mPassword.findFocus();
                mPassword.setError("请输入正确的密码");
                return;
            }

        }
    }

    private void vCode(){
        if(null != mVerification){
            if(TextUtils.isEmpty(mVerification.getText())) {
                mVerification.findFocus();
                mVerification.setError("验证码不能为空~");
                return;
            }
            if(!(ObjectCheck.getInstance().checkPwd(mVerification.getText().toString()))){
                mVerification.findFocus();
                mVerification.setError("验证码错误~");
                return;
            }

        }
    }


    @Override
    public void success(UserBean userBean, Response response) {
        if(null != userBean && true == userBean.getSuccess()){
            SystemTools.show_msg(this, userBean.getMsg());
            if(2 == vRequest) {
                if(null != userBean.getObj() && null != mPassword)
//                UserService.getInstance(this).setPwd(userBean.getObj().getId(), mPassword.getText().toString());
                UserService.getInstance(this).logout();
                sharedPreferencesUtil.del("user");
                Helper.getInstance().logout(false,null);
                ActivityJumper.getInstance().buttonIntJumpTo(this, LoginActivity.class, 1);
                finish();
            }

            if(1 == vRequest){
                if(null == textView) return;
                new CountDownUtil(textView)
                        .setCountDownMillis(60_000L)//倒计时60000ms
                        .setCountDownColor(android.R.color.holo_blue_light,android.R.color.darker_gray)//不同状态字体颜色
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("MainActivity","发送成功");
                                userAPI.getVCode(mUserName.getText().toString(),Config.DEFAULT_TOKENID,ForgetPwdActivity.this);
                            }
                        })
                        .start();
            }
        }else {
            SystemTools.show_msg(this,userBean.getMsg());
        }
    }

    @Override
    public void failure(RetrofitError error) {
        if(1 == vRequest) {
            SystemTools.show_msg(this, "验证码读取失败");
        }else{
            SystemTools.show_msg(this, "找回密码失败，请重试");
        }
    }
}
