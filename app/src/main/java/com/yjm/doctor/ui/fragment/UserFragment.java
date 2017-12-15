package com.yjm.doctor.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.BusinessSettingActivity;
import com.yjm.doctor.ui.MainAccountActivity;
import com.yjm.doctor.ui.UserInfoActivity;
import com.yjm.doctor.ui.base.BaseFragment;
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
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/10.
 */

public class UserFragment extends BaseFragment<UserBean> {
    private static final String TAG = "UserFragment";

    @BindView(R.id.user_icon)
    SimpleDraweeView mUserIcon;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.positional_hospital)
    TextView mPositionalHospital;

    @BindView(R.id.listview_layout)
    ListView mListviewLayout;

    private ListLayoutAdapter mLayoutAdapter;
    private UserAPI mUserAPI;
    private User mUser;
    private String tokenID;

    private SharedPreferencesUtil sharedPreferencesUtil;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user;
    }


    @Override
    protected void onLoadData() {

        // TODO: 2017/12/14  这里的图片显示有点错位，图片底部切图空白多大，故增加了 marginTop="5dp"
        List<ListLayoutModel> modelList = new ArrayList<ListLayoutModel>();
        modelList.add(new ListLayoutModel(R.drawable.business_setting, R.string.business_setting, R.drawable.comein));
        modelList.add(new ListLayoutModel(R.drawable.patient, R.string.patient, R.drawable.comein));
        modelList.add(new ListLayoutModel(R.drawable.account, R.string.account, R.drawable.comein));
        modelList.add(new ListLayoutModel(R.drawable.evaluate, R.string.evaluate, R.drawable.comein));
        mLayoutAdapter = new ListLayoutAdapter(getContext(), modelList);
        mListviewLayout.setAdapter(mLayoutAdapter);
        mLayoutAdapter.setOnListItemOnClickListener(new ListLayoutAdapter.OnListItemOnClickListener() {
            @Override
            public void OnItemClick(int position, ListLayoutModel model) {
                switch (position) {
                    case 0: {
                        startActivity(new Intent(getActivity(), BusinessSettingActivity.class));
                        break;
                    }
                    case 1: {

                        break;
                    }
                    case 2: {
                        startActivity(new Intent(getActivity(), MainAccountActivity.class));
                        break;
                    }
                    case 3: {

                        break;
                    }
                }
            }
        });

        try {
            sharedPreferencesUtil = SharedPreferencesUtil.instance(getContext());
            mUser = (User) sharedPreferencesUtil.deSerialization(sharedPreferencesUtil.getObject("user"));
            tokenID = UserService.getInstance(getContext()).getTokenId(mUser.getId());

            if (mUser.getCustomer().getUserId() != 0) {
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
        if (userBean != null) {
            try {
                sharedPreferencesUtil.saveObject("user", sharedPreferencesUtil.serialize(userBean.getObj()));
                updateUI(userBean.getObj());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void failure(RetrofitError error) {

    }

    private void updateUI(User user){
        if (!TextUtils.isEmpty(user.getPicUrl())) {
                mUserIcon.setImageURI(Uri.parse(user.getPicUrl()));
        }
        if (!TextUtils.isEmpty(user.getCustomer().getRealName())) {
            mUsername.setText(user.getCustomer().getRealName());
        }
        if (!TextUtils.isEmpty(user.getMemberDoctor().getHospitalName())) {
            String info = user.getMemberDoctor().getLevelName() + "\t\t" + user.getMemberDoctor().getHospitalName();
            mPositionalHospital.setText(info);
        }
    }



    @OnClick(R.id.user_icon)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), UserInfoActivity.class));
    }
}
