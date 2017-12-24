package com.yjm.doctor.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.yjm.doctor.R;
import com.yjm.doctor.model.User;
import com.yjm.doctor.ui.base.BaseActivity;
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

        User u = UserService.getInstance().getActiveAccountInfo();
        if(null != u && null != mTvBalance) {
            mTvBalance.setText(u.getAmount()+"");
        }



    }

    @Override
    public void finishButton() {

    }

}
