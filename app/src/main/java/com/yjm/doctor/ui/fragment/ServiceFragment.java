package com.yjm.doctor.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
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
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.base.BaseFragment;
import com.yjm.doctor.util.NetworkUtils;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SharedPreferencesUtil;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import java.io.IOException;

import butterknife.BindView;
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

    private SharedPreferencesUtil sharedPreferencesUtil;
    private User mUser=null;
    private UserAPI mUserAPI;
    private String tokenID;


    @Override
    protected int getLayoutRes() { return R.layout.fragment_service; }

    @Override
    public void onResume() {
        super.onResume();
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
                mUserAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, getContext());
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
            if(null != getActivity()) {
                if (null != userBean && !TextUtils.isEmpty(userBean.getMsg()) && userBean.getMsg().contains("token")) {

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

    }

    private void updateUI(User user){
        if (!TextUtils.isEmpty(user.getPicUrl())) {
            userLogo.setImageURI(Uri.parse(user.getPicUrl()));
        }
        if (null != user.getCustomer() && !TextUtils.isEmpty(user.getCustomer().getRealName())) {
            mUserName.setText(user.getCustomer().getRealName());
        }
        if (null != user.getMemberDoctor() && (!TextUtils.isEmpty(user.getMemberDoctor().getLevelName()) || !TextUtils.isEmpty(user.getMemberDoctor().getEducationName()))) {
            String info=user.getMemberDoctor().getLevelName()+"\t\t"+user.getMemberDoctor().getEducationName();
            mPositional.setText(info);
        }
        if (null != user.getMemberDoctor() && (!TextUtils.isEmpty(user.getMemberDoctor().getHospitalName()) || !TextUtils.isEmpty(user.getMemberDoctor().getDepartmentName()))) {
            String info=user.getMemberDoctor().getHospitalName()+"\t\t"+user.getMemberDoctor().getDepartmentName();
            mHospital.setText(info);
        }
    }
}
