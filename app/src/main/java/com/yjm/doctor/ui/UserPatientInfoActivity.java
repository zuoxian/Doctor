package com.yjm.doctor.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.model.Customer;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.model.UserPatientInfo;
import com.yjm.doctor.model.UserPatientInfoBean;
import com.yjm.doctor.model.UserPatientInfos;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.view.layout.UserPatientInfoAdapter;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.auth.UserService;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/22.
 */

public class UserPatientInfoActivity extends BaseActivity implements Callback<UserPatientInfoBean> ,UserPatientInfoAdapter.OnListItemOnClickListener{


    @BindView(R.id.username)
    TextView mUserName;

    @BindView(R.id.user_info)
    TextView mUserInfo;

    @BindView(R.id.user_icon)
    SimpleDraweeView mUsericon;

    @BindView(R.id.list_view)
    ListView listView;

    private User user;

    UserPatientInfoAdapter mAdapter;

    private UserAPI userAPI;

    private List<UserPatientInfo> userPatientInfos;

    @Override
    public int initView() {
        user = (User)getIntent().getSerializableExtra("object");
        userAPI = RestAdapterUtils.getRestAPI(Config.USER_BUSINESSSETTING,UserAPI.class,this);
        return R.layout.activity_user_patient_info;
    }

    @Override
    public void finishButton() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(user!= null) {
            if (null != mUsericon && !TextUtils.isEmpty(user.getPicUrl()))
                mUsericon.setImageURI(Uri.parse(user.getPicUrl()));
            Customer customer = user.getCustomer();
            if(null != mUserName && null != customer) {
                if (!TextUtils.isEmpty(customer.getRealName())) {
                    mUserName.setText(customer.getRealName());
                }
            }
            String sex =  "女";
            if(null != customer){
                if(1==customer.getSex()){
                    sex =  "男";
                }
                mUserInfo.setText("性别："+sex+"  年龄："+customer.getAge()+"岁  电话："+customer.getPhone() );
            }
            if(0 < user.getId()) {
                userAPI.getUserPatientInfo(user.getId(),this);
            }
        }


        mAdapter = new UserPatientInfoAdapter(this);
        if(null != listView){
            listView.setAdapter(mAdapter);
        }
        mAdapter.setOnListItemOnClickListener(this);


    }

    @OnClick(R.id.customerinfo)
    void OnclickInfo(){
        //跳到聊天框
    }


    @Override
    public void success(UserPatientInfoBean userPatientInfoBean, Response response) {


        if(null != userPatientInfoBean && true == userPatientInfoBean.getSuccess()){
            if(null != userPatientInfoBean.getObj() && null != userPatientInfoBean.getObj().getAppointments()){
                userPatientInfos = userPatientInfoBean.getObj().getAppointments();
                mAdapter.setData(userPatientInfos);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void failure(RetrofitError error) {

    }

    @Override
    public void OnItemClick(int position, UserPatientInfo model) {
        if(null != model || null != user )
        ActivityJumper.getInstance().buttonObjectJumpTo(this, UserAppointmentsInfoActivity.class,model,user);
    }
}
