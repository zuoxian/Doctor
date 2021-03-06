package com.yjm.doctor.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.model.EventType;
import com.yjm.doctor.model.SMessage;
import com.yjm.doctor.model.SMessageBean;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.GridActivity;
import com.yjm.doctor.ui.MessageActivity;
import com.yjm.doctor.ui.UserInfoActivity;
import com.yjm.doctor.ui.base.BaseFragment;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.NetworkUtils;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SharedPreferencesUtil;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/10.
 */

public class ServiceFragment extends BaseFragment<UserBean> {

    private User user;

    @BindView(R.id.user_icon)
    SimpleDraweeView userLogo;

    @BindView(R.id.username)
    TextView mUserName;


    @BindView(R.id.positional)
    TextView mPositional;

    @BindView(R.id.hospital)
    TextView mHospital;

    @BindView(R.id.tooltitle)
    TextView mTooltitle;

    @BindView(R.id.toolicon)
    RelativeLayout mToolicon;

    @BindView(R.id.toolfinish)
    TextView mToolFinish;

    @BindView(R.id.bar_num1)
    TextView barNum1;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private User mUser=null;
    private UserAPI mUserAPI;
    private String tokenID;

    private UserAPI userAPI;


    @OnClick(R.id.service_message)
    void mail(){
        if(null != getActivity()){
            ActivityJumper.getInstance().buttonJumpTo(getActivity(), MessageActivity.class);
        }
    }

    @OnClick(R.id.item)
    void OnClickItem(){
        if(null != getActivity())
            ActivityJumper.getInstance().buttonJumpTo(getActivity(), UserInfoActivity.class);
    }

    @OnClick(R.id.service_close)
    void serviceClose(){
        if(null != getActivity())
            ActivityJumper.getInstance().buttonJumpTo(getActivity(), GridActivity.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayoutRes() {

        return R.layout.fragment_service; }

    int readnum = 0;
    @Override
    public void onResume() {
        super.onResume();
        if(null != getActivity()) {
            readnum = 0;
            userAPI = RestAdapterUtils.getRestAPI(Config.MESSAGE, UserAPI.class, getActivity());
            userAPI.getMessage(1, 50, new Callback<SMessageBean>() {
                @Override
                public void success(SMessageBean sMessageBean, Response response) {
                    if (null != sMessageBean && sMessageBean.getSuccess()) {
                        if(null == sMessageBean.getObj())return ;
                        List<SMessage> list = sMessageBean.getObj().getRows();

                        for(SMessage sMessage : list){
                            if(!sMessage.isRead()){
                                readnum = readnum + 1;
                            }
                        }
                        if(0 != readnum){
                            if(null != barNum1){
                                barNum1.setText(String.valueOf(readnum));
                                barNum1.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
        if(null != mTooltitle)mTooltitle.setText("服务");
        if(null != mToolicon)mToolicon.setVisibility(View.GONE);
        if(null != mToolFinish)mToolFinish.setVisibility(View.GONE);

    }


    @Override
    protected void onLoadData() {
        try {
            sharedPreferencesUtil = SharedPreferencesUtil.instance(getContext());
            String u = sharedPreferencesUtil.getObject("user");
            if(null != u) {
                mUser = (User) sharedPreferencesUtil.deSerialization(sharedPreferencesUtil.getObject("user"));
                if(0 < mUser.getId()) {
                    tokenID = UserService.getInstance(getContext()).getTokenId(mUser.getId());
                }
            }


            if (null != mUser && null != mUser.getCustomer() && mUser.getCustomer().getUserId() != 0) {
                updateUI(mUser);
            }else {
                mUserAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, getContext(),"");
                if (NetworkUtils.isNetworkAvaliable(getContext())) {
                    mUserAPI.getUserInfoByTokenId(tokenID, this);
                } else {
                    SystemTools.show_msg(getContext(), R.string.toast_msg_no_network);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void success(UserBean userBean, Response response) {
        try {

            if (null != userBean && null != userBean.getObj()) {
                String u = sharedPreferencesUtil.serialize(userBean.getObj());
                if(!TextUtils.isEmpty(u)) {
                    sharedPreferencesUtil.saveObject("user", u);
                }
                updateUI(userBean.getObj());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void failure(RetrofitError error) {
        if(null != getActivity()) {
            if(null != error && error.getMessage().contains("path $.obj")){

                final UserService userService = UserService.getInstance(getActivity());
                final User user = userService.getActiveAccountInfo();
                mUserAPI.login(user.getMobile(), userService.getPwd(user.getId()), 2, new Callback<UserBean>() {

                    @Override
                    public void success(UserBean userBean, Response response) {
                        if (null != userBean && null != userBean.getObj() && !TextUtils.isEmpty(userBean.getObj().getTokenId())) {
                            userService.setTokenId(user.getId(), userBean.getObj().getTokenId());
                            mUserAPI.getUserInfoByTokenId(tokenID, this);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }
        }
    }

    private void updateUI(User user){
        if (null != userLogo && !TextUtils.isEmpty(user.getPicUrl())) {
            userLogo.setImageURI(Uri.parse(user.getPicUrl()));
        }
        if (null != mUserName && null != user.getCustomer() && !TextUtils.isEmpty(user.getCustomer().getRealName())) {
            mUserName.setText(TextUtils.isEmpty(user.getCustomer().getRealName())?"":user.getCustomer().getRealName());
        }
        if (null != mPositional && null != user.getMemberDoctor()) {
            String info=(TextUtils.isEmpty(user.getMemberDoctor().getLevelName())?"":user.getMemberDoctor().getLevelName())+"\t\t"+(TextUtils.isEmpty(user.getMemberDoctor().getEducationName())?"":user.getMemberDoctor().getEducationName());
            mPositional.setText(info);
        }
        if (null != mHospital && null != user.getMemberDoctor()) {
            String info=(TextUtils.isEmpty(user.getMemberDoctor().getHospitalName())?"":user.getMemberDoctor().getHospitalName())+"\t\t"+(TextUtils.isEmpty(user.getMemberDoctor().getDepartmentName())?"":user.getMemberDoctor().getDepartmentName());
            mHospital.setText(info);
        }
    }

    public void onEventMainThread(EventType type){

        if(Config.PUSH_TYPE.equals(type.getType()) && !TextUtils.isEmpty(type.getObject()+"")){
            Log.e("error===1",readnum+"");
                readnum = readnum + 1;
            Log.e("error===2",readnum+"");
                if(null != barNum1){
                    barNum1.setText(String.valueOf(readnum));
                    barNum1.setVisibility(View.VISIBLE);
                }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
