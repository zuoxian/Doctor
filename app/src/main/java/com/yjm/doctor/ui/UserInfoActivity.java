package com.yjm.doctor.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.view.layout.ListLayoutAdapter;
import com.yjm.doctor.ui.view.layout.ListLayoutModel;
import com.yjm.doctor.ui.view.layout.UserInfoListLayoutAdapter;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.Helper;
import com.yjm.doctor.util.NetworkUtils;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SharedPreferencesUtil;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserInfoActivity extends BaseActivity implements UserInfoListLayoutAdapter.OnListItemOnClickListener,Callback<UserBean>{

    private static final String TAG = "UserInfoActivity";

    @BindView(R.id.listview_layout)
    ListView mListviewLayout;



    private List<ListLayoutModel> modelList=null;
    private UserInfoListLayoutAdapter mLayoutAdapter;
    private UserAPI mUserAPI=null;
    private User mUser;
    private String tokenID;
    private SharedPreferencesUtil sharedPreferencesUtil;




    @Override
    public int initView() {
        YjmApplication.update = true;
        return R.layout.activity_user_info; }

    @OnClick(R.id.img_operation)
    public void onViewClicked() {
        this.finish();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelList=new ArrayList<ListLayoutModel>();
        modelList.add(new ListLayoutModel(R.string.user_logo," "));
        modelList.add(new ListLayoutModel(R.string.user_name," ",0));
        modelList.add(new ListLayoutModel(R.string.bir_date," ",0));
        modelList.add(new ListLayoutModel(R.string.user_sex," ",0));
        modelList.add(new ListLayoutModel(R.string.user_phone_number," ",0));

        modelList.add(new ListLayoutModel(R.string.user_email," ",0));
        modelList.add(new ListLayoutModel(R.string.user_hospital_name," ",0));
        modelList.add(new ListLayoutModel(R.string.user_department_name," ",0));
        modelList.add(new ListLayoutModel(R.string.user_level_name," ",0));
        modelList.add(new ListLayoutModel(R.string.user_speciality," ",0));
        modelList.add(new ListLayoutModel(R.string.user_des," ",0));
        modelList.add(new ListLayoutModel(R.string.user_pwd," ",0));
        modelList.add(new ListLayoutModel(0,"0",0));


        mLayoutAdapter=new UserInfoListLayoutAdapter(this,modelList);
        mListviewLayout.setAdapter(mLayoutAdapter);

        mLayoutAdapter.setOnListItemOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();



        showDialog("加载中");

        try {
            sharedPreferencesUtil = SharedPreferencesUtil.instance(this);
            String obj = sharedPreferencesUtil.getObject("user");
            if(null != obj)
                mUser = (User) sharedPreferencesUtil.deSerialization(obj);
            if(null != mUser) {
                tokenID = UserService.getInstance(this).getTokenId(mUser.getId());
            }

            if (null != mUser && null != mUser.getCustomer() && mUser.getCustomer().getUserId() != 0) {
                UpdateUI(mUser);
            }else {
                mUserAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, this,"");
                if (NetworkUtils.isNetworkAvaliable(this)) {
                    mUserAPI.getUserInfoByTokenId(tokenID, this);
                } else {
                    SystemTools.show_msg(this, R.string.toast_msg_no_network);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    private User userObject;

    @Override
    public void success(UserBean userBean, Response response) {

        if (userBean != null && null != userBean.getObj()){
            try {

                sharedPreferencesUtil.saveObject("user", sharedPreferencesUtil.serialize(userBean.getObj()));

                UpdateUI(userBean.getObj());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void UpdateUI(User user){
        userObject = user;
        modelList.set(0,new ListLayoutModel(R.string.user_logo,user.getPicUrl()));
        if(null != user && null !=user.getCustomer() )
            modelList.set(1,new ListLayoutModel(R.string.user_name,user.getCustomer().getRealName(),0));
        if(null != user && null != user.getCustomer())
            modelList.set(2,new ListLayoutModel(R.string.bir_date,user.getCustomer().getBirthdayStr(),0));
        if (null != user.getCustomer() && user.getCustomer().getSex() == 1 ) {
            modelList.set(3, new ListLayoutModel(R.string.user_sex, "男", 0));
        }else if (null != user.getCustomer() && user.getCustomer().getSex() == 2 ) {
            modelList.set(3, new ListLayoutModel(R.string.user_sex, "女", 0));
        }
        modelList.set(4,new ListLayoutModel(R.string.user_phone_number,user.getMobile(),0));
        modelList.set(5,new ListLayoutModel(R.string.user_email,user.getEmail(),0));
        if(null != user.getMemberDoctor()) {
            modelList.set(6, new ListLayoutModel(R.string.user_hospital_name, TextUtils.isEmpty(user.getMemberDoctor().getHospitalName())?"":user.getMemberDoctor().getHospitalName(), 0));

            modelList.set(7, new ListLayoutModel(R.string.user_department_name, TextUtils.isEmpty(user.getMemberDoctor().getDepartmentName())?"":user.getMemberDoctor().getDepartmentName(), 0));
            modelList.set(8, new ListLayoutModel(R.string.user_level_name, TextUtils.isEmpty(user.getMemberDoctor().getLevelName())?"无职称":user.getMemberDoctor().getLevelName(), 0));
//            Log.e("speciality",user.getMemberDoctor().getSpeciality());
            String spec = user.getMemberDoctor().getSpeciality();

            if(!TextUtils.isEmpty(spec)){
                if(spec.length()>14){
                    spec = spec.substring(0,14)+"...";
                }
            }
            String des = user.getMemberDoctor().getIntroduce();

            if(!TextUtils.isEmpty(des)){
                if(des.length()>14){
                    des = des.substring(0,14)+"....";
                }
            }

            modelList.set(9, new ListLayoutModel(R.string.user_speciality, spec, 0));
            modelList.set(10, new ListLayoutModel(R.string.user_des, des, 0));
        }
        modelList.set(11, new ListLayoutModel(R.string.user_pwd, "", R.drawable.comein));
        modelList.set(12,new ListLayoutModel(0,"0",0));
        mLayoutAdapter.notifyDataSetChanged();
        closeDialog();
    }

    @Override
    public void failure(RetrofitError error) {
        closeDialog();
        if(null != error && error.getMessage().contains("path $.obj")){

            final UserService userService = UserService.getInstance(this);
            final User user = userService.getActiveAccountInfo();
            mUserAPI.login(user.getMobile(),userService.getPwd(user.getId()),2,new Callback<UserBean>(){

                @Override
                public void success(UserBean userBean, Response response) {
                    if(null != userBean && null != userBean.getObj() && !TextUtils.isEmpty(userBean.getObj().getTokenId())){
                        userService.setTokenId(user.getId(),userBean.getObj().getTokenId());
                        mUserAPI.getUserInfoByTokenId(userBean.getObj().getTokenId(), this);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        }
    }

    @Override
    public void finishButton() {

        if(null == userObject) return;

        if(1 == userObject.getStatus()){
            ActivityJumper.getInstance().buttonObjectJumpTo(this,UpateUserActivity.class,userObject);
        }else{
            SystemTools.show_msg(this,"您的资料正在审核中，请等待审核结果");
        }

    }

    @Override
    public void OnItemClick(int position, ListLayoutModel model) {
        if(model!= null && model.getTitle()>0 && R.string.user_pwd == model.getTitle()){
            ActivityJumper.getInstance().buttonJumpTo(this,PasswordActivity.class);
        }
    }

    @Override
    public void exitButton() {
        UserService.getInstance(this).logout();
        Helper.getInstance().logout(false,null);
        sharedPreferencesUtil.del("user");
        ActivityJumper.getInstance().buttonIntJumpTo(this,LoginActivity.class, 1);
    }


}
