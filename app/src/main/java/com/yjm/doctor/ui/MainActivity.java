package com.yjm.doctor.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.platform.comapi.map.C;
import com.yjm.doctor.Config;
import com.yjm.doctor.Constant;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.dao.CustomerDao;
import com.yjm.doctor.dao.DaoMaster;
import com.yjm.doctor.dao.DaoSession;
import com.yjm.doctor.dao.GreenDaoHelper;
import com.yjm.doctor.dao.MemberDoctorDao;
import com.yjm.doctor.dao.UserDao;
import com.yjm.doctor.model.Customer;
import com.yjm.doctor.model.EventType;
import com.yjm.doctor.model.MemberDoctor;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.fragment.MainAppointmentFragment;
import com.yjm.doctor.ui.fragment.MainFragment;
import com.yjm.doctor.ui.fragment.ServiceFragment;
import com.yjm.doctor.ui.fragment.UserFragment;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.ExampleUtil;
import com.yjm.doctor.util.Helper;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SharedPreferencesUtil;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.TagAliasOperatorHelper;
import com.yjm.doctor.util.UpdateManager;
import com.yjm.doctor.util.auth.UserService;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
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

    private User accountUser;

    UserDao userDao ;
    private int accountStatus = 2;//注册审核中

    private SharedPreferencesUtil sharedPreferencesUtil = null;


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
        if(Config.REGISTER_STATUS.equals(type.getType())){
            logout();
        }
        if(Config.UPDATE_USER_STATUS .equals(type.getType()) && null != mUser){//编辑审核通过触发
            String u = sharedPreferencesUtil.getObject("user");
            if(null != u) {
                String a = sharedPreferencesUtil.getObject("user");
                if (TextUtils.isEmpty(a)) return;
                try {
                    mUser = (User) sharedPreferencesUtil.deSerialization(a);
                }catch (Exception e){
                    Log.e("error",e.getMessage());
                }

                if (null != mUser) {
                    mUser.setStatus(1);
                    try {
                        sharedPreferencesUtil.saveObject("user", sharedPreferencesUtil.serialize(mUser));
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());

                    }
                }
            }


        }


    }

    private void logout(){
        UserService.getInstance(this).logout();
        Helper.getInstance().logout(false,null);
        sharedPreferencesUtil = SharedPreferencesUtil.instance(this);
        sharedPreferencesUtil.del("user");
        ActivityJumper.getInstance().buttonIntJumpTo(this, LoginActivity.class, 1);
    }

    public static final int DELAY_SEND_ACTION = 1;

    private static int sequence;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sequence++ ;
        sharedPreferencesUtil = SharedPreferencesUtil.instance(this);
        getUser();

        accountUser = UserService.getInstance(this).getActiveAccountInfo();
        if(null != accountUser){
            accountStatus = accountUser.getStatus();
        }
        tabNames = new String[]{"首页", "服务", "个人中心"};

        fragmentClss = new Class<?>[]{MainFragment.class, ServiceFragment.class,UserFragment.class};

        mImageArray = new int[]{R.drawable.tab_home_selector,R.drawable.tab_service_selector,R.drawable.tab_user_selector};
        EventBus.getDefault().register(this);
        initViews();


        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, ""));

        try {
            new UpdateManager(this).checkUpdate();
        }catch (Exception e){
            Log.e("MainActivity",e.getMessage());
        }


    }

    private static boolean isConnect = false;
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i("handler", logs);
                    isConnect = true;
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i("handler", logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    if(!isConnect)
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e("handler", logs);
            }
//            ExampleUtil.showToast(logs, getApplicationContext());
        }
    };

    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            Set<String> s= new HashSet<String>();
            s.add("doctor");
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d("handler", "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            "2_"+accountUser.getMobile(),
                            s,
                            mAliasCallback);
                    break;
                default:
                    Log.i("handler", "Unhandled msg - " + msg.what);
            }
        }
    };

    private static User mUser = null;
    private int status = 2;//编辑审核中

    protected void getUser() {
        boolean isGetUserInfo = true;
        try {
            String u = sharedPreferencesUtil.getObject("user");
            if(null != u) {
                String a = sharedPreferencesUtil.getObject("user");
                if(TextUtils.isEmpty(a))return;
                mUser = (User) sharedPreferencesUtil.deSerialization(a);
                if (null != mUser) {
//                    Log.d("serial", "share3   =" + mUser.toString());
                    MemberDoctor doctor = mUser.getMemberDoctor();

                    if (null != mUser && null != doctor) {
                        if (mUser.getId() > 0) {
                            isGetUserInfo = false;//用户信息存在 不需要重新请求
                        }
                    }
                    status = mUser.getStatus();
                }
            }


//            User mUser = UserService.getInstance(this).getActiveAccountInfo();

            Log.i("main", "mainactivity " + isGetUserInfo);
            if (isGetUserInfo) {
                userAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, this,"");
//                showDialog("加载中~");
                userAPI.getUserInfo(this);
            }
        }catch (Exception e){
            Log.e("main",e.getMessage());
        }
    }

    private void initViews(){
        mLayoutInflater = LayoutInflater.from(this);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        for (int i = 0; i < tabNames.length; i++) {
            mTabHost.addTab(mTabHost.newTabSpec(tabNames[i]).setIndicator(tabNames[i]).setIndicator(getTabItemView(i)),
                    fragmentClss[i], null);


        }
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.getTabWidget().getChildTabViewAt(1).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(2 == accountStatus){
                    SystemTools.show_msg(MainActivity.this,"账号审核中，请等待~");
                }else if(1 == accountStatus){
                    mTabHost.setCurrentTab(1);
                }
            }
        });
        mTabHost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(2 == accountStatus){
                    SystemTools.show_msg(MainActivity.this,"账号审核中，请等待~");
                }else if(1 == accountStatus){
                    mTabHost.setCurrentTab(2);
                }
            }
        });

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
            Constant.isClose =true;
            super.onBackPressed();
        } else {
            SystemTools.show_msg(this,"再次点击退出");
        }
        back_pressed = System.currentTimeMillis();
    }


    @Override
    public void success(UserBean userBean, Response response) {
//        closeDialog();
        Log.i("serial","share1   ="+userBean);

        if(null != userBean && true == userBean.getSuccess() && null != userBean.getObj()){
            finishLogin(userBean.getObj());
//            EventBus.getDefault().post(new EventType(Config.USER,userBean.getObj()));
        }
    }


    private void finishLogin(User user) {
//        closeDialog();
        Log.i("main","mainactivity   user");
        status = user.getStatus();

        try {

            sharedPreferencesUtil.saveObject("user",sharedPreferencesUtil.serialize(user));
//
//            mUser = (User) sharedPreferencesUtil.deSerialization(sharedPreferencesUtil.getObject("user"));
//            Log.d("serial", "share2   ="+mUser.toString());

//            UserService.getInstance(this).signIn(user.getMobile(), user.getHxPassword(), user);

            if (!TextUtils.isEmpty(user.getTokenId())) {
                    UserService.getInstance(this).setTokenId(user.getId(), user.getTokenId());

            }
            Config.userId = user.getId();
            Config.mobile = user.getMobile();
            setResult(11);
        }catch (Exception ex){
            Log.i("main",ex.getMessage());
        }
    }

    @Override
    public void failure(RetrofitError error) {
        if(null != error && error.getMessage().contains("path $.obj")){

            final UserService userService = UserService.getInstance(this);
            final User user = userService.getActiveAccountInfo();
            userAPI.login(user.getMobile(),userService.getPwd(user.getId()),2,new Callback<UserBean>(){

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

        }
    }
}
