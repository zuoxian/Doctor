package com.yjm.doctor.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.platform.comapi.map.C;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.MainAPI;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.model.EventType;
import com.yjm.doctor.model.Message;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.model.UserChar;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SharedPreferencesUtil;
import com.yjm.doctor.util.auth.UserService;

import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ChatActivity extends EaseBaseActivity implements Callback<UserChar> {

    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;
    private SharedPreferencesUtil sharedPreferencesUtil = null;

    User user;

    MainAPI mainAPI1;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_chat);
        activityInstance = this;
        sharedPreferencesUtil = SharedPreferencesUtil.instance(this);
        EventBus.getDefault().register(this);
        //user or group id
        toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
        chatFragment = new EaseChatFragment();
        //set arguments
        Log.e("chat---",getIntent().getExtras().toString());

        mainAPI1 = RestAdapterUtils.getRestAPI(Config.USER_API,MainAPI.class,this);
        mainAPI1.getByHx(toChatUsername, this);

    }

    public void onEventMainThread(EventType type){
        if("EASE_CHAT".equals(type.getType())){
            MainAPI mainAPI = RestAdapterUtils.getRestAPI(Config.EASE_MESSAGE,MainAPI.class,this);
            if(null != user) {
                mainAPI.updateNewestConsultation(2,user.getId(),toChatUsername,(String) type.getObject(),"text",new Callback<Message>(){

                    @Override
                    public void success(Message message, Response response) {
                        Log.e("message ---",message.toString());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("message ---",error.getMessage());

                    }
                });
            }
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
        EventBus.getDefault().unregister(this);
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        // enter to chat activity when click notification bar, here make sure only one chat activiy
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }
    
    public String getToChatUsername(){
        return toChatUsername;
    }

    private ProgressDialog dialog;
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

    private User my ;
    @Override
    public void success(UserChar userBean, Response response) {
        try {
            my = (User) sharedPreferencesUtil.deSerialization(sharedPreferencesUtil.getObject("user"));
        }catch (Exception e){
            Log.e("ChaActivity",e.getMessage());
        }

        if(null != userBean && true == userBean.getSuccess() && null != userBean.getObj()){
            List<User> user = userBean.getObj();
            if(null != user && user.size()>0){
                Bundle bundle = new Bundle();
                bundle.putBundle("content",getIntent().getExtras());
                if(!TextUtils.isEmpty(user.get(0).getPicUrl())){
                    bundle.putString("headerurl",user.get(0).getPicUrl());
                }
                if(null != user.get(0).getCustomer() && !TextUtils.isEmpty(user.get(0).getCustomer().getRealName())){
                    bundle.putString("charname",user.get(0).getCustomer().getRealName());
                }
                if(null != my && !TextUtils.isEmpty(my.getPicUrl())){
                    bundle.putString("myheaderurl",my.getPicUrl());
                }
                chatFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
            }

        }
    }

    @Override
    public void failure(RetrofitError error) {
        if(null != error && error.getMessage().contains("path $.obj")){
            UserAPI userAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, this,"");

            final UserService userService = UserService.getInstance(this);
            final User user = userService.getActiveAccountInfo();
            userAPI.login(user.getMobile(),userService.getPwd(user.getId()),2,new Callback<UserBean>(){

                @Override
                public void success(UserBean userBean, Response response) {
                    if(null != userBean && null != userBean.getObj() && !TextUtils.isEmpty(userBean.getObj().getTokenId())){
                        userService.setTokenId(user.getId(),userBean.getObj().getTokenId());
                        mainAPI1.getByHx(toChatUsername, ChatActivity.this);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("error",error.getMessage());
                }
            });

        }
    }
}
