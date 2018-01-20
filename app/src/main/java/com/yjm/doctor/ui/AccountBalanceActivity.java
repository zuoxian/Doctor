package com.yjm.doctor.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.yjm.doctor.R;
import com.yjm.doctor.model.User;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.util.SharedPreferencesUtil;
import com.yjm.doctor.util.auth.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountBalanceActivity extends BaseActivity {


    @BindView(R.id.tv_balance)
    TextView mTvBalance;

    @Override
    public int initView() { return R.layout.activity_account_balance; }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferencesUtil sharedPreferencesUtil = SharedPreferencesUtil.instance(this);
        User u = null;
        try {
            u = (User) sharedPreferencesUtil.deSerialization(sharedPreferencesUtil.getObject("user"));
//        Log.d("serial", "share2   ="+mUser.toString());

        }catch (Exception e){
            Log.e("error",e.getMessage());
        }
        if(null != u && null != mTvBalance && null != u.getCustomer()) {
            mTvBalance.setText(u.getCustomer().getBalance()+"");
        }else{
            mTvBalance.setText("0");
        }



    }

    @Override
    public void finishButton() {

    }

}
