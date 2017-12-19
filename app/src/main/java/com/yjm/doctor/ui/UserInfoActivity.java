package com.yjm.doctor.ui;

import android.text.TextUtils;
import android.widget.ListView;

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

public class UserInfoActivity extends BaseActivity implements ListLayoutAdapter.OnListItemOnClickListener,Callback<UserBean>{

    private static final String TAG = "UserInfoActivity";

    @BindView(R.id.listview_layout)
    ListView mListviewLayout;

    private List<ListLayoutModel> modelList=null;
    private ListLayoutAdapter mLayoutAdapter;
    private UserAPI mUserAPI=null;
    private User mUser;
    private String tokenID;
    private SharedPreferencesUtil sharedPreferencesUtil;


    @Override
    public int initView() { return R.layout.activity_user_info; }

    @OnClick(R.id.img_operation)
    public void onViewClicked() {
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

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

        try {
            sharedPreferencesUtil = SharedPreferencesUtil.instance(this);
            mUser = (User) sharedPreferencesUtil.deSerialization(sharedPreferencesUtil.getObject("user"));
            tokenID = UserService.getInstance(this).getTokenId(mUser.getId());

            if (mUser.getCustomer().getUserId() != 0) {
                UpdateUI(mUser);
            }else {
                mUserAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, this);
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


    @Override
    public void success(UserBean userBean, Response response) {
        if(null != userBean && !TextUtils.isEmpty(userBean.getMsg()) && userBean.getMsg().contains("token")){

            final UserService userService = UserService.getInstance(this);
            final User user = userService.getActiveAccountInfo();
            mUserAPI.login(user.getMobile(),userService.getPwd(user.getId()),2,new Callback<UserBean>(){

                @Override
                public void success(UserBean userBean, Response response) {
                    if(null != userBean && null != userBean.getObj() && !TextUtils.isEmpty(userBean.getObj().getTokenId())){
                        userService.setTokenId(user.getId(),userBean.getObj().getTokenId());
                        mUserAPI.getUserInfoByTokenId(tokenID, this);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        }
        if (userBean != null){
            try {
                sharedPreferencesUtil.saveObject("user", sharedPreferencesUtil.serialize(userBean.getObj()));
                UpdateUI(userBean.getObj());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void UpdateUI(User user){
        modelList.set(0,new ListLayoutModel(R.string.user_logo,user.getPicUrl()));
        modelList.set(1,new ListLayoutModel(R.string.user_name,user.getCustomer().getRealName(),R.drawable.comein));
        if (user.getCustomer().getSex() == 1 ) {
            modelList.set(2, new ListLayoutModel(R.string.user_sex, "男", R.drawable.comein));
        }else if (user.getCustomer().getSex() == 2 ) {
            modelList.set(2, new ListLayoutModel(R.string.user_sex, "女", R.drawable.comein));
        }
        modelList.set(3,new ListLayoutModel(R.string.user_phone_number,user.getMobile(),0));
        modelList.set(5,new ListLayoutModel(R.string.user_email,user.getEmail(),R.drawable.comein));
        modelList.set(6,new ListLayoutModel(R.string.user_hospital_name,user.getMemberDoctor().getHospitalName(),R.drawable.comein));
        modelList.set(7,new ListLayoutModel(R.string.user_department_name,user.getMemberDoctor().getDepartmentName(),R.drawable.comein));
        modelList.set(8,new ListLayoutModel(R.string.user_level_name,user.getMemberDoctor().getLevelName(),R.drawable.comein));
        modelList.set(9,new ListLayoutModel(R.string.user_speciality,user.getMemberDoctor().getSpeciality(),R.drawable.comein));
        mLayoutAdapter.notifyDataSetChanged();
        closeDialog();
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
