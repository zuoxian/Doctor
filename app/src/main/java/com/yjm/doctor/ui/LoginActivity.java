package com.yjm.doctor.ui;


import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.model.User;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.NetworkUtils;
import com.yjm.doctor.util.ObjectCheck;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.AccountAuthenticatorActivity;
import com.yjm.doctor.util.auth.UserService;

import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/5.
 */

public class LoginActivity extends AccountAuthenticatorActivity implements Callback<UserBean>{


    //测试提交
    @BindView(R.id.username)
    EditText mUserName;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.forget_password)
    TextView forgetPassword;

    @Override
    public int initView() {
        return R.layout.activity_login;
    }

    @Override
    public void finishButton() {

    }

    private UserAPI userAPI;

    private UserService UserService;


    @OnClick(R.id.forget_password)
    void forgetPassword(){
        ActivityJumper.getInstance().buttonJumpTo(this,ForgetPwdActivity.class);
        finish();
    }

    @OnClick(R.id.register)
    void register(){
        ActivityJumper.getInstance().buttonJumpTo(this,RegisterActivity.class);
        finish();
    }

    @OnClick(R.id.login)
    void login(){
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

        userAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, this,"");

        if(NetworkUtils.isNetworkAvaliable(this)){
            userAPI.login(mUserName.getText().toString(),mPassword.getText().toString(),Config.DOCTOR,this);

            showDialog("正在登录中~");
        }else{
            SystemTools.show_msg(this, R.string.toast_msg_no_network);
        }
}

    @Override
    public void success(UserBean objectBean, Response response) {
        Log.i("main",response.getUrl());
        closeDialog();
        if(null != objectBean && true == objectBean.getSuccess()) {
            if(null == objectBean.getObj()){
                SystemTools.show_msg(this, objectBean.getMsg());
                return;
            }
            User user = objectBean.getObj();
            if(Config.AUTH_STATUS_SUCCESS == user.getStatus() || Config.AUTH_STATUS_AUTH == user.getStatus()) {
                SystemTools.show_msg(this, objectBean.getMsg());//登录成功
                Log.i("main","tokenid;"+user.getTokenId());

                Log.i("main",user.toString());
                finishLogin(user);
                jumpMainActivity();
            }else if(Config.AUTH_STATUS_FAIL == user.getStatus()) {
                ActivityJumper.getInstance().buttonJumpTo(this, LoginFailureActivity.class);
                finish();
            }else{
                if(TextUtils.isEmpty(objectBean.getMsg())) {
                    SystemTools.show_msg(this,R.string.login_fail_info);
                }else{
                    SystemTools.show_msg(this, objectBean.getMsg());
                }
            }
        }else{
            if(TextUtils.isEmpty(objectBean.getMsg())) {
                SystemTools.show_msg(this,R.string.login_fail_info);
            }else{
                SystemTools.show_msg(this, objectBean.getMsg());
            }
        }

    }

    @Override
    public void failure(RetrofitError error) {
        closeDialog();
        SystemTools.show_msg(this,R.string.login_fail_info);
    }

    private void finishLogin(User user) {

        UserService.getInstance(this).signIn(mUserName.getText().toString(), mPassword.getText().toString(), user);
        UserService.getInstance(this).setPwd(user.getId(), mPassword.getText().toString());
        if (!TextUtils.isEmpty(user.getTokenId())) {
            if (!TextUtils.isEmpty(user.getTokenId()))
                UserService.getInstance(this).setTokenId(user.getId(), user.getTokenId());

        }
        Config.userId = user.getId();
        Config.mobile = user.getMobile();
        setResult(11);

        if (!(EMClient.getInstance().isLoggedInBefore())){
            EMClient.getInstance().login("2-" + mUserName.getText().toString(), user.getHxPassword(), new EMCallBack() {
                @Override
                public void onSuccess() {

                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    Log.i("EMClient", "login is success");
                }

                @Override
                public void onError(int i, String s) {
                    Log.i("EMClient", "login error i=" + i + ",s=" + s);
                }

                @Override
                public void onProgress(int i, String s) {
                    Log.i("EMClient", "login onProgress i=" + i + ",s=" + s);
                }
            });
    }
    }

    private void jumpMainActivity(){
        ActivityJumper.getInstance().buttonJumpTo(this,MainActivity.class);
        finish();
    }

}
