package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.model.BusinessSettingBean;
import com.yjm.doctor.model.Message;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.view.layout.ListLayoutAdapter;
import com.yjm.doctor.ui.view.layout.ListLayoutModel;
import com.yjm.doctor.util.NetworkUtils;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BusinessSettingActivity extends BaseActivity {

    private static final String TAG = "BusinessSettingActivity";

//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;
//
//    @BindView(R.id.img_operation)
//    ImageView mImgOperation;
//
//    @BindView(R.id.toolicon)
//    RelativeLayout mToolicon;
//
//    @BindView(R.id.tooltitle)
//    TextView mTooltitle;
//
//    @BindView(R.id.toolfinish)
//    TextView mToolfinish;



    @BindView(R.id.listview_layout)
    ListView mListviewLayout;

    private List<ListLayoutModel> modelList=null;
    private ListLayoutAdapter mLayoutAdapter;

    private User mUser=null;
    private UserAPI mUserAPI=null;
    private String tokenID=null;
    private BusinessSettingBean mSettingBean;

    @Override
    public int initView() { return R.layout.activity_business_setting; }

    //返回上一级界面
    @OnClick(R.id.img_operation)
    public void onViewClicked() {
        this.finish();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = UserService.getInstance(this).getActiveAccountInfo();


//        setSupportActionBar(mToolbar);
//        mTooltitle.setText(R.string.account);
//        mToolicon.setVisibility(View.GONE);
//        mToolfinish.setVisibility(View.GONE);

        modelList=new ArrayList<ListLayoutModel>();
        modelList.add(new ListLayoutModel(R.string.business_accept_appointments,"",0,R.drawable.receiveicon_sel));
        modelList.add(new ListLayoutModel(R.string.business_appointments_fee,"￥0.00",R.color.colorAccent,0));
        modelList.add(new ListLayoutModel(R.string.business_accept_image_text_consulting,"",0,R.drawable.receiveicon_sel));
        modelList.add(new ListLayoutModel(R.string.business_image_text_consulting_fee,"￥0.00",R.color.colorAccent,0));
        mLayoutAdapter=new ListLayoutAdapter(this,modelList);
        mListviewLayout.setAdapter(mLayoutAdapter);

        showDialog("加载中");

        mUserAPI= RestAdapterUtils.getRestAPI(Config.USER_BUSINESSSETTING,UserAPI.class,this,"");
        tokenID=UserService.getInstance(this).getTokenId(mUser.getId());
        getSettingInfo();

        mLayoutAdapter.setOnListItemOnClickListener(new ListLayoutAdapter.OnListItemOnClickListener() {
            @Override
            public void OnItemClick(int position, ListLayoutModel model) {
                switch (position) {
                    case 0: {
                        if(null != mSettingBean && null != mSettingBean.getObj()) {
                            updateInfo(!mSettingBean.getObj().isAcceptAppointment(), mSettingBean.getObj().isAcceptConsultation());
                        }
                        break;
                    }
                    case 2: {
                        if(null != mSettingBean && null != mSettingBean.getObj()) {
                            updateInfo(mSettingBean.getObj().isAcceptAppointment(), !mSettingBean.getObj().isAcceptConsultation());
                        }
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getSettingInfo(){
        if(tokenID != null && NetworkUtils.isNetworkAvaliable(this)) {
            mUserAPI.getBusinessSetting(tokenID, new Callback<BusinessSettingBean>() {
                @Override
                public void success(BusinessSettingBean bean, Response response) {
                    closeDialog();
                    if (bean != null) {
                        mSettingBean=bean;
                        if (null != bean.getObj() && bean.getObj().isAcceptAppointment()){
                            modelList.set(0,new ListLayoutModel(R.string.business_accept_appointments,"",0,R.drawable.receiveicon_sel));
                        }else {
                            modelList.set(0,new ListLayoutModel(R.string.business_accept_appointments,"",0,R.drawable.receiveicon));
                        }
                        if (null != bean.getObj() && !TextUtils.isEmpty(bean.getObj().getAppointmentFee())) {
                            modelList.set(1, new ListLayoutModel(R.string.business_appointments_fee, "￥" + bean.getObj().getAppointmentFee(), R.color.colorAccent, 0));
                        }
                        if (null != bean.getObj() && bean.getObj().isAcceptConsultation()) {
                            modelList.set(2, new ListLayoutModel(R.string.business_accept_image_text_consulting, "", 0, R.drawable.receiveicon_sel));
                        }else {
                            modelList.set(2, new ListLayoutModel(R.string.business_accept_image_text_consulting, "", 0, R.drawable.receiveicon));
                        }

                        if (null != bean.getObj() && !TextUtils.isEmpty(bean.getObj().getConsultationFee())) {
                            modelList.set(3, new ListLayoutModel(R.string.business_image_text_consulting_fee, "￥" + bean.getObj().getConsultationFee(), R.color.colorAccent, 0));
                        }
                        Log.i(TAG, "获取成功: " + bean.toString());
                    } else {
                        Log.i(TAG, "为null: ");
                    }
                    mLayoutAdapter.notifyDataSetChanged();

                }

                @Override
                public void failure(RetrofitError error) {
                    closeDialog();
                    Log.i(TAG, "获取失败: "+error.getUrl()+"\n"+error.getMessage()+"\n"+error.getResponse());
                    if(null != error && error.getMessage().contains("path $.obj")){
                        final UserAPI userAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, BusinessSettingActivity.this);
                        final UserService userService = UserService.getInstance(BusinessSettingActivity.this);
                        final User user = userService.getActiveAccountInfo();
                        userAPI.login(user.getMobile(),userService.getPwd(user.getId()),2,new Callback<UserBean>(){

                            @Override
                            public void success(UserBean userBean, Response response) {
                                if(null != userBean && null != userBean.getObj() && !TextUtils.isEmpty(userBean.getObj().getTokenId())){
                                    userService.setTokenId(user.getId(),userBean.getObj().getTokenId());
                                    getSettingInfo();
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });

                    }
                }

            });
        }else {
            closeDialog();
            SystemTools.show_msg(this, R.string.toast_msg_no_network);
        }
    }

    private void updateInfo(boolean acceptAppointment,boolean acceptConsultation){
        if(tokenID != null && NetworkUtils.isNetworkAvaliable(this)) {
            mUserAPI.updateBusinessSetting(tokenID, acceptAppointment, acceptConsultation, new Callback<Message>() {
                @Override
                public void success(Message message, Response response) {
                    if (message != null){
                        if (message.getSuccess()){
                            getSettingInfo();
                            Log.i(TAG, "success: "+message.getMsg().toLowerCase());
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    SystemTools.show_msg(BusinessSettingActivity.this,"设置失败");
                }
            });
        }else {
            SystemTools.show_msg(this, R.string.toast_msg_no_network);
        }
    }




    @Override
    public void finishButton() {

    }
}
