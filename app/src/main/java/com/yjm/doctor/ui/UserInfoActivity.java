package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserInfoActivity extends BaseActivity implements ListLayoutAdapter.OnListItemOnClickListener,Callback<UserBean>{

    private static final String TAG = "UserInfoActivity";

//    @BindView(R.id.img_operation)
//    ImageView mImgOperation;
//    @BindView(R.id.toolicon)
//    RelativeLayout mToolicon;
//    @BindView(R.id.tooltitle)
//    TextView mTooltitle;
//    @BindView(R.id.toolfinish)
//    TextView mToolfinish;
//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;
    @BindView(R.id.listview_layout)
    ListView mListviewLayout;

    private List<ListLayoutModel> modelList=null;
    private ListLayoutAdapter mLayoutAdapter;
    private UserAPI mUserAPI=null;
    private User mUser;
    private String tokenID;

    @Override
    public int initView() { return R.layout.activity_user_info; }

    @OnClick(R.id.img_operation)
    public void onViewClicked() {
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
//        setSupportActionBar(mToolbar);
//        mTooltitle.setText(R.string.user_info);
//        mToolicon.setVisibility(View.GONE);
//        mToolfinish.setVisibility(View.GONE);

        modelList=new ArrayList<ListLayoutModel>();
        modelList.add(new ListLayoutModel(R.string.user_logo," "));
        modelList.add(new ListLayoutModel(R.string.user_name," ",R.drawable.comein));
        modelList.add(new ListLayoutModel(R.string.user_sex," ",R.drawable.comein));
        modelList.add(new ListLayoutModel(R.string.user_phone_number," ",0));
        modelList.add(new ListLayoutModel(R.string.user_qr_code,R.drawable.comein));
        modelList.add(new ListLayoutModel(R.string.user_email," ",R.drawable.comein));
        modelList.add(new ListLayoutModel(R.string.user_hospital_name," ",R.drawable.comein));
        modelList.add(new ListLayoutModel(R.string.user_department_name," ",R.drawable.comein));
        modelList.add(new ListLayoutModel(R.string.user_level_name," ",R.drawable.comein));
        modelList.add(new ListLayoutModel(R.string.user_speciality," ",R.drawable.comein));

        mLayoutAdapter=new ListLayoutAdapter(this,modelList);
        mListviewLayout.setAdapter(mLayoutAdapter);

        mLayoutAdapter.setOnListItemOnClickListener(this);

        showDialog("加载中");

        mUser = UserService.getInstance(this).getActiveAccountInfo();
        tokenID = UserService.getInstance(this).getTokenId(mUser.getId());

        mUserAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, this);
        if (NetworkUtils.isNetworkAvaliable(this)) {
            mUserAPI.getUserInfoByTokenId(tokenID, this);
        } else {
            SystemTools.show_msg(this, R.string.toast_msg_no_network);
        }


    }


    @Override
    public void success(UserBean userBean, Response response) {
        Log.i(TAG, "success: "+userBean.toString());


//        if (userBean != null){
            modelList.set(0,new ListLayoutModel(R.string.user_logo,userBean.getObj().getPic()));
            modelList.set(1,new ListLayoutModel(R.string.user_name,userBean.getObj().getCustomer().getRealName(),R.drawable.comein));
            if (userBean.getObj().getCustomer().getSex() == 1 ) {
                modelList.set(2, new ListLayoutModel(R.string.user_sex, "男", R.drawable.comein));
            }else if (userBean.getObj().getCustomer().getSex() == 2 ) {
                modelList.set(2, new ListLayoutModel(R.string.user_sex, "女", R.drawable.comein));
            }
            modelList.set(3,new ListLayoutModel(R.string.user_phone_number,userBean.getObj().getMobile(),0));
            modelList.set(5,new ListLayoutModel(R.string.user_email,userBean.getObj().getEmail(),R.drawable.comein));
            modelList.set(6,new ListLayoutModel(R.string.user_hospital_name,userBean.getObj().getMemberDoctor().getHospitalName(),R.drawable.comein));
            modelList.set(7,new ListLayoutModel(R.string.user_department_name,userBean.getObj().getMemberDoctor().getDepartmentName(),R.drawable.comein));
            modelList.set(8,new ListLayoutModel(R.string.user_level_name,userBean.getObj().getMemberDoctor().getLevelName(),R.drawable.comein));
            modelList.set(9,new ListLayoutModel(R.string.user_speciality,userBean.getObj().getMemberDoctor().getSpeciality(),R.drawable.comein));
            mLayoutAdapter.notifyDataSetChanged();
            closeDialog();
//        }

    }

    @Override
    public void failure(RetrofitError error) {
        closeDialog();
    }

    @Override
    public void finishButton() {

    }

    @Override
    public void OnItemClick(int position, ListLayoutModel model) {

    }
}
