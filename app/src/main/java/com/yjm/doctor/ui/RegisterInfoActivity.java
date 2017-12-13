package com.yjm.doctor.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.model.EventType;
import com.yjm.doctor.model.Level;
import com.yjm.doctor.model.LevelBean;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.NetworkUtils;
import com.yjm.doctor.util.ObjectCheck;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/8.
 */

public class RegisterInfoActivity extends BaseActivity implements Callback<UserBean>{

    private UserAPI userAPI;

    private UserAPI levelAPI;

    private Level level;

    @BindView(R.id.positionalname)
    TextView mPositionalName;

    @BindView(R.id.name)
    EditText mName;

    @BindView(R.id.hospital)
    EditText mHospital;

    @BindView(R.id.department)
    EditText mDepartment;

    private User userBasicInfo ;





    @Override
    public int initView() {
        return R.layout.activity_register_info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        levelAPI = RestAdapterUtils.getRestAPI(Config.USER_LEVELS_API,UserAPI.class,this);
        userAPI = RestAdapterUtils.getRestAPI(Config.USER_API,UserAPI.class,this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void finishButton() {

    }

    public void onEventMainThread(EventType event) {
        if(Config.LEVEL_EVENTTYPE.equals(event.getType())){
            if(null == mPositionalName){
                return;
            }
            if(null == event.getObject())
                return;
            level =(Level) event.getObject();

            mPositionalName.setText(level.getName());
        }
    }


    @OnClick(R.id.positional)
    void getLevels(){
        if(NetworkUtils.isNetworkAvaliable(this)){
            showDialog("提取级别中~");
            levelAPI.getLevels(new Callback<LevelBean>() {
                @Override
                public void success(LevelBean levelBean, Response response) {
                    closeDialog();
                    if(null != levelBean && true == levelBean.getSuccess()){
                        if(null == levelBean.getObj()) {
                            SystemTools.show_msg(RegisterInfoActivity.this, R.string.level_fail);
                            return;
                        }

                        level = new Level(1480562081846l,0,"0",1482214042373l,"无职位");

                        List<Level> levelList = levelBean.getObj();
                        levelList.add(level);
                        Collections.sort(levelList);
                        ActivityJumper.getInstance().buttoListJumpTo(RegisterInfoActivity.this,LevelActivity.class,levelList);
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    closeDialog();
                }
            });
        }else{
            SystemTools.show_msg(this, R.string.toast_msg_no_network);
        }
    }

    @OnClick(R.id.register)
    void register(){
        if(null == mName || null == mHospital || null == mDepartment) {
            SystemTools.show_msg(this,R.string.register_fail);
            return;
        }
//        if(TextUtils.isEmpty(mName.getText())) {
//            mName.setError("请输入真实姓名");
//            mName.findFocus();
//            return;
//        }
//        if(TextUtils.isEmpty(mHospital.getText())) {
//            mHospital.setError("请输入医院名称");
//            mHospital.findFocus();
//            return;
//        }
//        if(TextUtils.isEmpty(mDepartment.getText())) {
//            mDepartment.setError("请输入科室名");
//            mDepartment.findFocus();
//            return;
//        }
        userBasicInfo = (User) this.getIntent().getSerializableExtra("object");
        userAPI.addDoctorInfo(userBasicInfo.getId(),mName.getText().toString(),mHospital.getText().toString(),mDepartment.getText().toString(),level.getId(),this);
        showDialog("正在注册中~");
    }
//    finishLogin(user);
    @Override
    public void success(UserBean userBean, Response response) {
        closeDialog();
        if (null != userBean && true == userBean.getSuccess()) {
            if (null == userBean.getObj()) {
                SystemTools.show_msg(this, userBean.getMsg());
                return;
            }
            User user = userBean.getObj();

            finishLogin(user);
            ActivityJumper.getInstance().buttonJumpTo(this,ToExamineActivity.class);
        } else {
            if (TextUtils.isEmpty(userBean.getMsg())) {
                SystemTools.show_msg(this, R.string.register2_fail_info);
            } else {
                SystemTools.show_msg(this, userBean.getMsg());
            }
        }
    }

    private void finishLogin(User user) {
        UserService.getInstance(this).signIn(user.getUsername(), userBasicInfo.getPwd(), user);
        UserService.getInstance(this).setPwd(userBasicInfo.getId(), userBasicInfo.getPassword().toString());
        if(!TextUtils.isEmpty(user.getTokenId())) {
            if(!TextUtils.isEmpty(user.getTokenId()))
                UserService.getInstance(this).setTokenId(user.getId(),user.getTokenId());

        }
        Config.userId = user.getId();
        Config.mobile = user.getMobile();
        setResult(11);
    }

    @Override
    public void failure(RetrofitError error) {
        closeDialog();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
