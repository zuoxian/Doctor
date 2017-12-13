package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.model.EventType;
import com.yjm.doctor.model.MemberDoctor;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.fragment.MainFragment;
import com.yjm.doctor.ui.fragment.ServiceFragment;
import com.yjm.doctor.ui.fragment.UserFragment;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/8.
 */

public class MainActivity extends BaseActivity implements Callback<UserBean> {

    private String [] tabNames;
    private Class<?>[] fragmentClss;
    private int[] mImageArray;

    private LayoutInflater mLayoutInflater;

    @Nullable
    @BindView(android.R.id.tabhost)
    FragmentTabHost mTabHost;

    private UserAPI userAPI;

    private UserService  userService;


    @Override
    public int initView() {
        return R.layout.activity_main;
    }



    @Override
    public void finishButton() {

    }

    public void onEventMainThread(EventType type){

        if(Config.MAIN_SERVICE_TYPE.equals(type.getType())){
            mTabHost.setCurrentTab((int)type.getObject());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User mUser = UserService.getInstance(this).getActiveAccountInfo();
        MemberDoctor doctor = mUser.getMemberDoctor();
        boolean isGetUserInfo = true;
        if(null != mUser && null != doctor){
            if(mUser.getId() == doctor.getId()){
                isGetUserInfo = false;//用户信息存在 不需要重新请求
            }
        }
        if(isGetUserInfo) {
            userAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, this);
            userAPI.getUserInfo(this);
        }
        tabNames = new String[]{"首页", "服务", "个人中心"};

        fragmentClss = new Class<?>[]{MainFragment.class, ServiceFragment.class,UserFragment.class};

        mImageArray = new int[]{R.drawable.tab_home_selector,R.drawable.tab_service_selector,R.drawable.tab_user_selector};
        EventBus.getDefault().register(this);
        initViews();
    }



    private void initViews(){
        mLayoutInflater = LayoutInflater.from(this);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        for (int i = 0; i < tabNames.length; i++) {
            mTabHost.addTab(mTabHost.newTabSpec(tabNames[i]).setIndicator(tabNames[i]).setIndicator(getTabItemView(i)),
                    fragmentClss[i], null);

        }
        mTabHost.getTabWidget().setDividerDrawable(null);

    }


    private View getTabItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.tab_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tabImg);
        imageView.setImageResource(mImageArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.tabText);
        textView.setText(tabNames[index]);

        return view;
    }

    private static long back_pressed;
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            SystemTools.show_msg(this,"再次点击退出");
        }
        back_pressed = System.currentTimeMillis();
    }


    @Override
    public void success(UserBean userBean, Response response) {
        if(null != userBean && true == userBean.getSuccess() && null != userBean.getObj()){
            finishLogin(userBean.getObj());
        }
    }


    private void finishLogin(User user) {


        UserService.getInstance(this).signIn(user.getMobile(), userService.getPwd(user.getId()), user);

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

    }
}
