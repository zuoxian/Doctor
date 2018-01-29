package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.model.UserConfigBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PasswordActivity extends BaseActivity implements Callback<UserConfigBean>{
    //密碼是否可見
    private boolean isPwdVisible = false;

    @BindView(R.id.editText1)
    EditText editText1;
    @BindView(R.id.editText2)
    EditText editText2;

    @BindView(R.id.image1)
    RelativeLayout imageView1;
    @BindView(R.id.image2)
    RelativeLayout imageView2;

    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.image)
    ImageView image;

    private UserAPI userAPI;
    private String oldPwd = "";
    private String newPwd = "";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userAPI = RestAdapterUtils.getRestAPI(Config.USER_API,UserAPI.class,this);
        user = UserService.getInstance(this).getActiveAccountInfo();
        setPasswordVisible();
    }

    @OnClick(R.id.pudate_pwd)
    void updatePwd(){

        if(null == editText1 || null == editText1.getText() || TextUtils.isEmpty(editText1.getText().toString())) {
            SystemTools.show_msg(this, "请输入旧密码~");
            return;
        }



        if(TextUtils.isEmpty(user.getPwd()))return;

        if(user.getPwd().equals(editText1.getText().toString())){
            oldPwd = editText1.getText().toString();
        }else{
            SystemTools.show_msg(this, "输入的旧密码错误");
            return;
        }

        if(null == editText2 || null == editText2.getText() || TextUtils.isEmpty(editText2.getText().toString())){
            SystemTools.show_msg(this, "请输入新密码~");
            return;
        }
        newPwd = editText2.getText().toString();
        userAPI.updateUserInfo(newPwd,this);

    }

    @Override
    public int initView() {
        return R.layout.activity_password;
    }

    @Override
    public void finishButton() {

    }

    /**
     * 设置密码是否可见
     */
    //1.使用ImageView
    private void setPasswordVisible() {
        //ImageView点击事件
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //修改密码是否可见的状态
                isPwdVisible = !isPwdVisible;
                //設置密碼是否可見
                if (isPwdVisible) {
                    //设置密码为明文，并更改眼睛图标
                    editText1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    image.setImageResource(R.drawable.show_pwd_image);
                } else {
                    //设置密码为暗文，并更改眼睛图标
                    editText1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    image.setImageResource(R.drawable.hide_pwd_image);
                }
                //设置光标位置的代码需放在设置明暗文的代码后面
                editText1.setSelection(editText1.getText().toString().length());
            }
        });

        //ImageView点击事件
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改密码是否可见的状态
                isPwdVisible = !isPwdVisible;
                //設置密碼是否可見
                if (isPwdVisible) {
                    //设置密码为明文，并更改眼睛图标
                    editText2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    image3.setImageResource(R.drawable.show_pwd_image);
                } else {
                    //设置密码为暗文，并更改眼睛图标
                    editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    image3.setImageResource(R.drawable.hide_pwd_image);
                }
                //设置光标位置的代码需放在设置明暗文的代码后面
                editText2.setSelection(editText2.getText().toString().length());
            }
        });
    }

    @Override
    public void success(UserConfigBean userConfigBean, Response response) {
        if(null != userConfigBean && true == userConfigBean.getSuccess()){
            SystemTools.show_msg(this,"修改成功~");
            user.setPwd(newPwd);
            UserService.getInstance(this).signIn(user.getMobile(), newPwd, user);
            UserService.getInstance(this).setPwd(user.getId(),newPwd);
            finish();
        }
    }

    @Override
    public void failure(RetrofitError error) {
        closeDialog();
        if(null != error && error.getMessage().contains("path $.obj")){

            final UserService userService = UserService.getInstance(this);
            final User user = userService.getActiveAccountInfo();
            userAPI.login(user.getMobile(),userService.getPwd(user.getId()),2,new Callback<UserBean>(){

                @Override
                public void success(UserBean userBean, Response response) {
                    if(null != userBean && null != userBean.getObj() && !TextUtils.isEmpty(userBean.getObj().getTokenId())){
                        userService.setTokenId(user.getId(),userBean.getObj().getTokenId());
                        userAPI.updateUserInfo(newPwd,PasswordActivity.this);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        }
    }
}
