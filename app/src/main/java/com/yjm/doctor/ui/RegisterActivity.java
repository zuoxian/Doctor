package com.yjm.doctor.ui;


import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.NetworkUtils;
import com.yjm.doctor.util.ObjectCheck;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/5.
 */

public class RegisterActivity extends BaseActivity implements Callback<UserBean>{

    @BindView(R.id.username)
    EditText mUserName;

    @BindView(R.id.verification)
    EditText mVerification;

    @BindView(R.id.password)
    EditText mPassword;

    private UserAPI userAPI;

    private int vRequest = 0;

    @Override
    public int initView() {
        userAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class,this);
        return R.layout.activity_register;
    }

    @Override
    public void finishButton() {

    }


    @OnClick(R.id.login)
    void login(){
        ActivityJumper.getInstance().buttonJumpTo(this,LoginActivity.class);
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
            userAPI.register(mUserName.getText().toString(),mPassword.getText().toString(),2,mVerification.getText().toString(),this);
        }else{
            SystemTools.show_msg(this, R.string.toast_msg_no_network);
        }
    }

    @Override
    public void success(UserBean userBean, Response response) {
        if (1 == vRequest) {//验证码响应
            if (null != userBean && true == userBean.getSuccess()) {
                Log.e("userlogin", "获取验证码成功");
            }else{
                SystemTools.show_msg(this, userBean.getMsg());
            }
        } else if (2 == vRequest) {//注册响应
            if (null != userBean && true == userBean.getSuccess()) {
                if (null == userBean.getObj()) {
                    SystemTools.show_msg(this, userBean.getMsg());
                    return;
                }
                User user = userBean.getObj();
                if(null != mPassword)
                    user.setPwd(mPassword.getText().toString());
                
                ActivityJumper.getInstance().buttonObjectJumpTo(this, RegisterInfoActivity.class,user);
            } else {
                if (TextUtils.isEmpty(userBean.getMsg())) {
                    SystemTools.show_msg(this, R.string.register_fail_info);
                } else {
                    SystemTools.show_msg(this, userBean.getMsg());
                }
            }

        }
    }




    @Override
    public void failure(RetrofitError error) {
        if(1 == vRequest){//验证码响应
            SystemTools.show_msg(this, R.string.register_verification_fail);
        }else if(2 == vRequest){//注册响应
            SystemTools.show_msg(this,R.string.register_fail_info);
        }
    }

    private void vUserName(){
        if(null != mUserName) {
            if(TextUtils.isEmpty(mUserName.getText())) {
                mUserName.setError("请输入账号");
                mUserName.findFocus();
                return;
            }
            if(!(ObjectCheck.getInstance().checkPhoneNum(mUserName.getText().toString()))){
                mUserName.setError("请输入正确的账号");
                mUserName.findFocus();
                return;
            }
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
}
